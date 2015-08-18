package com.example.gerardogtn.partyrock.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by gerardogtn on 8/12/15.
 */
public class Reservation {

    @SerializedName("fname")
    private String mFirstName;

    @SerializedName("lname")
    private String mLastName;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("profilePic")
    private String mProfilePic;

    @SerializedName("rentedVenues")
    private ArrayList<RentedVenuesData> mRentedVenues;


    private class RentedVenuesData{

        @SerializedName("venueId")
        private String mVenueId;

        @SerializedName("name")
        private String mName;

        @SerializedName("rentedDate")
        private String mDate;
    }

}
