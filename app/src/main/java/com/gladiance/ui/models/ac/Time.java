
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Time {

    @SerializedName("TZ")
    @Expose
    private String tz;
    @SerializedName("TZ-POSIX")
    @Expose
    private String tzPosix;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("t")
    @Expose
    private Integer t;

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getTzPosix() {
        return tzPosix;
    }

    public void setTzPosix(String tzPosix) {
        this.tzPosix = tzPosix;
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
