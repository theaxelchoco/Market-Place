package com.example.group17project.ProviderFunctionality.AddUtils;

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


    /**
     * This method is used to verify if the product is valid THEN pass it along to the realAddProduct implementation to interact with the database
     * @param name of product
     * @param ownerId of product
     * @param desc of product
     * @param date of product
     * @param productType of product
     * @param exchangePlace of product
     * @param prefExchange of product
     * @param marketVal of product
     * @return true if the product has been successfully added, false otherwise
     */
    @Override
    public boolean add(String name, String ownerId, String desc, Calendar date, String productType, String exchangePlace, String prefExchange, String marketVal) {
        if(validProductName(name) && validMarketValue(marketVal) && validPlaceOfExchange(exchangePlace)){
            return addProductInstance.add(name, ownerId, desc, date, productType, exchangePlace, prefExchange, marketVal);

        }
        else{
            return false;
        }
    }

    /**
     * This method is used to verify if the productis valid THEN pass it along to the realAddProduct implementation of edit to interact w/ database
     * @param name of product
     * @param ownerId of product
     * @param desc of product
     * @param date of product
     * @param productType of product
     * @param exchangePlace of product
     * @param prefExchange of product
     * @param marketVal of product
     * @param productId of product
     * @return true if succesfully editted, false otherwise
     */
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
