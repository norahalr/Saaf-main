package com.example.login.models;

public class Reservations {
    private int id;
    private int GUEST_ID;
    private int ROOM_ID;
    private String CHECK_IN_DATE;
    private String CHECK_OUT_DATE;
    private int NUMBER_OF_GUESTS;
    private double TOTAL_PRICE;
    private String STATUS;

    public Reservations(int id, int GUEST_ID,
    int ROOM_ID,
    String CHECK_IN_DATE,
    String CHECK_OUT_DATE,
    int NUMBER_OF_GUESTS,
    double TOTAL_PRICE,
    String STATUS) {
        this.id = id;
        this.ROOM_ID = ROOM_ID;
        this.GUEST_ID = GUEST_ID;
        this.CHECK_IN_DATE = CHECK_IN_DATE;
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
        this.NUMBER_OF_GUESTS = NUMBER_OF_GUESTS;
        this.STATUS = STATUS;
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

    public int getId() {
        return id;
    }

    public double getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }

    public int getGUEST_ID() {
        return GUEST_ID;
    }

    public int getNUMBER_OF_GUESTS() {
        return NUMBER_OF_GUESTS;
    }

    public int getROOM_ID() {
        return ROOM_ID;
    }

    public String getCHECK_IN_DATE() {
        return CHECK_IN_DATE;
    }

    public String getCHECK_OUT_DATE() {
        return CHECK_OUT_DATE;
    }
    

    public String getSTATUS() {
        return STATUS;
    }

    public void setStatus(String STATUS) {
        this.STATUS  =STATUS;
    }
}


