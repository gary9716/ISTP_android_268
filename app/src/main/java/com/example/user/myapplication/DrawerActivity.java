package com.example.user.myapplication;

import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.myapplication.fragment.PokemonListFragment;
import com.example.user.myapplication.fragment.PokemonMapFragment;
import com.example.user.myapplication.fragment.PokemonSearchFragment;
import com.example.user.myapplication.fragment.TestFragment;
import com.example.user.myapplication.model.Utils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Created by user on 2016/8/4.
 */
public class DrawerActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    Toolbar toolbar;
    AccountHeader headerResult;
    IProfile profile;
    Drawer drawer;
    Fragment[] fragments;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        fragments = new Fragment[3];
        fragments[0] = PokemonListFragment.newInstance();
//        fragments[0] = TestFragment.newInstance("fake 0");
        fragments[1] = PokemonSearchFragment.newInstance();
        fragments[2] = PokemonMapFragment.newInstance();

        fragmentManager = getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        for(int i = fragments.length - 1;i >= 0;i--) {
            replaceWithFragment(fragments[i]);
        }

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Application.class.getName(), MODE_PRIVATE);
        String name = preferences.getString(MainActivity.nameEditTextKey, "Batman");
        String email = preferences.getString(MainActivity.emailKey, "batman@gmail.com");
        String url = preferences.getString(MainActivity.profileImgUrlKey, null);
        if(url == null) {
            Drawable profileIcon = Utils.getDrawable(this, R.drawable.profile3);
            profile = new ProfileDrawerItem()
                    .withName(name)
                    .withEmail(email)
                    .withIcon(profileIcon);
        }
        else {
            profile = new ProfileDrawerItem()
                    .withName(name)
                    .withEmail(email)
                    .withIcon(url);
        }



        buildDrawerHeader(false, savedInstanceState);
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .inflateMenu(R.menu.drawer_menu)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        replaceWithFragment(fragments[position - 1]);
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

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

    private void replaceWithFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = drawer.saveInstanceState(outState);
        outState = headerResult.saveInstanceState(outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

        if(drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }
        else if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public void onBackStackChanged() {
        for(int i = 0;i < fragments.length;i++) {
            if(fragments[i].isVisible()) {
                drawer.setSelectionAtPosition(i + 1, false);
                break;
            }
        }
    }
}
