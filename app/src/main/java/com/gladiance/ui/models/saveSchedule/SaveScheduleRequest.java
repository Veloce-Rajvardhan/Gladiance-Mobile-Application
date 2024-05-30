package com.gladiance.ui.models.saveSchedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveScheduleRequest {

    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GAAProjectSpaceTypeRef")
    @Expose
    private Long gAAProjectSpaceTypeRef;
    @SerializedName("Configurations")
    @Expose
    private List<Configuration> configurations;
    @SerializedName("Triggers")
    @Expose
    private List<Trigger> triggers;

    public SaveScheduleRequest(Long ref, String name, Long gAAProjectSpaceTypeRef, List<Configuration> configurations, List<Trigger> triggers) {
        this.ref = ref;
        this.name = name;
        this.gAAProjectSpaceTypeRef = gAAProjectSpaceTypeRef;
        this.configurations = configurations;
        this.triggers = triggers;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGAAProjectSpaceTypeRef() {
        return gAAProjectSpaceTypeRef;
    }

    public void setGAAProjectSpaceTypeRef(Long gAAProjectSpaceTypeRef) {
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
