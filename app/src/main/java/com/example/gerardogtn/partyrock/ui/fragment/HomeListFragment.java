package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.local.VenueDummy;
import com.example.gerardogtn.partyrock.adapter.HomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeListFragment extends Fragment {

    private List<VenueDummy> mVenues;

    @Bind(R.id.recycler_view_venue)
    RecyclerView mRecyclerView;

    public HomeListFragment() {
        mVenues = new ArrayList<>();

        VenueDummy venueJoselito = new VenueDummy("Casa Joselito", 1800.0, 100, 13.2);
        venueJoselito.addImageUrl("http://mlm-s1-p.mlstatic.com/excelente-casa-para-reuniones-y-fiestas-13295-MLM20075175773_042014-F.jpg");
        venueJoselito.addImageUrl("http://mlm-s1-p.mlstatic.com/excelente-casa-para-reuniones-y-fiestas-20672-MLM20195551809_112014-F.jpg");

        VenueDummy venueMaria = new VenueDummy("Jardin de bodas maria", 6500.0, 150, 27);
        venueMaria.addImageUrl("http://mlm-s1-p.mlstatic.com/jardin-de-bodas-cuernavaca-19431-MLM20171605553_092014-O.jpg");
        venueMaria.addImageUrl("http://mlm-s2-p.mlstatic.com/jardin-de-bodas-cuernavaca-19420-MLM20171605642_092014-O.jpg");

        mVenues.add(venueJoselito);
        mVenues.add(venueMaria);
        mVenues.add(new VenueDummy("Casa paco", 300.0, 3, 8.2));
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

    private void setUpRecycleView() {
        Context context = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        HomeListAdapter homeListAdapter = new HomeListAdapter(context, mVenues);
        mRecyclerView.setAdapter(homeListAdapter);
    }

    public interface OnVenueSelectedListener {
        void onVenueSelected(String name, int capacity, int price, int distance);
    }
}
