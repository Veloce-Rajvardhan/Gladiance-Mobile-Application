package com.gladiance.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefObject {

    public RefObject(String Ref) {
        this.Ref = Ref;

    }

    @SerializedName("Ref")
    @Expose
    private String Ref;


    public String getRef() {
        return Ref;
    }

    public void setRef(String ref) {
        Ref = ref;
    }
}
