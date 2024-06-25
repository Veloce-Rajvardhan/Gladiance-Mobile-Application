package com.gladiance.ui.models.privacystatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {

    @SerializedName("PrivacyModeActivated")
    @Expose
    private Boolean privacyModeActivated;

    public Boolean getPrivacyModeActivated() {
        return privacyModeActivated;
    }

    public void setPrivacyModeActivated(Boolean privacyModeActivated) {
        this.privacyModeActivated = privacyModeActivated;
    }

    public ObjectTag(Boolean privacyModeActivated) {
        this.privacyModeActivated = privacyModeActivated;
    }
}
