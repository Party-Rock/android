package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.service.PartyRockApiClient;
import com.example.gerardogtn.partyrock.service.SearchVenueEvent;
import com.example.gerardogtn.partyrock.ui.activity.DetailActivity;
import com.example.gerardogtn.partyrock.ui.adapter.HomeListAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchResultsFragment extends Fragment implements HomeListAdapter.OnVenueClickListener {
    private List<Venue> searchedVenues;
    private final String FTAG = "fragment_search_venue";

    @Bind(R.id.recycler_view_venue)
    RecyclerView mRecyclerView;
    @Bind(R.id.FAB_Search)
    FloatingActionButton fabSearch;
    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    public SearchResultsFragment() {
    }

    public static SearchResultsFragment newInstance() {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        setUpToolbar();
        setUpFabClick();
        EventBus.getDefault().registerSticky(this);
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
        Venue clickedVenue = searchedVenues.get(position);
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
        drawBackArrow();
        drawVenueTitle();
        mToolbar.inflateMenu(R.menu.menu_home);

    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Draws back arrow in toolbar.
    private void drawBackArrow(){
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets the Toolbar title to Search Results
    private void drawVenueTitle() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Search Results");
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

    //EventBus method to receive Search Parameters
    public void onEvent(SearchVenueEvent searchVenueEvent) {

        String location = searchVenueEvent.getLocation();
        int price = searchVenueEvent.getPrice();
        int capacity = searchVenueEvent.getCapacity();

        //TODO: Integrate location when server accepts this query
        PartyRockApiClient.getInstance().getSearchedVenues(null, price, capacity, new Callback<List<Venue>>() {
            @Override
            public void success(List<Venue> venues, Response response) {
                if (venues != null) {
                    searchedVenues = venues;
                    setUpRecycleView(searchedVenues);
                } else {
                    Toast.makeText(getActivity(), "Sorry, No results!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }

}
