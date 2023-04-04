package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.group17project.Homepages.LoginLanding;
import com.example.group17project.ReceiverFunctionality.ExpandedReceiverActivity;
import com.example.group17project.session.UserSession;
import com.example.group17project.utils.FirebaseConstants;
import com.example.group17project.utils.adapters.UserAdapter;
import com.example.group17project.utils.model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
// this here refers to the on click method - onUserClick
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
        String newCollection;
        String currentUser = LoginLanding.encodeUserEmail(User.getInstance().getEmail());
        String ownerEmail = LoginLanding.encodeUserEmail(username);

        if (currentUser.compareTo(ownerEmail) <= 0) {
            newCollection = currentUser + "_" + ownerEmail;
        } else {
            newCollection = ownerEmail + "_" + currentUser;
        }
//       passing the chat collection from one activity to another
        Intent chatActivityIntent = new Intent(UserActivity.this, ChatActivity.class);
        chatActivityIntent.putExtra(ChatActivity.CHAT_COLLECTION, newCollection);
        startActivity(chatActivityIntent);
    }
}