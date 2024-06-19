
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Speed {

    @SerializedName("fan_speed_0")
    @Expose
    private Integer fanSpeed0;
    @SerializedName("fan_speed_1")
    @Expose
    private Integer fanSpeed1;
    @SerializedName("fan_speed_2")
    @Expose
    private Integer fanSpeed2;
    @SerializedName("fan_speed_3")
    @Expose
    private Integer fanSpeed3;
    @SerializedName("fan_speed_4")
    @Expose
    private Integer fanSpeed4;
    @SerializedName("fan_speed_5")
    @Expose
    private Integer fanSpeed5;

    public Integer getFanSpeed0() {
        return fanSpeed0;
    }

    public void setFanSpeed0(Integer fanSpeed0) {
        this.fanSpeed0 = fanSpeed0;
    }

    public Integer getFanSpeed1() {
        return fanSpeed1;
    }

    public void setFanSpeed1(Integer fanSpeed1) {
        this.fanSpeed1 = fanSpeed1;
    }

    public Integer getFanSpeed2() {
        return fanSpeed2;
    }

    public void setFanSpeed2(Integer fanSpeed2) {
        this.fanSpeed2 = fanSpeed2;
    }

    public Integer getFanSpeed3() {
        return fanSpeed3;
    }

    public void setFanSpeed3(Integer fanSpeed3) {
        this.fanSpeed3 = fanSpeed3;
    }

    public Integer getFanSpeed4() {
        return fanSpeed4;
    }

    public void setFanSpeed4(Integer fanSpeed4) {
        this.fanSpeed4 = fanSpeed4;
    }

    public Integer getFanSpeed5() {
        return fanSpeed5;
    }

    public void setFanSpeed5(Integer fanSpeed5) {
        this.fanSpeed5 = fanSpeed5;
    }

}
