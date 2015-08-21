package com.example.gerardogtn.partyrock.service;

import android.location.Location;

/**
 * Created by Emilio on 21/08/2015.
 */
public class GoogleApiEvent {
    Location location;

    public GoogleApiEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
