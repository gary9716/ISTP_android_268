package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.myapplication.adapter.PokemonListViewAdapter;
import com.example.user.myapplication.model.OwningPokemonDataManager;
import com.example.user.myapplication.model.PokemonInfo;

import java.util.ArrayList;

/**
 * Created by user on 2016/7/25.
 */
public class PokemonListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    PokemonListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        ListView listView = (ListView)findViewById(R.id.listView);

        OwningPokemonDataManager dataManager = new OwningPokemonDataManager(this);

        ArrayList<PokemonInfo> pokemonInfos = dataManager.getPokemonInfos();
        adapter = new PokemonListViewAdapter(this, //context
                R.layout.row_view_pokemon_list, //row view layout id
                pokemonInfos); //data

        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_delete) {
            Log.d("menuItem", "action_delete");
            for(PokemonInfo pokemonInfo : adapter.selectedPokemons) {
                adapter.remove(pokemonInfo);
            }

            return true;
        }
        else if(itemId == R.id.action_heal) {
            Log.d("menuItem", "action_heal");
            return true;
        }
        else if(itemId == R.id.action_settings) {
            Log.d("menuItem", "action_settings");
            return true;
        }

        return false;
    }

    public final static int detailActivityRequestCode = 1;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PokemonInfo pokemonInfo = adapter.getItem(position);
        Intent intent = new Intent(PokemonListActivity.this, PokemonDetailActivity.class);
        intent.putExtra(PokemonInfo.parcelKey, pokemonInfo);
        startActivityForResult(intent, detailActivityRequestCode);

    }
}
