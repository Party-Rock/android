package com.example.gerardogtn.partyrock.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.gerardogtn.partyrock.R;
import com.google.android.gms.maps.SupportMapFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class DetailActivity extends AppCompatActivity{

    private SupportMapFragment mMapFragment;

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpMapFragment();
    }


    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Sets up the map fragment.
    private void setUpMapFragment() {
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMap().setMyLocationEnabled(true);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Sets support action toolbar with mToolbar.
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        drawBackArrow();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Draws back arrow in toolbar.
    private void drawBackArrow(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
