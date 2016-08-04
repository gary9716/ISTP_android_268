package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 2016/8/1.
 */
public class CustomizedActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        if(isTaskRoot()) {
            moveTaskToBack(true);
        }
        else {
            super.onBackPressed();
        }
    }

}
