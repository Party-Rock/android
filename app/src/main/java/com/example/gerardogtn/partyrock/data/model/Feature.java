package com.example.gerardogtn.partyrock.data.model;

import com.example.gerardogtn.partyrock.R;

import java.util.HashMap;

/**
 * Created by gerardogtn on 8/6/15.
 */
public class Feature {

    public static final String KEY_ALCOHOL = "alcohol";
    public static final String KEY_SMOKE = "smoke";

    private static HashMap<String, Integer> sFeatureAllowed = new HashMap<>();
    private static HashMap<String, Integer> sFeatureNotAllowed = new HashMap<>();

    private String mFeatureName;
    private boolean mIsAvailable;

    public Feature(){
        setUpAllowedFeatures();
        setUpNotAllowedFeatures();
    }

    private static void setUpAllowedFeatures(){
        sFeatureAllowed.put(KEY_ALCOHOL, R.mipmap.alcohol);
        sFeatureAllowed.put(KEY_SMOKE, R.mipmap.smoke);
    }

    private static void setUpNotAllowedFeatures(){
        sFeatureNotAllowed.put(KEY_ALCOHOL, R.mipmap.no_alcohol);
        sFeatureNotAllowed.put(KEY_SMOKE, R.mipmap.no_smoke);
    }

    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS:  If the feature has an associated image resource, return the appropiate image
    // resource. Else, return a default image resource.
    public int getImageResource(){
        Integer output;
        String nameToTag = this.mFeatureName.toLowerCase().replaceAll("\\s+","");

        if (this.mIsAvailable){
            output = sFeatureAllowed.get(nameToTag);
        } else {
            output = sFeatureNotAllowed.get(nameToTag);
        }

        if (output == null){
            return R.mipmap.ic_launcher;
        }

        return output;
    }

}
