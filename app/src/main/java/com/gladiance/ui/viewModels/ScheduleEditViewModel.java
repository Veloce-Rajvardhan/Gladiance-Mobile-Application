package com.gladiance.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gladiance.ui.models.saveSchedule.ObjectScheduleEdit;

import java.util.ArrayList;
import java.util.List;

public class ScheduleEditViewModel extends ViewModel {
    private MutableLiveData<List<ObjectScheduleEdit>> objectScenesListLiveData = new MutableLiveData<>();

    public void addObjectScenes(ObjectScheduleEdit objectScenes) {
        List<ObjectScheduleEdit> currentList = objectScenesListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(objectScenes);
        objectScenesListLiveData.setValue(currentList);
    }

    public LiveData<List<ObjectScheduleEdit>> getObjectScenesList() {
        return objectScenesListLiveData;
    }
    public void clearObjectEditSchedules() {
        objectScenesListLiveData.setValue(new ArrayList<>());
    }

}
