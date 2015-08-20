package com.example.gerardogtn.partyrock.service;

/**
 * Created by Emilio on 20/08/2015.
 */
public class GeocoderEvent {
    String address;

    public GeocoderEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
