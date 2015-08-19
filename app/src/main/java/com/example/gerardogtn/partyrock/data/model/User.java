package com.example.gerardogtn.partyrock.data.model;

import com.example.gerardogtn.partyrock.util.ApiConstants;
import com.google.android.gms.common.api.Api;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gerardogtn on 8/19/15.
 */
public class User {

    @SerializedName(ApiConstants.PARAM_FIRST_NAME)
    private String mFirstName;

    @SerializedName(ApiConstants.PARAM_LAST_NAME)
    private String mLastName;

    @SerializedName(ApiConstants.PARAM_PASSWORD)
    private String mPassword;

    public User() {

    }

    public User(String mFirstName, String mLastName, String mPassword) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mPassword = mPassword;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = mLastName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = mPassword;
    }
}
