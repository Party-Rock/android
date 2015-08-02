package com.example.gerardogtn.partyrock.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.local.VenueDummy;
import com.example.gerardogtn.partyrock.ui.adapter.HomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeListFragment extends Fragment {

    private List<VenueDummy> mVenues;

    @Bind(R.id.item_venue)
    RecyclerView mRecyclerView;

    public HomeListFragment() {
        mVenues = new ArrayList<>();
        mVenues.add(new VenueDummy("Casa Joselito", 200.00, 10, 11.2, 3));
        mVenues.add(new VenueDummy("Casa Maria", 350.00, 30, 7.5, 2, R.drawable.house_for_show));
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
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);
        ButterKnife.bind(this, view);
        setUpRecycleView();
        return view;
    }

    private void setUpRecycleView() {
        Context context = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        HomeListAdapter homeListAdapter = new HomeListAdapter(context, mVenues, (AppCompatActivity) getActivity());
        mRecyclerView.setAdapter(homeListAdapter);
    }

    public interface OnVenueSelectedListener {
        void onVenueSelected(String name, int capacity, int price, int distance);
    }
}
