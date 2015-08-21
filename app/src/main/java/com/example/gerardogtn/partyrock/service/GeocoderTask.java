package com.example.gerardogtn.partyrock.service;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by Emilio on 20/08/2015.
 */
public class GeocoderTask extends AsyncTask<Double,Void,String> {
    private Context mContext;

    public GeocoderTask(Context context) {
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
    protected String doInBackground(Double... position) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        String address="";

        try {
            List<Address> addresses = geocoder.getFromLocation(position[0],
                    position[1], 1);
            for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                if (i==0){address=addresses.get(0).getAddressLine(i);}else{
                address = address + ", " + addresses.get(0).getAddressLine(i);}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param s The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        EventBus.getDefault().post(new GeocoderEvent(s));
    }
}
