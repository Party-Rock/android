package com.example.gerardogtn.partyrock.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.service.VenueEvent;
import com.example.gerardogtn.partyrock.ui.adapter.FeatureAdapter;
import com.example.gerardogtn.partyrock.ui.adapter.ImagePagerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class DetailActivity extends AppCompatActivity implements ImagePagerAdapter.OnImageClickListener{

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    @Bind(R.id.venue_images)
    ViewPager mImages;

    @Bind(R.id.venue_features)
    RecyclerView mFeatures;

    @Bind(R.id.btn_rent)
    Button rentButton;

    private SupportMapFragment mMapFragment;
    private Venue venue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().registerSticky(this);

    }

    private void setUpRentButton() {
        setRentButtonText();
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setRentButtonText() {
        String buttonText;
        buttonText = "Throw your party here for: " + venue.getFormattedPrice();
        rentButton.setText(buttonText);
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

    //Method to share the Venue Party Rock's website.
    private void shareIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        //TODO: Change link for a party rock web link.
        String HTTPlink = venue.getImageUrls().get(0);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.txt_party_share));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, HTTPlink);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.txt_share)));
    }
    //A method in case we want to share a photo in the future.
    private void shareIntentPhoto() {
        View view = getWindow().getDecorView().getRootView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Title","Description");
        Uri uri = Uri.parse(path);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share image using"));
    }


    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        setUpViewPager(venue.getImageUrls());
        setUpRecycleView();
        setUpToolbar();
        setUpRentButton();
        setUpMapFragment();
        super.onPostResume();
    }

    // REQUIRES: Position is valid.
    // MODIFIES: None.
    // EFFECTS:  Displays via a Toast the position of the image as a list in the viewpager.
    @Override
    public void onImageClick(int position) {
        String toastDisplay = "This is Image No." + (position + 1) + " of " + mImages.getAdapter().getCount();
        Toast.makeText(DetailActivity.this, toastDisplay, Toast.LENGTH_SHORT).show();
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
    // EFFECTS: Center the map to the venue's location.
    private void centerOnVenueLocation(){
        mMapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLng(venue.getLatLng()));
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Adds a new marker to mMapFragment at a venue position with the venue name as
    // title.
    private void addVenueMarker(){
        MarkerOptions marker = new MarkerOptions().position(venue.getLatLng()).title(venue.getName());
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
    // EFFECTS: Sets the Toolbar title to the venue's name.
    private void drawVenueTitle() {
        getSupportActionBar().setTitle(venue.getName());
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Draws back arrow in toolbar.
    private void drawBackArrow(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Receives a Venue Event, sets the viewpager and mapfragment with the Venue's data.
    //EventBus method to receive Venue
    // TODO: Set up the Recycle view of the Venue's features.
    public void onEventMainThread(VenueEvent venueEvent){
        this.venue = venueEvent.getVenue();
       // Toast.makeText(this, "Venue received " + venue.getName(), Toast.LENGTH_SHORT).show();
        setUpViewPager(venue.getImageUrls());
        setUpRecycleView();
        setUpToolbar();
        setUpRentButton();
        setUpMapFragment();
    }


    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Sets mImages to a new adapter with imageUrls as images. Sets this as onClickListener.
    private void setUpViewPager(final List<String> imageUrls) {
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageUrls);
        mImages.setAdapter(adapter);
        adapter.setOnImageListener(this);
    }


    // TODO: Set up the Venue's features recycleview.
    private void setUpRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFeatures.setLayoutManager(linearLayoutManager);
               FeatureAdapter featureViewAdapter = new FeatureAdapter(this, venue.getFeatures());
               mFeatures.setAdapter(featureViewAdapter);
    }



}
