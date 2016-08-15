package com.example.user.myapplication;

import android.app.Application;

import com.example.user.myapplication.model.PokemonInfo;
import com.example.user.myapplication.model.PokemonType;
import com.example.user.myapplication.model.SearchPokemonInfo;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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
        ParseObject.registerSubclass(SearchPokemonInfo.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .enableLocalDataStore()
                .applicationId("d41d8cd98f00b204e9800998ecf8427e") //change to your AppId
                        //.clientKey("YJy27NUjuLfJaicKAFReic3gpCFxdemFsPrsQj05") //change to your clientKey
                .server("http://140.112.30.43:1337/parse")
                .build());

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheSize(50 * 1024 * 1024) //50MB
                .diskCacheFileCount(100)
                .build();

        ImageLoader.getInstance().init(config);

    }

}
