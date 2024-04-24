package com.example.login;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Room extends AppCompatActivity {

    private EditText checkInDateEditText;
    private EditText checkOutDateEditText;
    private EditText numberOfGuestsEditText;
    private Button searchButton;

    private AlertDialogManager alert;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        View rootLayout = findViewById(R.id.Room);
        if (rootLayout != null) {
            EdgeToEdge.enable(this); // Assuming this method exists in your project
            ViewCompat.setOnApplyWindowInsetsListener(rootLayout, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            // Handle the case where the root layout is not found
            // You may want to log an error or display a message
        }
        Bundle bundle = getIntent().getExtras();
        String branch = bundle.getString("branch");
        alert = new AlertDialogManager(getApplicationContext());
        checkInDateEditText = (EditText)findViewById(R.id.checkInDateEditText);
        checkOutDateEditText = (EditText)findViewById(R.id.checkInDateEditText2);
        numberOfGuestsEditText = (EditText)findViewById(R.id.numberOfGuestsEditText);

        searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidDateFormat(checkInDateEditText.getText().toString())) {
                    alert.showAlertDialog(Room.this, "Error", "Check in date is not in correct format.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }, false);
                    return;
                }
                if (!isFutureDate(checkInDateEditText.getText().toString())) {
                    alert.showAlertDialog(Room.this, "Error", "Check in date is not in the future.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }, false);
                    return;
                }

                if (!isValidDateFormat(checkOutDateEditText.getText().toString())) {
                    alert.showAlertDialog(Room.this, "Error", "Check out date is not in correct format.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }, false);
                    return;
                }
                if (!isFutureDate(checkOutDateEditText.getText().toString())) {
                    alert.showAlertDialog(Room.this, "Error", "Check out date is not in the future.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }, false);
                    return;
                }

                if(checkInDateEditText.getText().toString().equals("")){
                    alert.showAlertDialog(Room.this, "Error", "You must set check in date.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }, false);
                    return;
                }

                if(checkOutDateEditText.getText().toString().equals("")){
                    alert.showAlertDialog(Room.this, "Error", "You must set check out date.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }, false);
                    return;
                }

                String num = numberOfGuestsEditText.getText().toString();
                int numOfG = 0;
                try{
                    numOfG = Integer.parseInt(num);
                    if(numOfG > 0 && numOfG <= 10){
                        Intent intent = new Intent(Room.this, searchActivity.class);
                        intent.putExtra("branch", branch);
                        intent.putExtra("numOfG", numOfG);
                        intent.putExtra("checkInDate", checkInDateEditText.getText().toString());
                        intent.putExtra("checkOutDate", checkOutDateEditText.getText().toString());
                        startActivity(intent);

                    }else{
                        alert.showAlertDialog(Room.this, "Error", "Number of guest must between 1 and 10.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }, false);
                    }
                }catch (Exception e){
                    alert.showAlertDialog(Room.this, "Error", "Number of guest must be a number.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }, false);
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


    private boolean isValidDateFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);

        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isFutureDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();

        try {
            Date inputDate = format.parse(date);
            return inputDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }
}
