package com.example.gerardogtn.partyrock.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.data.model.Feature;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.service.GeocoderEvent;
import com.example.gerardogtn.partyrock.service.GeocoderTask;
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
    private static final String SCROLL_X = "scroll_position_x";
    private static final String SCROLL_Y = "scroll_position_y";

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    @Bind(R.id.txt_address)
    TextView mTxtAddress;

    @Bind(R.id.txt_description)
    TextView mTxtDescription;

    @Bind(R.id.venue_images)
    ViewPager mImages;

    @Bind(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    @Bind(R.id.btn_rent)
    Button rentButton;

    @Bind(R.id.btn_features)
    Button featButton;

    @Bind(R.id.btn_more_features)
    Button moreFeatsButton;

    @Bind(R.id.card_view_feature_button)
    CardView moreFeatsCardView;

    @Bind({R.id.feature_image1, R.id.feature_image2, R.id.feature_image3, R.id.feature_image4, R.id.feature_image5})
    List<ImageView> featureIcons;

    @Bind({R.id.feature_space1, R.id.feature_space2, R.id.feature_space3, R.id.feature_space4, R.id.feature_space5})
    List<Space> featureSpaces;

    private SupportMapFragment mMapFragment;
    private Venue mVenue;
    private Boolean searchVenueAlert;
    private String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        receiveVenue(savedInstanceState);
        decodeAddress(savedInstanceState);
        getParentActivityAlert();
        savedScrollState(savedInstanceState);
        setUpViewPager(mVenue.getImageUrls());
        setUpDescription();
        setUpFeatures();
        setUpToolbar();
        setUpRentButton();
        setUpMapFragment();
        setUpAddressTxt();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        EventBus.getDefault().postSticky(mVenue);
        outState.putSerializable("lastVenue", mVenue);
        outState.putInt(SCROLL_X, mNestedScrollView.getScrollX());
        outState.putInt(SCROLL_Y, mNestedScrollView.getScrollY());
        if (mAddress != null) {
            outState.putString("address", mAddress);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    // REQUIRES: Position is valid.
    // MODIFIES: None.
    // EFFECTS:  Opens ViewPagerFullScreenActivity on Image selected.
    @Override
    public void onImageClick(int position) {
        EventBus.getDefault().postSticky(mVenue);
        EventBus.getDefault().postSticky(position - 1);
        Intent intent = new Intent(DetailActivity.this, ViewPagerFullScreenActivity.class);
        startActivity(intent);

    }

    //Receives Venue and makes sure feature hash map is set
    private void receiveVenue(Bundle savedInstanceState) {
        mVenue = EventBus.getDefault().removeStickyEvent(Venue.class);
        if (mVenue == null) {
            rebuildVenue(savedInstanceState);
        }
        ArrayList<Feature> features = new ArrayList<>();
        for (Feature f : mVenue.getFeatures()) {
            features.add(new Feature(f.getFeatureName(), f.isAvailable()));
        }
        mVenue.setFeatures(features);
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = null;

        //If the venue is received from the SearchResultsActivity,
        // a intent to make this the parent activity is created.
        if (searchVenueAlert) {
            i = new Intent(this, SearchResultsActivity.class);
            // set any flags or extras that you need.
            // If you are reusing the previous Activity (i.e. bringing it to the top
            // without re-creating a new instance) set these flags:
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // Extras can be added as well.
        } else {
            i = new Intent(this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        return i;
    }

    //Verifies if there was a previous state and scrolls back to the last position.
    private void savedScrollState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            final int scrollX = savedInstanceState.getInt(SCROLL_X);
            final int scrollY = savedInstanceState.getInt(SCROLL_Y);
            mNestedScrollView.scrollTo(scrollX, scrollY);
        }
    }

    private void rebuildVenue(Bundle savedInstanceState) {
        mVenue = (Venue) savedInstanceState.getSerializable("lastVenue");
        ArrayList<Feature> features = new ArrayList<>();
        for (Feature f : mVenue.getFeatures()) {
            features.add(new Feature(f.getFeatureName(), f.isAvailable()));
        }
        mVenue.setFeatures(features);
    }

    private void setUpDescription() {
        StringBuffer res = new StringBuffer();

        String[] strArr = mVenue.getmDescription().toLowerCase().split("/. ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(". ");
        }
        mTxtDescription.setText(res.toString().trim());
    }


    //Ontins the address from lat,lng values
    private void decodeAddress(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        if (savedInstanceState != null) {
            mAddress = savedInstanceState.getString("address");
        }
        if (mAddress == null) {
            new GeocoderTask(this).execute(new Double[]{mVenue.getPosition().getLatitude(),
                    mVenue.getPosition().getLongitude()});
        }
    }

    private void setUpAddressTxt() {
        if (mAddress != null) {
            mTxtAddress.setVisibility(View.VISIBLE);
            mTxtAddress.setText(mAddress);
        }
    }

    //Receives true from the event, if the venue comes from the SearchResultsActivity.
    private void getParentActivityAlert() {
        searchVenueAlert = EventBus.getDefault().removeStickyEvent(Boolean.class);
        if (searchVenueAlert == null) {
            searchVenueAlert = false;
        }
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

    //Checks if the venue has more than 5 features and arrange the layout accordingly.
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
            //A dialogue stating how many features are not shown can also be implemented here.
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
        moreFeatsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeaturesDialog();
            }
        });
    }

    //Shows the Dialog where a recycler view of the features is shown.
    private void showFeaturesDialog() {
        EventBus.getDefault().postSticky(mVenue.getFeatures());

        FragmentManager fm = getSupportFragmentManager();

        FeaturesFragment featuresFragment = new FeaturesFragment();

        featuresFragment.show(fm, FTAG);

    }

    //Sends the venue to the confirmation activity and opens it.
    private void setUpRentButton() {
        setRentButtonText();
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(mVenue);
                if (mAddress != null) {
                    EventBus.getDefault().postSticky(mAddress);
                }
                Intent intent = new Intent(DetailActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });
    }

    //Shows a text in the button
    private void setRentButtonText() {
        String buttonText;
        buttonText = getString(R.string.rent_text) + " " + mVenue.getFormattedPrice();
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
        mImages.setCurrentItem(1);
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
                mPosition = position;
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

    public void onEvent(GeocoderEvent addressEvent) {
        mAddress = addressEvent.getAddress();
        setUpAddressTxt();
    }

}
