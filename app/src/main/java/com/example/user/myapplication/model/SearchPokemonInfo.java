package com.example.user.myapplication.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/11.
 */

@ParseClassName("Pokemon")
public class SearchPokemonInfo extends ParseObject {

    public final static String nameKey = "name";
    public final static String hpKey = "hp";
    public final static String typesKey = "types";
    public final static String resIdKey = "resId";

    public static ParseQuery<SearchPokemonInfo> getQuery() {
        return ParseQuery.getQuery(SearchPokemonInfo.class);
    }

    public ArrayList<Integer> getTypeIndices() {
        return (ArrayList)get(typesKey);
    }

    public String getName() {
        return getString(nameKey);
    }

    public int getHP() {
        return getInt(hpKey);
    }

    public String getPokedex() {
        return getString(resIdKey);
    }


}
