package com.example.user.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.fragment.PokemonSearchFragment;
import com.example.user.myapplication.model.SearchPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
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

        PokemonSearchListViewAdapter adapter;
        ImageView appearanceImg;
        TextView nameText;
        TextView hpText;
        TextView[] typeTexts = new TextView[2];

        ViewHolder(View rowView, PokemonSearchListViewAdapter adapter) {
            this.adapter = adapter;

            appearanceImg = (ImageView) rowView.findViewById(R.id.listImg);
            nameText = (TextView) rowView.findViewById(R.id.nameText);
            hpText = (TextView) rowView.findViewById(R.id.hpText);
            typeTexts[0] = (TextView) rowView.findViewById(R.id.type1Text);
            typeTexts[1] = (TextView) rowView.findViewById(R.id.type2Text);
        }

        public void setView(SearchPokemonInfo data) {
            typeTexts[0].setText("");
            typeTexts[1].setText("");
            ArrayList<Integer> typeIndices = data.getTypeIndices();
            for(int i = 0;i < typeIndices.size();i++) {
                if(adapter.searchFragment.typeList != null) {
                    int typeIndex = typeIndices.get(i);
                    if(adapter.searchFragment.typeList.get(0).equals("none")) {
                        typeIndex++;
                    }
                    typeTexts[i].setText(adapter.searchFragment.typeList.get(typeIndex));
                }
            }

            hpText.setText(String.valueOf(data.getHP()));
            nameText.setText(data.getName());

            ImageLoader.getInstance().displayImage(
                    "http://www.csie.ntu.edu.tw/~r03944003/listImg/" + data.getPokedex() + ".png",
                    appearanceImg);
        }

    }


}
