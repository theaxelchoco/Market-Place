package com.example.group17project.utils.model;

public class User {

    private static User instance = null;

    private String email = "test@dal.ca";


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

    public String getEmail(){
        return email;
    }


}
