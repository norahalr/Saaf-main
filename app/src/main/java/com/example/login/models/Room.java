package com.example.login.models;

import androidx.annotation.NonNull;

public class Room {
    private int id;
    private String ROOM_NUMBER;
    private String ROOM_TYPE;
    private int FLOOR;
    private String STATUS;
    private double PRICE_PER_NIGHT;
    private String LOCATION;

    public Room(int id, String ROOM_NUMBER, String ROOM_TYPE, int FLOOR, String STATUS,double PRICE_PER_NIGHT, String LOCATION) {
        this.id = id;
        this.ROOM_NUMBER = ROOM_NUMBER;
        this.ROOM_TYPE = ROOM_TYPE;
        this.FLOOR = FLOOR;
        this.STATUS = STATUS;
        this.PRICE_PER_NIGHT = PRICE_PER_NIGHT;
        this.LOCATION = LOCATION;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public double getPRICE_PER_NIGHT() {
        return PRICE_PER_NIGHT;
    }

    public int getFLOOR() {
        return FLOOR;
    }

    public int getId() {
        return id;
    }

    public String getROOM_NUMBER() {
        return ROOM_NUMBER;
    }

    public String getROOM_TYPE() {
        return ROOM_TYPE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getROOM_NUMBER() + "-Type: " + this.getROOM_TYPE() + "- Floor: "+ this.getFLOOR() ;
    }
}


