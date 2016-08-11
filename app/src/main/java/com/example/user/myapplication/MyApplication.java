package com.example.user.myapplication;

import android.app.Application;

import com.example.user.myapplication.model.PokemonInfo;
import com.example.user.myapplication.model.PokemonType;
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
        ParseObject.registerSubclass(PokemonType.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .enableLocalDataStore()
                .applicationId("d41d8cd98f00b204e9800998ecf8427e") //change to your AppId
                        //.clientKey("YJy27NUjuLfJaicKAFReic3gpCFxdemFsPrsQj05") //change to your clientKey
                .server("http://140.112.30.43:1337/parse")
                .build());


    }

}
