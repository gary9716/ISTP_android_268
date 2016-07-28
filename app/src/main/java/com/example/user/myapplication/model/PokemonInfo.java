package com.example.user.myapplication.model;

/**
 * Created by lab430 on 16/7/16.
 */
public class PokemonInfo {

    public final static int numCurrentSkills = 4;
    public static String[] typeNames;

    public int listImgId;
    public String name;
    public int level;
    public int currentHP;
    public int maxHP;

    public boolean isSelected = false;
    public boolean isHealing = false;

    //detail info
    public int detailImgId;
    public int type_1;
    public int type_2;
    public String[] skill = new String[numCurrentSkills];
}
