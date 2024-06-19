
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unoc {

    @SerializedName("hot")
    @Expose
    private Hot__1 hot;
    @SerializedName("Cool")
    @Expose
    private Cool__1 cool;

    public Hot__1 getHot() {
        return hot;
    }

    public void setHot(Hot__1 hot) {
        this.hot = hot;
    }

    public Cool__1 getCool() {
        return cool;
    }

    public void setCool(Cool__1 cool) {
        this.cool = cool;
    }

}
