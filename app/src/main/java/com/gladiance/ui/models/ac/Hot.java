
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hot {

    @SerializedName("Speed")
    @Expose
    private String speed;
    @SerializedName("sttm")
    @Expose
    private Integer sttm;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Integer getSttm() {
        return sttm;
    }

    public void setSttm(Integer sttm) {
        this.sttm = sttm;
    }

}
