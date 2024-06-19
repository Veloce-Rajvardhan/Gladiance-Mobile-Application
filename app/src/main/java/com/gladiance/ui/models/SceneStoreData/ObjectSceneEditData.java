package com.gladiance.ui.models.SceneStoreData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectSceneEditData implements Serializable {
    @SerializedName("Successful")
    @Expose
    private Boolean successful;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Tag")
    @Expose
    private String tag;
    @SerializedName("TagType")
    @Expose
    private String tagType;
    @SerializedName("ProcessToken")
    @Expose
    private String processToken;
    @SerializedName("ObjectTag")
    @Expose
    private ObjectTagSceneEditData objectTag;

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getProcessToken() {
        return processToken;
    }

    public void setProcessToken(String processToken) {
        this.processToken = processToken;
    }

    public ObjectTagSceneEditData getObjectTag() {
        return objectTag;
    }

    public void setObjectTag(ObjectTagSceneEditData objectTag) {
        this.objectTag = objectTag;
    }

}