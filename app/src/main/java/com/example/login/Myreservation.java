package com.example.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.adapter.reservationAdapter;
import com.example.login.adapter.roomAdapter;
import com.example.login.models.Reservations;
import com.example.login.models.Room;
import com.example.login.sqlLiteDB.DBManager;

import java.util.ArrayList;

public class Myreservation extends AppCompatActivity {

    private ListView listView;
    private reservationAdapter adapter;
    ArrayList<Reservations> resList = new ArrayList<>();
    private DBManager dbManager;
    private AlertDialogManager alert;
    private PrefManager perfMngr;
    private RadioGroup radioGroup;
    private RadioButton statusRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_myreservation);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */
        alert = new AlertDialogManager(Myreservation.this);

        perfMngr = new PrefManager(getApplicationContext());
        dbManager = new DBManager(this);
        dbManager.open();


        resList = dbManager.getMyReservations(perfMngr.getUserID(),"New");
        listView=(ListView)findViewById(R.id.listView);
        adapter= new reservationAdapter(resList, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                statusRadio = (RadioButton) findViewById(selectedId);
                resList.clear();
                if(selectedId==R.id.radioCancel){
                    resList = dbManager.getMyReservations(perfMngr.getUserID(),"Cancel");
                }else if(selectedId==R.id.radioCurrent){
                    resList = dbManager.getMyReservations(perfMngr.getUserID(),"New");
                }else if(selectedId==R.id.radioOld){
                    resList = dbManager.getMyReservations(perfMngr.getUserID(),"CheckIn");
                }
                adapter= new reservationAdapter(resList, Myreservation.this);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
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

        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Reservations rs =  dbManager.searchForReservation(query, perfMngr.getUserID());
                if(rs != null) {
                    resList.clear();
                    resList.add(rs);
                    adapter = new reservationAdapter(resList, Myreservation.this);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    resList.clear();
                    adapter.notifyDataSetChanged();
                    alert.showAlertDialog(Myreservation.this, "Search Result", "Book number is not found", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false
                    );
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    public void cancelBook(Reservations book) {

        alert.showMessageOKCancel("Are you want to cancel this book? \n"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbManager.updateBook(book, "Cancel")){
                            alert.showAlertDialog(Myreservation.this, "Success", "Book canceled", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    book.setStatus("Cancel");
                                    adapter.notifyDataSetChanged();
                                }
                            }, true);
                        }else{
                            alert.showAlertDialog(Myreservation.this, "Error", "Couldn't cancel the book", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }, false);
                        }
                    }
                });
    }

    public void checkInBook(Reservations book) {

        alert.showMessageOKCancel("Are you want to check in? \n"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbManager.updateBook(book, "CheckIn")){
                            alert.showAlertDialog(Myreservation.this, "Success", "You've checked in", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    book.setStatus("CheckIn");
                                    adapter.notifyDataSetChanged();
                                }
                            }, true);
                        }else{
                            alert.showAlertDialog(Myreservation.this, "Error", "Couldn't checked in", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }, false);
                        }
                    }
                });
    }
}