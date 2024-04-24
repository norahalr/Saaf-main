package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.models.Room;
import com.example.login.sqlLiteDB.DBManager;

public class Welcom extends AppCompatActivity {

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Welcome), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the Sign Up button
        Button signUpButton = findViewById(R.id.buttonSignUp);
        Button signInButton = findViewById(R.id.buttonSignIn);

        // Set click listener for the Sign Up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SignUpActivity when the button is clicked
                startActivity(new Intent(Welcom.this, Signup.class));
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SignUpActivity when the button is clicked
                startActivity(new Intent(Welcom.this, MainActivity.class));
            }
        });



        dbManager = new DBManager(this);
        dbManager.open();

        Room rom = dbManager.getRoom(1);
        if(rom == null){
            rom = new Room(1, "101", "Single Room",1,"Available", 60, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(2, "102", "King Room",1,"Available", 500, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(3, "103", "Twin Room",1,"Available", 120, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(4, "104", "Triple Room",1,"Available", 200, "Ryadh");
            dbManager.addRoom(rom);

            rom = new Room(5, "201", "Single Room",2,"Available", 60, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(6, "202", "King Room",2,"Available", 500, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(7, "203", "Twin Room",2,"Available", 120, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(8, "204", "Triple Room",2,"Available", 200, "Ryadh");
            dbManager.addRoom(rom);

            rom = new Room(9, "301", "Single Room",3,"Available", 60, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(10, "302", "King Room",3,"Available", 500, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(11, "303", "Twin Room",3,"Available", 120, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(12, "304", "Triple Room",3,"Available", 200, "Ryadh");
            dbManager.addRoom(rom);

            rom = new Room(13, "301", "Single Room",4,"Available", 60, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(14, "302", "King Room",4,"Available", 500, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(15, "303", "Twin Room",4,"Available", 120, "Ryadh");
            dbManager.addRoom(rom);
            rom = new Room(16, "304", "Triple Room",4,"Available", 200, "Ryadh");
            dbManager.addRoom(rom);
//------------------------------------------
            rom = new Room(17, "101", "Single Room",1,"Available", 60, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(18, "102", "King Room",1,"Available", 500, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(19, "103", "Twin Room",1,"Available", 120, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(20, "104", "Triple Room",1,"Available", 200, "Khobar");
            dbManager.addRoom(rom);

            rom = new Room(21, "201", "Single Room",2,"Available", 60, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(22, "202", "King Room",2,"Available", 500, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(23, "203", "Twin Room",2,"Available", 120, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(24, "204", "Triple Room",2,"Available", 200, "Khobar");
            dbManager.addRoom(rom);

            rom = new Room(25, "301", "Single Room",3,"Available", 60, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(26, "302", "King Room",3,"Available", 500, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(27, "303", "Twin Room",3,"Available", 120, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(28, "304", "Triple Room",3,"Available", 200, "Khobar");
            dbManager.addRoom(rom);

            rom = new Room(29, "401", "Single Room",4,"Available", 60, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(30, "402", "King Room",4,"Available", 500, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(31, "403", "Twin Room",4,"Available", 120, "Khobar");
            dbManager.addRoom(rom);
            rom = new Room(32, "404", "Triple Room",4,"Available", 200, "Khobar");
            dbManager.addRoom(rom);


        }
    }
}
