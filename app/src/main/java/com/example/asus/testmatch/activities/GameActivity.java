package com.example.asus.testmatch.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.testmatch.R;
import com.example.asus.testmatch.adapter.GridImageAdapter;
import com.example.asus.testmatch.adapter.UniversalImageLoader;
import com.example.asus.testmatch.flickrFetch.JsonData;
import com.example.asus.testmatch.model.Game;
import com.example.asus.testmatch.model.Photo;
import com.example.asus.testmatch.model.Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class GameActivity extends AppCompatActivity{

    //Constant and static fields -------------------------------------------------------------
    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    private static String CATEGORY;
    private static String LEVEL;
    public static final int NUM_GRID_COLUMNS = 4;
    private static final long BLINK_INTERVAL = 1000L;
    private static final String defaultPhotoURL = "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/socialmedia/apple/129/white-question-mark-ornament_2754.png";

    //Game control fields -------------------------------------------------------------------
    private Game game;
    private String extra;
    private long pinPoint;
    private boolean isBackgroundBlue = true;
    private int SCORE = 0;
    private static long timeLeft;
    private CountDownTimer timer;

    //Widgets and data ---------------------------------------------------------------------------------
    private List<Square> squares;
    private GridView gridView;
    // private TextView textTimeLeft;
    private Button btnStart;
    private RelativeLayout mainLayout;
    private RelativeLayout relHomeGame, relReplayGame;
    private RelativeLayout relGridView, relBottom;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        setupWidgets();

        Bundle bundle = getIntent().getExtras();
        extra = bundle.getString(getString(R.string.level));

        setImageCategory();
        setDifficulty();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        relHomeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle(R.string.title) //
                        .setMessage(R.string.areYouSure) //
                        .setPositiveButton(getString(R.string.YesGoHome), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }) //
                        .setNegativeButton(getString(R.string.NoCancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // TODO
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        relReplayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle(R.string.title) //
                        .setMessage(R.string.areYouSureReplay) //
                        .setPositiveButton(getString(R.string.yesReplay), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(GameActivity.this, CategoriesActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }) //
                        .setNegativeButton(getString(R.string.NoCancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // TODO
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });
    }

    private void setupWidgets(){
        relGridView = (RelativeLayout)findViewById(R.id.relGridView);
        relBottom = (RelativeLayout)findViewById(R.id.relBottom);
        gridView = (GridView)findViewById(R.id.gridView);
        //textTimeLeft = (TextView)findViewById(R.id.textTimeLeft);
        btnStart = (Button)findViewById(R.id.btnStart);
        mainLayout =(RelativeLayout)findViewById(R.id.mainLayout);

        relHomeGame = (RelativeLayout)findViewById(R.id.relHomeGame);
        relReplayGame = (RelativeLayout)findViewById(R.id.relReplayGame);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        relGridView.setVisibility(View.GONE);
        relBottom.setVisibility(View.GONE);

    }

    private void setImageCategory(){
        CATEGORY = extra.substring(0, extra.indexOf(":"));
    }

    private void setDifficulty(){
        String selectedLevel = extra.substring(extra.indexOf(":")+1);
        switch (selectedLevel){
            case "levelEasy": LEVEL = getString(R.string.level_easy); break;
            case "levelMedium": LEVEL = getString(R.string.level_medium); break;
            case "levelHard": LEVEL = getString(R.string.level_hard); break;
            default: LEVEL = getString(R.string.level_medium);
        }
    }

    private void startGame(){
        game = new Game(CATEGORY, LEVEL);
        btnStart.setVisibility(View.GONE);
        startTimer();
        setCategory();
        relGridView.setVisibility(View.VISIBLE);
        relBottom.setVisibility(View.VISIBLE);
    }

    private void startTimer(){
        timer = new CountDownTimer(game.getLevel(), 100L) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                setProgressBarProgress();
                if(pinPoint == 0 || pinPoint-timeLeft >= BLINK_INTERVAL){}
                setBackgroundColor();
            }

            @Override
            public void onFinish() {
                SCORE = 0;
                timer.cancel();
                Intent intent = new Intent(GameActivity.this, GameFinishedActivity.class);
                intent.putExtra(getString(R.string.score), SCORE);
                intent.putExtra(getString(R.string.level), game.getLevelStr());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        };
        timer.start();
    }

    //method to calculate score depending on time passed and number of pictures opened
    private void calculateScore(){
        long timePassed = game.getLevel() - timeLeft;
        int tapCount = game.getTapCount();
        SCORE = (int)(((double)game.getLevel()/(timePassed + 200*tapCount))*1000);
    }

    //set progressbar percentage depending on time remaining
    private void setProgressBarProgress(){
        progressBar.setProgress((int)((timeLeft*1000)/game.getLevel()));
    }

    //Blinking background when less than 20% time remaining
    private void setBackgroundColor(){
        if((double)timeLeft/game.getLevel()*100 <= 20){
            if(isBackgroundBlue){
                mainLayout.setBackgroundColor(getResources().getColor(R.color.red));
                isBackgroundBlue = false;
            }else {
                mainLayout.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                isBackgroundBlue = true;
            }
            pinPoint = timeLeft;
        }
    }

    // Method to represent time numerically -- Substituted with progressbar
