package com.gladiance.ui.models.securitystatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {

    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("GAAProjectSpaceRef")
    @Expose
    private Long gAAProjectSpaceRef;
    @SerializedName("RequestedByUserRef")
    @Expose
    private Long requestedByUserRef;
    @SerializedName("RequestedAtDateTime")
    @Expose
    private String requestedAtDateTime;
    @SerializedName("MarkedAsAttendedByUserRef")
    @Expose
    private Integer markedAsAttendedByUserRef;
    @SerializedName("MarkedAsAttendedAtDateTime")
    @Expose
    private String markedAsAttendedAtDateTime;
    @SerializedName("CancelledByUserRef")
    @Expose
    private Integer cancelledByUserRef;
    @SerializedName("CancelledAtDateTime")
    @Expose
    private String cancelledAtDateTime;
    @SerializedName("TriggerDueAtDateTime")
    @Expose
    private String triggerDueAtDateTime;
    @SerializedName("GAAProjectSpaceName")
    @Expose
    private String gAAProjectSpaceName;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;
    @SerializedName("SecurityRequestTriggerDelayInMs")
    @Expose
    private Integer securityRequestTriggerDelayInMs;
    @SerializedName("RequestedByUserName")
    @Expose
    private String requestedByUserName;
    @SerializedName("MarkedAsAttendedByUserName")
    @Expose
    private String markedAsAttendedByUserName;
    @SerializedName("CancelledByUserName")
    @Expose
    private String cancelledByUserName;
    @SerializedName("Triggered")
    @Expose
    private Boolean triggered;

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public Long getgAAProjectSpaceRef() {
        return gAAProjectSpaceRef;
    }

    public void setgAAProjectSpaceRef(Long gAAProjectSpaceRef) {
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
    }

    public Long getRequestedByUserRef() {
        return requestedByUserRef;
    }

    public void setRequestedByUserRef(Long requestedByUserRef) {
        this.requestedByUserRef = requestedByUserRef;
    }

    public String getRequestedAtDateTime() {
        return requestedAtDateTime;
    }

    public void setRequestedAtDateTime(String requestedAtDateTime) {
        this.requestedAtDateTime = requestedAtDateTime;
    }

    public Integer getMarkedAsAttendedByUserRef() {
        return markedAsAttendedByUserRef;
    }

    public void setMarkedAsAttendedByUserRef(Integer markedAsAttendedByUserRef) {
        this.markedAsAttendedByUserRef = markedAsAttendedByUserRef;
    }

    public String getMarkedAsAttendedAtDateTime() {
        return markedAsAttendedAtDateTime;
    }

    public void setMarkedAsAttendedAtDateTime(String markedAsAttendedAtDateTime) {
        this.markedAsAttendedAtDateTime = markedAsAttendedAtDateTime;
    }

    public Integer getCancelledByUserRef() {
        return cancelledByUserRef;
    }

    public void setCancelledByUserRef(Integer cancelledByUserRef) {
        this.cancelledByUserRef = cancelledByUserRef;
    }

    public String getCancelledAtDateTime() {
        return cancelledAtDateTime;
    }

    public void setCancelledAtDateTime(String cancelledAtDateTime) {
        this.cancelledAtDateTime = cancelledAtDateTime;
    }

    public String getTriggerDueAtDateTime() {
        return triggerDueAtDateTime;
    }

    public void setTriggerDueAtDateTime(String triggerDueAtDateTime) {
        this.triggerDueAtDateTime = triggerDueAtDateTime;
    }

    public String getgAAProjectSpaceName() {
        return gAAProjectSpaceName;
    }

    public void setgAAProjectSpaceName(String gAAProjectSpaceName) {
        this.gAAProjectSpaceName = gAAProjectSpaceName;
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

    public Integer getSecurityRequestTriggerDelayInMs() {
        return securityRequestTriggerDelayInMs;
    }

    public void setSecurityRequestTriggerDelayInMs(Integer securityRequestTriggerDelayInMs) {
        this.securityRequestTriggerDelayInMs = securityRequestTriggerDelayInMs;
    }

    public String getRequestedByUserName() {
        return requestedByUserName;
    }

    public void setRequestedByUserName(String requestedByUserName) {
        this.requestedByUserName = requestedByUserName;
    }

    public String getMarkedAsAttendedByUserName() {
        return markedAsAttendedByUserName;
    }

    public void setMarkedAsAttendedByUserName(String markedAsAttendedByUserName) {
        this.markedAsAttendedByUserName = markedAsAttendedByUserName;
    }

    public String getCancelledByUserName() {
        return cancelledByUserName;
    }

    public void setCancelledByUserName(String cancelledByUserName) {
        this.cancelledByUserName = cancelledByUserName;
    }

    public Boolean getTriggered() {
        return triggered;
    }

    public void setTriggered(Boolean triggered) {
        this.triggered = triggered;
    }
}
