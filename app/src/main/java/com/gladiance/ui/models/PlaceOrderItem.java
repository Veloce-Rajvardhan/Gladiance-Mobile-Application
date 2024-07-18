package com.gladiance.ui.models;

public class PlaceOrderItem {
    private String name;
    private String rate;
    private String quantity;
    private boolean isVeg;
    private Long ref;

    public PlaceOrderItem(String name, String rate, String quantity,boolean isVeg,Long ref) {
        this.name = name;
        this.rate = rate;
        this.quantity = quantity;
        this.isVeg = isVeg;
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean veg) {
        isVeg = veg;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }
}