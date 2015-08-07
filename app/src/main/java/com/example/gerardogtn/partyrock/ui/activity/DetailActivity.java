package com.example.gerardogtn.partyrock.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.ui.fragment.DetailFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.google.android.gms.maps.SupportMapFragment;

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
