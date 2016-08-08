package com.example.user.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lab430 on 16/7/16.
 */
@ParseClassName("PokemonInfo")
public class PokemonInfo extends ParseObject implements Parcelable {

    public final static String parcelKey = "PokemonInfo.parcel";
    public final static String nameKey = "PokemonInfo.name";
    public final static String listImgIdKey = "PokemonInfo.listImgId";
    public final static String levelKey = "PokemonInfo.level";
    public final static String currentHPKey = "PokemonInfo.currentHP";
    public final static String maxHPKey = "PokemonInfo.maxHP";
    public final static String type1Key = "PokemonInfo.type1";
    public final static String type2Key = "PokemonInfo.type2";
    public final static String skillKey = "PokemonInfo.skill";
    public final static String detailImgIdKey = "PokemonInfo.detailImgId";

    public final static int numCurrentSkills = 4;
    public static String[] typeNames;

//    private int listImgId;
//    private String name;
//    private int level;
//    private int currentHP;
//    private int maxHP;

    public boolean isSelected = false;
    public boolean isHealing = false;

    //detail info
//    private int detailImgId;
//    private int type_1;
//    private int type_2;
    private String[] skill = new String[numCurrentSkills];

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeInt(this.getLevel());
        dest.writeInt(this.getCurrentHP());
        dest.writeInt(this.getMaxHP());
        dest.writeInt(this.getDetailImgId());
        dest.writeInt(this.getType_1());
        dest.writeInt(this.getType_2());
        dest.writeStringArray(this.getSkill());
    }

    public PokemonInfo() {
    }

    protected PokemonInfo(Parcel in) {
        this.setName(in.readString());
        this.setLevel(in.readInt());
        this.setCurrentHP(in.readInt());
        this.setMaxHP(in.readInt());
        this.setDetailImgId(in.readInt());
        this.setType_1(in.readInt());
        this.setType_2(in.readInt());
        this.setSkill(in.createStringArray());
    }

    public static final Parcelable.Creator<PokemonInfo> CREATOR = new Parcelable.Creator<PokemonInfo>() {
        @Override
        public PokemonInfo createFromParcel(Parcel source) {
            return new PokemonInfo(source);
        }

        @Override
        public PokemonInfo[] newArray(int size) {
            return new PokemonInfo[size];
        }
    };

    //getter and setter
    public int getListImgId() {
        return getInt(listImgIdKey);
    }

    public void setListImgId(int listImgId) {
        put(listImgIdKey, listImgId);
    }

    public String getName() {
        return getString(nameKey);
    }

    public void setName(String name) {
        put(nameKey, name);
    }

    public int getLevel() {
        return getInt(levelKey);
    }

    public void setLevel(int level) {
        put(levelKey, level);
    }

    public int getCurrentHP() {
        return getInt(currentHPKey);
    }

    public void setCurrentHP(int currentHP) {
        put(currentHPKey, currentHP);
    }

    public int getMaxHP() {
        return getInt(maxHPKey);
    }

    public void setMaxHP(int maxHP) {
        put(maxHPKey, maxHP);
    }

    public int getDetailImgId() {
        return getInt(detailImgIdKey);
    }

    public void setDetailImgId(int detailImgId) {
        put(detailImgIdKey, detailImgId);
    }

    public int getType_1() {
        return getInt(type1Key);
    }

    public void setType_1(int type_1) {
        put(type1Key, type_1);
    }

    public int getType_2() {
        return getInt(type2Key);
    }

    public void setType_2(int type_2) {
        put(type2Key, type_2);
    }

    public String[] getSkill() {
        return skill;
    }

    public void setSkill(String[] skill) {
        List<String> skillList = new ArrayList<>(skill.length);
        for(String skillName : skill) {
            skillList.add(skillName);
        }
        put(skillKey, skillList);
        this.skill = skill;
    }
}
