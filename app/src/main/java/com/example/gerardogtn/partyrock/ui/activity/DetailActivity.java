package com.example.gerardogtn.partyrock.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.adapter.ImagePagerAdapter;
import com.example.gerardogtn.partyrock.data.Venue;
import com.example.gerardogtn.partyrock.service.VenueEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class DetailActivity extends AppCompatActivity implements ImagePagerAdapter.OnImageClickListener{

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    @Bind(R.id.venue_images)
    ViewPager mImages;

//    @Bind(R.id.venue_features)
//    RecyclerView mFeatures;

    @Bind(R.id.btn_rent)
    Button rentButton;

    private SupportMapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().registerSticky(this);
        setUpToolbar();
        setUpMapFragment();
        setUpRentButton();

    }

    private void setUpRentButton() {
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    //EventBus method to receive Venue
    public void onEvent(VenueEvent clickedVenue){
        Toast.makeText(this, "Venue received " + clickedVenue.getVenue().getmName(), Toast.LENGTH_SHORT).show();
        setUpViewPager(clickedVenue.getVenue().getImageUrls());
        //setUpRecycleView(clickedVenue.getVenue());

    }


    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets mImages to a new adapter with imageUrls as images. Sets this as onClickListener.
    private void setUpViewPager(final List<String> imageUrls) {
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageUrls);
        mImages.setAdapter(adapter);
        adapter.setOnImageListener(this);
    }


    private void setUpRecycleView(Venue venue) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //mFeatures.setLayoutManager(linearLayoutManager);
        //       FeatureRecyclerViewAdapter featureViewAdapter = new FeatureRecyclerViewAdapter(context, venue.getmFeatures());
        //      mRecyclerView.setAdapter(featureViewAdapter);
    }

    // REQUIRES: Position is valid.
    // MODIFIES: None.
    // EFFECTS:  Displays via a Toast the position of the image as a list in the viewpager.
    @Override
    public void onImageClick(int position) {
        String toastDisplay = "This is Image No." + (position + 1) + " of " + mImages.getAdapter().getCount();
        Toast.makeText(DetailActivity.this, toastDisplay, Toast.LENGTH_SHORT).show();
    }
}
