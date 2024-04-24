package com.example.login.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.login.R;
import com.example.login.models.Room;
import com.example.login.searchActivity;

import java.util.ArrayList;
import java.util.List;

public class roomSpainnerAdapter extends ArrayAdapter<Room> {
    LayoutInflater flater;

    public roomSpainnerAdapter(Activity context, int resouceId, int textviewId, List<Room> list){
        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Room rowItem = getItem(position);

        View rowview = flater.inflate(com.google.android.material.R.layout.support_simple_spinner_dropdown_item,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(android.R.id.text1);
        txtTitle.setText(rowItem.toString());

        return rowview;
    }
}
