package com.example.user.myapplication.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.example.user.myapplication.MainActivity;
import com.example.user.myapplication.model.OwningPokemonDataManager;
import com.example.user.myapplication.model.PokemonInfo;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/4.
 */
public class PokemonListFragment extends Fragment {

    private Activity activity;

    public static PokemonListFragment newInstance() {

//        Bundle args = new Bundle();
        PokemonListFragment fragment = new PokemonListFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        OwningPokemonDataManager dataManager = new OwningPokemonDataManager(activity);
        int selectedPokemonIndex = activity.getIntent().getIntExtra(MainActivity.optionSelectedKey, 0);
        PokemonInfo[] initThreePokemons = dataManager.getInitThreePokemonInfos();

        ArrayList<PokemonInfo> pokemonInfos = dataManager.getPokemonInfos();
        pokemonInfos.add(0, initThreePokemons[selectedPokemonIndex]);

    }

    

}
