package com.gladiance.ui.models.laundrysinglelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineItem {

    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("GAAProjectSpaceLaundryRequestRef")
    @Expose
    private Long gAAProjectSpaceLaundryRequestRef;
    @SerializedName("CustomerLaundryItemRef")
    @Expose
    private Long customerLaundryItemRef;
    @SerializedName("LaundryServiceType")
    @Expose
    private Integer laundryServiceType;
    @SerializedName("Quantity")
    @Expose
    private Double quantity;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;
    @SerializedName("GAAProjectSpaceRef")
    @Expose
    private Long gAAProjectSpaceRef;
    @SerializedName("GAAProjectSpaceName")
    @Expose
    private String gAAProjectSpaceName;
    @SerializedName("CustomerLaundryItemName")
    @Expose
    private String customerLaundryItemName;
    @SerializedName("LaundryServiceTypeName")
    @Expose
    private String laundryServiceTypeName;
    @SerializedName("Rate")
    @Expose
    private Double rate;

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public Long getgAAProjectSpaceLaundryRequestRef() {
        return gAAProjectSpaceLaundryRequestRef;
    }

    public void setgAAProjectSpaceLaundryRequestRef(Long gAAProjectSpaceLaundryRequestRef) {
        this.gAAProjectSpaceLaundryRequestRef = gAAProjectSpaceLaundryRequestRef;
    }

    public Long getCustomerLaundryItemRef() {
        return customerLaundryItemRef;
    }

    public void setCustomerLaundryItemRef(Long customerLaundryItemRef) {
        this.customerLaundryItemRef = customerLaundryItemRef;
    }

    public Integer getLaundryServiceType() {
        return laundryServiceType;
    }

    public void setLaundryServiceType(Integer laundryServiceType) {
        this.laundryServiceType = laundryServiceType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getgAAProjectRef() {
        return gAAProjectRef;
    }

    public void setgAAProjectRef(Long gAAProjectRef) {
        this.gAAProjectRef = gAAProjectRef;
    }

    public String getgAAProjectName() {
        return gAAProjectName;
    }

    public void setgAAProjectName(String gAAProjectName) {
        this.gAAProjectName = gAAProjectName;
    }

    public Long getgAAProjectSpaceRef() {
        return gAAProjectSpaceRef;
    }

    public void setgAAProjectSpaceRef(Long gAAProjectSpaceRef) {
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
    }

    public String getgAAProjectSpaceName() {
        return gAAProjectSpaceName;
    }

    public void setgAAProjectSpaceName(String gAAProjectSpaceName) {
        this.gAAProjectSpaceName = gAAProjectSpaceName;
    }

    public String getCustomerLaundryItemName() {
        return customerLaundryItemName;
    }

    public void setCustomerLaundryItemName(String customerLaundryItemName) {
        this.customerLaundryItemName = customerLaundryItemName;
    }

    public String getLaundryServiceTypeName() {
        return laundryServiceTypeName;
    }

    public void setLaundryServiceTypeName(String laundryServiceTypeName) {
        this.laundryServiceTypeName = laundryServiceTypeName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LineItem(Long ref, Long gAAProjectSpaceLaundryRequestRef, Long customerLaundryItemRef, Integer laundryServiceType, Double quantity, Long gAAProjectRef, String gAAProjectName, Long gAAProjectSpaceRef, String gAAProjectSpaceName, String customerLaundryItemName, String laundryServiceTypeName, Double rate) {
        this.ref = ref;
        this.gAAProjectSpaceLaundryRequestRef = gAAProjectSpaceLaundryRequestRef;
        this.customerLaundryItemRef = customerLaundryItemRef;
        this.laundryServiceType = laundryServiceType;
        this.quantity = quantity;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
        this.gAAProjectSpaceName = gAAProjectSpaceName;
        this.customerLaundryItemName = customerLaundryItemName;
        this.laundryServiceTypeName = laundryServiceTypeName;
        this.rate = rate;
    }
}
