package com.gladiance.ui.models;

import android.os.Bundle;

import com.gladiance.AppConstants;

public class UpdateEvent {
    private AppConstants.UpdateEventType eventType;
    private Bundle data;

    public Bundle getData() {
        return data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    public UpdateEvent(AppConstants.UpdateEventType type) {
        eventType = type;
    }

    public AppConstants.UpdateEventType getEventType() {
        return eventType;
    }
}
