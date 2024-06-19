package com.gladiance.ui.models.scene;

public class CheckBox {
    private String gAAProjectSpaceTypePlannedDeviceName;
    private String gAAProjectSpaceTypePlannedDeviceRef;
    private boolean isChecked; // New field for checkbox state

    // Constructor, getters, setters

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}