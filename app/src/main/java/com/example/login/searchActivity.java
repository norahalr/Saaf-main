package com.example.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.adapter.roomAdapter;
import com.example.login.models.Reservations;
import com.example.login.models.Room;
import com.example.login.sqlLiteDB.DBHelper;
import com.example.login.sqlLiteDB.DBManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {
    ArrayList<Room> roomsList = new ArrayList<>();
    private ListView listView;
    private roomAdapter adapter;
    private DBManager dbManager;
    private AlertDialogManager alert;
    String branch ;
    int numOfG ;
    String checkInDate ;
    String checkOutDate;
    PrefManager perfMngr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_result);

        alert = new AlertDialogManager(searchActivity.this);

        Bundle bundle = getIntent().getExtras();
        branch = bundle.getString("branch");
        numOfG = bundle.getInt("numOfG");
        checkInDate = bundle.getString("checkInDate");
        checkOutDate = bundle.getString("checkOutDate");
        perfMngr = new PrefManager(getApplicationContext());
        dbManager = new DBManager(this);
        dbManager.open();

        roomsList = dbManager.getAvailableRooms(branch, checkInDate, checkOutDate);
        listView=(ListView)findViewById(R.id.listView);
        adapter= new roomAdapter(roomsList, this);
        listView.setAdapter(adapter);

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

    public long getDaysDiff(){
        String d1_str[] = checkInDate.split("-");
        String d2_str[] = checkOutDate.split("-");
        LocalDate date1 = LocalDate.of(Integer.parseInt(d1_str[2]), Integer.parseInt(d1_str[1]), Integer.parseInt(d1_str[0]));
        LocalDate date2 = LocalDate.of(Integer.parseInt(d2_str[2]), Integer.parseInt(d2_str[1]), Integer.parseInt(d2_str[0]));

        long daysDifference = ChronoUnit.DAYS.between(date1, date2);
        return daysDifference;
    }
    public void bookRoom(Room room) {
        alert.showMessageOKCancel("Are you want to book room ("+room.getROOM_NUMBER()+")? \n"+
                    "Hotel Branch: "+branch+" \n"+
                    "Check in date: "+checkInDate+" \n"+
                    "Check out date: "+checkOutDate+" \n"+
                    "No of Guests: "+numOfG+" \n"+
                    "No of days: "+getDaysDiff()+" \n"+
                    "Total Price: "+(getDaysDiff()*room.getPRICE_PER_NIGHT())+" \n"
                , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int ROOM_ID = room.getId();
                int GUEST_ID = perfMngr.getUserID();
                String CHECK_IN_DATE = checkInDate;
                String CHECK_OUT_DATE = checkOutDate;
                int NUMBER_OF_GUESTS = numOfG;
                String STATUS = "New";
                double TOTAL_PRICE = (getDaysDiff()*room.getPRICE_PER_NIGHT());

                Reservations book = new Reservations(0,GUEST_ID,
                  ROOM_ID,
                  CHECK_IN_DATE,
                  CHECK_OUT_DATE,
                  NUMBER_OF_GUESTS,
                  TOTAL_PRICE,
                  STATUS);
                if(dbManager.bookdRoom(book)) {
                    alert.showAlertDialog(searchActivity.this, "Success", "You've book the room successfully", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            roomsList.remove(room);
                            adapter.notifyDataSetChanged();
                        }
                    }, true);
                }else{
                    alert.showAlertDialog(searchActivity.this, "Error", "Couldn't book the room", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false);
                }
            }
        });
    }
}