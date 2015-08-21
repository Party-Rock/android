package com.example.gerardogtn.partyrock.data.model;

import com.example.gerardogtn.partyrock.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by gerardogtn on 8/6/15.
 */
public class Feature implements Serializable {

    public static final String KEY_ALCOHOL = "alcohol";
    public static final String KEY_SMOKE = "smoke";

    public static final String DESCRIPTION_ALLOWED_ALCOHOL = "Drinking alcohol is allowed.";
    public static final String DESCRIPTION_ALLOWED_SMOKE = "Smoking is allowed.";
    public static final String DESCRIPTION_NALLOWED_ALCOHOL = "Drinking alcohol is NOT allowed.";
    public static final String DESCRIPTION_NALLOWED_SMOKE = "Smoking is NOT allowed.";

    private static HashMap<String, Integer> sFeatureIconsAllowed = new HashMap<>();
    private static HashMap<String, Integer> sFeatureIconsNotAllowed = new HashMap<>();

    private static HashMap<String, String> sFeatureDescriptionAllowed = new HashMap<>();
    private static HashMap<String, String> sFeatureDescriptionNotAllowed = new HashMap<>();

    private String tag;

    @SerializedName("feature")
    private String mFeatureName;

    @SerializedName("option")
    private boolean mIsAvailable;

    public Feature(){
        setUpFeatureDefaults();
    }

    public Feature(String name, boolean isAvailable){
        setUpFeatureDefaults();
        setFeatureName(name);
        setAvailable(isAvailable);
    }

    public String getFeatureName() {
        return mFeatureName;
    }

    public void setFeatureName(String mFeatureName) {
        this.mFeatureName = mFeatureName;
        this.tag = this.mFeatureName.toLowerCase().replaceAll("\\s+","");
    }

    public boolean isAvailable() {
        return mIsAvailable;
    }

    public void setAvailable(boolean mIsAvailable) {
        this.mIsAvailable = mIsAvailable;
    }

    private static void setUpFeatureDefaults(){
        setUpAllowedFeatureIcons();
        setUpNotAllowedFeatureIcons();
        setUpAllowedFeatureDescriptions();
        setUpNotAllowedFeatureDescriptions();
    }

    private static void setUpAllowedFeatureIcons(){
        sFeatureIconsAllowed.put(KEY_ALCOHOL, R.mipmap.alcohol);
        sFeatureIconsAllowed.put(KEY_SMOKE, R.mipmap.smoke);
    }

    private static void setUpNotAllowedFeatureIcons(){
        sFeatureIconsNotAllowed.put(KEY_ALCOHOL, R.mipmap.no_alcohol);
        sFeatureIconsNotAllowed.put(KEY_SMOKE, R.mipmap.no_smoke);
    }

    private static void setUpAllowedFeatureDescriptions(){
        sFeatureDescriptionAllowed.put(KEY_ALCOHOL, DESCRIPTION_ALLOWED_ALCOHOL);
        sFeatureDescriptionAllowed.put(KEY_SMOKE, DESCRIPTION_ALLOWED_SMOKE);
    }

    private static void setUpNotAllowedFeatureDescriptions(){
        sFeatureDescriptionNotAllowed.put(KEY_ALCOHOL, DESCRIPTION_NALLOWED_ALCOHOL);
        sFeatureDescriptionNotAllowed.put(KEY_SMOKE, DESCRIPTION_NALLOWED_SMOKE);
    }

    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS:  If the feature has an associated image resource, return the appropiate image
    // resource. Else, return a default image resource.
    public int getImageResource(){
        Integer output;

        if (this.mIsAvailable){
            output = sFeatureIconsAllowed.get(tag);
        } else {
            output = sFeatureIconsNotAllowed.get(tag);
        }

        if (output == null){
            return R.mipmap.ic_launcher;
        }

        return output;
    }

    public String getDescription(){
        String output;

        if (this.mIsAvailable){
            output = sFeatureDescriptionAllowed.get(tag);
        } else {
            output = sFeatureDescriptionNotAllowed.get(tag);
        }

        if (output == null){
            return "Description not available.";
        }

        return output;
    }

}
