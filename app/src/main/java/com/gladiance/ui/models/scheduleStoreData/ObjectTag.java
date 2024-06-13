package com.gladiance.ui.models.scheduleStoreData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjectTag {
    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GAAProjectSpaceTypeRef")
    @Expose
    private Long gAAProjectSpaceTypeRef;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("IsSystemDefinedSchedule")
    @Expose
    private Integer isSystemDefinedSchedule;
    @SerializedName("GAAProjectSpaceTypeName")
    @Expose
    private String gAAProjectSpaceTypeName;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;
    @SerializedName("Configurations")
    @Expose
    private List<Configuration> configurations;
    @SerializedName("Triggers")
    @Expose
    private List<Trigger> triggers;

    public ObjectTag(Long ref, String name, Long gAAProjectSpaceTypeRef, String code, Integer isSystemDefinedSchedule, String gAAProjectSpaceTypeName, Long gAAProjectRef, String gAAProjectName, List<Configuration> configurations, List<Trigger> triggers) {
        this.ref = ref;
        this.name = name;
        this.gAAProjectSpaceTypeRef = gAAProjectSpaceTypeRef;
        this.code = code;
        this.isSystemDefinedSchedule = isSystemDefinedSchedule;
        this.gAAProjectSpaceTypeName = gAAProjectSpaceTypeName;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsSystemDefinedSchedule() {
        return isSystemDefinedSchedule;
    }

    public void setIsSystemDefinedSchedule(Integer isSystemDefinedSchedule) {
        this.isSystemDefinedSchedule = isSystemDefinedSchedule;
    }

    public String getGAAProjectSpaceTypeName() {
        return gAAProjectSpaceTypeName;
    }

    public void setGAAProjectSpaceTypeName(String gAAProjectSpaceTypeName) {
        this.gAAProjectSpaceTypeName = gAAProjectSpaceTypeName;
    }

    public Long getGAAProjectRef() {
        return gAAProjectRef;
    }

    public void setGAAProjectRef(Long gAAProjectRef) {
        this.gAAProjectRef = gAAProjectRef;
    }

    public String getGAAProjectName() {
        return gAAProjectName;
    }

    public void setGAAProjectName(String gAAProjectName) {
        this.gAAProjectName = gAAProjectName;
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