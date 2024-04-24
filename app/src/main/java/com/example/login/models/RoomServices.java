package com.example.login.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RoomServices {
    private int id;
    private int RESERVATION_ID;
    private String SERVICE_TYPE;
    private String SERVICE_DATE;
    private String DESCRIPTION;

    public RoomServices(int id, int RESERVATION_ID
    , String SERVICE_TYPE
    , String SERVICE_DATE
    , String DESCRIPTION) {
        this.id = id;
        this.RESERVATION_ID = RESERVATION_ID;
        this.SERVICE_TYPE = SERVICE_TYPE;
        if(SERVICE_DATE.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateAndTime = sdf.format(new Date());
            this.SERVICE_DATE = currentDateAndTime;
        }else{
            this.SERVICE_DATE = SERVICE_DATE;
        }
        this.DESCRIPTION = DESCRIPTION;
    }

    public int getId() {
        return id;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public int getRESERVATION_ID() {
        return RESERVATION_ID;
    }

    public String getSERVICE_DATE() {
        return SERVICE_DATE;
    }

    public String getSERVICE_TYPE() {
        return SERVICE_TYPE;
    }

}


