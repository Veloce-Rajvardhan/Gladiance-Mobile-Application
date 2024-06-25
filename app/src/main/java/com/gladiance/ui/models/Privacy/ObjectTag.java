package com.gladiance.ui.models.Privacy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {

    @SerializedName("NodeRef")
    @Expose
    private Long nodeRef;
    @SerializedName("GAAProjectSpaceTypePlannedDeviceConnectionRef")
    @Expose
    private Long gAAProjectSpaceTypePlannedDeviceConnectionRef;
    @SerializedName("PrivacyModeAvailable")
    @Expose
    private Boolean privacyModeAvailable;
    @SerializedName("ServiceModeAvailable")
    @Expose
    private Boolean serviceModeAvailable;
    @SerializedName("NodeId")
    @Expose
    private String nodeId;
    @SerializedName("GAAProjectSpaceRef")
    @Expose
    private Long gAAProjectSpaceRef;
    @SerializedName("GAAProjectSpaceTypeAreaRef")
    @Expose
    private Integer gAAProjectSpaceTypeAreaRef;
    @SerializedName("Label")
    @Expose
    private String label;
    @SerializedName("InternalDeviceName")
    @Expose
    private String internalDeviceName;

    public Long getNodeRef() {
        return nodeRef;
    }

    public void setNodeRef(Long nodeRef) {
        this.nodeRef = nodeRef;
    }

    public Long getgAAProjectSpaceTypePlannedDeviceConnectionRef() {
        return gAAProjectSpaceTypePlannedDeviceConnectionRef;
    }

    public void setgAAProjectSpaceTypePlannedDeviceConnectionRef(Long gAAProjectSpaceTypePlannedDeviceConnectionRef) {
        this.gAAProjectSpaceTypePlannedDeviceConnectionRef = gAAProjectSpaceTypePlannedDeviceConnectionRef;
    }

    public Boolean getPrivacyModeAvailable() {
        return privacyModeAvailable;
    }

    public void setPrivacyModeAvailable(Boolean privacyModeAvailable) {
        this.privacyModeAvailable = privacyModeAvailable;
    }

    public Boolean getServiceModeAvailable() {
        return serviceModeAvailable;
    }

    public void setServiceModeAvailable(Boolean serviceModeAvailable) {
        this.serviceModeAvailable = serviceModeAvailable;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Long getgAAProjectSpaceRef() {
        return gAAProjectSpaceRef;
    }

    public void setgAAProjectSpaceRef(Long gAAProjectSpaceRef) {
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
    }

    public Integer getgAAProjectSpaceTypeAreaRef() {
        return gAAProjectSpaceTypeAreaRef;
    }

    public void setgAAProjectSpaceTypeAreaRef(Integer gAAProjectSpaceTypeAreaRef) {
        this.gAAProjectSpaceTypeAreaRef = gAAProjectSpaceTypeAreaRef;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInternalDeviceName() {
        return internalDeviceName;
    }

    public void setInternalDeviceName(String internalDeviceName) {
        this.internalDeviceName = internalDeviceName;
    }

    public ObjectTag(Long nodeRef, Long gAAProjectSpaceTypePlannedDeviceConnectionRef, Boolean privacyModeAvailable, Boolean serviceModeAvailable, String nodeId, Long gAAProjectSpaceRef, Integer gAAProjectSpaceTypeAreaRef, String label, String internalDeviceName) {
        this.nodeRef = nodeRef;
        this.gAAProjectSpaceTypePlannedDeviceConnectionRef = gAAProjectSpaceTypePlannedDeviceConnectionRef;
        this.privacyModeAvailable = privacyModeAvailable;
        this.serviceModeAvailable = serviceModeAvailable;
        this.nodeId = nodeId;
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
        this.gAAProjectSpaceTypeAreaRef = gAAProjectSpaceTypeAreaRef;
        this.label = label;
        this.internalDeviceName = internalDeviceName;
    }
}
