package com.example.user.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.model.PokemonInfo;
import com.squareup.picasso.Picasso;
import com.example.user.myapplication.R;

import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class PokemonListViewAdapter extends ArrayAdapter<PokemonInfo> {

    int mRowLayoutId;
    LayoutInflater mInflater;
    Picasso mPicasso;
    public PokemonListViewAdapter(Context context, int rowLayoutId, List<PokemonInfo> objects) {
        super(context, rowLayoutId, objects);
        mRowLayoutId = rowLayoutId;
        mInflater = LayoutInflater.from(context);
        mPicasso = Picasso.with(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PokemonInfo pokemonInfo = getItem(position);
        ViewHolder viewHolder = null;
        if(convertView == null) { //first time
            convertView = mInflater.inflate(mRowLayoutId, parent, false);
            viewHolder = new ViewHolder(convertView, mPicasso);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.setView(pokemonInfo);

        return convertView;
    }

    public PokemonInfo getItemWithName(String name) {

        for(int i = 0;i < getCount();i++) {
            PokemonInfo pokemonInfo = getItem(i);
            if(name.equals(pokemonInfo.name)) {
                return pokemonInfo;
            }
        }

        return null;
    }

    public void update(PokemonInfo newData) {
        PokemonInfo oldData = getItemWithName(newData.name);
        oldData.skill = newData.skill;
        oldData.currentHP = newData.currentHP;
        oldData.maxHP = newData.maxHP;
        oldData.level = newData.level;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        private ImageView appearanceImg;
        private TextView nameText;
        private TextView levelText;
        private TextView currentHPText;
        private TextView maxHPText;
        private ProgressBar hpBar;
        private Picasso mPicasso;

        public ViewHolder(View row_view, Picasso picasso) {
            appearanceImg = (ImageView)row_view.findViewById(R.id.appearance_img);
            nameText = (TextView)row_view.findViewById(R.id.name_text);
            levelText = (TextView)row_view.findViewById(R.id.level_text);
            currentHPText = (TextView)row_view.findViewById(R.id.currentHP_text);
            maxHPText = (TextView)row_view.findViewById(R.id.maxHP_text);
            hpBar = (ProgressBar)row_view.findViewById(R.id.HP_bar);
            mPicasso = picasso;
        }

        public void setView(PokemonInfo pokemonInfo) {
            currentHPText.setText(String.valueOf(pokemonInfo.currentHP));
            maxHPText.setText(String.valueOf(pokemonInfo.maxHP));
            levelText.setText(String.valueOf(pokemonInfo.level));
            nameText.setText(String.valueOf(pokemonInfo.name));
            int progress = (int)((((float)pokemonInfo.currentHP)/pokemonInfo.maxHP) * 100);
            hpBar.setProgress(progress);
            mPicasso.load(pokemonInfo.listImgId).into(appearanceImg);
        }



    }


}
