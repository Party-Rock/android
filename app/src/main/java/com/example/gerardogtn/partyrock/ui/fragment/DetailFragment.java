package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.adapter.ImagePagerAdapter;
import com.example.gerardogtn.partyrock.data.Venue;
import com.example.gerardogtn.partyrock.service.VenueEvent;
import com.example.gerardogtn.partyrock.ui.activity.ConfirmationActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class DetailFragment extends Fragment {

    @Bind(R.id.venue_features)
    RecyclerView mRecyclerView;

    @Bind(R.id.venue_images)
    ViewPager mImages;

    @Bind(R.id.btn_rent)
    Button rentButton;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //EventBus method to receive Venue
    public void onEvent(VenueEvent clickedVenue){
        Toast.makeText(getActivity(), "Venue received " + clickedVenue.getVenue().getmName(), Toast.LENGTH_SHORT).show();
        setUpViewPager(clickedVenue.getVenue().getImageUrls());
        setUpRecycleView(clickedVenue.getVenue());

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
        EventBus.getDefault().registerSticky(this);
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void setUpRecycleView(Venue venue) {
        Context context = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
 //       FeatureRecyclerViewAdapter featureViewAdapter = new FeatureRecyclerViewAdapter(context, venue.getmFeatures());
  //      mRecyclerView.setAdapter(featureViewAdapter);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  If the imageResource is invalid(-1) add a new ImageFragment with default Image.
    //           Otherwise, add a new ImageFragment with the specified image resource.
    private void setUpViewPager(final List<String> imageUrls) {
        ImagePagerAdapter adapter = new ImagePagerAdapter(getActivity(), imageUrls);
        mImages.setAdapter(adapter);
        adapter.setOnItemClickListener(new ImagePagerAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
               Toast.makeText(getActivity(),"This is Image No."+(position+1) +" of " + imageUrls.size(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        ButterKnife.unbind(this);
        super.onDetach();
    }

}
