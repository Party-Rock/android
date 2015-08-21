package com.example.gerardogtn.partyrock.ui.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.gerardogtn.partyrock.service.PartyRockApiClient;
import com.example.gerardogtn.partyrock.service.VenueEvent;
import com.example.gerardogtn.partyrock.util.ApiConstants;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment implements DatePickerDialog.OnDateSetListener,
        Callback<Response>{

    public static final String LOG_TAG = ConfirmationFragment.class.getSimpleName();

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

    @Bind(R.id.txt_checkout)
    TextView mCheckoutText;

    @Bind(R.id.txt_date_picker)
    TextView mDatePickerText;

    private DatePicker mDatePicker;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mSimpleDateFormat;
    private Date mDateSelected;
    private Venue mVenue;
    private String mAddress;

    public ConfirmationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mVenue = EventBus.getDefault().removeStickyEvent(Venue.class);
        mAddress=EventBus.getDefault().removeStickyEvent(String.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        ButterKnife.bind(this, view);

        if (mVenue == null  && savedInstanceState!=null) {
            mVenue = (Venue) savedInstanceState.getSerializable("lastVenue");
            EventBus.getDefault().postSticky(mVenue);
        }
        if (mAddress==null && savedInstanceState!=null){
            mAddress = savedInstanceState.getString("address");
        }else if (mAddress==null) {
            mAddress="Location not available";
        }

        setUpCalendar();
        setUpLayout();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        EventBus.getDefault().postSticky(mVenue);
        outState.putSerializable("lastVenue", mVenue);
        outState.putString("address", mAddress);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        ButterKnife.unbind(this);
        super.onDetach();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        mDateSelected = newDate.getTime();
        mDatePickerText.setText(mSimpleDateFormat.format(newDate.getTime()));
        mDatePickerText.setText(mSimpleDateFormat.format(mDateSelected));
        mCheckoutText.setVisibility(View.VISIBLE);
        mCheckoutText.setText(getString(R.string.date_alert) + " " +
                        (mSimpleDateFormat.format(mDateSelected)) + ". "
                        + getString(R.string.TOS_alert));
    }

    @Override
    public void success(Response response, Response response2) {
        Log.i(LOG_TAG, "Reservation was posted successfully");
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(LOG_TAG, "Posting reservation failed.");
        error.printStackTrace();
    }

    @OnClick(R.id.btn_rent)
    public void onClickRentButton(){

        if (mDateSelected == null) {
            Toast.makeText(getActivity(), "Please, select a date first.", Toast.LENGTH_SHORT).show();
        } else {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            df.setTimeZone(tz);
            String dateIso = df.format(mDateSelected);
            postReservation(dateIso);
            postRentedDate(dateIso);
            Toast.makeText(getActivity(), "Venue rented!", Toast.LENGTH_SHORT).show();
        }

    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Shows the date picker dialog.
    @OnClick(R.id.txt_date_picker)
    public void onClickDatePicker(){
        mDatePickerDialog.show();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Display the venue data in this.fields
    public void setUpLayout() {
        Activity parentActivity = getActivity();
        Picasso.with(parentActivity)
                .load(mVenue.getImageUrls().get(0))
                .error(R.mipmap.ic_launcher)
                .into(mMainVenueImage);
        mAddressText.setText(getString(R.string.address) + ": " + mAddress);
        mCapacityText.setText(getString(R.string.capacity) + ": " +
                Integer.toString(mVenue.getCapacity()) + getString(R.string.persons));
        mPriceText.setText("$" + Double.toString(mVenue.getPrice()));
        mCheckoutText.setVisibility(View.INVISIBLE);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets up the mSimpleDateFormat, the mDatePicker and the mDatePickerDialog.
    public void setUpCalendar() {
        mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        setUpDatePickerDialog();
        customizeDatePicker();
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

        customizeDatePicker();
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

    // REQUIRES: dateIso is a valid date format.
    // MODIFIES: database.
    // EFFECTS: Post a reservation to the reservation database.
    private void postReservation(String dateIso) {
        SharedPreferences preferences = getActivity()
                .getSharedPreferences(getString(R.string.shared_preferences)
                        , Context.MODE_PRIVATE);

        String userId = preferences.getString(ApiConstants.PARAM_USER_ID, "");
        String ownerId = preferences.getString(ApiConstants.PARAM_OWNER_ID, "");
        PartyRockApiClient.getInstance().postReservation(userId,
                ownerId,
                mVenue.getId(),
                dateIso,
                this);
    }

    // REQUIRES: dateIso is a valid date.
    // MODIFIES: database.
    // EFFECTS:  Posts a rentedDate to the venue.renteddate[] in the database.
    private void postRentedDate(String dateIso) {
        PartyRockApiClient.getInstance().addRentedDate(mVenue.getId(),
                dateIso,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Log.i(LOG_TAG, "Patch rent date successful.");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(LOG_TAG, "Unable to path rent date.");
                        error.printStackTrace();
                    }
                });
    }
}
