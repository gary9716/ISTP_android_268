package com.example.user.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.user.myapplication.adapter.PokemonListViewAdapter;
import com.example.user.myapplication.model.OwningPokemonDataManager;
import com.example.user.myapplication.model.PokemonInfo;

import java.util.ArrayList;

/**
 * Created by user on 2016/7/25.
 */
public class PokemonListActivity extends CustomizedActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

    PokemonListViewAdapter adapter;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        ListView listView = (ListView)findViewById(R.id.listView);

        OwningPokemonDataManager dataManager = new OwningPokemonDataManager(this);

        int selectedPokemonIndex = getIntent().getIntExtra(MainActivity.optionSelectedKey, 0);
        PokemonInfo[] initThreePokemons = dataManager.getInitThreePokemonInfos();

        ArrayList<PokemonInfo> pokemonInfos = dataManager.getPokemonInfos();

        pokemonInfos.add(0, initThreePokemons[selectedPokemonIndex]);

        adapter = new PokemonListViewAdapter(this, //context
                R.layout.row_view_pokemon_list, //row view layout id
                pokemonInfos); //data

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        alertDialog = new AlertDialog.Builder(this)
                .setMessage("你確定要丟棄神奇寶貝們嗎?")
                .setTitle("警告")
                .setNegativeButton("取消", this)
                .setPositiveButton("確認", this)
                .setCancelable(false)
                .create();

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
            if(adapter.selectedPokemons.size() > 0)
                alertDialog.show();
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
    public final static int removeFromList = 1;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PokemonInfo pokemonInfo = adapter.getItem(position);
        Intent intent = new Intent(PokemonListActivity.this, PokemonDetailActivity.class);
        intent.putExtra(PokemonInfo.parcelKey, pokemonInfo);
        startActivityForResult(intent, detailActivityRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == detailActivityRequestCode) {
            if(resultCode == removeFromList) {
                String pokemonName = data.getStringExtra(PokemonInfo.nameKey);
                PokemonInfo pokemonInfo = adapter.getItemWithName(pokemonName);
                if(pokemonInfo != null) {
                    adapter.remove(pokemonInfo);
                    Toast.makeText(this, pokemonInfo.name + "已被存入電腦", Toast.LENGTH_LONG).show();
                }
            }
        }


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if(which == AlertDialog.BUTTON_NEGATIVE) {
            Toast.makeText(this, "取消丟棄", Toast.LENGTH_SHORT).show();
        }
        else if(which == AlertDialog.BUTTON_POSITIVE) {

            for(PokemonInfo pokemonInfo : adapter.selectedPokemons) {
                adapter.remove(pokemonInfo);
            }

        }

    }
}
