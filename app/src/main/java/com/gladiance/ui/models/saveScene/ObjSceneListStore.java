package com.gladiance.ui.models.saveScene;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjSceneListStore {
    @SerializedName("Configurations")
    @Expose
    private List<Configuration> configurations;

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

}
