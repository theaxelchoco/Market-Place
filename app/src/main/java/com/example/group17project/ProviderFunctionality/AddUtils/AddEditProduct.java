package com.example.group17project.ProviderFunctionality.AddUtils;

import android.content.Context;

import java.util.Calendar;

public interface AddEditProduct {
    boolean add(Context context, String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal);
    boolean edit(Context context, String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal, String productId);
}
