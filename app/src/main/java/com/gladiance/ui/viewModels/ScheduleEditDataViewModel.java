package com.gladiance.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.gladiance.ui.models.scheduleEdit.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ScheduleEditDataViewModel extends ViewModel {
    private MutableLiveData<List<Configuration>> objectScheduleEditListLiveData = new MutableLiveData<>();

    public void addObjectEditSchedule(Configuration objectScenes) {
        List<Configuration> currentList = objectScheduleEditListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(objectScenes);
        objectScheduleEditListLiveData.setValue(currentList);
    }

    public LiveData<List<Configuration>> getObjectScheduleList() {
        return objectScheduleEditListLiveData;
    }
    public void clearObjectScene() {
        objectScheduleEditListLiveData.setValue(new ArrayList<>());
    }

}
