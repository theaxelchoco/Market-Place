package com.example.group17project;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddProductUnitTest {

    static AddProductActivity addProduct;

    @BeforeClass
    public static void setup() throws Exception{
        addProduct = new AddProductActivity();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        System.gc();
    }

    @Test
    public void checkIfProductNameValid(){
        assertFalse(addProduct.validProductName(""));
        assertTrue(addProduct.validProductName("Iphone 12"));
    }

    @Test
    public void checkIfMarketValueValid(){
        assertTrue(addProduct.validMarketValue("10"));
        assertFalse(addProduct.validMarketValue(""));
        assertFalse(addProduct.validMarketValue("0"));
        assertFalse(addProduct.validMarketValue("-10"));
        assertFalse(addProduct.validMarketValue("10.1"));
        assertFalse(addProduct.validMarketValue("ten"));
        assertFalse(addProduct.validMarketValue("10 1"));
    }

    @Test
    public void checkIfPlaceOfExchangeValid(){
        assertTrue(addProduct.validPlaceOfExchange("Halifax"));
        assertFalse(addProduct.validPlaceOfExchange(""));
    }



}
