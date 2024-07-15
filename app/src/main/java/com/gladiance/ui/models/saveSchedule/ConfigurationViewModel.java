package com.gladiance.ui.models.saveSchedule;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gladiance.ui.models.saveScene.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationViewModel extends ViewModel {

    private MutableLiveData<List<com.gladiance.ui.models.scheduleStoreData.Configuration>> matchedConfigurationsLiveData = new MutableLiveData<>();

    public void setMatchedConfigurations(List<com.gladiance.ui.models.scheduleStoreData.Configuration> matchedConfigurations) {
        matchedConfigurationsLiveData.setValue(matchedConfigurations);
    }
    // Method to add configurations to the existing list in ViewModel
    public void addMatchedConfiguration(com.gladiance.ui.models.scheduleStoreData.Configuration configuration) {
        List<com.gladiance.ui.models.scheduleStoreData.Configuration> currentConfigurations = matchedConfigurationsLiveData.getValue();
        if (currentConfigurations == null) {
            currentConfigurations = new ArrayList<>();
        }
        currentConfigurations.add(configuration);
        matchedConfigurationsLiveData.setValue(currentConfigurations);
        Log.d("ConfigurationViewModelSchedule", "Size of matchedConfigurations list Schdeule: " + currentConfigurations.size());
    }

    public LiveData<List<com.gladiance.ui.models.scheduleStoreData.Configuration>> getMatchedConfigurations() {
        return matchedConfigurationsLiveData;
    }
}