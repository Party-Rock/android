package com.example.gerardogtn.partyrock.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gerardogtn.partyrock.R;
import com.example.gerardogtn.partyrock.ui.fragment.ConfirmationFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gerardogtn on 8/1/15.
 */
public class ConfirmationActivity extends AppCompatActivity{

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setUpToolbar();
        drawBackArrow();
        if (savedInstanceState==null){
            addHomeListFragment();}
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

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Sets support action toolbar with mToolbar.
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS:  Draws back arrow in toolbar.
    private void drawBackArrow(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // REQUIRES: None.
    // MODIFIES: this.
    // EFFECTS: Draws a HomeListFragment on this.mFragmentContainer
    private void addHomeListFragment() {
        FragmentTransaction tm = getSupportFragmentManager().beginTransaction();
        ConfirmationFragment confirmationFragment= new ConfirmationFragment();
        tm.replace(R.id.fragment_container, confirmationFragment);
        tm.commit();
    }
}
