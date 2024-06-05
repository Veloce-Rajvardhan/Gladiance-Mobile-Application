package com.gladiance.ui.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.scenelist.ObjectSchedule;

import java.util.ArrayList;
import java.util.List;

public class SceneViewModel extends ViewModel {
    private MutableLiveData<List<ObjectScenes>> objectScenesListLiveData = new MutableLiveData<>();

    public void addObjectScenes(ObjectScenes objectScenes) {
        List<ObjectScenes> currentList = objectScenesListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(objectScenes);
        objectScenesListLiveData.setValue(currentList);
    }

    public LiveData<List<ObjectScenes>> getObjectScenesList() {
        return objectScenesListLiveData;
    }
}

//    private MutableLiveData<ObjectScenes> sceneLiveData = new MutableLiveData<>();
//
//    public void setObjectSchedule(ObjectScenes scenes) {
//        sceneLiveData.setValue(scenes);
//    }
//
//    public LiveData<ObjectScenes> getObjectSchedule() {
//        return sceneLiveData;
//    }
//}
