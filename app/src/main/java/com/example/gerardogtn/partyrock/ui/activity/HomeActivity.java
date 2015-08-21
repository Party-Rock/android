package com.example.gerardogtn.partyrock.ui.activity;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.service.GoogleApiEvent;
import com.example.gerardogtn.partyrock.service.GoogleApiServiceTask;
import com.example.gerardogtn.partyrock.ui.fragment.HomeListFragment;

import de.greenrobot.event.EventBus;


public class HomeActivity extends AppCompatActivity{

    public static final String TAG = HomeActivity.class.getSimpleName();

    private Location mLastLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (mLastLocation==null){
            new GoogleApiServiceTask(this).execute();
        }
        setContentView(R.layout.activity_home);
        addHomeListFragment(savedInstanceState);
    }

    /**
     * This is the same as {@link #onSaveInstanceState} but is called for activities
     * created with the attribute {@link android.R.attr#persistableMode} set to
     * <code>persistAcrossReboots</code>. The {@link PersistableBundle} passed
     * in will be saved and presented in {@link #onCreate(Bundle, PersistableBundle)}
     * the first time that this activity is restarted following the next device reboot.
     *
     * @param outState           Bundle in which to place your saved state.
     * @param outPersistentState State which will be saved across reboots.
     * @see #onSaveInstanceState(Bundle)
     * @see #onCreate
     * @see #onRestoreInstanceState(Bundle, PersistableBundle)
     * @see #onPause
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
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
        EventBus.getDefault().unregister(this);
    }


    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Draws a HomeListFragment on this.mFragmentContainer
    private void addHomeListFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentTransaction tm = getSupportFragmentManager().beginTransaction();
            HomeListFragment homeListFragment = HomeListFragment.newInstance();
            tm.replace(R.id.fragment_container, homeListFragment, "home");
            tm.commit();
        } else {
            getSupportFragmentManager().findFragmentByTag("home");
        }

    }

    public void onEvent(GoogleApiEvent locationEvent) {
        mLastLocation = locationEvent.getLocation();

    }

}
