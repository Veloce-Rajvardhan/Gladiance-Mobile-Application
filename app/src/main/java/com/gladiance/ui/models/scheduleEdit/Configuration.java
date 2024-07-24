package com.gladiance.ui.models.scheduleEdit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Configuration implements Serializable {
    @SerializedName("GAAProjectNodeScheduleRef")
    @Expose
    private Long gAAProjectNodeScheduleRef;
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

    public Configuration(Long ref, Long gAAProjectNodeScheduleRef,Long gAAProjectSpaceTypePlannedDeviceRef, String nodeConfigDeviceName, String nodeConfigParamName, String value) {
        this.ref = ref;
        this.gAAProjectNodeScheduleRef = gAAProjectNodeScheduleRef;
        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
        this.nodeConfigDeviceName = nodeConfigDeviceName;
        this.nodeConfigParamName = nodeConfigParamName;
        this.value = value;
    }

    public Long getGAAProjectNodeScheduleRef() {
        return gAAProjectNodeScheduleRef;
    }

    public void setGAAProjectNodeScheduleRef(Long gAAProjectNodeScheduleRef) {
        this.gAAProjectNodeScheduleRef = gAAProjectNodeScheduleRef;
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
