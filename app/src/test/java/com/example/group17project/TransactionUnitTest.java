package com.example.group17project;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.example.group17project.ReceiverFunctionality.TransactionActivity;

public class TransactionUnitTest {

    static TransactionActivity transaction;

    @BeforeClass
    public static void setup() throws Exception{
        transaction = new TransactionActivity();
    }

    @AfterClass
    public static void tearDown() throws Exception{
        System.gc();
    }

    @Test
    public void checkIfNameEmpty(){
        assertFalse(transaction.isProductNameValid(""));
        assertTrue(transaction.isProductNameValid("Trade Item"));
    }

    @Test
    public void checkIfValueValid(){
        assertTrue(transaction.isValidMarketValue("10"));
        assertFalse(transaction.isValidMarketValue(""));
        assertFalse(transaction.isValidMarketValue("0"));
        assertFalse(transaction.isValidMarketValue("-10"));
        assertFalse(transaction.isValidMarketValue("10.1"));
        assertFalse(transaction.isValidMarketValue("ten"));
        assertFalse(transaction.isValidMarketValue("10 1"));
    }

    @Test
    public void checkIfRatingAdded(){
        assertFalse(transaction.isRatingSet());
        transaction.setRating(4);
        assertTrue(transaction.isRatingSet());
    }
}
