package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String USER_ID = "id";
    private static final String PREF_NAME = "hotel";

    // Constructor
    public PrefManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserId(int id){
        editor.putInt(USER_ID, id);
        editor.commit();
    }

    public int getUserID(){
        return pref.getInt(USER_ID, 0);
    }


}
