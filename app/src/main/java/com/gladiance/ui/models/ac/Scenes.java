
package com.gladiance.ui.models.ac;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scenes {

    @SerializedName("Scenes")
    @Expose
    private List<Object> scenes;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("t")
    @Expose
    private Integer t;

    public List<Object> getScenes() {
        return scenes;
    }

    public void setScenes(List<Object> scenes) {
        this.scenes = scenes;
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
