package com.gladiance.ui.models.saveSchedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveScheduleRequest {

    @SerializedName("Ref")
    @Expose
    private Integer ref;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GAAProjectSpaceTypeRef")
    @Expose
    private Integer gAAProjectSpaceTypeRef;
    @SerializedName("Configurations")
    @Expose
    private List<Configuration> configurations;
    @SerializedName("Triggers")
    @Expose
    private List<Trigger> triggers;

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGAAProjectSpaceTypeRef() {
        return gAAProjectSpaceTypeRef;
    }

    public void setGAAProjectSpaceTypeRef(Integer gAAProjectSpaceTypeRef) {
        this.gAAProjectSpaceTypeRef = gAAProjectSpaceTypeRef;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }


}
