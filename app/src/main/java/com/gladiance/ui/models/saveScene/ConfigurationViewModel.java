package com.gladiance.ui.models.saveScene;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationViewModel extends ViewModel {

    private MutableLiveData<List<Configuration>> matchedConfigurationsLiveData = new MutableLiveData<>();

    public void setMatchedConfigurations(List<Configuration> matchedConfigurations) {
        matchedConfigurationsLiveData.setValue(matchedConfigurations);
    }
    // Method to add configurations to the existing list in ViewModel
    public void addMatchedConfiguration(Configuration configuration) {
        List<Configuration> currentConfigurations = matchedConfigurationsLiveData.getValue();
        if (currentConfigurations == null) {
            currentConfigurations = new ArrayList<>();
        }
        currentConfigurations.add(configuration);
        matchedConfigurationsLiveData.setValue(currentConfigurations);
        Log.d("ConfigurationViewModel", "Size of matchedConfigurations list: " + currentConfigurations.size());
    }

    public LiveData<List<Configuration>> getMatchedConfigurations() {
        return matchedConfigurationsLiveData;
    }
}

    //////////////////-------------
//    private MutableLiveData<List<Configuration>> matchedConfigurationsLiveData;
//
//    public ConfigurationViewModel() {
//        matchedConfigurationsLiveData = new MutableLiveData<>();
//    }

    // Method to set matchedConfigurations in ViewModel
//    public void setMatchedConfigurations(List<Configuration> matchedConfigurations) {
//        matchedConfigurationsLiveData.setValue(matchedConfigurations);
//    }
//
//    // LiveData to observe matchedConfigurations in UI
//    public LiveData<List<Configuration>> getMatchedConfigurations() {
//        return matchedConfigurationsLiveData;
//    }
//}