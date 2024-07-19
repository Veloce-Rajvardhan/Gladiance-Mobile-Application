package com.gladiance.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaceOrderItem implements Parcelable {
    private String name;
    private String rate;
    private String quantity;
    private boolean isVeg;
    private Long ref;

    // Constructor
    public PlaceOrderItem(String name, String rate, String quantity, boolean isVeg, Long ref) {
        this.name = name;
        this.rate = rate;
        this.quantity = quantity;
        this.isVeg = isVeg;
        this.ref = ref;
    }

    // Getter and Setter methods
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

    // Parcelable implementation
    protected PlaceOrderItem(Parcel in) {
        name = in.readString();
        rate = in.readString();
        quantity = in.readString();
        isVeg = in.readByte() != 0;
        if (in.readByte() == 0) {
            ref = null;
        } else {
            ref = in.readLong();
        }
    }

    public static final Creator<PlaceOrderItem> CREATOR = new Creator<PlaceOrderItem>() {
        @Override
        public PlaceOrderItem createFromParcel(Parcel in) {
            return new PlaceOrderItem(in);
        }

        @Override
        public PlaceOrderItem[] newArray(int size) {
            return new PlaceOrderItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(rate);
        dest.writeString(quantity);
        dest.writeByte((byte) (isVeg ? 1 : 0));
        if (ref == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(ref);
        }
    }
}
