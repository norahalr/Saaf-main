package com.example.login.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.login.Edit;
import com.example.login.Myreservation;
import com.example.login.R;
import com.example.login.RequestRoomService;
import com.example.login.models.Reservations;
import com.example.login.models.Room;
import com.example.login.models.RoomServices;
import com.example.login.sqlLiteDB.DBManager;

import java.util.ArrayList;

public class servicesAdapter extends ArrayAdapter<RoomServices> implements View.OnClickListener{

    private final DBManager dbManager;
    private ArrayList<RoomServices> dataSet;
        Context mContext;

        // View lookup cache
        private static class ViewHolder {
            TextView descLbl;
            TextView bookLbl;
            TextView reqDateLbl;
            TextView serviceTypeLbl;
        }

        public servicesAdapter(ArrayList<RoomServices> data, Activity context) {
            super(context, R.layout.service_row, data);
            this.dataSet = data;
            this.mContext=context;
            dbManager = new DBManager(context);
            dbManager.open();

        }

        @Override
        public void onClick(View v) {

            int position=(Integer) v.getTag();
            Object object= getItem(position);

        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            RoomServices rmsrvs = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.service_row, parent, false);
                viewHolder.bookLbl = (TextView) convertView.findViewById(R.id.bookLbl);
                viewHolder.descLbl = (TextView) convertView.findViewById(R.id.descLbl);
                viewHolder.reqDateLbl = (TextView) convertView.findViewById(R.id.reqDateLbl);
                viewHolder.serviceTypeLbl = (TextView) convertView.findViewById(R.id.serviceTypeLbl);

                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result=convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
            lastPosition = position;
            viewHolder.bookLbl.setText(""+rmsrvs.getRESERVATION_ID());
            //viewHolder.descLbl.setText(""+rmsrvs.getDESCRIPTION());
            viewHolder.reqDateLbl.setText(""+rmsrvs.getSERVICE_DATE());
            viewHolder.serviceTypeLbl.setText(""+rmsrvs.getSERVICE_TYPE());
            // Return the completed view to render on screen
            return convertView;
        }
    }
