package com.example.asus.testmatch.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.testmatch.R;

import org.w3c.dom.Text;

public class HighscoresActivity extends AppCompatActivity{

    private TextView txtEasy, txtMedium, txtHard;
    private RelativeLayout relHomeHighscore;

    private String easyHStr, mediumHStr, hardHStr;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.asus.testmatch";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        setupWidgets();

        getHighscores();

        setTextToWidgets();

        relHomeHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setTextToWidgets(){
        txtEasy.setText(easyHStr);
        txtMedium.setText(mediumHStr);
        txtHard.setText(hardHStr);
    }

    private void setupWidgets(){
        txtEasy = (TextView)findViewById(R.id.txtHighscoreEasy);
        txtMedium =(TextView)findViewById(R.id.txtHighscoreMedium);
        txtHard = (TextView)findViewById(R.id.txtHighscoreHard);
        relHomeHighscore = (RelativeLayout)findViewById(R.id.relHomeHighscore);
    }

    private void getHighscores(){
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        //fetching all three highscores
        easyHStr = Integer.toString(mPreferences.getInt(getString(R.string.level_easy), 0));
        mediumHStr = Integer.toString(mPreferences.getInt(getString(R.string.level_medium), 0));
        hardHStr = Integer.toString(mPreferences.getInt(getString(R.string.level_hard), 0));
    }
}
