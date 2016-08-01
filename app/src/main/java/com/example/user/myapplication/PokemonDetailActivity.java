package com.example.user.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.model.PokemonInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by user on 2016/7/28.
 */
public class PokemonDetailActivity extends CustomizedActivity {

    PokemonInfo mPokemonInfo;
    Resources mRes;
    String packageName;

    //UI
    ImageView appearanceImg;
    TextView nameText;
    TextView levelText;
    TextView currentHP;
    TextView maxHP;
    TextView type_1;
    TextView type_2;
    TextView[] skillText;
    ProgressBar hpBar;
    Picasso mPicasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);
        mRes = getResources();
        mPicasso = Picasso.with(this);
        packageName = getPackageName();
        mPokemonInfo = getIntent().getParcelableExtra(PokemonInfo.parcelKey);
        skillText = new TextView[PokemonInfo.numCurrentSkills];

        setView();
    }

    private void setView() {
        appearanceImg = (ImageView)findViewById(R.id.detail_appearance_img);
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

        //bind with data
        mPicasso.load(mPokemonInfo.detailImgId).into(appearanceImg);
        nameText.setText(mPokemonInfo.name);
        levelText.setText(String.valueOf(mPokemonInfo.level));
        currentHP.setText(String.valueOf(mPokemonInfo.currentHP));
        maxHP.setText(String.valueOf(mPokemonInfo.maxHP));
        if(mPokemonInfo.type_1 != -1) {
            type_1.setText(PokemonInfo.typeNames[mPokemonInfo.type_1]);
        }
        else {
            type_1.setText("");
        }

        if(mPokemonInfo.type_2 != -1) {
            type_2.setText(PokemonInfo.typeNames[mPokemonInfo.type_2]);
        }
        else {
            type_2.setText("");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pokemon_detail_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_save) {
            Intent intent = new Intent();
            intent.putExtra(PokemonInfo.nameKey, mPokemonInfo.name);
            setResult(PokemonListActivity.removeFromList, intent);
            finish();
            return true;
        }
        else if(itemId == R.id.action_level_up) {

            return true;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        Log.d("detailStage","onDestroy");
        super.onDestroy();
    }
}
