package com.example.group17project.ProviderFunctionality.AddUtils;

import com.example.group17project.utils.model.Product;

import java.util.Calendar;

public class ProxyAddProduct implements AddEditProduct{
    private static ProxyAddProduct instance = null;
    RealAddProduct addProductInstance;

    private ProxyAddProduct(){
        addProductInstance = RealAddProduct.getInstance();
    }

    public static ProxyAddProduct getInstance() {
        if(instance == null){
            instance = new ProxyAddProduct();
        }
        return instance;
    }


    @Override
    public boolean add(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal) {
        if(validProductName(name) && validMarketValue(marketVal) && validPlaceOfExchange(exchangePlace)){
            return addProductInstance.add(name, ownerId, desc, date, productType, exchangePlace, prefExchange, marketVal);

        }
        else{
            return false;
        }
    }

    @Override
    public boolean edit(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal, String productId) {
        if(validProductName(name) && validMarketValue(marketVal) && validPlaceOfExchange(exchangePlace)){
            return addProductInstance.edit(name, ownerId, desc, date, productType, exchangePlace, prefExchange, marketVal, productId);
        }
        else{
            return false;
        }
    }


    /**
     * Checks to see if the product name is valid (not empty)
     * @param name product name entered by user
     * @return true if valid, false otherwise
     */
    public boolean validProductName(String name) {
        return !name.isEmpty();
    }

    /**
     * Checks to see if market value is valid (not empty and only integer)
     * @param marketValue market val entered by user
     * @return true if valid, false otherwise
     */
    public boolean validMarketValue(String marketValue) {
        if (marketValue.isEmpty()) {
            return false;
        }
        try {
            int intVal = Integer.parseInt(marketValue);
            return intVal > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks to see if place of exchange is valid (not empty)
     * @param place place of exchange entered by user
     * @return true if valid, false otherwise
     */
    public boolean validPlaceOfExchange(String place) {
        return !place.isEmpty();
    }

}
