package com.example.gerardogtn.partyrock.data.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gerardogtn on 8/10/15.
 */
public class Position {

    private double latitude;
    private double longitude;
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
