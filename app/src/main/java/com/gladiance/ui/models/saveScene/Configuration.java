package com.gladiance.ui.models.saveScene;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Configuration implements Serializable {
    @SerializedName("GAAProjectSceneRef")
    @Expose
    private Long gAAProjectSceneRef;
    @SerializedName("NodeConfigParamName")
    @Expose
    private String nodeConfigParamName;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("NodeConfigDeviceName")
    @Expose
    private String nodeConfigDeviceName;
    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("GAAProjectSpaceTypePlannedDeviceRef")
    @Expose
    private Long gAAProjectSpaceTypePlannedDeviceRef;

    public Configuration(Long gAAProjectSceneRef, String nodeConfigParamName, String value, String nodeConfigDeviceName, Long ref, Long gAAProjectSpaceTypePlannedDeviceRef) {
        this.gAAProjectSceneRef = gAAProjectSceneRef;
        this.nodeConfigParamName = nodeConfigParamName;
        this.value = value;
        this.nodeConfigDeviceName = nodeConfigDeviceName;
        this.ref = ref;
        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
    }

    public Long getGAAProjectSceneRef() {
        return gAAProjectSceneRef;
    }

    public void setGAAProjectSceneRef(Long gAAProjectSceneRef) {
        this.gAAProjectSceneRef = gAAProjectSceneRef;
    }

    public String getNodeConfigParamName() {
        return nodeConfigParamName;
    }

    public void setNodeConfigParamName(String nodeConfigParamName) {
        this.nodeConfigParamName = nodeConfigParamName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNodeConfigDeviceName() {
        return nodeConfigDeviceName;
    }

    public void setNodeConfigDeviceName(String nodeConfigDeviceName) {
        this.nodeConfigDeviceName = nodeConfigDeviceName;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public Long getGAAProjectSpaceTypePlannedDeviceRef() {
        return gAAProjectSpaceTypePlannedDeviceRef;
    }

    public void setGAAProjectSpaceTypePlannedDeviceRef(Long gAAProjectSpaceTypePlannedDeviceRef) {
        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
    }

}