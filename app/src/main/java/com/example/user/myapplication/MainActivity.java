package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user.myapplication.model.OwningPokemonDataManager;
import com.example.user.myapplication.model.PokemonInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    TextView infoText;
    RadioGroup optionGrp;
    EditText name_editText;
    int selectedOptionIndex = 0;
    String[] pokemonNames = new String[]{
        "小火龍","傑尼龜","妙蛙種子"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button confirm_button = (Button)findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(this);

        optionGrp = (RadioGroup) findViewById(R.id.optionsGroup);
        optionGrp.setOnCheckedChangeListener(this);

        infoText = (TextView) findViewById(R.id.infoText);
        name_editText = (EditText) findViewById(R.id.name_editText);


        OwningPokemonDataManager dataManager = new OwningPokemonDataManager(this);
        ArrayList<PokemonInfo> pokemonInfos = dataManager.getPokemonInfos();
        for(PokemonInfo pokemonInfo : pokemonInfos) {
            Log.d("QAQ", pokemonInfo.name);
        }

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.confirm_button) {
            infoText.setText(String.format("你好, 訓練家%s 歡迎來到神奇寶貝的世界,你的夥伴是%s",
                    name_editText.getText().toString(),
                    pokemonNames[selectedOptionIndex]));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int radioGrpID = group.getId();
        if(radioGrpID == R.id.optionsGroup) {
            switch(checkedId) {
                case R.id.option1:
                    selectedOptionIndex = 0;
                    break;
                case R.id.option2:
                    selectedOptionIndex = 1;
                    break;
                case R.id.option3:
                    selectedOptionIndex = 2;
                    break;
            }
        }
    }
}
