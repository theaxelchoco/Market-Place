package com.example.group17project.ProviderFunctionality.AddUtils;

import com.example.group17project.utils.model.Product;

import java.util.Calendar;

public interface AddEditProduct {
    boolean add(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal);
    boolean edit(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal, String productId);
}
