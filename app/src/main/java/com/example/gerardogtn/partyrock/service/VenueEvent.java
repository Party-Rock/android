package com.example.gerardogtn.partyrock.service;

import com.example.gerardogtn.partyrock.data.model.Venue;

/**
 * Created by Emilio on 05/08/2015.
 */
public class VenueEvent {
        public final Venue venue;

        public VenueEvent(Venue mVenue) {
            this.venue = mVenue;
        }

    public Venue getVenue() {
        return venue;
    }
}
