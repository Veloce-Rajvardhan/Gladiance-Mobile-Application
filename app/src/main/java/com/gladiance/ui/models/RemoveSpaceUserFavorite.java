package com.gladiance.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveSpaceUserFavorite {

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
    private Object objectTag;

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

    public Object getObjectTag() {
        return objectTag;
    }

    public void setObjectTag(Object objectTag) {
        this.objectTag = objectTag;
    }

    public RemoveSpaceUserFavorite(Boolean successful, String message, String tag, String tagType, String processToken, Object objectTag) {
        this.successful = successful;
        this.message = message;
        this.tag = tag;
        this.tagType = tagType;
        this.processToken = processToken;
        this.objectTag = objectTag;
    }

}
