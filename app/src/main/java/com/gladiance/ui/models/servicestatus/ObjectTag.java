package com.gladiance.ui.models.servicestatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {

    @SerializedName("ServiceModeActivated")
    @Expose
    private Boolean serviceModeActivated;

    public Boolean getServiceModeActivated() {
        return serviceModeActivated;
    }

    public void setServiceModeActivated(Boolean serviceModeActivated) {
        this.serviceModeActivated = serviceModeActivated;
    }

}
