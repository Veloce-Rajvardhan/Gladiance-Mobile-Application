package com.gladiance.ui.models.roomservicesingleitemlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineItem {

    @SerializedName("Ref")
    @Expose
    private Integer ref;
    @SerializedName("GAAProjectSpaceInRoomDiningRequestRef")
    @Expose
    private Integer gAAProjectSpaceInRoomDiningRequestRef;
    @SerializedName("RBItemRef")
    @Expose
    private Long rBItemRef;
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
    @SerializedName("RBItemName")
    @Expose
    private String rBItemName;
    @SerializedName("Rate")
    @Expose
    private Double rate;

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getgAAProjectSpaceInRoomDiningRequestRef() {
        return gAAProjectSpaceInRoomDiningRequestRef;
    }

    public void setgAAProjectSpaceInRoomDiningRequestRef(Integer gAAProjectSpaceInRoomDiningRequestRef) {
        this.gAAProjectSpaceInRoomDiningRequestRef = gAAProjectSpaceInRoomDiningRequestRef;
    }

    public Long getrBItemRef() {
        return rBItemRef;
    }

    public void setrBItemRef(Long rBItemRef) {
        this.rBItemRef = rBItemRef;
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

    public String getrBItemName() {
        return rBItemName;
    }

    public void setrBItemName(String rBItemName) {
        this.rBItemName = rBItemName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LineItem(Integer ref, Integer gAAProjectSpaceInRoomDiningRequestRef, Long rBItemRef, Double quantity, Long gAAProjectRef, String gAAProjectName, Long gAAProjectSpaceRef, String gAAProjectSpaceName, String rBItemName, Double rate) {
        this.ref = ref;
        this.gAAProjectSpaceInRoomDiningRequestRef = gAAProjectSpaceInRoomDiningRequestRef;
        this.rBItemRef = rBItemRef;
        this.quantity = quantity;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
        this.gAAProjectSpaceName = gAAProjectSpaceName;
        this.rBItemName = rBItemName;
        this.rate = rate;
    }
}