//    private void setTextFromMillis(long n){
//        String minutes = "";
//        String seconds = "";
//
//        int min = (int)n/60000;
//        int sec = (int)((n%60000)/1000);
//
//        minutes = Integer.toString(min);
//        seconds = Integer.toString(sec);
//        if(sec<10){
//            seconds = "0"+seconds;
//        }
//        textTimeLeft.setText(minutes + " : " + seconds);
//    }

    //sets category of pictures intended to be fetched
    private void setCategory(){
        setPreferenceData();
        String query = getSavedPreferenceData(FLICKR_QUERY);
        if (query.length() > 0) {
            GameActivity.ProcessPhotos processPhotos = new GameActivity.ProcessPhotos(query, true);
            processPhotos.execute();
        }
    }

    //sets up gridview consisting of pictures fetched from Flickr
    private void setUpGridView(){
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);
        ArrayList<String> imgUrls = new ArrayList<>();

        //at first we are populating gridview with default pictures
        for(int i = 0; i < 20; i++){
            imgUrls.add(defaultPhotoURL);
        }

        final GridImageAdapter gridImageAdapter = new GridImageAdapter(GameActivity.this, R.layout.layout_grid_imageview, imgUrls);
        gridView.setAdapter(gridImageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                game.incrTapCount();
                int lastID = game.getLastIDSelected();
                int currentID = squares.get(position).getID();

                //nothing happens if there are two opened pictures, the puzzle is solved, tapping on the same currently opened picture or tapping on an already soled picture
                if(game.getCurrentlyOpened() < 2 && !game.allSolved() && position != game.getLastPosition() && !game.getSolvedPositions().contains(position)) {
                    final ImageView imgView = (ImageView) ((RelativeLayout) gridView.getChildAt(position)).getChildAt(0);
                    UniversalImageLoader.setImage(squares.get(position).getPhoto().getImage(), imgView, null, GameActivity.this);

                    game.incrCurrentlyOpened();

                    //checking if there is no opened pictures currently, thus setting last selected ID and position
                    if (game.getLastIDSelected() == -1) {
                        game.setLastIDSelected(squares.get(position).getID());
                        game.setLastPosition(position);
                    } else {
                        if (lastID == currentID) {  //checking if we found a match, thus incrementing the number of matched pairs
                            game.addInSolvedPositions(position, game.getLastPosition());
                            game.incrSolved();
                            game.currentlyOpenedReset();
                        } else {  //Wait for 0.7 seconds to give the player a chance to look at the picture which didn'tturn out to be a match
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ImageView prevImgView = (ImageView) ((RelativeLayout) gridView.getChildAt(game.getLastPosition())).getChildAt(0);
                                    UniversalImageLoader.setImage(defaultPhotoURL, imgView, null, GameActivity.this);
                                    UniversalImageLoader.setImage(defaultPhotoURL, prevImgView, null, GameActivity.this);
                                    game.currentlyOpenedReset();
                                }
                            }, 700);
                        }
                        game.setNoLastId();
                    }
                    if (game.allSolved()) { //if game is solved cancel any current activity and save score
                        calculateScore();
                        timer.cancel();
                        Intent intent = new Intent(GameActivity.this, GameFinishedActivity.class);
                        intent.putExtra(getString(R.string.score), SCORE);
                        intent.putExtra(getString(R.string.level), game.getLevelStr());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private String getSavedPreferenceData(String key) { //returns the category that we intend to fetch pictures for
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPref.getString(key, "");
    }


    private void setPreferenceData(){ //method to set our fetching preferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPref.edit().putString(FLICKR_QUERY, game.getCategory()).commit();
    }

    //static inner class to process the action of fetching pictures form flickr (while resolving the json data)
    public class ProcessPhotos extends JsonData {

        public ProcessPhotos(String searchCriteria, boolean matchAll) {
            super(searchCriteria, matchAll);
        }

        public void execute() {

            GameActivity.ProcessPhotos.ProcessData processData = new GameActivity.ProcessPhotos.ProcessData();
            processData.execute();
        }

        public class ProcessData extends DownloadJsonData {

            protected void onPostExecute(String webData) {
                super.onPostExecute(webData);
                List<Photo> photos = getPhotos();

                squares = new ArrayList<>();
                for(int i = 0; i < photos.size(); i+=2){
                    squares.add(new Square(photos.get(i), i));
                }
                squares.addAll(squares);
                Collections.shuffle(squares);

                game.setTotalCount(squares.size());
                setUpGridView();
            }
        }

    }
}