package com.gladiance.ui.models.amenities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {
    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("VideoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("FromTime")
    @Expose
    private String fromTime;
    @SerializedName("ToTime")
    @Expose
    private String toTime;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getgAAProjectRef() {
        return gAAProjectRef;
    }

    public void setgAAProjectRef(Long gAAProjectRef) {
        this.gAAProjectRef = gAAProjectRef;
    }

    public String getgAAProjectName() {
        return gAAProjectName;
    }

    public void setgAAProjectName(String gAAProjectName) {
        this.gAAProjectName = gAAProjectName;
    }

    public ObjectTag(Long ref, String videoUrl, String name, String fromTime, String toTime, String description, Long gAAProjectRef, String gAAProjectName) {
        this.ref = ref;
        this.videoUrl = videoUrl;
        this.name = name;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.description = description;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
    }
}
