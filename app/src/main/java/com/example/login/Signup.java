package com.example.login;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.models.User;
import com.example.login.sqlLiteDB.DBManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {
    private  PrefManager prefMngr;
    private TextInputLayout nameLayout, dobLayout, mobileLayout, emailLayout, usernameLayout, passwordLayout;
    private TextInputEditText nameEditText, dobEditText, mobileEditText, emailEditText, usernameEditText, passwordEditText;
    private Button signupButton;

    DBManager dbManager;
    private AlertDialogManager alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        nameEditText = (TextInputEditText) findViewById(R.id.nameInput);
        dobEditText = (TextInputEditText)findViewById(R.id.dobInput);
        mobileEditText = (TextInputEditText)findViewById(R.id.mobileInput);
        emailEditText = (TextInputEditText)findViewById(R.id.emailInput);
        usernameEditText = (TextInputEditText)findViewById(R.id.usernameInput);
        passwordEditText = (TextInputEditText)findViewById(R.id.passwordInput);

        alert = new AlertDialogManager(this);
        dbManager = new DBManager(this);
        dbManager.open();


        signupButton = (Button) findViewById(R.id.buttonLogin);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        nameEditText.getText().toString().equals("")||
                        emailEditText.getText().toString().equals("")||
                        passwordEditText.getText().toString().equals("")||
                        usernameEditText.getText().toString().equals("")
                ){

                    alert.showAlertDialog(Signup.this, "Error", "Inputs are empty", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }, false);
                }else{
                    int id = 0;
                    String name = nameEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String phoneNumber = mobileEditText.getText().toString();
                    String dob= dobEditText.getText().toString();
                    String username= usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();

                    User userOb = new User(id, name, email, phoneNumber, dob, username, password);

                    if(dbManager.register(userOb)){
                        alert.showAlertDialog(Signup.this, "Success", "You have successfully registered, you can login now.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, true);
                    }else{
                        alert.showAlertDialog(Signup.this, "Error", "Could not registered, try again.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }, false);
                    }
                }
            }
        });

    }

}
