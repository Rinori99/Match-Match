package com.example.asus.testmatch.model;

public class Square {
    private Photo photo;
    private int ID;

    public Square(){}

    public Square(Photo photo, int ID) {
        this.photo = photo;
        this.ID = ID;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
