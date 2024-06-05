package com.gladiance.ui.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gladiance.ui.models.scenelist.ObjectSchedule;

public class ScheduleViewModel extends ViewModel {
    private MutableLiveData<ObjectSchedule> scheduleLiveData = new MutableLiveData<>();

    public void setObjectSchedule(ObjectSchedule schedule) {
        scheduleLiveData.setValue(schedule);
    }

    public LiveData<ObjectSchedule> getObjectSchedule() {
        return scheduleLiveData;
    }
}