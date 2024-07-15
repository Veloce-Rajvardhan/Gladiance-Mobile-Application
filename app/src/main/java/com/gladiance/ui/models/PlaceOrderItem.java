package com.gladiance.ui.models;

public class PlaceOrderItem {
    private String name;
    private String rate;
    private boolean isVeg;

    public PlaceOrderItem(String name, String rate, boolean isVeg) {
        this.name = name;
        this.rate = rate;
        this.isVeg = isVeg;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }

    public boolean isVeg() {
        return isVeg;
    }
}