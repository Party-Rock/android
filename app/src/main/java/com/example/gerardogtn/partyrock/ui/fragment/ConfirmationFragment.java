package com.example.gerardogtn.partyrock.ui.fragment;


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
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {
    @Bind(R.id.btn_rent)
    Button rentButton;

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

    @Bind(R.id.datePickerText)
    TextView datePickerText;


    private DatePicker mDatePicker;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mSimpleDateFormat;
    private Date mDateSelected;
    private Venue mVenue;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    //EventBus method to receive Venue
    public void onEvent(VenueEvent clickedVenue) {
        Venue venue = clickedVenue.getVenue();
        setLayoutDetails(venue);
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
        setLayoutDetails(mVenue);
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

    public void setLayoutDetails(Venue venue) {
        Picasso.with(getActivity())
                .load(venue.getImageUrls().get(0))
                .error(R.mipmap.ic_launcher)
                .into(mMainVenueImage);
        mAddressText.setText(getString(R.string.address) + ": " + venue.getName());
        mCapacityText.setText(getString(R.string.capacity) + ": " +
                Integer.toString(venue.getCapacity())+ getString(R.string.persons));
        mPriceText.setText("$" + Double.toString(venue.getPrice()));
        mVenueText.setVisibility(View.INVISIBLE);

    }


    private void setUpRentButton() {
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Venue rented!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Method to call a DatePicker Dialog when TextView is clicked.
    public void setCalendar() {
        //Define the format in which the date is shown.
        mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        datePickerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();
            }
        });

        //Get the selected date from the dialog
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mDateSelected= newDate.getTime();
                datePickerText.setText(mSimpleDateFormat.format(mDateSelected));
                mVenueText.setVisibility(View.VISIBLE);
                mVenueText.setText(getString(R.string.date_alert) + " " +
                        (mSimpleDateFormat.format(mDateSelected)) + ". "
                        + getString(R.string.TOS_alert));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        //Customization of the Calendar View
        long currentTime = System.currentTimeMillis();
        mDatePicker = mDatePickerDialog.getDatePicker();
        mDatePicker.setMinDate(currentTime - 1000);
        long dateMax = (currentTime / 90) + currentTime;
        mDatePicker.setMaxDate(dateMax);

    }


}
