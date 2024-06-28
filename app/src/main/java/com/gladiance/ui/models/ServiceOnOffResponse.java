package com.gladiance.ui.models;

import com.google.gson.annotations.SerializedName;

public class ServiceOnOffResponse {

    @SerializedName("Successful")
    public boolean successful;

    @SerializedName("Message")
    public String message;

    @SerializedName("Tag")
    public String tag;

    @SerializedName("TagType")
    public String tagType;

    @SerializedName("ProcessToken")
    public String processToken;

    @SerializedName("ObjectTag")
    public Object objectTag;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
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

    public Object getObjectTag() {
        return objectTag;
    }

    public void setObjectTag(Object objectTag) {
        this.objectTag = objectTag;
    }
}
