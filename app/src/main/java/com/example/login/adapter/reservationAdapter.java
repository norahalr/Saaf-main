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
import com.example.login.searchActivity;
import com.example.login.sqlLiteDB.DBManager;

import java.util.ArrayList;

public class reservationAdapter extends ArrayAdapter<Reservations> implements View.OnClickListener{

    private final DBManager dbManager;
    private ArrayList<Reservations> dataSet;
        Context mContext;

        // View lookup cache
        private static class ViewHolder {
            TextView hotelLbl;
            TextView roomNoLbl;
            TextView roomType;
            TextView totalPrice;
            TextView checkInDate;
            TextView checkOutDate;
            TextView bookLbl;
            TextView status;
            Button cancel;
            Button edit;
            Button checkIn;
            Button requestBtn;
        }

        public reservationAdapter(ArrayList<Reservations> data, Activity context) {
            super(context, R.layout.reservation_row, data);
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
            Reservations book = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.reservation_row, parent, false);
                viewHolder.bookLbl = (TextView) convertView.findViewById(R.id.bookLbl);
                viewHolder.hotelLbl = (TextView) convertView.findViewById(R.id.hotelLbl);
                viewHolder.roomNoLbl = (TextView) convertView.findViewById(R.id.roomNoLbl);
                viewHolder.roomType = (TextView) convertView.findViewById(R.id.roomType);
                viewHolder.totalPrice = (TextView) convertView.findViewById(R.id.totalPrice);
                viewHolder.checkInDate = (TextView) convertView.findViewById(R.id.checkInDate);
                viewHolder.checkOutDate = (TextView) convertView.findViewById(R.id.checkOutDate);
                viewHolder.status = (TextView) convertView.findViewById(R.id.status);
                viewHolder.cancel = (Button) convertView.findViewById(R.id.cancelBtn);
                viewHolder.edit = (Button) convertView.findViewById(R.id.editBtn);
                viewHolder.checkIn = (Button) convertView.findViewById(R.id.checkInBtn);
                viewHolder.requestBtn = (Button) convertView.findViewById(R.id.requestBtn);

                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result=convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
            lastPosition = position;
            Room room = dbManager.getRoom(book.getROOM_ID());
            viewHolder.bookLbl.setText(""+book.getId());
            viewHolder.hotelLbl.setText(room.getLOCATION());
            viewHolder.roomType.setText(room.getROOM_TYPE());
            viewHolder.roomNoLbl.setText(room.getROOM_NUMBER());
            viewHolder.totalPrice.setText(book.getTOTAL_PRICE() + " SAR");
            viewHolder.checkInDate.setText(book.getCHECK_IN_DATE());
            viewHolder.checkOutDate.setText(book.getCHECK_OUT_DATE());
            viewHolder.status.setText(book.getSTATUS());
            viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Myreservation tmp = (Myreservation) mContext;
                    tmp.cancelBook(book);
                }
            });

            viewHolder.checkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Myreservation tmp = (Myreservation) mContext;
                    tmp.checkInBook(book);
                }
            });

            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext , Edit.class);
                    i.putExtra("book_id" , book.getId());
                    mContext.startActivity(i);
                }
            });
            viewHolder.requestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext , RequestRoomService.class);
                    i.putExtra("book_id" , book.getId());
                    mContext.startActivity(i);
                }
            });

            if(!book.getSTATUS().equals("New")){
                viewHolder.edit.setVisibility(View.INVISIBLE);
                viewHolder.checkIn.setVisibility(View.INVISIBLE);
                viewHolder.cancel.setVisibility(View.INVISIBLE);
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }
