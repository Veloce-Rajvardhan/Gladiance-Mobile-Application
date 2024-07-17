package com.gladiance.ui.models.laundryItemHotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {

    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("PressCharges")
    @Expose
    private Double pressCharges;
    @SerializedName("DryWashCharges")
    @Expose
    private Double dryWashCharges;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;

    private boolean selected;
    private boolean selectedPress;

    private Integer dryWashQuantity;
    private Integer pressQuantity;

    private boolean isDrywashSelected;
    private boolean isPressSelected;

    public Integer getDryWashQuantity() {
        return dryWashQuantity != null ? dryWashQuantity : 0;
    }

    public void setDryWashQuantity(Integer dryWashQuantity) {
        this.dryWashQuantity = dryWashQuantity;
    }

    public boolean isDrywashSelected() {
        return isDrywashSelected;
    }

    public void setDrywashSelected(boolean drywashSelected) {
        isDrywashSelected = drywashSelected;
    }

    public boolean isPressSelected() {
        return isPressSelected;
    }

    public void setPressSelected(boolean pressSelected) {
        isPressSelected = pressSelected;
    }


    public Integer getPressQuantity() {
        return pressQuantity != null ? pressQuantity : 0;
    }

    public void setPressQuantity(Integer pressQuantity) {
        this.pressQuantity = pressQuantity;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelectedPress() {
        return selectedPress;
    }

    public void setSelectedPress(boolean selectedPress) {
        this.selectedPress = selectedPress;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPressCharges() {
        return pressCharges;
    }

    public void setPressCharges(Double pressCharges) {
        this.pressCharges = pressCharges;
    }

    public Double getDryWashCharges() {
        return dryWashCharges;
    }

    public void setDryWashCharges(Double dryWashCharges) {
        this.dryWashCharges = dryWashCharges;
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

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public ObjectTag(Long ref ,String name, Double pressCharges, Double dryWashCharges, Long gAAProjectRef, String gAAProjectName) {
        this.ref = ref;
        this.name = name;
        this.pressCharges = pressCharges;
        this.dryWashCharges = dryWashCharges;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
    }
}
