package com.example.group17project.ProviderFunctionality.AddUtils;

import java.util.Calendar;

/**
 * This interface is used to add products to the database, abstracting away logic from the add product activity
 */
public interface AddEditProduct {
    boolean add(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal);
    boolean edit(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal, String productId);
}
