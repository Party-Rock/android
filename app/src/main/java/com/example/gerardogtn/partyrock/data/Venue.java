package com.example.gerardogtn.partyrock.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilio on 29/07/2015.
 */
public class Venue {
    private String mName;
    private double mPrice;
    private int mCapacity;
    private double mDistance;
    private Integer mImageResource;
    private ArrayList<String> mImageUrls;
    private LatLng mLatLng;
    private int mSize;
    private ArrayList<Feature> mFeatures;
    private double mRatingAverage;
    private ArrayList<Integer> mRating;

    public Venue() {
        mImageUrls = new ArrayList<>();
        mFeatures=new ArrayList<>();
        mRating=new ArrayList<>();
    }

    public Venue(String mName, double mPrice, int mCapacity, double mDistance) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCapacity = mCapacity;
        this.mDistance = mDistance;
        mImageUrls = new ArrayList<>();
    }

    public Venue(String mName, double mPrice, int mCapacity, double mDistance, Integer mImageResource, ArrayList<String> mImageUrls, LatLng mLatLng, int size, ArrayList<Feature> mFeatures, double mRatingAverage, ArrayList<Integer> mRating) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCapacity = mCapacity;
        this.mDistance = mDistance;
        this.mImageResource = mImageResource;
        this.mImageUrls = mImageUrls;
        this.mLatLng = mLatLng;
        this.mSize = size;
        this.mFeatures = mFeatures;
        this.mRatingAverage = mRatingAverage;
        this.mRating = mRating;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public int getmCapacity() {
        return mCapacity;
    }

    public void setmCapacity(int mCapacity) {
        this.mCapacity = mCapacity;
    }

    public double getmDistance() {
        return mDistance;
    }

    public void setmDistance(double mDistance) {
        this.mDistance = mDistance;
    }

    public Integer getImageResource() {
        return this.mImageResource;
    }

    public void setImageResource(Integer imageResource){
        this.mImageResource = imageResource;
    }

    public void addImageUrl(String url){
        mImageUrls.add(url);
    }

    public List<String> getImageUrls(){
        return mImageUrls;
    }

    public int getImageCount(){
        return mImageUrls.size();
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public void setmLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public int getmSize() {
        return mSize;
    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
    }

    public ArrayList<Feature> getmFeatures() {
        return mFeatures;
    }

    public void setmFeatures(ArrayList<Feature> mFeatures) {
        this.mFeatures = mFeatures;
    }

    public void addFeatures(Feature mFeatures) {
        this.mFeatures.add(mFeatures);
    }

    public double getmRatingAverage() {
        return mRatingAverage;
    }

    public void setmRatingAverage(double mRatingAverage) {
        this.mRatingAverage = mRatingAverage;
    }

    public ArrayList<Integer> getmRating() {
        return mRating;
    }

    public void setmRating(ArrayList<Integer> mRating) {
        this.mRating = mRating;
    }
    public void addRating(Integer mRating) {
        this.mRating.add(mRating);
    }

    @Override
    public String toString() {
        return "Venue:" +
                "mName='" + mName + '\'' +
                ", mPrice=" + mPrice +
                ", mCapacity=" + mCapacity +
                ", mDistance=" + mDistance +
                ", mSize=" + mSize +
                ", mFeatures=" + mFeatures +
                ", mRatingAverage=" + mRatingAverage +
                ", mRating=" + mRating +
                '}';
    }
}
