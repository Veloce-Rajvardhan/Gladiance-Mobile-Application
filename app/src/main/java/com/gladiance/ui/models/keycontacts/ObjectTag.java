package com.gladiance.ui.models.keycontacts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {

    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GoogleLocation")
    @Expose
    private String googleLocation;
    @SerializedName("TelephoneNumber")
    @Expose
    private String telephoneNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoogleLocation() {
        return googleLocation;
    }

    public void setGoogleLocation(String googleLocation) {
        this.googleLocation = googleLocation;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
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

    public ObjectTag(Long ref, String name, String googleLocation, String telephoneNumber, Long gAAProjectRef, String gAAProjectName) {
        this.ref = ref;
        this.name = name;
        this.googleLocation = googleLocation;
        this.telephoneNumber = telephoneNumber;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
    }
}
