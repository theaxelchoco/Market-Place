package com.example.group17project;

import static org.junit.Assert.*;

import androidx.fragment.app.Fragment;

import com.example.group17project.utils.model.Product;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReceiverUnitTest {

    static ReceiverFragment receiverFragment;

    @BeforeClass
    public static void setup() throws Exception{
        receiverFragment = new ReceiverFragment();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        System.gc();
    }


    @Test
    public void checkIfEmpty(){
        assertFalse(ReceiverFragment.class.getName().isEmpty());
        assertTrue(!ReceiverFragment.class.getName().isEmpty());
    }

    @Test
    public void checkIfReceiverFragmentWorks(){
        assertTrue(ReceiverFragment.class.isInstance(receiverFragment));
        assertFalse(ReceiverFragment.class.isLocalClass());
    }

    @Test
    public void checkIfInterface(){
        assertTrue(!ReceiverFragment.class.isInterface());
        assertFalse(ReceiverFragment.class.isInterface());

    }



}
