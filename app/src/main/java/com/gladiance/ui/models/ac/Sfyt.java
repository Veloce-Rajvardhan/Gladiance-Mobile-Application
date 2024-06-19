
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sfyt {

    @SerializedName("lower")
    @Expose
    private Integer lower;
    @SerializedName("upper")
    @Expose
    private Integer upper;

    public Integer getLower() {
        return lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public Integer getUpper() {
        return upper;
    }

    public void setUpper(Integer upper) {
        this.upper = upper;
    }

}
