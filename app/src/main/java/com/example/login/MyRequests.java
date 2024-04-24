package com.example.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.adapter.reservationAdapter;
import com.example.login.adapter.servicesAdapter;
import com.example.login.models.Reservations;
import com.example.login.models.RoomServices;
import com.example.login.sqlLiteDB.DBManager;

import java.util.ArrayList;

public class MyRequests extends AppCompatActivity {

    private ListView listView;
    private servicesAdapter adapter;
    ArrayList<RoomServices> requestsList = new ArrayList<>();
    private DBManager dbManager;
    private AlertDialogManager alert;
    private PrefManager perfMngr;
    private RadioGroup radioGroup;
    private RadioButton statusRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_myrequests);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */
        alert = new AlertDialogManager(MyRequests.this);

        perfMngr = new PrefManager(getApplicationContext());
        dbManager = new DBManager(this);
        dbManager.open();


        requestsList = dbManager.getMyServicesReq(perfMngr.getUserID());
        listView=(ListView)findViewById(R.id.listView);
        adapter= new servicesAdapter(requestsList, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
 
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


    }

}