package com.example.user.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.example.user.myapplication.fragment.PokemonSearchFragment;
import com.example.user.myapplication.model.SearchPokemonInfo;

import java.util.List;

/**
 * Created by user on 2016/8/15.
 */
public class PokemonSearchListViewAdapter extends ArrayAdapter<SearchPokemonInfo> {

    int mRowViewLayoutId;
    LayoutInflater inflater;
    PokemonSearchFragment searchFragment;

    public PokemonSearchListViewAdapter(Context context,
                                        int resource,
                                        List<SearchPokemonInfo> objects,
                                        PokemonSearchFragment fragment) {

        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        mRowViewLayoutId = resource;
        searchFragment = fragment;

    }





}
