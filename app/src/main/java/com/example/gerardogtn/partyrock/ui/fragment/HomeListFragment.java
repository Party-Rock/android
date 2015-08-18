package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Feature;
import com.example.gerardogtn.partyrock.data.model.Position;
import com.example.gerardogtn.partyrock.data.model.VenuesResponse;
import com.example.gerardogtn.partyrock.service.PartyRockApiClient;
import com.example.gerardogtn.partyrock.ui.adapter.HomeListAdapter;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.service.VenueEvent;
import com.example.gerardogtn.partyrock.ui.activity.DetailActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeListFragment extends Fragment implements HomeListAdapter.OnVenueClickListener,
        Callback<List<Venue>> {

    public static final String LOG_TAG = HomeListFragment.class.getSimpleName();
    private List<Venue> mVenues;

    @Bind(R.id.recycler_view_venue)
    RecyclerView mRecyclerView;

    public HomeListFragment() {
        mVenues = new ArrayList<>();
        PartyRockApiClient.getInstance().getAllVenues(this);
    }

    public static HomeListFragment newInstance() {
        HomeListFragment fragment = new HomeListFragment();
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
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
    }

    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS: When a Venue is clicked, navigate to Detail Activity with the venue's data.
    @Override
    public void onVenueClick(int position) {
        Context context = getActivity();
        Venue clickedVenue = mVenues.get(position);
        Toast.makeText(context, clickedVenue.getName() + " was clicked!", Toast.LENGTH_SHORT).show();
        VenueEvent venueEvent = new VenueEvent(clickedVenue);
        EventBus.getDefault().postSticky(venueEvent);
        startActivity(new Intent(context, DetailActivity.class));
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets this.mVenues to venues.
    @Override
    public void success(List<Venue> venues, Response response) {
        this.mVenues = venues;
        setUpRecycleView();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Notifies a connection error and prints the stack of the error.
    @Override
    public void failure(RetrofitError error) {
        Log.e(LOG_TAG, "Error getting venues");
        error.printStackTrace();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Creates a vertical recycleview filled with mVenues, each view using a HomeListAdapter.
    private void setUpRecycleView() {
        Context context = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        HomeListAdapter homeListAdapter = new HomeListAdapter(context, mVenues);
        mRecyclerView.setAdapter(homeListAdapter);
        homeListAdapter.setOnItemClickListener(this);
    }

}
