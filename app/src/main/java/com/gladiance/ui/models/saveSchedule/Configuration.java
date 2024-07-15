package com.gladiance.ui.models.saveSchedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Configuration {
    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("GAAProjectNodeScheduleRef")
    @Expose
    private Long gAAProjectNodeScheduleRef;
    @SerializedName("GAAProjectSpaceTypePlannedDeviceRef")
    @Expose
    private Long gAAProjectSpaceTypePlannedDeviceRef;
    @SerializedName("NodeConfigDeviceName")
    @Expose
    private String nodeConfigDeviceName;
    @SerializedName("NodeConfigParamName")
    @Expose
    private String nodeConfigParamName;
    @SerializedName("Value")
    @Expose
    private String value;

    public Configuration(Long ref, Long gAAProjectNodeScheduleRef, Long gAAProjectSpaceTypePlannedDeviceRef, String nodeConfigDeviceName, String nodeConfigParamName, String value) {
        this.ref = ref;
        this.gAAProjectNodeScheduleRef = gAAProjectNodeScheduleRef;
        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
        this.nodeConfigDeviceName = nodeConfigDeviceName;
        this.nodeConfigParamName = nodeConfigParamName;
        this.value = value;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public Long getGAAProjectNodeScheduleRef() {
        return gAAProjectNodeScheduleRef;
    }

    public void setGAAProjectNodeScheduleRef(Long gAAProjectNodeScheduleRef) {
        this.gAAProjectNodeScheduleRef = gAAProjectNodeScheduleRef;
    }

    public Long getGAAProjectSpaceTypePlannedDeviceRef() {
        return gAAProjectSpaceTypePlannedDeviceRef;
    }

    public void setGAAProjectSpaceTypePlannedDeviceRef(Long gAAProjectSpaceTypePlannedDeviceRef) {
        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
    }

    public String getNodeConfigDeviceName() {
        return nodeConfigDeviceName;
    }

    public void setNodeConfigDeviceName(String nodeConfigDeviceName) {
        this.nodeConfigDeviceName = nodeConfigDeviceName;
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

}

//        @SerializedName("GAAProjectScheduleRef")
//        @Expose
//        private Long gAAProjectScheduleRef;
//        @SerializedName("GAAProjectSpaceTypePlannedDeviceRef")
//        @Expose
//        private Long gAAProjectSpaceTypePlannedDeviceRef;
//        @SerializedName("NodeConfigDeviceName")
//        @Expose
//        private String nodeConfigDeviceName;
//        @SerializedName("NodeConfigParamName")
//        @Expose
//        private String nodeConfigParamName;
//        @SerializedName("Value")
//        @Expose
//        private String value;
//
//    public Configuration(Long gAAProjectScheduleRef, Long gAAProjectSpaceTypePlannedDeviceRef, String nodeConfigDeviceName, String nodeConfigParamName, String value) {
//        this.gAAProjectScheduleRef = gAAProjectScheduleRef;
//        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//        this.nodeConfigDeviceName = nodeConfigDeviceName;
//        this.nodeConfigParamName = nodeConfigParamName;
//        this.value = value;
//    }
//
//    public Long getGAAProjectScheduleRef() {
//            return gAAProjectScheduleRef;
//        }
//
//        public void setGAAProjectScheduleRef(Long gAAProjectScheduleRef) {
//            this.gAAProjectScheduleRef = gAAProjectScheduleRef;
//        }
//
//        public Long getGAAProjectSpaceTypePlannedDeviceRef() {
//            return gAAProjectSpaceTypePlannedDeviceRef;
//        }
//
//        public void setGAAProjectSpaceTypePlannedDeviceRef(Long gAAProjectSpaceTypePlannedDeviceRef) {
//            this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//        }
//
//        public String getNodeConfigDeviceName() {
//            return nodeConfigDeviceName;
//        }
//
//        public void setNodeConfigDeviceName(String nodeConfigDeviceName) {
//            this.nodeConfigDeviceName = nodeConfigDeviceName;
//        }
//
//        public String getNodeConfigParamName() {
//            return nodeConfigParamName;
//        }
//
//        public void setNodeConfigParamName(String nodeConfigParamName) {
//            this.nodeConfigParamName = nodeConfigParamName;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//
//    }