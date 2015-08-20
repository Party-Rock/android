package com.example.gerardogtn.partyrock.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Feature;
import com.example.gerardogtn.partyrock.ui.adapter.FeatureAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturesFragment extends DialogFragment {
    @Bind(R.id.btn_close)
    Button mCloseButton;

    @Bind(R.id.venue_features)
    RecyclerView mFeatureRecyclerView;


    private ArrayList<Feature> mFeatures;



    public FeaturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_features, container, false);
        ButterKnife.bind(this,view);
        mFeatures = (ArrayList<Feature>) EventBus.getDefault().removeStickyEvent(ArrayList.class);
        if (mFeatures==null){
            getDialog().dismiss();
        }
        setUpRecycleView();
        setUpButton();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        EventBus.getDefault().postSticky(mFeatures);
        super.onSaveInstanceState(outState);
    }


    private void setUpButton() {
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }


    private void setUpRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mFeatureRecyclerView.setLayoutManager(linearLayoutManager);
        FeatureAdapter featureViewAdapter = new FeatureAdapter(getActivity(), mFeatures);
        mFeatureRecyclerView.setAdapter(featureViewAdapter);
    }




}
