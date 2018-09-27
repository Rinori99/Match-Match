package com.example.asus.testmatch.activities;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.testmatch.R;
import com.example.asus.testmatch.adapter.GridImageAdapter;
import com.example.asus.testmatch.flickrFetch.JsonData;
import com.example.asus.testmatch.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay, btnHighscores, btnAboutGame, btnQuit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupWidgets();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if player's device is connected to the internet, thus allowing him or not to play
                ConnectivityManager cm =
                        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    //asking if he wants to go to the wi-fi settings window on his phone, in order to connect to the internet
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Warning") //
                            .setMessage("You require internet connection to play!") //
                            .setPositiveButton("Find a connection", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN, null);
                                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                    ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                                    intent.setComponent(cn);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity( intent);
                                }
                            }) //
                            .setNegativeButton(getString(R.string.NoCancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // TODO
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }else{
                    Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HighscoresActivity.class);
                startActivity(intent);
            }
        });

        btnAboutGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutGameActivity.class);
                startActivity(intent);
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    private void setupWidgets(){
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnHighscores = (Button)findViewById(R.id.btnHighscores);
        btnAboutGame = (Button)findViewById(R.id.btnAboutGame);
        btnQuit = (Button)findViewById(R.id.btnQuitGame);
    }
}
