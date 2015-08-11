package com.example.gerardogtn.partyrock.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.gerardogtn.partyrock.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchVenueFragment extends DialogFragment {
    @Bind(R.id.btn_search)
    Button mSearchButton;
    @Bind(R.id.people_slider)
    SeekBar mSeekBarPeople;
    @Bind(R.id.price_slider)
    SeekBar mSeekBarPrice;
    @Bind(R.id.search_bar)
    EditText mEditTextSearch;




    public SearchVenueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_venues, container, false);
        ButterKnife.bind(this,view);
        mEditTextSearch.requestFocus();
        return view;
    }


}
