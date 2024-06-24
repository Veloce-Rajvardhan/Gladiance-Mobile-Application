package com.gladiance.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gladiance.ui.models.scene.ObjectSceneCreate;
import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.scenelist.ObjectSchedule;

import java.util.ArrayList;
import java.util.List;

public class SceneCreateViewModel extends ViewModel {
    private MutableLiveData<List<ObjectSceneCreate>> objectScenesListLiveData = new MutableLiveData<>();

    public void addObjectScenes(ObjectSceneCreate objectScenes) {
        List<ObjectSceneCreate> currentList = objectScenesListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(objectScenes);
        objectScenesListLiveData.setValue(currentList);
    }

    public LiveData<List<ObjectSceneCreate  >> getObjectScenesList() {
        return objectScenesListLiveData;
    }
    public void clearObjectCreateScene() {
        objectScenesListLiveData.setValue(new ArrayList<>());
    }

    public void removeObjectSchedule(ObjectSceneCreate objectSchedule) {
        List<ObjectSceneCreate> currentList = objectScenesListLiveData.getValue();
        if (currentList != null) {
            currentList.remove(objectSchedule);
            objectScenesListLiveData.setValue(currentList);
        }
    }
}
