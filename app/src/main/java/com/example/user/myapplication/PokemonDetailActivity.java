package com.example.user.myapplication;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.model.PokemonInfo;

/**
 * Created by user on 2016/7/28.
 */
public class PokemonDetailActivity extends AppCompatActivity {

    PokemonInfo mPokemonInfo;
    Resources mRes;
    String packageName;

    //UI
    TextView nameText;
    TextView levelText;
    TextView currentHP;
    TextView maxHP;
    TextView type_1;
    TextView type_2;
    TextView[] skillText;
    ProgressBar hpBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);
        mRes = getResources();
        packageName = getPackageName();
        mPokemonInfo = (PokemonInfo)getIntent().getParcelableExtra(PokemonInfo.parcelKey);
        skillText = new TextView[PokemonInfo.numCurrentSkills];

        setView();
    }

    private void setView() {
        nameText = (TextView)findViewById(R.id.name_text);
        levelText = (TextView)findViewById(R.id.level_text);
        currentHP = (TextView)findViewById(R.id.currentHP_text);
        maxHP = (TextView)findViewById(R.id.maxHP_text);
        type_1 = (TextView)findViewById(R.id.type_1_text);
        type_2 = (TextView)findViewById(R.id.type_2_text);
        hpBar = (ProgressBar)findViewById(R.id.HP_progressBar);
        for(int i = 0;i < PokemonInfo.numCurrentSkills;i++) {
            int skillTextId = mRes.getIdentifier(String.format("skill_%d_text",i+1),
                    "id",
                    packageName);
            skillText[i] = (TextView)findViewById(skillTextId);
        }

        nameText.setText(mPokemonInfo.name);
        levelText.setText(String.valueOf(mPokemonInfo.level));
        currentHP.setText(String.valueOf(mPokemonInfo.currentHP));
        maxHP.setText(String.valueOf(mPokemonInfo.maxHP));
        if(mPokemonInfo.type_1 != -1) {
            type_1.setText(PokemonInfo.typeNames[mPokemonInfo.type_1]);
        }
        if(mPokemonInfo.type_2 != -1) {
            type_2.setText(PokemonInfo.typeNames[mPokemonInfo.type_2]);
        }
        for(int i = 0;i < PokemonInfo.numCurrentSkills;i++) {
            if(mPokemonInfo.skill[i] != null) {
                skillText[i].setText(mPokemonInfo.skill[i]);
            }
            else {
                skillText[i].setText("");
            }
        }

        int progress = (int)((((float)mPokemonInfo.currentHP)/mPokemonInfo.maxHP) * 100);
        hpBar.setProgress(progress);


    }



}
