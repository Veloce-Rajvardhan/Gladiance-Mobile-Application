
package com.gladiance.ui.models.ac;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Map {

    @SerializedName("array")
    @Expose
    private List<Object> array;

    public List<Object> getArray() {
        return array;
    }

    public void setArray(List<Object> array) {
        this.array = array;
    }

}
