package com.example.group17project.utils.model;

import android.location.Location;

public class User {

    private static User instance = null;

    private String email = "test@dal.ca";

    private Location userLocation;


    private User(){

    }

    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    public void setUserDetails(String email){
        this.email = email;
    }

    public void setUserLocation(Location userLocation){
        this.userLocation = userLocation;
    }
    public Location getUserLocation(){
        return userLocation;
    }

    public String getEmail(){
        return email;
    }


}
