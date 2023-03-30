package com.example.group17project;

import com.example.group17project.Homepages.LoginLanding;
import com.example.group17project.ProviderFunctionality.ExpandedProviderActivity;
import com.example.group17project.ReceiverFunctionality.TransactionActivity;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class RatingUnitTest {

    static ExpandedProviderActivity expandedProviderActivity;
    static TransactionActivity transactionActivity;

    @BeforeClass
    public static void setUp() throws Exception{
        expandedProviderActivity = new ExpandedProviderActivity();
        transactionActivity = new TransactionActivity();
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    @Test
    public void transactionTestRatingSet(){
        assertFalse(transactionActivity.isRatingSet());
        transactionActivity.setRating(5f);
        assertTrue(transactionActivity.isRatingSet());
    }

    @Test
    public void providerTestRatingSet(){
        assertFalse(expandedProviderActivity.isRatingSet());
        expandedProviderActivity.setRating(3f);
        assertTrue(expandedProviderActivity.isRatingSet());
    }



}


