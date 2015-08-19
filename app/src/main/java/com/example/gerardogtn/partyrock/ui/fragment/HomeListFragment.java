package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Feature;
import com.example.gerardogtn.partyrock.data.model.Position;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.service.PartyRockApiClient;
import com.example.gerardogtn.partyrock.ui.activity.DetailActivity;
import com.example.gerardogtn.partyrock.ui.adapter.HomeListAdapter;
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
    private List<Venue> searchedVenues;
    private final String FTAG = "fragment_search_venue";

    @Bind(R.id.recycler_view_venue)
    RecyclerView mRecyclerView;
    @Bind(R.id.FAB_Search)
    FloatingActionButton fabSearch;
    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    public HomeListFragment() {

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

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        PartyRockApiClient.getInstance().getAllVenues(this);
        ButterKnife.bind(this, view);
        setUpToolbar();
        setUpRecycleView(mVenues);
        setUpFabClick();
        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_bar_search) {
            showSearchDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    // REQUIRES: None.
    // MODIFIES: None.
    // EFFECTS: When a Venue is clicked, navigate to Detail Activity with the venue's data.
    @Override
    public void onVenueClick(int position) {
        Context context = getActivity();
        Venue clickedVenue = mVenues.get(position);
        EventBus.getDefault().postSticky(clickedVenue);
        startActivity(new Intent(context, DetailActivity.class));
    }

    private void setUpFabClick() {
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Sets support action toolbar with mToolbar.
    private void setUpToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_home);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets this.mVenues to venues.
    @Override
    public void success(List<Venue> venues, Response response) {
        this.mVenues.addAll(venues);

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
    private void setUpRecycleView(List<Venue> venues) {
        Context context = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        HomeListAdapter homeListAdapter = new HomeListAdapter(context, venues);
        mRecyclerView.setAdapter(homeListAdapter);
        homeListAdapter.setOnItemClickListener(this);
    }

    //FAB Button OnClick Method
    private void showSearchDialog() {

        FragmentManager fm = getFragmentManager();

        SearchVenueFragment searchDialog = new SearchVenueFragment();

        searchDialog.show(fm, FTAG);


    }



}
