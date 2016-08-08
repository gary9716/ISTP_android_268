package com.example.user.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Handler;

import com.example.user.myapplication.model.OwningPokemonDataManager;
import com.example.user.myapplication.model.PokemonInfo;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

public class MainActivity extends CustomizedActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,TextView.OnEditorActionListener {

    TextView infoText;
    RadioGroup optionGrp;
    int selectedOptionIndex = 0;
    Button confirm_button;
    String[] pokemonNames = new String[]{
        "小火龍","傑尼龜","妙蛙種子"
    };
    ProgressBar progressBar;
    SharedPreferences preferences;

    String nameOfTheTrainer = null;
    public final static String nameEditTextKey = "nameOfTheTrainer";
    public final static String profileImgUrlKey = "profileImgUrlKey";
    public final static String emailKey = "emailKey";

    public enum UISetting {
        Initial,
        DataIsKnown
    }

    UISetting uiSetting;

    LoginButton loginButton;
    CallbackManager callbackManager;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fb_login);

        confirm_button = (Button)findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(this);

        optionGrp = (RadioGroup) findViewById(R.id.optionsGroup);
        optionGrp.setOnCheckedChangeListener(this);

        infoText = (TextView) findViewById(R.id.infoText);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setIndeterminateDrawable(new CircularProgressDrawable
                .Builder(this)
                .colors(getResources().getIntArray(R.array.gplus_colors))
                .sweepSpeed(1f)
                .strokeWidth(8f)
                .build());

        preferences = getSharedPreferences(Application.class.getName(), MODE_PRIVATE);
        AccessToken currentToken;
        currentToken = AccessToken.getCurrentAccessToken();
        if(currentToken != null) {
            accessToken = currentToken;
        }
        else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(nameEditTextKey);
            editor.remove(profileImgUrlKey);
            editor.remove(emailKey);
            editor.commit();

            accessToken = null;
        }

        loginButton = (LoginButton)findViewById(R.id.login_button);
        setupFBLogin();

        selectedOptionIndex = preferences.getInt(optionSelectedKey, selectedOptionIndex);
        nameOfTheTrainer = preferences.getString(nameEditTextKey, nameOfTheTrainer);

        if(nameOfTheTrainer == null) {
            uiSetting = UISetting.Initial;
        }
        else {
            uiSetting = UISetting.DataIsKnown;
        }

        changeUIAccordingToRecord();
    }

    public void setupFBLogin() {
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("public_profile", "email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                sendGraphRequest();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    public void sendGraphRequest() {
        if(accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    if(response != null) {
//                        Log.d("FB", object.toString());
//                        Log.d("FB", object.optString("name"));
//                        Log.d("FB", object.optString("email"));
//                        Log.d("FB", object.optString("id"));

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(nameEditTextKey, object.optString("name"));
                        editor.putString(emailKey, object.optString("email"));
                        if(object.has("picture")) {
                            try {
                                String profilePicUrl = object.getJSONObject("picture")
                                        .getJSONObject("data")
                                        .getString("url");
                                Log.d("FB",profilePicUrl);
                                editor.putString(profileImgUrlKey, profilePicUrl);
                            }
                            catch(Exception e) {
                            }
                        }

                        editor.commit();
                    }
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields","id,email,name,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }


    private void changeUIAccordingToRecord() {
        if(uiSetting == UISetting.DataIsKnown) {
            confirm_button.setVisibility(View.INVISIBLE);
            optionGrp.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.INVISIBLE);

            progressBar.setVisibility(View.VISIBLE);
            confirm_button.performClick();
        }
        else {
            confirm_button.setVisibility(View.VISIBLE);
            optionGrp.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public final static String optionSelectedKey = "selectedOption";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(optionSelectedKey, selectedOptionIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedOptionIndex = savedInstanceState.getInt(optionSelectedKey, 0);
        ((RadioButton)optionGrp.getChildAt(selectedOptionIndex)).setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("testStage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("testStage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("testStage", "onStop");
        confirm_button.setClickable(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("testStage", "onDestroy");
    }

    int changeActivityInSecs = 5;

    private void setInfoTextWithFormat() {

        if(uiSetting == UISetting.Initial) {
            infoText.setText(String.format("你好, 訓練家%s 歡迎來到神奇寶貝的世界, 你的夥伴是%s, 冒險將於%d秒後開始",
                    nameOfTheTrainer,
                    pokemonNames[selectedOptionIndex],
                    changeActivityInSecs));
        }
        else if(uiSetting == UISetting.DataIsKnown){
            infoText.setText(String.format("你好, 訓練家%s 歡迎回到神奇寶貝的世界, 你的夥伴是%s, 冒險將於%d秒後繼續",
                    nameOfTheTrainer,
                    pokemonNames[selectedOptionIndex],
                    changeActivityInSecs));
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.confirm_button) {
            v.setClickable(false);
            if(uiSetting == UISetting.Initial) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(optionSelectedKey, selectedOptionIndex);
                editor.commit();
            }

            nameOfTheTrainer = preferences.getString(nameEditTextKey, nameOfTheTrainer);
            setInfoTextWithFormat();

            Handler handler = new Handler(MainActivity.this.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
                    intent.putExtra(optionSelectedKey, selectedOptionIndex);
                    startActivity(intent);
                    finish();
                }
            }, changeActivityInSecs * 1000);

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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager inm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(),0);

            confirm_button.performClick();
            return true;
        }

        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
