package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.session.UserSession;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        incase the user is logged in, then displaying the chat activity
        if (UserSession.getInstance() != null && UserSession.getInstance().getUser() != null) {
            startActivity(new Intent(SplashActivity.this, SelectActivity.class));
        }
//        incase the user is not logged in, then displaying the login activity
        else {
            startActivity(new Intent(SplashActivity.this, LoginLanding.class));
        }

        finish();
    }
}
