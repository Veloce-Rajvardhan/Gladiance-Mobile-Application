
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Gendb {

    @SerializedName("gendbarr")
    @Expose
    private Gendbarr gendbarr;

    public Gendbarr getGendbarr() {
        return gendbarr;
    }

    public void setGendbarr(Gendbarr gendbarr) {
        this.gendbarr = gendbarr;
    }

}
