package com.gladiance.ui.models.guestlandingpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Controls {
    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("Label")
    @Expose
    private String label;
    @SerializedName("NodeId")
    @Expose
    private String nodeId;
    @SerializedName("InternalDeviceName")
    @Expose
    private String internalDeviceName;
    @SerializedName("DisplayOrder")
    @Expose
    private Integer displayOrder;
    @SerializedName("GAAProjectSpaceTypePlannedDeviceRef")
    @Expose
    private Long gAAProjectSpaceTypePlannedDeviceRef;

    @SerializedName("GAAProjectSpaceTypePlannedDeviceName")
    @Expose
    private String gAAProjectSpaceTypePlannedDeviceName;

    @SerializedName("GAAProjectSpaceTypePlannedDeviceConnectionRef")
    @Expose
    private String gAAProjectSpaceTypePlannedDeviceConnectionRef;

    @SerializedName("ControlTypeName")
    @Expose
    private String controlTypeName;

    @SerializedName("isChecked")
    @Expose
    private boolean isChecked;

    @SerializedName("isCheck")
    @Expose

    private boolean isCheckBox = false;

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        isCheckBox = checkBox;
    }

    private boolean powerState = false;

    public boolean isPowerState() {
        return powerState;
    }

    public void setPowerState(boolean powerState) {
        this.powerState = powerState;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Long getgAAProjectSpaceTypePlannedDeviceRef() {
        return gAAProjectSpaceTypePlannedDeviceRef;
    }

    public void setgAAProjectSpaceTypePlannedDeviceRef(Long gAAProjectSpaceTypePlannedDeviceRef) {
        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
    }

    public String getgAAProjectSpaceTypePlannedDeviceName() {
        return gAAProjectSpaceTypePlannedDeviceName;
    }

    public void setgAAProjectSpaceTypePlannedDeviceName(String gAAProjectSpaceTypePlannedDeviceName) {
        this.gAAProjectSpaceTypePlannedDeviceName = gAAProjectSpaceTypePlannedDeviceName;
    }

    public String getgAAProjectSpaceTypePlannedDeviceConnectionRef() {
        return gAAProjectSpaceTypePlannedDeviceConnectionRef;
    }

    public void setgAAProjectSpaceTypePlannedDeviceConnectionRef(String gAAProjectSpaceTypePlannedDeviceConnectionRef) {
        this.gAAProjectSpaceTypePlannedDeviceConnectionRef = gAAProjectSpaceTypePlannedDeviceConnectionRef;
    }

    public String getControlTypeName() {
        return controlTypeName;
    }

    public void setControlTypeName(String controlTypeName) {
        this.controlTypeName = controlTypeName;
    }
}
