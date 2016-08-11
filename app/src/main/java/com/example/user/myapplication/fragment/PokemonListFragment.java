package com.example.user.myapplication.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myapplication.MainActivity;
import com.example.user.myapplication.PokemonDetailActivity;
import com.example.user.myapplication.R;
import com.example.user.myapplication.adapter.PokemonListViewAdapter;
import com.example.user.myapplication.model.OwningPokemonDataManager;
import com.example.user.myapplication.model.PokemonInfo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/4.
 */
public class PokemonListFragment extends Fragment implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener, FindCallback<PokemonInfo>{

    private Activity activity;
    private ArrayList<PokemonInfo> pokemonInfos;
    private AlertDialog alertDialog;
    private PokemonListViewAdapter adapter;

    public static PokemonListFragment newInstance() {

//        Bundle args = new Bundle();
        PokemonListFragment fragment = new PokemonListFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    private final static String recordIsInDBKey = "recordIsInDB";

    public void prepareListViewData() {
        SharedPreferences preferences = activity.getSharedPreferences(Application.class.getName(), Activity.MODE_PRIVATE);
        boolean recordIsInDB = preferences.getBoolean(recordIsInDBKey, false);

        OwningPokemonDataManager dataManager = new OwningPokemonDataManager(activity);
        dataManager.loadPokemonTypes();

        pokemonInfos = new ArrayList<>();

        if(!recordIsInDB) {
            dataManager.loadListViewData();
            int selectedPokemonIndex = activity.getIntent().getIntExtra(MainActivity.optionSelectedKey, 0);
            PokemonInfo[] initThreePokemons = dataManager.getInitThreePokemonInfos();

            ArrayList<PokemonInfo> tempInfos = dataManager.getPokemonInfos();
            for(PokemonInfo pokemonInfo : tempInfos) {
                pokemonInfos.add(pokemonInfo);
            }
            pokemonInfos.add(0, initThreePokemons[selectedPokemonIndex]);

            //Save into DB
            PokemonInfo.initTable(pokemonInfos);
            preferences.edit().putBoolean(recordIsInDBKey, true).commit();
        }
        else {
            //load from DB
            ParseQuery<PokemonInfo> query = PokemonInfo.getQuery();
            query.fromPin(PokemonInfo.localDBTableName).findInBackground(this);
            query = PokemonInfo.getQuery();
            query.findInBackground(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        prepareListViewData();

    }

    View fragmentView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.activity_pokemon_list, container, false);
            ListView listView = (ListView) fragmentView.findViewById(R.id.listView);
            adapter = new PokemonListViewAdapter(activity, //context
                    R.layout.row_view_pokemon_list, //row view layout id
                    pokemonInfos); //data

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);

            alertDialog = new AlertDialog.Builder(activity)
                    .setMessage("你確定要丟棄神奇寶貝們嗎?")
                    .setTitle("警告")
                    .setNegativeButton("取消", this)
                    .setPositiveButton("確認", this)
                    .setCancelable(false)
                    .create();

            setHasOptionsMenu(true);
        }
        return fragmentView;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if(which == AlertDialog.BUTTON_NEGATIVE) {
            Toast.makeText(activity, "取消丟棄", Toast.LENGTH_SHORT).show();
        }
        else if(which == AlertDialog.BUTTON_POSITIVE) {
            for(PokemonInfo pokemonInfo : adapter.selectedPokemons) {
                removePokemonInfo(pokemonInfo);
            }
            adapter.selectedPokemons.clear();

        }
    }

    public final static int detailActivityRequestCode = 1;
    public final static int removeFromList = 1;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PokemonInfo pokemonInfo = adapter.getItem(position);
        Intent intent = new Intent(activity, PokemonDetailActivity.class);
        intent.putExtra(PokemonInfo.parcelKey, pokemonInfo);
        startActivityForResult(intent, detailActivityRequestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == detailActivityRequestCode) {
            if(resultCode == removeFromList) {
                String pokemonName = data.getStringExtra(PokemonInfo.nameKey);
                PokemonInfo pokemonInfo = adapter.getItemWithName(pokemonName);
                if(pokemonInfo != null) {
                    removePokemonInfo(pokemonInfo);
                    adapter.selectedPokemons.remove(pokemonInfo);
                    Toast.makeText(activity, pokemonInfo.getName() + "已被存入電腦", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_action_bar_menu, menu);
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

    public void removePokemonInfo(PokemonInfo pokemonInfo) {
        adapter.remove(pokemonInfo);
        //remove from database
        pokemonInfo.unpinInBackground(PokemonInfo.localDBTableName);
        pokemonInfo.deleteEventually();

    }

    @Override
    public void done(List<PokemonInfo> objects, ParseException e) {
        if(e == null) {
            pokemonInfos.clear();
            for(PokemonInfo object : objects) {
                pokemonInfos.add(object);
            }
        }

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
