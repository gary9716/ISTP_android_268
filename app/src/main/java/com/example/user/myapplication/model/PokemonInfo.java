package com.example.user.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.level);
        dest.writeInt(this.currentHP);
        dest.writeInt(this.maxHP);
        dest.writeInt(this.detailImgId);
        dest.writeInt(this.type_1);
        dest.writeInt(this.type_2);
        dest.writeStringArray(this.skill);
    }

    public PokemonInfo() {
    }

    protected PokemonInfo(Parcel in) {
        this.name = in.readString();
        this.level = in.readInt();
        this.currentHP = in.readInt();
        this.maxHP = in.readInt();
        this.detailImgId = in.readInt();
        this.type_1 = in.readInt();
        this.type_2 = in.readInt();
        this.skill = in.createStringArray();
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


}
