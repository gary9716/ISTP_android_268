package com.example.user.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.user.myapplication.model.Utils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Created by user on 2016/8/4.
 */
public class DrawerActivity extends AppCompatActivity{

    Toolbar toolbar;
    AccountHeader headerResult;
    IProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawable profileIcon = Utils.getDrawable(this, R.drawable.profile3);
        profile = new ProfileDrawerItem()
                .withName("Batman")
                .withEmail("batman@gmail.com")
                .withIcon(profileIcon);

        

    }

    private void buildDrawerHeader(boolean compact, Bundle savedInstanceState) {

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

    }

}
