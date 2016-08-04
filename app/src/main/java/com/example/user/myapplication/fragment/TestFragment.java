package com.example.user.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.myapplication.R;

/**
 * Created by user on 2016/8/4.
 */
public class TestFragment extends LogFragment {

    public final static String contentKey = "TestFragment.content";
    private String mContent;

    public static TestFragment newInstance(String content) {

        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString(contentKey, content);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = getArguments().getString(contentKey);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_test,
                container,
                false);
        TextView textView = (TextView)fragmentView.findViewById(R.id.textView);
        textView.setText(mContent);

        return fragmentView;
    }

}
