package com.example.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.models.RoomServices;
import com.example.login.sqlLiteDB.DBManager;

public class RequestService extends AppCompatActivity {

    private AlertDialogManager alert;
    private PrefManager perfMngr;
    private DBManager dbManager;
    private EditText editTextRequest;
    private Button buttonCancel;
    private Button buttonSendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_room_service);

        Bundle bundle = getIntent().getExtras();
        int book_id = bundle.getInt("book_id");
        alert = new AlertDialogManager(getApplicationContext());
        perfMngr = new PrefManager(getApplicationContext());
        dbManager = new DBManager(this);
        dbManager.open();


        editTextRequest= (EditText)findViewById(R.id.editTextRequest);
        buttonCancel= (Button)findViewById(R.id.buttonCancel);
        buttonSendRequest= (Button)findViewById(R.id.buttonSendRequest);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomServices rms= new RoomServices(0,book_id,"Room Service","",editTextRequest.getText().toString());
                if(!editTextRequest.getText().toString().equals("")){
                    if(dbManager.addService(rms)){
                        alert.showAlertDialog(RequestService.this, "Success", "Request sent succssfully", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }, true);
                    }else{
                        alert.showAlertDialog(RequestService.this, "Error", "Request not sent succssfully", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }, true);
                    }
                }
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