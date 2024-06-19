
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class System {

    @SerializedName("Reboot")
    @Expose
    private Boolean reboot;
    @SerializedName("Factory-Reset")
    @Expose
    private Boolean factoryReset;
    @SerializedName("Wi-Fi-Reset")
    @Expose
    private Boolean wiFiReset;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("t")
    @Expose
    private Integer t;

    public Boolean getReboot() {
        return reboot;
    }

    public void setReboot(Boolean reboot) {
        this.reboot = reboot;
    }

    public Boolean getFactoryReset() {
        return factoryReset;
    }

    public void setFactoryReset(Boolean factoryReset) {
        this.factoryReset = factoryReset;
    }

    public Boolean getWiFiReset() {
        return wiFiReset;
    }

    public void setWiFiReset(Boolean wiFiReset) {
        this.wiFiReset = wiFiReset;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

}
