package com.example.gerardogtn.partyrock.ui.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.ui.fragment.HomeListFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.BreakIterator;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
             GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private boolean mResolvingError = false;

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;



    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) { 
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buildGoogleApiClient();
        ButterKnife.bind(this);
        setUpToolbar();
        addHomeListFragment();
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
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "onConnected executed.");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            String position = "Lat: " +
                    String.valueOf(mLastLocation.getLatitude()) +
                    "Lon: " +
                    String.valueOf(mLastLocation.getLongitude());

            Toast.makeText(this, position, Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "Location was null.");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed to get location", Toast.LENGTH_LONG).show();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Sets support action toolbar with mToolbar.
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }


    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Draws a HomeListFragment on this.mFragmentContainer
    private void addHomeListFragment() {
        FragmentTransaction tm = getSupportFragmentManager().beginTransaction();
        HomeListFragment homeListFragment = HomeListFragment.newInstance();
        tm.replace(R.id.fragment_container, homeListFragment);
        tm.commit();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Builds the API for Google's LocationServices
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

}
