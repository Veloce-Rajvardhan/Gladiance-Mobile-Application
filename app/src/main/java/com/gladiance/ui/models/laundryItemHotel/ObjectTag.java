package com.gladiance.ui.models.laundryItemHotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {



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

    public ObjectTag(String name, Double pressCharges, Double dryWashCharges, Long gAAProjectRef, String gAAProjectName) {
        this.name = name;
        this.pressCharges = pressCharges;
        this.dryWashCharges = dryWashCharges;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
    }
}
