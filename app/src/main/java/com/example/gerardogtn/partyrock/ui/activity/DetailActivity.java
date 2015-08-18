package com.example.gerardogtn.partyrock.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Space;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Feature;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.ui.adapter.ImagePagerAdapter;
import com.example.gerardogtn.partyrock.ui.fragment.FeaturesFragment;
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
public class DetailActivity extends AppCompatActivity implements ImagePagerAdapter.OnImageClickListener {

    public static final String LOG = DetailActivity.class.getSimpleName();
    private static final String FTAG = "feats";

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    @Bind(R.id.venue_images)
    ViewPager mImages;


    @Bind(R.id.btn_rent)
    Button rentButton;

    @Bind(R.id.btn_features)
    Button featButton;

    @Bind(R.id.btn_more_features)
    Button moreFeatsButton;

    @Bind({R.id.feature_image1, R.id.feature_image2, R.id.feature_image3, R.id.feature_image4, R.id.feature_image5})
    List<ImageView> featureIcons;

    @Bind({R.id.feature_space1, R.id.feature_space2, R.id.feature_space3, R.id.feature_space4, R.id.feature_space5})
    List<Space> featureSpaces;

    private SupportMapFragment mMapFragment;
    private Venue mVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mVenue = EventBus.getDefault().removeStickyEvent(Venue.class);
        if (mVenue == null) {
            rebuildVenue(savedInstanceState);
        }
        setUpViewPager(mVenue.getImageUrls());
        setUpFeatures();
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

    private void rebuildVenue(Bundle savedInstanceState) {
        mVenue = (Venue) savedInstanceState.getSerializable("lastVenue");
        ArrayList<Feature> features = new ArrayList<>();
        for (Feature f : mVenue.getFeatures()) {
            features.add(new Feature(f.getFeatureName(), f.isAvailable()));
        }
        mVenue.setFeatures(features);
    }

    // REQUIRES: Position is valid.
    // MODIFIES: None.
    // EFFECTS:  Opens ViewPagerFullScreenActivity on Image selected.
    @Override
    public void onImageClick(int position) {
        EventBus.getDefault().postSticky(mVenue);
        EventBus.getDefault().postSticky(position-1);
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

    private void setUpFeatures() {
        if (mVenue.getFeatures().size() <= 5) {
            for (int i = 0; i < mVenue.getFeatures().size(); i++) {
                featureIcons.get(i).setVisibility(View.VISIBLE);
                featureIcons.get(i).setImageResource(mVenue.getFeatures().get(i).getImageResource());
                featureSpaces.get(i).setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                featureIcons.get(i).setVisibility(View.VISIBLE);
                featureIcons.get(i).setImageResource(mVenue.getFeatures().get(i).getImageResource());
                featureSpaces.get(i).setVisibility(View.VISIBLE);
            }
            featureSpaces.get(4).setVisibility(View.VISIBLE);
            moreFeatsButton.setVisibility(View.VISIBLE);
            moreFeatsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFeaturesDialog();
                }
            });
        }
        featButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeaturesDialog();
            }
        });

    }

    private void showFeaturesDialog() {
        EventBus.getDefault().postSticky(mVenue.getFeatures());

        FragmentManager fm = getSupportFragmentManager();

        FeaturesFragment featuresFragment = new FeaturesFragment();

        featuresFragment.show(fm, FTAG);

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
        //Add a new List for infinite scrolling. (This way, the imageUrls list is not modified)
        List<String> infiniteList = new ArrayList<>();
        //Infinite scrolling is created using dummy list elements at the first and last position.
        infiniteList.addAll(imageUrls);
        infiniteList.add(imageUrls.get(0));
        infiniteList.add(0, infiniteList.get(imageUrls.size() - 1));
        //Update the position of the selected image
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, infiniteList);
        mImages.setAdapter(adapter);
        //Using the Infinite scrolling, position value in the listener must be modified to
        // (position -1) so the image is correct when selected
        adapter.setOnImageListener(this);
        setUpPageScrollListener();
    }

    private void setUpPageScrollListener() {
        //A Page change listener is used to create Infinite scrolling and to update screen elements.
        mImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int mPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //If positioned in the dummyvalues, the viewpager is redirected to the original item.
                if (state == ViewPager.SCROLL_STATE_IDLE) { //this is triggered when the switch to a new page is complete
                    final int lastPosition = mImages.getAdapter().getCount() - 1;
                    if (mPosition == lastPosition) {
                        mImages.setCurrentItem(1, false); //false so we don't animate
                        mPosition = 1;
                    } else if (mPosition == 0) {
                        mImages.setCurrentItem(lastPosition - 1, false);
                        mPosition = lastPosition - 1;
                    }
                }
            }

        });
    }


}
