package com.example.gerardogtn.partyrock.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gerardogtn.partyrock.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchLocationFragment extends Fragment {


    public SearchLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_location, container, false);
        return view;
    }


}
