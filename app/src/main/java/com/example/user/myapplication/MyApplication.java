package com.example.user.myapplication;

import android.app.Application;

import com.example.user.myapplication.model.PokemonInfo;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by user on 2016/8/8.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        ParseObject.registerSubclass(PokemonInfo.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .enableLocalDataStore()
                .applicationId("aBriKu0h4EZgnb8Sft9Uv4HyDZHOj01WZQp3jPs1")
                .clientKey("YJy27NUjuLfJaicKAFReic3gpCFxdemFsPrsQj05")
                .server("https://parseapi.back4app.com/")
                .build());


    }

}
