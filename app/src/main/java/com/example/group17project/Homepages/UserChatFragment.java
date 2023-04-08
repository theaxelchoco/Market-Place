/*
Visualization code
Group 17
*/

package com.example.group17project.Homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.group17project.R;

import com.example.group17project.utils.adapters.UserAdapter;

import com.example.group17project.utils.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserChatFragment extends Fragment {

    DatabaseReference databaseRef;
    private UserAdapter userAdapter;
    private ListView availableUsers;
    FirebaseDatabase database = null;
    ArrayList<String> userList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.firebase_database_url));
        databaseRef = database.getReference("chat");
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(getActivity(), userList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user, container, false);
        availableUsers = view.findViewById(R.id.usersListView);
        availableUsers.setAdapter(userAdapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        //First check to see which chats the user has interacted in, and only display those
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    String chatInteractions = data.getKey();

                    String[] usernames = usernameSplitter(chatInteractions);
                    String user1 = usernames[0];
                    String user2 = usernames[1];

                    //We only want the user to see users in their message history that they have interacted with
                    if(user1.equals(User.getInstance().getEmail())){
                        userList.add(user2);
                    }
                    else if(user2.equals(User.getInstance().getEmail())){
                        userList.add(user1);
                    }
                }

                userAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // This method should not do anything if cancelled, hence why it is empty
            }

        });

        availableUsers.setClickable(true);
        availableUsers.setEnabled(true);

        //Setting the onclick method for the items in the listView, passing the chat collection string to the next activity to correspond to a chat
        availableUsers.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedUser = userList.get(i);
            String newCollection;
            String currentUser = User.getInstance().getEmail();
            String otherEmail = selectedUser;

            newCollection = chatCollectionCreator(currentUser, otherEmail);

            Intent chatActivityIntent = new Intent(getActivity(), ChatActivity.class);
            chatActivityIntent.putExtra(ChatActivity.CHAT_COLLECTION, newCollection);
            startActivity(chatActivityIntent);
        });

    }

    /**
     * This method is used to check a chat collection string to see if the user is part of the interaction
     * @param key the chat collection key
     * @return a string array with the two users parsed from collection key
     */
    public String[] usernameSplitter(String key){
        String[] usernames = key.split("_");
        if(usernames.length == 2){
            String user1 = LoginLanding.decodeUserEmail(usernames[0]);
            String user2 = LoginLanding.decodeUserEmail(usernames[1]);

            usernames[0] = user1;
            usernames[1] = user2;
        }

        return usernames;
    }

    /**
     * This method is used to create a chat collection string from two user strings
     * @param user1 the first user
     * @param user2 the second user
     * @return the string representing the chat interaction
     */
    public String chatCollectionCreator(String user1, String user2){
        String currentUser = LoginLanding.encodeUserEmail(user1);
        String otherEmail = LoginLanding.encodeUserEmail(user2);

        if (currentUser.compareTo(otherEmail) <= 0) {
            return currentUser + "_" + otherEmail;
        } else {
            return otherEmail + "_" + currentUser;
        }
    }

}
