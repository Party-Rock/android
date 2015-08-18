package com.example.gerardogtn.partyrock.data.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gerardogtn on 8/10/15.
 */
public class Position implements Serializable {

    @SerializedName("lat")
    private double latitude;

    @SerializedName("long")
    private double longitude;

    @SerializedName("colonia")
    private String colonia;

    public Position() {

    }

    public Position(LatLng latLng, String colonia){
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.colonia = colonia;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }
}
