package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList; 
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "hotel.db";
    public static final int DATABASE_VERSION = 1;

    // Guests table
    public static final String TABLE_USERS = "Guests";
    public static final String COLUMN_GUEST_ID = "GuestID";
    public static final String COLUMN_FIRST_NAME = "FirstName";
    public static final String COLUMN_LAST_NAME = "LastName";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PHONE_NUMBER = "PhoneNumber";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_CHECK_IN_DATE = "CheckInDate";
    public static final String COLUMN_CHECK_OUT_DATE = "CheckOutDate";
    public static final String COLUMN_NUMBER_OF_GUESTS = "NumberOfGuests";

    // Rooms table
    public static final String TABLE_ROOMS = "Rooms";
    public static final String COLUMN_ROOM_ID = "RoomID";
    public static final String COLUMN_ROOM_NUMBER = "RoomNumber";
    public static final String COLUMN_ROOM_TYPE = "RoomType";
    public static final String COLUMN_FLOOR = "Floor";
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMN_PRICE_PER_NIGHT = "PricePerNight";

    // Reservations table
    public static final String TABLE_RESERVATIONS = "Reservations";
    public static final String COLUMN_RESERVATION_ID = "ReservationID";
    public static final String COLUMN_RESERVATION_GUEST_ID = "GuestID";
    public static final String COLUMN_RESERVATION_ROOM_ID = "RoomID";
    public static final String COLUMN_RESERVATION_CHECK_IN_DATE = "CheckInDate";
    public static final String COLUMN_RESERVATION_CHECK_OUT_DATE = "CheckOutDate";
    public static final String COLUMN_RESERVATION_NUMBER_OF_GUESTS = "NumberOfGuests";
    public static final String COLUMN_RESERVATION_TOTAL_PRICE = "TotalPrice";
    public static final String COLUMN_RESERVATION_STATUS = "ReservationStatus";

    // RoomServices table
    public static final String TABLE_ROOM_SERVICES = "RoomServices";
    public static final String COLUMN_SERVICE_ID = "ServiceID";
    public static final String COLUMN_SERVICE_RESERVATION_ID = "ReservationID";
    public static final String COLUMN_SERVICE_TYPE = "ServiceType";
    public static final String COLUMN_SERVICE_DATE = "ServiceDate";
    public static final String COLUMN_SERVICE_COST = "ServiceCost";

    // Payments table
    public static final String TABLE_PAYMENTS = "Payments";
    public static final String COLUMN_PAYMENT_ID = "PaymentID";
    public static final String COLUMN_PAYMENT_RESERVATION_ID = "ReservationID";
    public static final String COLUMN_PAYMENT_DATE = "PaymentDate";
    public static final String COLUMN_PAYMENT_METHOD = "PaymentMethod";
    public static final String COLUMN_PAYMENT_AMOUNT_PAID = "AmountPaid";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Guests table
        String createGuestsTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_GUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_CHECK_IN_DATE + " TEXT, " +
                COLUMN_CHECK_OUT_DATE + " TEXT, " +
                COLUMN_NUMBER_OF_GUESTS + " INTEGER )";
        db.execSQL(createGuestsTable);

        // Create Rooms table
        String createRoomsTable = "CREATE TABLE " + TABLE_ROOMS + " (" +
                COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROOM_NUMBER + " TEXT, " +
                COLUMN_ROOM_TYPE + " TEXT, " +
                COLUMN_FLOOR + " INTEGER, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_PRICE_PER_NIGHT + " REAL)";
        db.execSQL(createRoomsTable);

        // Create Reservations table
        String createReservationsTable = "CREATE TABLE " + TABLE_RESERVATIONS + " (" +
                COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RESERVATION_GUEST_ID + " INTEGER, " +
                COLUMN_RESERVATION_ROOM_ID + " INTEGER, " +
                COLUMN_RESERVATION_CHECK_IN_DATE + " TEXT, " +
                COLUMN_RESERVATION_CHECK_OUT_DATE + " TEXT, " +
                COLUMN_RESERVATION_NUMBER_OF_GUESTS + " INTEGER, " +
                COLUMN_RESERVATION_TOTAL_PRICE + " REAL, " +
                COLUMN_RESERVATION_STATUS + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_RESERVATION_GUEST_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_GUEST_ID + "), " +
                "FOREIGN KEY(" + COLUMN_RESERVATION_ROOM_ID + ") REFERENCES " + TABLE_ROOMS + "(" + COLUMN_ROOM_ID + "))";
        db.execSQL(createReservationsTable);

        // Create RoomServices table
        String createRoomServicesTable = "CREATE TABLE " + TABLE_ROOM_SERVICES                + " (" +
                COLUMN_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SERVICE_RESERVATION_ID + " INTEGER, " +
                COLUMN_SERVICE_TYPE + " TEXT, " +
                COLUMN_SERVICE_DATE + " TEXT, " +
                COLUMN_SERVICE_COST + " REAL, " +
                "FOREIGN KEY(" + COLUMN_SERVICE_RESERVATION_ID + ") REFERENCES " + TABLE_RESERVATIONS + "(" + COLUMN_RESERVATION_ID + "))";
        db.execSQL(createRoomServicesTable);

        // Create Payments table
        String createPaymentsTable = "CREATE TABLE " + TABLE_PAYMENTS + " (" +
                COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PAYMENT_RESERVATION_ID + " INTEGER, " +
                COLUMN_PAYMENT_DATE + " TEXT, " +
                COLUMN_PAYMENT_METHOD + " TEXT, " +
                COLUMN_PAYMENT_AMOUNT_PAID + " REAL, " +
                "FOREIGN KEY(" + COLUMN_PAYMENT_RESERVATION_ID + ") REFERENCES " + TABLE_RESERVATIONS + "(" + COLUMN_RESERVATION_ID + "))";
        db.execSQL(createPaymentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENTS);

        onCreate(db);
    }
}

