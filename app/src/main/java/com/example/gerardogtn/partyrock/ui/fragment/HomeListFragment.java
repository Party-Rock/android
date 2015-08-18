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
        PartyRockApiClient.getInstance().getAllVenues(this);

        Feature alcoholAllowed = new Feature("alcohol", true);
        Feature alcoholNotAllowed = new Feature("alcohol", false);
        Feature smokeNotAllowed = new Feature("smoke", false);


        Position positionJoselito = new Position(new LatLng(19.361704, -99.184427), "Crédito Constructor");

        ArrayList<String> imagesJoselito = new ArrayList<>();
        imagesJoselito.add("http://mlm-s1-p.mlstatic.com/excelente-casa-para-reuniones-y-fiestas-13295-MLM20075175773_042014-F.jpg");
        imagesJoselito.add("http://mlm-s1-p.mlstatic.com/excelente-casa-para-reuniones-y-fiestas-20672-MLM20195551809_112014-F.jpg");

        ArrayList<Feature> featuresJoselito = new ArrayList<>();
        featuresJoselito.add(alcoholNotAllowed);
        featuresJoselito.add(smokeNotAllowed);

        Venue venueJoselito = new Venue("Casa Joselito", positionJoselito, imagesJoselito, 50, 1800.0);
        venueJoselito.setFeatures(featuresJoselito);


        Position positionMaria = new Position(new LatLng(19.366694, -99.182528), "Crédito Constructor");

        ArrayList<String> imagesMaria = new ArrayList<>();
        imagesMaria.add("http://mlm-s1-p.mlstatic.com/jardin-de-bodas-cuernavaca-19431-MLM20171605553_092014-O.jpg");
        imagesMaria.add("http://mlm-s2-p.mlstatic.com/jardin-de-bodas-cuernavaca-19420-MLM20171605642_092014-O.jpg");

        ArrayList<Feature> featuresMaria = new ArrayList<>();
        featuresMaria.add(alcoholAllowed);
        featuresMaria.add(smokeNotAllowed);
        Venue venueMaria = new Venue("Jardin de bodas Maria", positionMaria, imagesMaria, 150, 6500.0);
        venueMaria.setFeatures(featuresMaria);

        mVenues = new ArrayList<>();
        mVenues.add(venueJoselito);
        mVenues.add(venueMaria);
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
        setUpRecycleView();
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
    // EFFECTS: Creates a vertical recycleview filled with mVenues, each view using a HomeListAdapter.
    private void setUpRecycleView() {
        Context context = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        HomeListAdapter homeListAdapter = new HomeListAdapter(context, mVenues);
        mRecyclerView.setAdapter(homeListAdapter);
        homeListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void success(List<Venue> venues, Response response) {
        for(Venue v : venues){
            Log.i(LOG_TAG, v.getName());
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(LOG_TAG, "Error getting venues");
        error.printStackTrace();
    }
}
