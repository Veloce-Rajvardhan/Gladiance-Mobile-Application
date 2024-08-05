package com.gladiance.ui.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gladiance.ui.models.scene.ObjectSceneCreate;
import com.gladiance.ui.models.scenelist.ObjectSchedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleViewModel extends ViewModel {
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

    public void clearObjectSchedules() {
        objectScenesListLiveData.setValue(new ArrayList<>());
    }

    // In your ViewModel
    public int getObjectScenesListSize() {
        List<ObjectSchedule> list = getObjectScenesList().getValue();
        return (list != null) ? list.size() : 0;
    }
    public void removeObjectSchedule(ObjectSchedule objectSchedule) {
        List<ObjectSchedule> currentList = objectScenesListLiveData.getValue();
        if (currentList != null) {
            currentList.remove(objectSchedule);
            objectScenesListLiveData.setValue(currentList);
        }
    }

}
//}
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