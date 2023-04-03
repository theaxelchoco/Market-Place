package com.example.group17project.model;

public class User {
    //    keys
    private String username;
    private String password;

    //required empty constructor for firebase
    public User() {
    }

    //initialising constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}