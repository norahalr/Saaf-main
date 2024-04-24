package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.models.User;
import com.example.login.sqlLiteDB.DBManager;

public class Profile extends AppCompatActivity {

    private DBManager dbManager;
    private PrefManager prefMngr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);


        TextView username = (TextView)findViewById(R.id.userName);
        TextView useremail = (TextView)findViewById(R.id.userEmail);


        dbManager = new DBManager(this);
        dbManager.open();
        prefMngr = new PrefManager(getApplicationContext());

        User currentUsre = dbManager.getUser(prefMngr.getUserID());

        username.setText(currentUsre.getUsername() + "("+currentUsre.getName()+")");
        useremail.setText(currentUsre.getEmail());
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        TextView myResv = (TextView)findViewById(R.id.myResLbl);
        myResv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Myreservation.class);
                startActivity(i);
            }
        });

        TextView req_serv_lbl = (TextView)findViewById(R.id.req_serv_lbl);
        req_serv_lbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MyRequests.class);
                startActivity(i);
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

    }
}