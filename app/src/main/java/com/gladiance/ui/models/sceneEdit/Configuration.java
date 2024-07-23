package com.gladiance.ui.models.sceneEdit;

import android.util.Log;

import com.gladiance.AppConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Configuration implements Serializable{
    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("GAAProjectSceneRef")
    @Expose
    private Long gAAProjectSceneRef;
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

    public Configuration(Long ref, Long gAAProjectSceneRef, Long gAAProjectSpaceTypePlannedDeviceRef, String nodeConfigDeviceName, String nodeConfigParamName, String value) {
        this.ref = ref;
        this.gAAProjectSceneRef = gAAProjectSceneRef;
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

    public Long getgAAProjectSceneRef() {
        return gAAProjectSceneRef;
    }

    public void setgAAProjectSceneRef(Long gAAProjectSceneRef) {
        this.gAAProjectSceneRef = gAAProjectSceneRef;
    }

    public Long getgAAProjectSpaceTypePlannedDeviceRef() {
        return gAAProjectSpaceTypePlannedDeviceRef;
    }

    public void setgAAProjectSpaceTypePlannedDeviceRef(Long gAAProjectSpaceTypePlannedDeviceRef) {
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

//    public void modify() {
//        Log.e("APPCONSTS1",""+ AppConstants.Ref_dyn);
//        Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
//        Log.e("APPCONSTS3",""+AppConstants.SceneRef);
//        Log.e("APPCONSTS44",""+AppConstants.Space_dyn);
//        Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
//        Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
//        Log.e("APPCONSTS",""+AppConstants.powerState);
//        Log.e("APPCONSTS",""+AppConstants.power);
//        this.setRef();
//        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
//    }
}
