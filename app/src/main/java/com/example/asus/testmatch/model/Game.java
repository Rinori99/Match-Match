package com.example.asus.testmatch.model;

import com.example.asus.testmatch.R;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int lastIDSelected = -1;
    private int solvedCount;
    private int totalCount;
    private int lastPosition;
    private int currentlyOpened;
    private int tapCount;

    private long level;
    private String category;
    private String levelStr;

    private List<Integer> solvedPositions;

    public Game(){}

    public Game(String category, String levelStr){
        switch (levelStr){
            case "levelEasy": level = 3*60000; break;
            case "levelMedium": level = 2*60000; break;
            case "levelHard": level = 60000; break;
            default: level = 2*60000;
        }
        this.category = category;
        this.levelStr = levelStr;
        solvedPositions = new ArrayList<>();
    }

    public String getLevelStr(){
        return levelStr;
    }

    public int getLastPosition(){
        return lastPosition;
    }

    public String getCategory(){
        return category;
    }

    public long getLevel(){
        return level;
    }

    public void setLastPosition(int newLastPosition){
        lastPosition = newLastPosition;
    }

    public void incrSolved(){
        solvedCount++;
    }

    public void setTotalCount(int tCount){
        totalCount = tCount/2;
    }

    public boolean allSolved(){
        return solvedCount == totalCount;
    }

    public int getSolvedCount(){
        return solvedCount;
    }

    public void setLastIDSelected(int newID){
        lastIDSelected = newID;
    }

    public int getLastIDSelected(){
        return lastIDSelected;
    }

    public void setNoLastId(){
        lastIDSelected = -1;
    }

    public ArrayList<Integer> getSolvedPositions(){
        return (ArrayList<Integer>)solvedPositions;
    }

    public void addInSolvedPositions(int a, int b){
        solvedPositions.add(a);
        solvedPositions.add(b);
    }

    public void incrCurrentlyOpened(){
        currentlyOpened++;
    }

    public int getCurrentlyOpened(){
        return currentlyOpened;
    }

    public void currentlyOpenedReset(){
        currentlyOpened = 0;
    }

    public void incrTapCount(){
        tapCount++;
    }

    public int getTapCount(){return tapCount;}
}
