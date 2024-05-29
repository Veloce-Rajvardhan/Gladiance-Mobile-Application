package com.gladiance.ui.models.saveSchedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Configuration {

        @SerializedName("GAAProjectScheduleRef")
        @Expose
        private Integer gAAProjectScheduleRef;
        @SerializedName("GAAProjectSpaceTypePlannedDeviceRef")
        @Expose
        private Integer gAAProjectSpaceTypePlannedDeviceRef;
        @SerializedName("NodeConfigDeviceName")
        @Expose
        private String nodeConfigDeviceName;
        @SerializedName("NodeConfigParamName")
        @Expose
        private String nodeConfigParamName;
        @SerializedName("Value")
        @Expose
        private String value;

        public Integer getGAAProjectScheduleRef() {
            return gAAProjectScheduleRef;
        }

        public void setGAAProjectScheduleRef(Integer gAAProjectScheduleRef) {
            this.gAAProjectScheduleRef = gAAProjectScheduleRef;
        }

        public Integer getGAAProjectSpaceTypePlannedDeviceRef() {
            return gAAProjectSpaceTypePlannedDeviceRef;
        }

        public void setGAAProjectSpaceTypePlannedDeviceRef(Integer gAAProjectSpaceTypePlannedDeviceRef) {
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