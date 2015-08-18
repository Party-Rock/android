package com.example.gerardogtn.partyrock.ui.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.service.VenueEvent;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    @Bind(R.id.btn_rent)
    Button mRentButton;

    @Bind(R.id.img_venue_main)
    ImageView mMainVenueImage;

    @Bind(R.id.txt_address)
    TextView mAddressText;

    @Bind(R.id.txt_price)
    TextView mPriceText;

    @Bind(R.id.txt_capacity)
    TextView mCapacityText;

    @Bind(R.id.txt_venue_description)
    TextView mVenueText;

    @Bind(R.id.txt_date_picker)
    TextView mDatePickerText;

    private DatePicker mDatePicker;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mSimpleDateFormat;
    private Date mDateSelected;
    private Venue mVenue;

    public ConfirmationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        ButterKnife.bind(this, view);
        mVenue = EventBus.getDefault().removeStickyEvent(Venue.class);
        if (mVenue == null) {
            mVenue = (Venue) savedInstanceState.getSerializable("lastVenue");
        }
        setLayoutDetails();
        setUpRentButton();
        setCalendar();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        EventBus.getDefault().postSticky(mVenue);
        outState.putSerializable("lastVenue", mVenue);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        mDatePickerText.setText(mSimpleDateFormat.format(newDate.getTime()));
    }

    @OnClick(R.id.btn_rent)
    public void onClickRentButton(){
        if (mDateSelected == null) {
            Toast.makeText(getActivity(), "Please, select a date first.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Venue rented!", Toast.LENGTH_SHORT).show();
        }
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Shows the date picker dialog.
    @OnClick(R.id.txt_date_picker)
    public void onClickDatePicker(){
        mDatePickerDialog.show();
        mVenueText.setVisibility(View.VISIBLE);
        mVenueText.setText(getString(R.string.date_alert) + " " +
                (mSimpleDateFormat.format(mDateSelected)) + ". "
                + getString(R.string.TOS_alert));

    }

    

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Display the venue data in this.fields
    public void setUpLayout(Venue venue) {
        Activity parentActivity = getActivity();
        Picasso.with(getActivity())
                .load(venue.getImageUrls().get(0))
                .error(R.mipmap.ic_launcher)
                .into(mMainVenueImage);
        mAddressText.setText(getString(R.string.address) + ": " + venue.getName());
        mCapacityText.setText(getString(R.string.capacity) + ": " +
                Integer.toString(venue.getCapacity()) + getString(R.string.persons));
        mPriceText.setText("$" + Double.toString(venue.getPrice()));
        mVenueText.setVisibility(View.INVISIBLE);
    }


  
    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Creates a new DatePickerDialog with today's year, month, and day.
    private void setUpDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();
        
        mDatePickerDialog = new DatePickerDialog(getActivity(), 
                this,
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets up mDatePicker with minDate = today, and minDate approx. 6 months.
    private void customizeDatePicker() {
        long currentTime = System.currentTimeMillis();
        mDatePicker = mDatePickerDialog.getDatePicker();
        mDatePicker.setMinDate(currentTime - 1000);
        long dateMax = (currentTime / 90) + currentTime;
        mDatePicker.setMaxDate(dateMax);
    }

}
