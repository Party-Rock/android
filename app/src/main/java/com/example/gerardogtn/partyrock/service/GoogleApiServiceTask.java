package com.example.gerardogtn.partyrock.service;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.CountDownLatch;

import de.greenrobot.event.EventBus;

/**
 * Created by Emilio on 21/08/2015.
 */
public class GoogleApiServiceTask extends AsyncTask<Void,Void, Location> {
    private static final String TAG =  "Api Task";
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Context mContext;

    public GoogleApiServiceTask(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();

        this.mContext = context;
    }


    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Location doInBackground(Void... params) {
        Log.i(TAG, "on Background.");
        final CountDownLatch latch = new CountDownLatch(1);


        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
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
                    Log.i(TAG, position);
                } else {
                    Log.e(TAG, "Location was null.");
                }
                latch.countDown();
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });
        mGoogleApiClient.registerConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.e(TAG, "Location failed.");
                latch.countDown();
            }
        });

        mGoogleApiClient.connect();
        try {
            latch.await();
        } catch (InterruptedException e) {
            return null;
        }
        if (!mGoogleApiClient.isConnected()) {
            return null;
        }
        try {
            return mLastLocation;
        }
        finally {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param location The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Location location) {
        super.onPostExecute(location);
        EventBus.getDefault().post(new GoogleApiEvent(location));
    }
}
