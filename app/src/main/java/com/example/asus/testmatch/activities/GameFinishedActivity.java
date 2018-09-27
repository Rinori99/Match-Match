package com.example.asus.testmatch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.testmatch.R;

import org.w3c.dom.Text;

public class GameFinishedActivity extends AppCompatActivity {

    private TextView textMessageResult, textScore;
    private ImageView imgReaction;
    private RelativeLayout mainLayoutGameFinished, relReplay, relHome;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.asus.testmatch";

    private int score;
    private int highscore;
    private String levelStr;
    private String highscoreStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);

        //getting the score and level of the last game
        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt(getString(R.string.score));
        levelStr = bundle.getString(getString(R.string.level));

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        //setting the highscore type depending on the level
        switch (levelStr){
            case "levelEasy" : highscoreStr =  getString(R.string.level_easy); break;
            case "levelMedium" : highscoreStr = getString(R.string.level_medium); break;
            case "levelHard" : highscoreStr = getString(R.string.level_hard); break;
        }
        highscore = mPreferences.getInt(highscoreStr, 0);

        setupWidgets();


        relHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameFinishedActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        relReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameFinishedActivity.this, CategoriesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


    }

    private void setupWidgets(){
        textMessageResult = (TextView)findViewById(R.id.textMessageResult);
        textScore = (TextView)findViewById(R.id.txtScore);
        imgReaction = (ImageView)findViewById(R.id.imgReaction);
        mainLayoutGameFinished = (RelativeLayout)findViewById(R.id.mainGameFinishedLayout);
        relHome = (RelativeLayout)findViewById(R.id.relHome);
        relReplay = (RelativeLayout)findViewById(R.id.relReplay);


        checkResult();
    }

    private void checkResult(){
        boolean hasNewHighscore = score > highscore ? true : false;

        updateLayout(hasNewHighscore);
    }

    //updating the layout depending on wether the player won, lost or got a new highscore
    private void updateLayout(boolean hasNewHighscore){
        if(score > 0){
            if(hasNewHighscore){
                imgReaction.setImageResource(R.drawable.firstplace);
                textMessageResult.setText("NEW HIGHSCORE!!");
                highscore = score;
            }else{
                imgReaction.setImageResource(R.drawable.clapping);
                textMessageResult.setText("YOU WON!");
            }
            mainLayoutGameFinished.setBackgroundColor(getResources().getColor(R.color.dark_green));
            textScore.setText(Integer.toString(score));
        }else{
            mainLayoutGameFinished.setBackgroundColor(getResources().getColor(R.color.red));
            textScore.setText(Integer.toString(score));
            imgReaction.setImageResource(R.drawable.fail);
            textMessageResult.setText("You LOST!");
        }
    }

    @Override
    protected void onPause() { // saving the new highscore if present
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(highscoreStr, highscore);
        preferencesEditor.apply();
    }
}
