package com.example.group17project;

import static org.junit.Assert.*;

import com.example.group17project.Homepages.LoginLanding;
import com.example.group17project.Homepages.UserChatFragment;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChatUnitTest {

    static UserChatFragment chatFragment;

    @BeforeClass
    public static void setLogin() throws Exception{
        chatFragment = new UserChatFragment();
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    @Test
    public void testSplitUsername(){
        String[] users = chatFragment.usernameSplitter("test@dal,ca_test2@dal,ca");
        assertEquals(2, users.length);
        assertEquals("test@dal.ca", users[0]);
        assertEquals("test2@dal.ca", users[1]);
    }

    @Test
    public void unsuccessfulSplitUser(){
        String[] users = chatFragment.usernameSplitter("test@dal,ca,test2@dal,ca");
        assertEquals(1, users.length);
        assertEquals("test@dal,ca,test2@dal,ca", users[0]);
    }

    @Test
    public void chatCollectionCreationSuccess(){
        String collection = chatFragment.chatCollectionCreator("xyz@dal.ca", "abc@dal.ca");
        assertEquals(collection, "abc@dal,ca_xyz@dal,ca");
    }

    @Test
    public void chatCollectionCreationSuccess2(){
        String collection = chatFragment.chatCollectionCreator("abc@dal.ca", "xyz@dal.ca");
        assertEquals(collection, "abc@dal,ca_xyz@dal,ca");
    }

}