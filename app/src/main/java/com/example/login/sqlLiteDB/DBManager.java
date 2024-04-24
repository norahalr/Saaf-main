package com.example.login.sqlLiteDB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.login.RequestRoomService;
import com.example.login.models.Guest;
import com.example.login.models.Reservations;
import com.example.login.models.Room;
import com.example.login.models.RoomServices;
import com.example.login.models.User;

import java.util.ArrayList;

public class DBManager {

    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
    public boolean register(User guest) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.TABLE_USERS_COLUMN_NAME, guest.getName());
        contentValue.put(DBHelper.TABLE_USERS_COLUMN_EMAIL, guest.getEmail());
        contentValue.put(DBHelper.TABLE_USERS_COLUMN_PHONE_NUMBER, guest.getPhoneNumber());
        contentValue.put(DBHelper.TABLE_USERS_COLUMN_DOB, guest.getDob());
        contentValue.put(DBHelper.TABLE_USERS_COLUMN_USERNAME, guest.getUsername());
        contentValue.put(DBHelper.TABLE_USERS_COLUMN_PASSWORD, guest.getPassword());
        int d = (int)database.insert(DBHelper.TABLE_USERS, null, contentValue);
        return (d>0);
    }
    public User loginUser(String username, String password) {

        String sql = "select * from "+ DBHelper.TABLE_USERS + " where "+
                DBHelper.TABLE_USERS_COLUMN_USERNAME+" = '"+username+"' and "+
                DBHelper.TABLE_USERS_COLUMN_PASSWORD+" = '"+password+"'";
        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        User user = null;
        while(res.isAfterLast() == false) {
            @SuppressLint("Range") int id = res.getInt(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_USER_ID));
            @SuppressLint("Range") String name = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_NAME));
            @SuppressLint("Range") String email = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_EMAIL));
            @SuppressLint("Range") String phoneNumber = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_PHONE_NUMBER));
            @SuppressLint("Range") String dob = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_DOB));
            @SuppressLint("Range") String _username = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_USERNAME));
            @SuppressLint("Range") String _password = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_PASSWORD));


            user = new User(id, name, email, phoneNumber, dob, username, password);
            res.moveToNext();
        }
        return user;
    }

    public boolean addRoom(Room room) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.COLUMN_ROOM_NUMBER, room.getROOM_NUMBER());
        contentValue.put(DBHelper.COLUMN_ROOM_TYPE, room.getROOM_TYPE());
        contentValue.put(DBHelper.COLUMN_FLOOR, room.getFLOOR());
        contentValue.put(DBHelper.COLUMN_STATUS, room.getSTATUS());
        contentValue.put(DBHelper.COLUMN_PRICE_PER_NIGHT, room.getPRICE_PER_NIGHT());
        contentValue.put(DBHelper.COLUMN_LOCATION, room.getLOCATION());
        int d = (int)database.insert(DBHelper.TABLE_ROOMS, null, contentValue);
        return (d>0);
    }
    public boolean addService(RoomServices srvice) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.COLUMN_SERVICE_RESERVATION_ID, srvice.getRESERVATION_ID());
        contentValue.put(DBHelper.COLUMN_SERVICE_TYPE, srvice.getSERVICE_TYPE());
        contentValue.put(DBHelper.COLUMN_SERVICE_DATE, srvice.getSERVICE_DATE());
        contentValue.put(DBHelper.COLUMN_SERVICE_DESCRIPTION, srvice.getDESCRIPTION());
        int d = (int)database.insert(DBHelper.TABLE_ROOM_SERVICES, null, contentValue);
        return (d>0);
    }

    public ArrayList<RoomServices> getMyServicesReq(int user_id) {
        ArrayList  array_list = new ArrayList<RoomServices>();
        String sql = "select * from RoomServices ,Reservations " +
                " where RoomServices.ReservationID = Reservations.ReservationID " +
                " and Reservations.GuestID = '"+user_id+"'" ;
        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        RoomServices roomSrv = null;
        while(res.isAfterLast() == false) {
            @SuppressLint("Range") int _id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_SERVICE_ID));
            @SuppressLint("Range") int book_id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_SERVICE_RESERVATION_ID));
            @SuppressLint("Range") String service_type = res.getString(res.getColumnIndex(DBHelper.COLUMN_SERVICE_TYPE));
            @SuppressLint("Range") String service_date = res.getString(res.getColumnIndex(DBHelper.COLUMN_SERVICE_DATE));
            @SuppressLint("Range") String description = res.getString(res.getColumnIndex(DBHelper.COLUMN_SERVICE_DESCRIPTION));

            roomSrv= new RoomServices(_id, book_id, service_type, service_date, description);
            array_list.add(roomSrv);

            res.moveToNext();
        }
        return array_list;
    }
    public boolean bookdRoom(Reservations reserve) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.COLUMN_RESERVATION_ROOM_ID, reserve.getROOM_ID());
        contentValue.put(DBHelper.COLUMN_RESERVATION_USER_ID, reserve.getGUEST_ID());
        contentValue.put(DBHelper.COLUMN_RESERVATION_CHECK_IN_DATE, reserve.getCHECK_IN_DATE());
        contentValue.put(DBHelper.COLUMN_RESERVATION_CHECK_OUT_DATE, reserve.getCHECK_OUT_DATE());
        contentValue.put(DBHelper.COLUMN_RESERVATION_NUMBER_OF_GUESTS, reserve.getNUMBER_OF_GUESTS());
        contentValue.put(DBHelper.COLUMN_RESERVATION_TOTAL_PRICE, reserve.getTOTAL_PRICE());
        contentValue.put(DBHelper.COLUMN_RESERVATION_STATUS, reserve.getSTATUS());

        int d = (int)database.insert(DBHelper.TABLE_RESERVATIONS, null, contentValue);
        return (d>0);
    }

    public Room getRoom(int id) {

        String sql = "select * from "+ DBHelper.TABLE_ROOMS + " where "+
                DBHelper.COLUMN_ROOM_ID+" = '"+id+"'";
        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        Room room = null;
        while(res.isAfterLast() == false) {

            @SuppressLint("Range") int _id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_ROOM_ID));
            @SuppressLint("Range") String roomNo = res.getString(res.getColumnIndex(DBHelper.COLUMN_ROOM_NUMBER));
            @SuppressLint("Range") String type = res.getString(res.getColumnIndex(DBHelper.COLUMN_ROOM_TYPE));
            @SuppressLint("Range") int floor = res.getInt(res.getColumnIndex(DBHelper.COLUMN_FLOOR));
            @SuppressLint("Range") String status = res.getString(res.getColumnIndex(DBHelper.COLUMN_STATUS));
            @SuppressLint("Range") double price = res.getDouble(res.getColumnIndex(DBHelper.COLUMN_PRICE_PER_NIGHT));
            @SuppressLint("Range") String location = res.getString(res.getColumnIndex(DBHelper.COLUMN_LOCATION));


            room = new Room(id, roomNo, type, floor, status, price,location);
            res.moveToNext();
        }
        return room;
    }


    public boolean isRoomReserved(int room_id, String check_in, String check_out) {
        boolean reserved = false;
        String sql = "select * from "+ DBHelper.TABLE_RESERVATIONS +" where "+ DBHelper.COLUMN_RESERVATION_ROOM_ID + " = '"+room_id+"'"
                + " and ( (CheckInDate BETWEEN '" + check_in + "' AND '" + check_out + "')"
                + " or    (CheckOutDate BETWEEN '" + check_in + "' AND '" + check_out + "'))"
                + " and  "+ DBHelper.COLUMN_RESERVATION_STATUS + " != 'Cancel'"
                ;

        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            reserved = true;
            res.moveToNext();
        }
        return reserved;
    }


    public ArrayList<Room> getAvailableRooms(String branch, String checkInDate, String checkOutDate) {
        ArrayList  array_list = new ArrayList<Room>();
        String sql = "select * from "+ DBHelper.TABLE_ROOMS +  " where "+DBHelper.COLUMN_LOCATION+" ='"+branch+"'" ;
        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        Room room = null;
        while(res.isAfterLast() == false) {

            @SuppressLint("Range") int _id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_ROOM_ID));
            @SuppressLint("Range") String roomNo = res.getString(res.getColumnIndex(DBHelper.COLUMN_ROOM_NUMBER));
            @SuppressLint("Range") String type = res.getString(res.getColumnIndex(DBHelper.COLUMN_ROOM_TYPE));
            @SuppressLint("Range") int floor = res.getInt(res.getColumnIndex(DBHelper.COLUMN_FLOOR));
            @SuppressLint("Range") String status = res.getString(res.getColumnIndex(DBHelper.COLUMN_STATUS));
            @SuppressLint("Range") double price = res.getDouble(res.getColumnIndex(DBHelper.COLUMN_PRICE_PER_NIGHT));
            @SuppressLint("Range") String location = res.getString(res.getColumnIndex(DBHelper.COLUMN_LOCATION));

            if(!this.isRoomReserved(_id, checkInDate, checkOutDate)) {

                room = new Room(_id, roomNo, type, floor, status, price, location);
                array_list.add(room);
            }
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Reservations> getMyReservations(int userID, String status) {

        ArrayList  array_list = new ArrayList<Reservations>();
        String sql = "select * from "+ DBHelper.TABLE_RESERVATIONS +  " where "+DBHelper.COLUMN_RESERVATION_USER_ID+" ='"+userID+"'" ;
        if(!status.equals("")) {
            sql = sql + " and "+DBHelper.COLUMN_RESERVATION_STATUS+" ='"+status+"'";
        }
        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        Reservations book  = null;
        while(res.isAfterLast() == false) {
            @SuppressLint("Range") int _id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_ID));
            @SuppressLint("Range") int user_id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_USER_ID));
            @SuppressLint("Range") int roomNo = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_ROOM_ID));
            @SuppressLint("Range") String checkInDate = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_CHECK_IN_DATE));
            @SuppressLint("Range") String checkOutDate = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_CHECK_OUT_DATE));
            @SuppressLint("Range") int noOfG = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_NUMBER_OF_GUESTS));
            @SuppressLint("Range") double total_price = res.getDouble(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_TOTAL_PRICE));
            @SuppressLint("Range") String _status = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_STATUS));
            book = new Reservations(_id,user_id,
                    roomNo,
                    checkInDate,
                    checkOutDate,
                    noOfG,
                    total_price,
                    _status);
            array_list.add(book);
            res.moveToNext();
        }
        return array_list;
    }

    public boolean updateBook(Reservations book, String status) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.COLUMN_RESERVATION_STATUS, status);
        int d = (int)database.update(DBHelper.TABLE_RESERVATIONS,  contentValue, DBHelper.COLUMN_RESERVATION_ID+"=?", new String[]{""+book.getId()});
        return (d>0);
    }

    public Reservations getReservation(String bookId) {

        String sql = "select * from "+ DBHelper.TABLE_RESERVATIONS +  " where "+DBHelper.COLUMN_RESERVATION_ID+" ='"+bookId+"'" ;

        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        Reservations book  = null;
        while(res.isAfterLast() == false) {
            @SuppressLint("Range") int _id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_ID));
            @SuppressLint("Range") int user_id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_USER_ID));
            @SuppressLint("Range") int roomNo = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_ROOM_ID));
            @SuppressLint("Range") String checkInDate = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_CHECK_IN_DATE));
            @SuppressLint("Range") String checkOutDate = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_CHECK_OUT_DATE));
            @SuppressLint("Range") int noOfG = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_NUMBER_OF_GUESTS));
            @SuppressLint("Range") double total_price = res.getDouble(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_TOTAL_PRICE));
            @SuppressLint("Range") String _status = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_STATUS));
            book = new Reservations(_id,user_id,
                    roomNo,
                    checkInDate,
                    checkOutDate,
                    noOfG,
                    total_price,
                    _status);
            res.moveToNext();
        }
        return book;
    }

    public boolean updateBookCheck(Reservations book, String checkIn, String checkOut, int nOfG , double totalPrice) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.COLUMN_RESERVATION_CHECK_IN_DATE, checkIn);
        contentValue.put(DBHelper.COLUMN_RESERVATION_CHECK_OUT_DATE, checkOut);
        contentValue.put(DBHelper.COLUMN_RESERVATION_NUMBER_OF_GUESTS, nOfG);
        contentValue.put(DBHelper.COLUMN_RESERVATION_TOTAL_PRICE, totalPrice);
        int d = (int)database.update(DBHelper.TABLE_RESERVATIONS,  contentValue,
                DBHelper.COLUMN_RESERVATION_ID+"=?",
                new String[]{""+book.getId()});
        return (d>0);
    }

    public boolean updateBookBranchRoom(Reservations book, int room_id , double totalPrice) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.COLUMN_RESERVATION_ROOM_ID, room_id);
        contentValue.put(DBHelper.COLUMN_RESERVATION_TOTAL_PRICE, totalPrice);
        int d = (int)database.update(DBHelper.TABLE_RESERVATIONS,  contentValue,
                DBHelper.COLUMN_RESERVATION_ID+"=?",
                new String[]{""+book.getId()});
        return (d>0);
    }

    public User getUser(int userID) {
        String sql = "select * from "+ DBHelper.TABLE_USERS + " where "+
                DBHelper.TABLE_USERS_COLUMN_USER_ID+" = '"+userID+"'";
        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        User userObj = null;
        while(res.isAfterLast() == false) {
            @SuppressLint("Range") int _id = res.getInt(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_USER_ID));
            @SuppressLint("Range") String name = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_NAME));
            @SuppressLint("Range") String email = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_EMAIL));
            @SuppressLint("Range") String phoneNumber = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_PHONE_NUMBER));
            @SuppressLint("Range") String dob = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_DOB));
            @SuppressLint("Range") String username = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_USERNAME));
            @SuppressLint("Range") String password = res.getString(res.getColumnIndex(DBHelper.TABLE_USERS_COLUMN_PASSWORD));

            userObj = new User(userID, name, email, phoneNumber, dob, username, password);
            res.moveToNext();
        }
        return userObj;
    }

    public Reservations searchForReservation(String bookId, int userID) {

        String sql = "select * from "+ DBHelper.TABLE_RESERVATIONS +  " where "
                +DBHelper.COLUMN_RESERVATION_ID+" ='"+bookId+"'  and "
                +DBHelper.COLUMN_RESERVATION_USER_ID+" ='"+userID+"' "
                ;

        Cursor res = database.rawQuery( sql, null );
        res.moveToFirst();
        Reservations book  = null;
        while(res.isAfterLast() == false) {
            @SuppressLint("Range") int _id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_ID));
            @SuppressLint("Range") int user_id = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_USER_ID));
            @SuppressLint("Range") int roomNo = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_ROOM_ID));
            @SuppressLint("Range") String checkInDate = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_CHECK_IN_DATE));
            @SuppressLint("Range") String checkOutDate = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_CHECK_OUT_DATE));
            @SuppressLint("Range") int noOfG = res.getInt(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_NUMBER_OF_GUESTS));
            @SuppressLint("Range") double total_price = res.getDouble(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_TOTAL_PRICE));
            @SuppressLint("Range") String _status = res.getString(res.getColumnIndex(DBHelper.COLUMN_RESERVATION_STATUS));
            book = new Reservations(_id,user_id,
                    roomNo,
                    checkInDate,
                    checkOutDate,
                    noOfG,
                    total_price,
                    _status);
            res.moveToNext();
        }
        return book;
    }
}
