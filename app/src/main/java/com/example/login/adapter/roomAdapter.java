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

public class roomAdapter extends ArrayAdapter<Room> implements View.OnClickListener{

        private ArrayList<Room> dataSet;
        Context mContext;

        // View lookup cache
        private static class ViewHolder {
            TextView hotelLbl;
            TextView roomNoLbl;
            TextView roomType;
            TextView roomPrice;
            Button bookNow;
        }

        public roomAdapter(ArrayList<Room> data, Activity context) {
            super(context, R.layout.room_row, data);
            this.dataSet = data;
            this.mContext=context;

        }

        @Override
        public void onClick(View v) {

            int position=(Integer) v.getTag();
            Object object= getItem(position);
            Room Room=(Room)object;

            if (v.getId() == R.id.reserveBtn) {
            }
        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Room Room = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.room_row, parent, false);
                viewHolder.hotelLbl = (TextView) convertView.findViewById(R.id.hotelLbl);
                viewHolder.roomNoLbl = (TextView) convertView.findViewById(R.id.roomNoLbl);
                viewHolder.roomType = (TextView) convertView.findViewById(R.id.roomType);
                viewHolder.roomPrice = (TextView) convertView.findViewById(R.id.roomPrice);
                viewHolder.bookNow = (Button) convertView.findViewById(R.id.reserveBtn);

                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result=convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
            lastPosition = position;

            viewHolder.hotelLbl.setText(Room.getLOCATION());
            viewHolder.roomType.setText(Room.getROOM_TYPE());
            viewHolder.roomNoLbl.setText(Room.getROOM_NUMBER());
            viewHolder.roomPrice.setText(Room.getPRICE_PER_NIGHT() + " SAR");
            viewHolder.bookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchActivity tmp = (searchActivity) mContext;
                    tmp.bookRoom(Room);
                }
            });
            // Return the completed view to render on screen
            return convertView;
        }
    }
