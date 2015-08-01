package com.example.gerardogtn.partyrock.data;

import java.util.ArrayList;
/**
 * Created by Emilio on 29/07/2015.
 */
public class Venue {
    String name;
    LatLng latLng;
    ArrayList<String> imageURL;
    Integer size;
    Integer price;
    ArrayList<Feature> features;
    Integer ratingAverage;
    ArrayList<Integer> rating;

    public Venue() {
        imageURL=new ArrayList<>();
        features=new ArrayList<>();
        rating=new ArrayList<>();
    }

    public Venue(String name, LatLng latLng, ArrayList<String> imageURL, Integer size, Integer price, ArrayList<Feature> features, Integer ratingAverage, ArrayList<Integer> rating) {
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public Integer getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Integer ratingAverage) {
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
