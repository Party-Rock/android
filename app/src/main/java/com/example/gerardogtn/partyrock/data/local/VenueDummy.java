package com.example.gerardogtn.partyrock.data.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class VenueDummy {
    
    private String mName;
    private double mPrice;
    private int mCapacity;
    private double mDistance;
    private Integer mImageResource;
    private List<String> mImageUrls;


    public VenueDummy(String mName, double mPrice, int mCapacity, double mDistance) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCapacity = mCapacity;
        this.mDistance = mDistance;
        mImageUrls = new ArrayList<>();
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

}
