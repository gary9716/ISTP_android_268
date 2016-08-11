package com.example.user.myapplication.model;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lab430 on 16/7/24.
 */
public class OwningPokemonDataManager {
    Context mContext;
    Resources mRes;
    String packageName;
    ArrayList<PokemonInfo> pokemonInfos = null;
    ArrayList<String> pokemonNames = null;
    PokemonInfo[] initThreePokemons = new PokemonInfo[3];

    public OwningPokemonDataManager(Context context) {
        mContext = context;
        mRes = mContext.getResources();
        packageName = context.getPackageName();

    }

    public void loadPokemonTypes() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("pokemon_types.csv")));
            PokemonInfo.typeNames = reader.readLine().split(",");
            reader.close();
        }
        catch (Exception e) {

        }
    }

    public void loadListViewData() {
        pokemonInfos = new ArrayList<>();
        BufferedReader reader;
        String line = null;
        String[] dataFields = null;
        try {
            //load initial three pokemon data
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("init_pokemon_data.csv")));
            for(int i = 0;i < 3;i++) {
                dataFields = reader.readLine().split(",");
                initThreePokemons[i] = constructPokemonInfo(dataFields);
            }
            reader.close();

            //load rest of pokemon data
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("pokemon_data.csv")));
            while ((line = reader.readLine()) != null) {
                dataFields = line.split(",");
                pokemonInfos.add(constructPokemonInfo(dataFields));
            }
            reader.close();

        }
        catch(Exception e) {
            Log.d("testCsv", e.getLocalizedMessage());
        }


    }

    static final int skill_startIndex = 8;

    private PokemonInfo constructPokemonInfo(String[] dataFields) {
        PokemonInfo pokemonInfo = new PokemonInfo();
        pokemonInfo.setDetailImgId(mRes.getIdentifier("detail_" + dataFields[1],"drawable",packageName));
        pokemonInfo.setListImgId(mRes.getIdentifier("list_" + dataFields[1],"drawable",packageName));
        pokemonInfo.setName(dataFields[2]);
        pokemonInfo.setLevel(Integer.valueOf(dataFields[3]));
        pokemonInfo.setCurrentHP(Integer.valueOf(dataFields[4]));
        pokemonInfo.setMaxHP(Integer.valueOf(dataFields[5]));
        pokemonInfo.setType_1(Integer.valueOf(dataFields[6]));
        pokemonInfo.setType_2(Integer.valueOf(dataFields[7]));
        //if strings are not enough, rest of array index would point to null.
        for(int i = skill_startIndex;i < dataFields.length;i++) {
            pokemonInfo.getSkill()[i - skill_startIndex] = dataFields[i];
        }

        return pokemonInfo;
    }

    public ArrayList<String> getPokemonNames() {
        return pokemonNames;
    }

    public ArrayList<PokemonInfo> getPokemonInfos() {
        return pokemonInfos;
    }

    public PokemonInfo[] getInitThreePokemonInfos() {
        return initThreePokemons;
    }

}
