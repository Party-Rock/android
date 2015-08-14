package com.example.gerardogtn.partyrock.data.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Emilio on 29/07/2015.
 */
public class Venue {

    @SerializedName("capacity")
    private int mCapacity;

    @SerializedName("price")
    private double mPrice;

    @SerializedName("size")
    private double mSize;

    @SerializedName("name")
    private String mName;

    @SerializedName("position")
    private Position mPosition;

    @SerializedName("imageURL")
    private ArrayList<String> mImageUrls;

    @SerializedName("features")
    private ArrayList<Feature> mFeatures;

    public Venue() {
        mImageUrls =new ArrayList<>();
        mFeatures =new ArrayList<>();
    }

    public Venue(String name, Position position, ArrayList<String> imageUrls, int capacity, double price) {
        this.mName = name;
        this.mImageUrls = imageUrls;
        this.mPosition = position;
        this.mCapacity = capacity;
        this.mPrice = price;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int capacity) {
        this.mCapacity = capacity;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }

    public String getFormattedPrice(){
        return (NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(this.mPrice));
    }

    public double getSize() {
        return mSize;
    }

    public void setSize(double size) {
        this.mSize = size;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public LatLng getLatLng() {
        return new LatLng(mPosition.getLatitude(), mPosition.getLongitude());
    }

    public ArrayList<String> getImageUrls() {
        return mImageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.mImageUrls = imageUrls;
    }

    public ArrayList<Feature> getFeatures() {
        return mFeatures;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.mFeatures = features;
    }

    public Position getPosition() {
        return mPosition;
    }

    public void setPosition(Position position) {
        this.mPosition = position;
    }

}
