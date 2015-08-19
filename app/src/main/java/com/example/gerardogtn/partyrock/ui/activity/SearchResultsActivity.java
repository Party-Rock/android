package com.example.gerardogtn.partyrock.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.ui.fragment.SearchResultsFragment;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addSearchResultsFragment();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Draws a SearchResultsFragment on this.mFragmentContainer
    private void addSearchResultsFragment() {
        FragmentTransaction tm = getSupportFragmentManager().beginTransaction();
        SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance();
        tm.replace(R.id.fragment_container, searchResultsFragment);
        tm.commit();
    }
}
