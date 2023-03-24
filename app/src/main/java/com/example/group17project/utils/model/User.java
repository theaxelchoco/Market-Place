package com.example.group17project.utils.model;

import android.location.Location;

public class User {

    private static User instance = null;

    private String email = "test@dal.ca";
    private int pValuation = 0;
    private int rValuation = 0;
    private float rating = 0;

    private String userLocation;


    private User(){

    }

    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    public void setpValuation(int pValuation){
        this.pValuation = pValuation;
    }

    public int getpValuation() {
        return pValuation;
    }

    public void setrValuation(int rValuation){
        this.rValuation = rValuation;
    }

    public int getrValuation() {
        return rValuation;
    }

    public void setRating(float rating){
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setUserDetails(String email, int pVal, int rVal, float rate){
        this.email = email;
        this.pValuation = pVal;
        this.rValuation = rVal;
        this.rating = rate;
    }

    public void setUserLocation(String userLocation){
        this.userLocation = userLocation;
    }
    public String getUserLocation(){
        return userLocation;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }


}
