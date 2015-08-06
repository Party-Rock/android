package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.adapter.ImagePagerAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {

    @Bind(R.id.venue_features)
    RecyclerView mRecyclerView;

    @Bind(R.id.venue_images)
    ViewPager mImages;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
 //       setUpViewPager(venue.getImageUrls());
        setUpRecycleView();
        return view;
    }

    private void setUpRecycleView() {
        Context context = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
   //     FeatureRecyclerViewAdapter featureViewAdapter = new FeatureRecyclerViewAdapter(context, features);
  //      mRecyclerView.setAdapter(featureViewAdapter);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  If the imageResource is invalid(-1) add a new ImageFragment with default Image.
    //           Otherwise, add a new ImageFragment with the specified image resource.
    private void setUpViewPager(List<String> imageUrls) {
        ImagePagerAdapter adapter = new ImagePagerAdapter(getActivity(), imageUrls);
        mImages.setAdapter(adapter);
    }


    @Override
    public void onDetach() {
        ButterKnife.unbind(this);
    }

}
