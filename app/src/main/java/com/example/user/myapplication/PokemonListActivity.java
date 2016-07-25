package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.myapplication.adapter.PokemonListViewAdapter;
import com.example.user.myapplication.model.OwningPokemonDataManager;
import com.example.user.myapplication.model.PokemonInfo;

import java.util.ArrayList;

/**
 * Created by user on 2016/7/25.
 */
public class PokemonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        ListView listView = (ListView)findViewById(R.id.listView);

        OwningPokemonDataManager dataManager = new OwningPokemonDataManager(this);

        ArrayList<PokemonInfo> pokemonInfos = dataManager.getPokemonInfos();
        PokemonListViewAdapter adapter = new PokemonListViewAdapter(this, //context
                R.layout.row_view_pokemon_list, //row view layout id
                pokemonInfos); //data

        listView.setAdapter(adapter);
    }


}
