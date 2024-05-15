package com.gladiance.ui.models.scene;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectScenes implements Serializable {

    public ObjectScenes(String Ref_dyn, String Name_dyn, String SceneRef, String Space_dyn, String gaaProjectSpaceTypePlannedDeviceRef, String projectSpaceTypePlannedDeviceName, String NodeConfigParamName, String value) {
        this.gaaProjectSpaceTypePlannedDeviceRef = gaaProjectSpaceTypePlannedDeviceRef;
        this.projectSpaceTypePlannedDeviceName = projectSpaceTypePlannedDeviceName;
        this.Ref_dyn = Ref_dyn;
        this.Name_dyn = Name_dyn;
        this.SceneRef = SceneRef;
        this.Space_dyn = Space_dyn;
        this.NodeConfigParamName = NodeConfigParamName;
        this.Value = value;
    }

    @SerializedName("Ref_dyn")
    @Expose
    private String Ref_dyn;

    @SerializedName("Name_dyn")
    @Expose
    private String Name_dyn;

    @SerializedName("SceneRef")
    @Expose
    private String SceneRef;

    @SerializedName("Space_dyn")
    @Expose
    private String Space_dyn;


    @SerializedName("GaaProjectSpaceTypePlannedDeviceRef")
    @Expose
    private String gaaProjectSpaceTypePlannedDeviceRef;


    @SerializedName("projectSpaceTypePlannedDeviceName")
    @Expose
    private String projectSpaceTypePlannedDeviceName;

    @SerializedName("NodeConfigParamName")
    @Expose
    private String NodeConfigParamName;

    @SerializedName("Value")
    @Expose
    private String Value;


    public String getSpace_dyn() {
        return Space_dyn;
    }

    public void setSpace_dyn(String space_dyn) {
        Space_dyn = space_dyn;
    }


    public String getRef_dyn() {
        return Ref_dyn;
    }

    public void setRef_dyn(String ref_dyn) {
        Ref_dyn = ref_dyn;
    }

    public String getName_dyn() {
        return Name_dyn;
    }

    public void setName_dyn(String name_dyn) {
        Name_dyn = name_dyn;
    }

    public String getSceneRef() {
        return SceneRef;
    }

    public void setSceneRef(String sceneRef) {
        SceneRef = sceneRef;
    }


    public String getGaaProjectSpaceTypePlannedDeviceRef() {
        return gaaProjectSpaceTypePlannedDeviceRef;
    }

    public void setGaaProjectSpaceTypePlannedDeviceRef(String gaaProjectSpaceTypePlannedDeviceRef) {
        this.gaaProjectSpaceTypePlannedDeviceRef = gaaProjectSpaceTypePlannedDeviceRef;
    }

    public String getProjectSpaceTypePlannedDeviceName() {
        return projectSpaceTypePlannedDeviceName;
    }

    public void setProjectSpaceTypePlannedDeviceName(String projectSpaceTypePlannedDeviceName) {
        this.projectSpaceTypePlannedDeviceName = projectSpaceTypePlannedDeviceName;
    }

    public String getNodeConfigParamName() {
        return NodeConfigParamName;
    }

    public void setNodeConfigParamName(String nodeConfigParamName) {
        NodeConfigParamName = nodeConfigParamName;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

}