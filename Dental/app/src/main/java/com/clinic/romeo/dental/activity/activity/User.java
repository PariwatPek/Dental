package com.clinic.romeo.dental.activity.activity;

/**
 * Created by Romeo on 10/31/2017.
 */

public class User {
    public String PatientID;
    public String Email;
    public String Username;
    public String PhoneNum;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String PatientID, String Email, String Username , String PhoneNum) {
        this.PatientID = PatientID;
        this.Email = Email;
        this.Username = Username;
        this.PhoneNum = PhoneNum;
    }
}
