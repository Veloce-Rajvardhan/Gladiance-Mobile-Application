
package com.gladiance.ui.models.ac;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("Schedules")
    @Expose
    private List<Object> schedules;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("t")
    @Expose
    private Integer t;

    public List<Object> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Object> schedules) {
        this.schedules = schedules;
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
