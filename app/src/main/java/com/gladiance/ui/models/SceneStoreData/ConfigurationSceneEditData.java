package com.gladiance.ui.models.SceneStoreData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigurationSceneEditData {
    @SerializedName("GAAProjectSceneRef")
    @Expose
    private Long gAAProjectSceneRef;
    @SerializedName("GAAProjectSpaceTypePlannedDeviceConnectionRef")
    @Expose
    private Long gAAProjectSpaceTypePlannedDeviceConnectionRef;
    @SerializedName("NodeConfigParamName")
    @Expose
    private String nodeConfigParamName;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("NodeConfigDeviceName")
    @Expose
    private String nodeConfigDeviceName;
    @SerializedName("GAAProjectSceneName")
    @Expose
    private String gAAProjectSceneName;
    @SerializedName("GAAProjectSceneCode")
    @Expose
    private String gAAProjectSceneCode;
    @SerializedName("GAAProjectSpaceTypeRef")
    @Expose
    private Long gAAProjectSpaceTypeRef;
    @SerializedName("GAAProjectSpaceTypeName")
    @Expose
    private String gAAProjectSpaceTypeName;
    @SerializedName("GAAProjectSpaceTypeAreaRef")
    @Expose
    private Long gAAProjectSpaceTypeAreaRef;
    @SerializedName("GAAProjectSpaceTypeAreaName")
    @Expose
    private String gAAProjectSpaceTypeAreaName;
    @SerializedName("GAAProjectSpaceTypePlannedDeviceRef")
    @Expose
    private Long gAAProjectSpaceTypePlannedDeviceRef;
    @SerializedName("GAAProjectSpaceTypePlannedDeviceName")
    @Expose
    private String gAAProjectSpaceTypePlannedDeviceName;
    @SerializedName("Label")
    @Expose
    private String label;
    @SerializedName("OutputDriverChannelRef")
    @Expose
    private Long outputDriverChannelRef;
    @SerializedName("OutputDriverChannelName")
    @Expose
    private String outputDriverChannelName;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;

    public ConfigurationSceneEditData(Long gAAProjectSceneRef, Long gAAProjectSpaceTypePlannedDeviceConnectionRef, String nodeConfigParamName, String value, String nodeConfigDeviceName, String gAAProjectSceneName, String gAAProjectSceneCode, Long gAAProjectSpaceTypeRef, String gAAProjectSpaceTypeName, Long gAAProjectSpaceTypeAreaRef, String gAAProjectSpaceTypeAreaName, Long gAAProjectSpaceTypePlannedDeviceRef, String gAAProjectSpaceTypePlannedDeviceName, String label, Long outputDriverChannelRef, String outputDriverChannelName, Long gAAProjectRef, String gAAProjectName) {
        this.gAAProjectSceneRef = gAAProjectSceneRef;
        this.gAAProjectSpaceTypePlannedDeviceConnectionRef = gAAProjectSpaceTypePlannedDeviceConnectionRef;
        this.nodeConfigParamName = nodeConfigParamName;
        this.value = value;
        this.nodeConfigDeviceName = nodeConfigDeviceName;
        this.gAAProjectSceneName = gAAProjectSceneName;
        this.gAAProjectSceneCode = gAAProjectSceneCode;
        this.gAAProjectSpaceTypeRef = gAAProjectSpaceTypeRef;
        this.gAAProjectSpaceTypeName = gAAProjectSpaceTypeName;
        this.gAAProjectSpaceTypeAreaRef = gAAProjectSpaceTypeAreaRef;
        this.gAAProjectSpaceTypeAreaName = gAAProjectSpaceTypeAreaName;
        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
        this.gAAProjectSpaceTypePlannedDeviceName = gAAProjectSpaceTypePlannedDeviceName;
        this.label = label;
        this.outputDriverChannelRef = outputDriverChannelRef;
        this.outputDriverChannelName = outputDriverChannelName;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
    }

    public Long getGAAProjectSceneRef() {
        return gAAProjectSceneRef;
    }

    public void setGAAProjectSceneRef(Long gAAProjectSceneRef) {
        this.gAAProjectSceneRef = gAAProjectSceneRef;
    }

    public Long getGAAProjectSpaceTypePlannedDeviceConnectionRef() {
        return gAAProjectSpaceTypePlannedDeviceConnectionRef;
    }

    public void setGAAProjectSpaceTypePlannedDeviceConnectionRef(Long gAAProjectSpaceTypePlannedDeviceConnectionRef) {
        this.gAAProjectSpaceTypePlannedDeviceConnectionRef = gAAProjectSpaceTypePlannedDeviceConnectionRef;
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

    public String getGAAProjectSceneName() {
        return gAAProjectSceneName;
    }

    public void setGAAProjectSceneName(String gAAProjectSceneName) {
        this.gAAProjectSceneName = gAAProjectSceneName;
    }

    public String getGAAProjectSceneCode() {
        return gAAProjectSceneCode;
    }

    public void setGAAProjectSceneCode(String gAAProjectSceneCode) {
        this.gAAProjectSceneCode = gAAProjectSceneCode;
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

    public Long getGAAProjectSpaceTypeAreaRef() {
        return gAAProjectSpaceTypeAreaRef;
    }

    public void setGAAProjectSpaceTypeAreaRef(Long gAAProjectSpaceTypeAreaRef) {
        this.gAAProjectSpaceTypeAreaRef = gAAProjectSpaceTypeAreaRef;
    }

    public String getGAAProjectSpaceTypeAreaName() {
        return gAAProjectSpaceTypeAreaName;
    }

    public void setGAAProjectSpaceTypeAreaName(String gAAProjectSpaceTypeAreaName) {
        this.gAAProjectSpaceTypeAreaName = gAAProjectSpaceTypeAreaName;
    }

    public Long getGAAProjectSpaceTypePlannedDeviceRef() {
        return gAAProjectSpaceTypePlannedDeviceRef;
    }

    public void setGAAProjectSpaceTypePlannedDeviceRef(Long gAAProjectSpaceTypePlannedDeviceRef) {
        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
    }

    public String getGAAProjectSpaceTypePlannedDeviceName() {
        return gAAProjectSpaceTypePlannedDeviceName;
    }

    public void setGAAProjectSpaceTypePlannedDeviceName(String gAAProjectSpaceTypePlannedDeviceName) {
        this.gAAProjectSpaceTypePlannedDeviceName = gAAProjectSpaceTypePlannedDeviceName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getOutputDriverChannelRef() {
        return outputDriverChannelRef;
    }

    public void setOutputDriverChannelRef(Long outputDriverChannelRef) {
        this.outputDriverChannelRef = outputDriverChannelRef;
    }

    public String getOutputDriverChannelName() {
        return outputDriverChannelName;
    }

    public void setOutputDriverChannelName(String outputDriverChannelName) {
        this.outputDriverChannelName = outputDriverChannelName;
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

}
