package com.example.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.adapter.reservationAdapter;
import com.example.login.adapter.roomSpainnerAdapter;
import com.example.login.models.Reservations;
import com.example.login.models.Room;
import com.example.login.sqlLiteDB.DBManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class Edit extends AppCompatActivity {

    private ImageView dateImgView;
    private Dialog dialog;
    private PrefManager perfMngr;
    private DBManager dbManager;
    private AlertDialogManager alert;
    private Reservations book;
    private Room room;
    private ArrayList<Room> roomsList;
    private roomSpainnerAdapter roomsListAdapter;
    private Spinner roomListSpinner;

    String selectedBranch = "Ryadh";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);


        ImageView profileBtn = (ImageView)findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Profile.class);
                startActivity(i);
            }
        });
        ImageView homeBtn = (ImageView)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
            }
        });
        ImageView logoutBtn = (ImageView)findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignOut.class);
                startActivity(i);
            }
        });

        Bundle bundle = getIntent().getExtras();
        int book_id = bundle.getInt("book_id");
        alert = new AlertDialogManager(getApplicationContext());
        perfMngr = new PrefManager(getApplicationContext());
        dbManager = new DBManager(this);
        dbManager.open();

        book = dbManager.getReservation(""+book_id);
        room = dbManager.getRoom(book.getROOM_ID());

        dateImgView = (ImageView)findViewById(R.id.dateImgView);
        ImageView branch_room1 = (ImageView)findViewById(R.id.branch_room1);
        ImageView branch_room2 = (ImageView)findViewById(R.id.branch_room2);
        dialog = new Dialog(Edit.this);

        branch_room1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRoomBranch();
            }
        });
        branch_room2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRoomBranch();
            }
        });
        dateImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.date_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                Button updateDatesBtn = dialog.findViewById(R.id.updateDatesBtn);
                Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
                EditText checkIn = dialog.findViewById(R.id.checkInDateEditText);
                EditText checkOut = dialog.findViewById(R.id.checkOutDateEditText);
                EditText nofG = dialog.findViewById(R.id.num_of_guests);

                checkIn.setText(book.getCHECK_IN_DATE());
                checkOut.setText(book.getCHECK_OUT_DATE());
                nofG.setText(""+book.getNUMBER_OF_GUESTS());

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                updateDatesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (!isValidDateFormat(checkIn.getText().toString())) {
                            alert.showAlertDialog(Edit.this, "Error", "Check in date is not in correct format.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }, false);
                            return;
                        }
                        if (!isFutureDate(checkIn.getText().toString())) {
                            alert.showAlertDialog(Edit.this, "Error", "Check in date is not in the future.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }, false);
                            return;
                        }

                        if (!isValidDateFormat(checkOut.getText().toString())) {
                            alert.showAlertDialog(Edit.this, "Error", "Check out date is not in correct format.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }, false);
                            return;
                        }
                        if (!isFutureDate(checkOut.getText().toString())) {
                            alert.showAlertDialog(Edit.this, "Error", "Check out date is not in the future.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }, false);
                            return;
                        }

                        if(checkIn.getText().toString().equals("")){
                            alert.showAlertDialog(Edit.this, "Error", "You must set check in date.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }, false);
                            return;
                        }

                        if(checkOut.getText().toString().equals("")){
                            alert.showAlertDialog(Edit.this, "Error", "You must set check out date.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }, false);
                            return;
                        }

                        String num = nofG.getText().toString();
                        int numOfG = 0;
                        try{
                            numOfG = Integer.parseInt(num);
                            if(numOfG > 0 && numOfG <= 10){
                                int diffDays = (int) getDaysDiff(checkIn.getText().toString(), checkOut.getText().toString());
                                double totalPrice = diffDays * room.getPRICE_PER_NIGHT();
                                if(dbManager.updateBookCheck(book, checkIn.getText().toString(), checkOut.getText().toString() ,numOfG, totalPrice)){
                                    alert.showAlertDialog(Edit.this, "Success", "Book updated", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            book = dbManager.getReservation(""+book_id);
                                        }
                                    }, true);
                                }else{
                                    alert.showAlertDialog(Edit.this, "Error", "Book not updated", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }, false);
                                }
                            }else{
                                alert.showAlertDialog(Edit.this, "Error", "Number of guest must between 1 and 10.", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }, false);
                            }
                        }catch (Exception e){
                            alert.showAlertDialog(Edit.this, "Error", "Number of guest must be a number.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }, false);
                        }
                    }
                });

                dialog.show();

            }
        });
    }

    private void changeRoomBranch() {
        dialog.setContentView(R.layout.branchroom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);


        roomsList = dbManager.getAvailableRooms("Ryadh", book.getCHECK_IN_DATE(), book.getCHECK_OUT_DATE());
        roomListSpinner = (Spinner)dialog.findViewById(R.id.roomListSpinner);
        roomsListAdapter = new roomSpainnerAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                android.R.id.text1,
                roomsList);
        roomListSpinner.setAdapter(roomsListAdapter);
        roomsListAdapter.notifyDataSetChanged();

        Button updateDatesBtn = dialog.findViewById(R.id.updateDatesBtn);
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);


        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private RadioButton statusRadio;

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                statusRadio = (RadioButton) findViewById(selectedId);
                roomsList.clear();
                if(selectedId==R.id.radioRyadh){
                    roomsList = dbManager.getAvailableRooms("Ryadh", book.getCHECK_IN_DATE(), book.getCHECK_OUT_DATE());
                    selectedBranch = "Ryadh";
                }else if(selectedId==R.id.radioKhobar){
                    roomsList = dbManager.getAvailableRooms("Khobar", book.getCHECK_IN_DATE(), book.getCHECK_OUT_DATE());
                    selectedBranch = "Khobar";
                }

                roomsListAdapter = new roomSpainnerAdapter(Edit.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        android.R.id.text1,
                        roomsList);
                roomListSpinner.setAdapter(roomsListAdapter);
                roomsListAdapter.notifyDataSetChanged();
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        updateDatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Room rom = (Room)roomListSpinner.getSelectedItem();
                int daysDiff = (int)getDaysDiff(book.getCHECK_IN_DATE(), book.getCHECK_OUT_DATE());
                double totalPrice = daysDiff * rom.getPRICE_PER_NIGHT();
                if(dbManager.updateBookBranchRoom(book, rom.getId(), totalPrice)){
                    alert.showAlertDialog(Edit.this, "Success", "Room changed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            book = dbManager.getReservation(""+book.getId());
                        }
                    }, true);
                }else{
                    alert.showAlertDialog(Edit.this, "Error", "Book not changed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false);
                }
            }
        });

        dialog.show();
    }


    public long getDaysDiff(String checkInDate, String checkOutDate){
        String d1_str[] = checkInDate.split("-");
        String d2_str[] = checkOutDate.split("-");
        LocalDate date1 = LocalDate.of(Integer.parseInt(d1_str[0]), Integer.parseInt(d1_str[1]), Integer.parseInt(d1_str[2]));
        LocalDate date2 = LocalDate.of(Integer.parseInt(d2_str[0]), Integer.parseInt(d2_str[1]), Integer.parseInt(d2_str[2]));

        long daysDifference = ChronoUnit.DAYS.between(date1, date2);
        return daysDifference;
    }

    private boolean isValidDateFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);

        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isFutureDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        try {
            Date inputDate = format.parse(date);
            return inputDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }

}