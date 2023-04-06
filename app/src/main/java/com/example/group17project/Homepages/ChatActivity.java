package com.example.group17project.Homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group17project.R;
import com.example.group17project.utils.adapters.ChatAdapter;
import com.example.group17project.utils.FirebaseConstants;
import com.example.group17project.utils.model.Chat;
import com.example.group17project.utils.model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    public static final String CHAT_COLLECTION = "CHAT_COLLECTION";
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private EditText chatMessageET;
    private Button chatSendBtn;
    private Button chatBackBtn;
    private String chatCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        getChatCollection();
        setListeners();
        getChatMessages();
    }

    private void init() {
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatMessageET = findViewById(R.id.chatMessageET);
        chatSendBtn = findViewById(R.id.chatSendBtn);
        chatBackBtn = findViewById(R.id.chatBackBtn);
    }

    private void getChatCollection() {
        chatCollection = getIntent().getExtras().getString(CHAT_COLLECTION);
    }

    private void setListeners() {
        chatSendBtn.setOnClickListener(view -> sendMessage());
        chatBackBtn.setOnClickListener(this::backButtonOnClick);
    }

    private void backButtonOnClick(View view){
        startActivity(new Intent(ChatActivity.this, HomepageActivity.class));
    }

    private void getChatMessages() {
//        getting the chat messages
        final FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(FirebaseDatabase.getInstance(FirebaseConstants.FIREBASE_URL)
                        .getReference("chat")
                        .child(chatCollection), Chat.class)
                .build();
//        getting chat adapter object and then bind the recycler view to the adapter
        chatAdapter = new ChatAdapter(options);
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void sendMessage() {
        final String chatMessage = chatMessageET.getText().toString();
        final Chat chatMessageObj = new Chat(User.getInstance().getEmail(), chatMessage);
//storing the message and the username in the chat collection in database
        FirebaseDatabase.getInstance(FirebaseConstants.FIREBASE_URL)
                .getReference("chat")
                .child(chatCollection)
                .push()
                .setValue(chatMessageObj)
                .addOnSuccessListener(unused -> chatMessageET.setText(""))
                .addOnFailureListener(e ->
                        Toast.makeText(ChatActivity.this,
                                        getString(R.string.failed_to_send_message),
                                        Toast.LENGTH_SHORT)
                                .show());
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatAdapter.stopListening();
    }
}