package com.example.gerardogtn.partyrock.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gerardogtn.partyrock.ui.fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class ImagePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public ImagePagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    // REQUIRES: None
    // MODIFIES: this
    // EFFECTS: Adds fragment to this.mFragments
    public void addFragment(ImageFragment fragment){
        mFragments.add(fragment);
    }
}
