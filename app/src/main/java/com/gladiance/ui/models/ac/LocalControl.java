
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LocalControl {

    @SerializedName("POP")
    @Expose
    private String pop;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("t")
    @Expose
    private Integer t;

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
