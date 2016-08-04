package com.example.user.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.myapplication.fragment.LogFragment;
import com.example.user.myapplication.fragment.TestFragment;

/**
 * Created by user on 2016/8/4.
 */
public class FragmentTestActivity extends AppCompatActivity implements View.OnClickListener{

    Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        Button replaceF1Btn = (Button)findViewById(R.id.replace_fragment_1);
        replaceF1Btn.setOnClickListener(this);
        Button replaceF2Btn = (Button)findViewById(R.id.replace_fragment_2);
        replaceF2Btn.setOnClickListener(this);
        Button removeF2Btn = (Button)findViewById(R.id.remove_fragment_2);
        removeF2Btn.setOnClickListener(this);

        fragments = new Fragment[2];
        fragments[0] = TestFragment.newInstance("fragment 1");
        ((LogFragment)fragments[0]).actualName = "F1";
        fragments[1] = TestFragment.newInstance("fragment 2");
        ((LogFragment)fragments[1]).actualName = "F2";
        
    }


    @Override
    public void onClick(View v) {

    }
}
