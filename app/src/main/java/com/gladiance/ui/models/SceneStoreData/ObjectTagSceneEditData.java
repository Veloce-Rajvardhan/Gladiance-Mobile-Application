package com.gladiance.ui.models.SceneStoreData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ObjectTagSceneEditData implements Serializable {
    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("IsSystemDefinedScene")
    @Expose
    private Integer isSystemDefinedScene;
    @SerializedName("GAAProjectSpaceTypeRef")
    @Expose
    private Long gAAProjectSpaceTypeRef;
    @SerializedName("GAAProjectSpaceTypeName")
    @Expose
    private String gAAProjectSpaceTypeName;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;
    @SerializedName("Configurations")
    @Expose
    private List<ConfigurationSceneEditData> configurations;

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

    public Long getGAAProjectRef() {
        return gAAProjectRef;
    }

    public void setGAAProjectRef(Long gAAProjectRef) {
        this.gAAProjectRef = gAAProjectRef;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsSystemDefinedScene() {
        return isSystemDefinedScene;
    }

    public void setIsSystemDefinedScene(Integer isSystemDefinedScene) {
        this.isSystemDefinedScene = isSystemDefinedScene;
    }

    public Long getGAAProjectSpaceTypeRef() {
        return gAAProjectSpaceTypeRef;
    }

    public void setGAAProjectSpaceTypeRef(Long gAAProjectSpaceTypeRef) {
        this.gAAProjectSpaceTypeRef = gAAProjectSpaceTypeRef;
    }

    public String getGAAProjectSpaceTypeName() {
        return gAAProjectSpaceTypeName;
    }

    public void setGAAProjectSpaceTypeName(String gAAProjectSpaceTypeName) {
        this.gAAProjectSpaceTypeName = gAAProjectSpaceTypeName;
    }

    public String getGAAProjectName() {
        return gAAProjectName;
    }

    public void setGAAProjectName(String gAAProjectName) {
        this.gAAProjectName = gAAProjectName;
    }

    public List<ConfigurationSceneEditData> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ConfigurationSceneEditData> configurations) {
        this.configurations = configurations;
    }

}