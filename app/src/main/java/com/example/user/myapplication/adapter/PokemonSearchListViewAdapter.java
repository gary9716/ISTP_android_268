package com.example.user.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchPokemonInfo dataItem = getItem(position);
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = inflater.inflate(mRowViewLayoutId, parent, false);
            viewHolder = new ViewHolder(convertView, this);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.setView(dataItem);

        return convertView;

    }

    public static class ViewHolder {

        ViewHolder(View rowView, PokemonSearchListViewAdapter adapter) {

        }

        public void setView(SearchPokemonInfo data) {

        }

    }


}
