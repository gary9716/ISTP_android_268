package com.example.user.myapplication.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by lab430 on 16/8/3.
 */
public class Utils {

    public static Drawable getDrawable(Context context, int drawableId) {
        if(Build.VERSION.SDK_INT < 21) {
            return context.getResources().getDrawable(drawableId);
        }
        else {
            return context.getResources().getDrawable(drawableId, null);
        }
    }

    public static MediaPlayer loadSongFromAssets(Context context, String fileName) {
        MediaPlayer mediaPlayer = null;
        try {
            AssetFileDescriptor descriptor;
            descriptor = context.getAssets().openFd(fileName);

            if(descriptor != null) {
                mediaPlayer = new MediaPlayer();

                long start = descriptor.getStartOffset();
                long end = descriptor.getLength();

                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), start, end);
                mediaPlayer.prepareAsync();
            }

        }
        catch(Exception e) {
        }

        return mediaPlayer;
    }

    public static byte[] urlToBytes(String urlString)
    {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1)
            {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double[] getLatLngFromAddress(String address)
    {
        try {
            address = URLEncoder.encode(address, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String apiURL = "http://maps.google.com/maps/api/geocode/json?address=" + address;

        byte[] data = Utils.urlToBytes(apiURL);

        if(data == null)
            return null;

        String result = new String(data);

        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("status").equals("OK"))
            {
                JSONObject location = jsonObject.getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");

                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                return new double[]{lat, lng};
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
