package com.example.group17project.session;

import com.example.group17project.utils.model.User;

public class UserSession {

    private static UserSession instance;
    private User user;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}