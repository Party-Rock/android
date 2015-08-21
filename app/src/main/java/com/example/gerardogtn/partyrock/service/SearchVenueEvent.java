package com.example.gerardogtn.partyrock.service;

/**
 * Created by Emilio on 11/08/2015.
 */
public class SearchVenueEvent {
    public final String location;
    public final int price;
    public final int capacity;

    public SearchVenueEvent(String location, int price, int capacity) {
        this.location = location;
        this.price = price;
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }
}
