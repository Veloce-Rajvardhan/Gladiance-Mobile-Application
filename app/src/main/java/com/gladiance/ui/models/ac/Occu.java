
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Occu {

    @SerializedName("hot")
    @Expose
    private Hot hot;
    @SerializedName("Cool")
    @Expose
    private Cool cool;

    public Hot getHot() {
        return hot;
    }

    public void setHot(Hot hot) {
        this.hot = hot;
    }

    public Cool getCool() {
        return cool;
    }

    public void setCool(Cool cool) {
        this.cool = cool;
    }

}
