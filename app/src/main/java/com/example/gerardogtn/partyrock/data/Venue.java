package com.example.gerardogtn.partyrock.data;

import java.util.ArrayList;
/**
 * Created by Emilio on 29/07/2015.
 */
public class Venue {
    String name;
    LatLng latLng;
    ArrayList<String> imageURL;
    Double size;
    Double price;
    ArrayList<Feature> features;
    Double ratingAverage;
    ArrayList<Integer> rating;

    public Venue() {
        imageURL=new ArrayList<>();
        features=new ArrayList<>();
        rating=new ArrayList<>();
    }

    public Venue(String name, LatLng latLng, ArrayList<String> imageURL, Double size, Double price) {
        this.name = name;
        this.latLng = latLng;
        this.imageURL = imageURL;
        this.size = size;
        this.price = price;
    }

    public Venue(String name, LatLng latLng, ArrayList<String> imageURL, Double size, Double price, ArrayList<Feature> features, Double ratingAverage, ArrayList<Integer> rating) {
        this.name = name;
        this.latLng = latLng;
        this.imageURL = imageURL;
        this.size = size;
        this.price = price;
        this.features = features;
        this.ratingAverage = ratingAverage;
        this.rating = rating;
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

    public ArrayList<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(ArrayList<String> imageURL) {
        this.imageURL = imageURL;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public ArrayList<Integer> getRating() {
        return rating;
    }

    public void setRating(ArrayList<Integer> rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "name='" + name + '\'' +
                ", latLng=" + latLng.toString() +
                ", imageURL=" + imageURL +
                ", size=" + size +
                ", price=" + price +
                ", features=" + features +
                ", ratingAverage=" + ratingAverage +
                ", rating=" + rating +
                '}';
    }
}
