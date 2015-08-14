package com.example.gerardogtn.partyrock.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Feature;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.ui.adapter.FeatureAdapter;
import com.example.gerardogtn.partyrock.ui.adapter.ImagePagerAdapterCenterImage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class DetailActivity extends AppCompatActivity implements ImagePagerAdapterCenterImage.OnImageClickListener {

    public static final String LOG = DetailActivity.class.getSimpleName();

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    @Bind(R.id.venue_images)
    ViewPager mImages;

    @Bind(R.id.venue_features)
    RecyclerView mFeatures;

    @Bind(R.id.btn_rent)
    Button rentButton;

    private SupportMapFragment mMapFragment;
    private Venue mVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mVenue = EventBus.getDefault().removeStickyEvent(Venue.class);
        if (mVenue == null) {
            mVenue = (Venue) savedInstanceState.getSerializable("lastVenue");
            ArrayList<Feature> features = new ArrayList<>();
            for (Feature f : mVenue.getFeatures()){
                features.add(new Feature(f.getFeatureName(), f.isAvailable()));
            }
            mVenue.setFeatures(features);
        }
        setUpViewPager(mVenue.getImageUrls());
        setUpRecycleView();
        setUpToolbar();
        setUpRentButton();
        setUpMapFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        EventBus.getDefault().postSticky(mVenue);
        outState.putSerializable("lastVenue", mVenue);
        outState.putSerializable("venueFeatures", mVenue.getFeatures());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_bar_share) {
            shareIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // REQUIRES: Position is valid.
    // MODIFIES: None.
    // EFFECTS:  Displays via a Toast the position of the image as a list in the viewpager.
    @Override
    public void onImageClick(int position) {
        //TODO: Implement Fullscreen Viewpager
        EventBus.getDefault().postSticky(mVenue);
        Intent intent = new Intent(DetailActivity.this, ViewPagerFullScreenActivity.class);
        startActivity(intent);

    }

    //Method to share the Venue Party Rock's website.
    private void shareIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        //TODO: Change link for a party rock web link.
        String HTTPlink = mVenue.getImageUrls().get(0);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.txt_party_share));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, HTTPlink);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.txt_share)));
    }








    private void setUpRentButton() {
        setRentButtonText();
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(mVenue);
                Intent intent = new Intent(DetailActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setRentButtonText() {
        String buttonText;
        buttonText = "Throw your party here for: " + mVenue.getFormattedPrice();
        rentButton.setText(buttonText);
    }


    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Sets up the map fragment.
    private void setUpMapFragment() {
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMap().setMyLocationEnabled(true);
        centerOnVenueLocation();
        addVenueMarker();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Center the map to the mVenue's location.
    private void centerOnVenueLocation() {
        mMapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLng(mVenue.getLatLng()));
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Adds a new marker to mMapFragment at a mVenue position with the mVenue name as
    // title.
    private void addVenueMarker() {
        MarkerOptions marker = new MarkerOptions().position(mVenue.getLatLng()).title(mVenue.getName());
        this.mMapFragment.getMap().addMarker(marker);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Sets support action toolbar with mToolbar.
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        drawVenueTitle();
        drawBackArrow();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets the Toolbar title to the mVenue's name.
    private void drawVenueTitle() {
        getSupportActionBar().setTitle(mVenue.getName());
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Draws back arrow in toolbar.
    private void drawBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets mImages to a new adapter with imageUrls as images. Sets this as onClickListener.
    private void setUpViewPager(final List<String> imageUrls) {
        ImagePagerAdapterCenterImage adapter = new ImagePagerAdapterCenterImage(this, imageUrls);
        mImages.setAdapter(adapter);
        adapter.setOnImageListener(this);
    }


    // TODO: Set up the Venue's features recycleview.
    private void setUpRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFeatures.setLayoutManager(linearLayoutManager);
        FeatureAdapter featureViewAdapter = new FeatureAdapter(this, mVenue.getFeatures());
        mFeatures.setAdapter(featureViewAdapter);
    }


}
