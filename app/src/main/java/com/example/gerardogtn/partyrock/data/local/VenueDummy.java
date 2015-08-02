package com.example.gerardogtn.partyrock.data.local;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class VenueDummy {
    
    private String mName;
    private double mPrice;
    private int mCapacity;
    private double mDistance;
    private int mImageCount;
    private Integer mImageResource;

    public VenueDummy(String mName, double mPrice, int mCapacity, double mDistance, int imageCount) {
        this(mName, mPrice, mCapacity, mDistance, imageCount, -1);
    }

    public VenueDummy(String mName, double mPrice, int mCapacity, double mDistance, int mImageCount, Integer mImageResource) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCapacity = mCapacity;
        this.mDistance = mDistance;
        this.mImageCount = mImageCount;
        this.mImageResource = mImageResource;
    }

    public int getmImageCount() {
        return mImageCount;
    }

    public void setmImageCount(int mImageCount) {
        this.mImageCount = mImageCount;
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

    @Override
    public String toString() {
        return "VenueDummy{" +
                "mName='" + mName + '\'' +
                ", mPrice=" + mPrice +
                ", mCapacity=" + mCapacity +
                ", mDistance=" + mDistance +
                ", mImageCount=" + mImageCount +
                '}';
    }

}
