package com.gladiance.ui.models.saveScene;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.List;

public class SaveSceneRequest {
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
    private List<SceneConfig> configurations;

    public SaveSceneRequest(Long ref, String name, Long gAAProjectSpaceTypeRef, List<SceneConfig> configurations) {
        this.ref = ref;
        this.name = name;
        this.gAAProjectSpaceTypeRef = gAAProjectSpaceTypeRef;
        this.configurations = configurations;
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

    public Long getgAAProjectSpaceTypeRef() {
        return gAAProjectSpaceTypeRef;
    }

    public void setgAAProjectSpaceTypeRef(Long gAAProjectSpaceTypeRef) {
        this.gAAProjectSpaceTypeRef = gAAProjectSpaceTypeRef;
    }

    public List<SceneConfig> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<SceneConfig> configurations) {
        this.configurations = configurations;
    }
}
