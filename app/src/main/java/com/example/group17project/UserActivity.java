package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group17project.adapter.UserAdapter;
import com.example.group17project.session.UserSession;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity
        implements UserAdapter.OnUserClickListener {

    private RecyclerView usersRV;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();
        getUsers();
    }

    private void init() {
        usersRV = findViewById(R.id.usersRV);
    }

    private void getUsers() {
        final FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(FirebaseDatabase.getInstance(FirebaseConstants.FIREBASE_URL)
                        .getReference()
                        .child(FirebaseConstants.USERS_COLLECTION), User.class)
                .build();
//        this here refers to the on click method - onUserClick
        userAdapter = new UserAdapter(options, this);

        usersRV.setAdapter(userAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        userAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userAdapter.stopListening();
    }

    @Override
    public void onUserClick(String username) {
//        getting the clicked username
        String newCollection;
        if (UserSession.getInstance().getUser().getUsername().compareTo(username) <= 0) {
            newCollection = UserSession.getInstance().getUser().getUsername() + "_" + username;
        } else {
            newCollection = username + "_" + UserSession.getInstance().getUser().getUsername();
        }
//       passing the chat collection from one activity to another
        Intent chatActivityIntent = new Intent(UserActivity.this, ChatActivity.class);
        chatActivityIntent.putExtra(ChatActivity.CHAT_COLLECTION, newCollection);
        startActivity(chatActivityIntent);
        finish();
    }
}