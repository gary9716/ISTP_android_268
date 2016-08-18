package com.example.user.myapplication;

import android.os.AsyncTask;

import com.example.user.myapplication.model.Utils;
import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

/**
 * Created by user on 2016/8/18.
 */
public class GeoCodingTask extends AsyncTask<String, Void, double[]> {

    WeakReference<GeoCodingResponse> geoCodingResponseWeakReference;

    public GeoCodingTask(GeoCodingResponse geoCodingResponse) {
        geoCodingResponseWeakReference =
                new WeakReference<GeoCodingResponse>(geoCodingResponse);
    }

    @Override
    protected double[] doInBackground(String... params) {

        double[] latlng = Utils.getLatLngFromAddress(params[0]);
        if(latlng != null) {
            return latlng;
        }
        else {
            return null;
        }

    }

    @Override
    protected void onPostExecute(double[] latlng) {
        super.onPostExecute(latlng);
        if(latlng != null) {
            LatLng result = new LatLng(latlng[0], latlng[1]);
            if(geoCodingResponseWeakReference.get() != null) {
                geoCodingResponseWeakReference.get()
                        .callbackWithGeoCodingResult(result);
            }
        }
    }

    public interface GeoCodingResponse {
        void callbackWithGeoCodingResult(LatLng latLng);
    }

}
