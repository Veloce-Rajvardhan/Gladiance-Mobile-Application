package com.gladiance.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.sceneEdit.Configuration;

import java.util.ArrayList;
import java.util.List;

public class SceneEditDataViewModel extends ViewModel {
    private MutableLiveData<List<Configuration>> objectScenesEditListLiveData = new MutableLiveData<>();

    public void addObjectEditScenes(Configuration objectScenes) {
        List<Configuration> currentList = objectScenesEditListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(objectScenes);
        objectScenesEditListLiveData.setValue(currentList);
    }

    public LiveData<List<Configuration>> getObjectScenesList() {
        return objectScenesEditListLiveData;
    }
    public void clearObjectScene() {
        objectScenesEditListLiveData.setValue(new ArrayList<>());
    }

}
