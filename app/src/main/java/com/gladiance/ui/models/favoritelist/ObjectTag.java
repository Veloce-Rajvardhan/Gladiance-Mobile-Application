package com.gladiance.ui.models.favoritelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {

    @SerializedName("GAAProjectSpaceRef")
    @Expose
    private Long gAAProjectSpaceRef;
    @SerializedName("UserRef")
    @Expose
    private Long userRef;
    @SerializedName("GAAProjectSpaceTypePlannedDeviceConnectionRef")
    @Expose
    private Long gAAProjectSpaceTypePlannedDeviceConnectionRef;
    @SerializedName("GAAProjectSpaceName")
    @Expose
    private String gAAProjectSpaceName;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Label")
    @Expose
    private String label;
    @SerializedName("NodeId")
    @Expose
    private String nodeId;
    @SerializedName("InternalDeviceName")
    @Expose
    private String internalDeviceName;

    private boolean powerState = false;

    public boolean isPowerState() {
        return powerState;
    }

    public void setPowerState(boolean powerState) {
        this.powerState = powerState;
    }

    public Long getgAAProjectSpaceRef() {
        return gAAProjectSpaceRef;
    }

    public void setgAAProjectSpaceRef(Long gAAProjectSpaceRef) {
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
    }

    public Long getUserRef() {
        return userRef;
    }

    public void setUserRef(Long userRef) {
        this.userRef = userRef;
    }

    public Long getgAAProjectSpaceTypePlannedDeviceConnectionRef() {
        return gAAProjectSpaceTypePlannedDeviceConnectionRef;
    }

    public void setgAAProjectSpaceTypePlannedDeviceConnectionRef(Long gAAProjectSpaceTypePlannedDeviceConnectionRef) {
        this.gAAProjectSpaceTypePlannedDeviceConnectionRef = gAAProjectSpaceTypePlannedDeviceConnectionRef;
    }

    public String getgAAProjectSpaceName() {
        return gAAProjectSpaceName;
    }

    public void setgAAProjectSpaceName(String gAAProjectSpaceName) {
        this.gAAProjectSpaceName = gAAProjectSpaceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getInternalDeviceName() {
        return internalDeviceName;
    }

    public void setInternalDeviceName(String internalDeviceName) {
        this.internalDeviceName = internalDeviceName;
    }

    public ObjectTag(Long gAAProjectSpaceRef, Long userRef, Long gAAProjectSpaceTypePlannedDeviceConnectionRef, String gAAProjectSpaceName, String userName, String label, String nodeId, String internalDeviceName) {
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
        this.userRef = userRef;
        this.gAAProjectSpaceTypePlannedDeviceConnectionRef = gAAProjectSpaceTypePlannedDeviceConnectionRef;
        this.gAAProjectSpaceName = gAAProjectSpaceName;
        this.userName = userName;
        this.label = label;
        this.nodeId = nodeId;
        this.internalDeviceName = internalDeviceName;
    }
}
