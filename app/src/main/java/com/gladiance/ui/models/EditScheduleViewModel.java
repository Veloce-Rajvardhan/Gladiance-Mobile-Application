package com.gladiance.ui.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gladiance.ui.models.scene.ObjectSceneCreate;
import com.gladiance.ui.models.scenelist.ObjectSchedule;

import java.util.ArrayList;
import java.util.List;

public class EditScheduleViewModel extends ViewModel {
    ///////////////////////////////////
    private MutableLiveData<List<ObjectSchedule>> objectScenesListLiveData = new MutableLiveData<>();

    public void addObjectSchedule(ObjectSchedule objectScenes) {
        List<ObjectSchedule> currentList = objectScenesListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(objectScenes);
        objectScenesListLiveData.setValue(currentList);
    }

    public LiveData<List<ObjectSchedule  >> getObjectScenesList() {
        return objectScenesListLiveData;
    }
}


    ///////////////////////////////////
//    private MutableLiveData<ObjectSchedule> scheduleLiveData = new MutableLiveData<>();
//
//    public void setObjectSchedule(ObjectSchedule schedule) {
//        scheduleLiveData.setValue(schedule);
//    }
//
//    public LiveData<ObjectSchedule> getObjectSchedule() {
//        return scheduleLiveData;
//    }
//}