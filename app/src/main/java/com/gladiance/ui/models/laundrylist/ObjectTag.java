package com.gladiance.ui.models.laundrylist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjectTag {

    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("GAAProjectSpaceRef")
    @Expose
    private Long gAAProjectSpaceRef;
    @SerializedName("DocNo")
    @Expose
    private String docNo;
    @SerializedName("SerialNumberWithinDate")
    @Expose
    private Integer serialNumberWithinDate;
    @SerializedName("RequestedByUserRef")
    @Expose
    private Long requestedByUserRef;
    @SerializedName("RequestedAtDateTime")
    @Expose
    private String requestedAtDateTime;
    @SerializedName("MarkedAsPickedUpByUserRef")
    @Expose
    private Integer markedAsPickedUpByUserRef;
    @SerializedName("MarkedAsPickedUpAtDateTime")
    @Expose
    private String markedAsPickedUpAtDateTime;
    @SerializedName("MarkedAsDeliveredByUserRef")
    @Expose
    private Integer markedAsDeliveredByUserRef;
    @SerializedName("MarkedAsDeliveredAtDateTime")
    @Expose
    private String markedAsDeliveredAtDateTime;
    @SerializedName("CancelledByUserRef")
    @Expose
    private Integer cancelledByUserRef;
    @SerializedName("CancelledAtDateTime")
    @Expose
    private String cancelledAtDateTime;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("PickupRequestedFromDateTime")
    @Expose
    private String pickupRequestedFromDateTime;
    @SerializedName("PickupRequestedUptoDateTime")
    @Expose
    private String pickupRequestedUptoDateTime;
    @SerializedName("GAAProjectSpaceName")
    @Expose
    private String gAAProjectSpaceName;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;
    @SerializedName("RequestedByUserName")
    @Expose
    private String requestedByUserName;
    @SerializedName("MarkedAsPickedUpByUserName")
    @Expose
    private String markedAsPickedUpByUserName;
    @SerializedName("MarkedAsDeliveredByUserName")
    @Expose
    private String markedAsDeliveredByUserName;
    @SerializedName("CancelledByUserName")
    @Expose
    private String cancelledByUserName;
    @SerializedName("RequestStatusName")
    @Expose
    private String requestStatusName;
    @SerializedName("LineItems")
    @Expose
    private List<LineItem> lineItems;

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

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public Integer getSerialNumberWithinDate() {
        return serialNumberWithinDate;
    }

    public void setSerialNumberWithinDate(Integer serialNumberWithinDate) {
        this.serialNumberWithinDate = serialNumberWithinDate;
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

    public Integer getMarkedAsPickedUpByUserRef() {
        return markedAsPickedUpByUserRef;
    }

    public void setMarkedAsPickedUpByUserRef(Integer markedAsPickedUpByUserRef) {
        this.markedAsPickedUpByUserRef = markedAsPickedUpByUserRef;
    }

    public String getMarkedAsPickedUpAtDateTime() {
        return markedAsPickedUpAtDateTime;
    }

    public void setMarkedAsPickedUpAtDateTime(String markedAsPickedUpAtDateTime) {
        this.markedAsPickedUpAtDateTime = markedAsPickedUpAtDateTime;
    }

    public Integer getMarkedAsDeliveredByUserRef() {
        return markedAsDeliveredByUserRef;
    }

    public void setMarkedAsDeliveredByUserRef(Integer markedAsDeliveredByUserRef) {
        this.markedAsDeliveredByUserRef = markedAsDeliveredByUserRef;
    }

    public String getMarkedAsDeliveredAtDateTime() {
        return markedAsDeliveredAtDateTime;
    }

    public void setMarkedAsDeliveredAtDateTime(String markedAsDeliveredAtDateTime) {
        this.markedAsDeliveredAtDateTime = markedAsDeliveredAtDateTime;
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

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getPickupRequestedFromDateTime() {
        return pickupRequestedFromDateTime;
    }

    public void setPickupRequestedFromDateTime(String pickupRequestedFromDateTime) {
        this.pickupRequestedFromDateTime = pickupRequestedFromDateTime;
    }

    public String getPickupRequestedUptoDateTime() {
        return pickupRequestedUptoDateTime;
    }

    public void setPickupRequestedUptoDateTime(String pickupRequestedUptoDateTime) {
        this.pickupRequestedUptoDateTime = pickupRequestedUptoDateTime;
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

    public String getRequestedByUserName() {
        return requestedByUserName;
    }

    public void setRequestedByUserName(String requestedByUserName) {
        this.requestedByUserName = requestedByUserName;
    }

    public String getMarkedAsPickedUpByUserName() {
        return markedAsPickedUpByUserName;
    }

    public void setMarkedAsPickedUpByUserName(String markedAsPickedUpByUserName) {
        this.markedAsPickedUpByUserName = markedAsPickedUpByUserName;
    }

    public String getMarkedAsDeliveredByUserName() {
        return markedAsDeliveredByUserName;
    }

    public void setMarkedAsDeliveredByUserName(String markedAsDeliveredByUserName) {
        this.markedAsDeliveredByUserName = markedAsDeliveredByUserName;
    }

    public String getCancelledByUserName() {
        return cancelledByUserName;
    }

    public void setCancelledByUserName(String cancelledByUserName) {
        this.cancelledByUserName = cancelledByUserName;
    }

    public String getRequestStatusName() {
        return requestStatusName;
    }

    public void setRequestStatusName(String requestStatusName) {
        this.requestStatusName = requestStatusName;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public ObjectTag(Long ref, Long gAAProjectSpaceRef, String docNo, Integer serialNumberWithinDate, Long requestedByUserRef, String requestedAtDateTime, Integer markedAsPickedUpByUserRef, String markedAsPickedUpAtDateTime, Integer markedAsDeliveredByUserRef, String markedAsDeliveredAtDateTime, Integer cancelledByUserRef, String cancelledAtDateTime, Integer requestStatus, String pickupRequestedFromDateTime, String pickupRequestedUptoDateTime, String gAAProjectSpaceName, Long gAAProjectRef, String gAAProjectName, String requestedByUserName, String markedAsPickedUpByUserName, String markedAsDeliveredByUserName, String cancelledByUserName, String requestStatusName, List<LineItem> lineItems) {
        this.ref = ref;
        this.gAAProjectSpaceRef = gAAProjectSpaceRef;
        this.docNo = docNo;
        this.serialNumberWithinDate = serialNumberWithinDate;
        this.requestedByUserRef = requestedByUserRef;
        this.requestedAtDateTime = requestedAtDateTime;
        this.markedAsPickedUpByUserRef = markedAsPickedUpByUserRef;
        this.markedAsPickedUpAtDateTime = markedAsPickedUpAtDateTime;
        this.markedAsDeliveredByUserRef = markedAsDeliveredByUserRef;
        this.markedAsDeliveredAtDateTime = markedAsDeliveredAtDateTime;
        this.cancelledByUserRef = cancelledByUserRef;
        this.cancelledAtDateTime = cancelledAtDateTime;
        this.requestStatus = requestStatus;
        this.pickupRequestedFromDateTime = pickupRequestedFromDateTime;
        this.pickupRequestedUptoDateTime = pickupRequestedUptoDateTime;
        this.gAAProjectSpaceName = gAAProjectSpaceName;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
        this.requestedByUserName = requestedByUserName;
        this.markedAsPickedUpByUserName = markedAsPickedUpByUserName;
        this.markedAsDeliveredByUserName = markedAsDeliveredByUserName;
        this.cancelledByUserName = cancelledByUserName;
        this.requestStatusName = requestStatusName;
        this.lineItems = lineItems;
    }
}
