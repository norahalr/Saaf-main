package com.example.login.models;

public class User {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String dob;
    private String username;
    private String password;

    public User(int guestId, String name, String email, String phoneNumber, String dob, String username, String password) {
        this.id = guestId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }
}


