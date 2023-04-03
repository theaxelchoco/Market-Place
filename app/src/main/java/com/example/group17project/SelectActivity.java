package com.example.group17project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.session.UserSession;

public class SelectActivity extends AppCompatActivity {

    private Button groupChatBtn;
    private Button individualChatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        init();
        setListeners();
    }

    private void init() {
        groupChatBtn = findViewById(R.id.groupChatBtn);
        individualChatBtn = findViewById(R.id.individualChatBtn);
    }

    private void setListeners() {
//        if the group chat button is clicked, steps performed
        groupChatBtn.setOnClickListener(view -> {
            Intent chatActivityIntent = new Intent(SelectActivity.this, ChatActivity.class);
//            passing extra intent here to get all the users and their chat collections
            chatActivityIntent.putExtra(ChatActivity.CHAT_COLLECTION, FirebaseConstants.CHAT_COLLECTION);
            startActivity(chatActivityIntent);
        });

        individualChatBtn.setOnClickListener(view ->
                startActivity(new Intent(SelectActivity.this, UserActivity.class)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        inflating the menu icon
        if (item.getItemId() == R.id.logoutItem) {
//            actions performed after the user clicks the logout button
            UserSession.getInstance().setUser(null);
            startActivity(new Intent(SelectActivity.this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
