package com.example.gerardogtn.partyrock.data.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
/**
 * Created by Emilio on 29/07/2015.
 */
public class Venue {

    private int capacity;
    private double price;
    private double size;
    private double ratingAverage;
    private String name;
    private LatLng latLng;
    private ArrayList<String> imageUrls;
    private ArrayList<Integer> rating;
    private ArrayList<Feature> features;

    public Venue() {
        imageUrls =new ArrayList<>();
        features=new ArrayList<>();
        rating=new ArrayList<>();
    }

    public Venue(String name, LatLng latLng, ArrayList<String> imageUrls, int capacity, double price) {
        this.name = name;
        this.latLng = latLng;
        this.imageUrls = imageUrls;
        this.capacity = capacity;
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public ArrayList<Integer> getRating() {
        return rating;
    }

    public void setRating(ArrayList<Integer> rating) {
        this.rating = rating;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

}
