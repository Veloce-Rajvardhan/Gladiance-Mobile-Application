package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;

import static com.gladiance.AppConstants.MY_CONSTANT_LIST;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.AppConstants;
import com.gladiance.ui.activities.AddDeviceActivity;
import com.gladiance.ui.activities.EspMainActivity;
//import com.gladiance.ui.fragment.MyProfile.EditSceneFragment;
import com.gladiance.ui.fragment.MyProfile.EditSceneFragment;
import com.gladiance.ui.fragment.MyProfile.ProfileDeviceCardFragment;
import com.gladiance.ui.fragment.MyProfile.SceneDeviceCardFragment;
import com.gladiance.ui.fragment.RoomControl.DeviceCardFragment;
import com.gladiance.ui.models.SceneStoreData.ConfigurationSceneEditData;
import com.gladiance.ui.models.guestlandingpage.Controls;
import com.gladiance.ui.models.saveScene.ConfigurationViewModel;
import com.gladiance.ui.models.saveScene.SceneConfig;
import com.gladiance.ui.models.scene.Configuration;
import com.gladiance.R;
import com.gladiance.ui.viewModels.SceneEditDataViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SceneCheckAdapter extends RecyclerView.Adapter<SceneCheckAdapter.ViewHolder> {
    private List<Controls> ConArrayList;
    private List<Configuration> ConfigArrayList;
    private SharedPreferences prefs;
    private ConfigurationViewModel viewModel;
    private Context context;
    private EditSceneFragment editFragment;

    private SceneEditDataViewModel sceneEditDataViewModel;
    private List<com.gladiance.ui.models.sceneEdit.Configuration> con;

    private LifecycleOwner lifecycleOwner;
    private com.gladiance.ui.models.saveScene.Configuration configuration2;
    private List<ConfigurationSceneEditData> configurationSceneEditData;
    private List<com.gladiance.ui.models.sceneEdit.Configuration> config;


    public SceneCheckAdapter(ArrayList<Controls> ConArrayList, ArrayList<Configuration>ConfigArrayList, ConfigurationViewModel viewModel, Context context,EditSceneFragment editSceneFragment,LifecycleOwner lifecycleOwner,SceneEditDataViewModel sceneEditDataViewModel, List<ConfigurationSceneEditData> configurationSceneEditData, List<com.gladiance.ui.models.sceneEdit.Configuration> config) {
        this.ConArrayList = ConArrayList;
        this.ConfigArrayList = ConfigArrayList;
        this.viewModel = viewModel;
        this.context = context;
        this.editFragment = editSceneFragment;
        this.lifecycleOwner = lifecycleOwner;
        this.sceneEditDataViewModel = sceneEditDataViewModel;
        this.configurationSceneEditData = configurationSceneEditData;
        this.config = config;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scene_control_card, parent, false);

            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Controls control = ConArrayList.get(position);

        // Set device name text
        holder.deviceNameTextView.setText(control.getgAAProjectSpaceTypePlannedDeviceName());

        // Clear any previous OnCheckedChangeListener to prevent unwanted behavior
        holder.deviceNameCheckBox.setOnCheckedChangeListener(null);

        // Set checkbox state based on isChecked flag in Controls object
        holder.deviceNameCheckBox.setChecked(control.isChecked());
        List<SceneEditDataViewModel> config2 = new ArrayList<>();
        sceneEditDataViewModel.getObjectScenesList().observe(lifecycleOwner, data -> {
            // Update your UI or process data
            for (com.gladiance.ui.models.sceneEdit.Configuration sceneEditDataViewModel : data){
                Log.e(TAG, "blaaa: "+sceneEditDataViewModel.getgAAProjectSpaceTypePlannedDeviceRef());
                if (sceneEditDataViewModel.getgAAProjectSpaceTypePlannedDeviceRef().equals(control.getgAAProjectSpaceTypePlannedDeviceRef())) {

                    //  configuration.getRef();
                    holder.deviceNameCheckBox.setChecked(true);

                }
            }

        });
//        List<com.gladiance.ui.models.saveScene.Configuration> matchedConfigurations = new ArrayList<>();
//        viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ConfigurationViewModel.class);
//
//        Log.d(TAG, "ConfigArrayList size: " + ConfigArrayList.size());
//
//        for (Configuration configuration : ConfigArrayList) {
//            String configRef = String.valueOf(configuration.getgAAProjectSpaceTypePlannedDeviceRef());
//            String controlRef = String.valueOf(control.getgAAProjectSpaceTypePlannedDeviceRef());
//            Log.d(TAG, "Config Ref: " + configRef + ", Control Ref: " + controlRef);
//
//            if (configRef.equals(controlRef)) {
//                matchedConfigurations.add(new com.gladiance.ui.models.saveScene.Configuration(
//                        configuration.getgAAProjectSceneRef(),
//                        configuration.getNodeConfigParamName(),
//                        configuration.getValue(),
//                        configuration.getgAAProjectSpaceTypePlannedDeviceName(),
//                        configuration.getRef(),
//                        configuration.getgAAProjectSpaceTypePlannedDeviceRef()
//                ));
//            }
//        }
//
//        int sizeOfMatchedConfigurations = matchedConfigurations.size();
//        Log.d("MatchedConfigurations", "Size of matchedConfigurations list: " + sizeOfMatchedConfigurations);
//
//        viewModel.setMatchedConfigurations(matchedConfigurations);

        // Update checkbox state based on Configuration list
//        List<com.gladiance.ui.models.saveScene.Configuration> matchedConfigurations = new ArrayList<>();
//        ConfigurationViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner)context).get(ConfigurationViewModel.class);
//        for(ConfigurationSceneEditData config : configurationSceneEditData) {
//            if (config.getGAAProjectSpaceTypePlannedDeviceRef().equals(control.getgAAProjectSpaceTypePlannedDeviceRef())) {
//
//              //  configuration.getRef();
//                holder.deviceNameCheckBox.setChecked(true);
//
//            }
//        }




///////////////////////////////////////

//        for (Configuration configuration : ConfigArrayList) {
//            if (configuration.getgAAProjectSpaceTypePlannedDeviceRef().equals(control.getgAAProjectSpaceTypePlannedDeviceRef())) {
//
//                configuration.getRef();
//                // Log the values for debugging
////                Log.e(TAG, "onBindViewHolder: " + configuration.getgAAProjectSpaceTypePlannedDeviceRef() + " " + control.getgAAProjectSpaceTypePlannedDeviceRef());
////                viewModel.addMatchedConfiguration(new com.gladiance.ui.models.saveScene.Configuration(
////                        configuration.getgAAProjectSceneRef(),
////                        configuration.getNodeConfigParamName(),
////                        configuration.getValue(),
////                        configuration.getgAAProjectSpaceTypePlannedDeviceName(),
////                        configuration.getRef(),
////                        configuration.getgAAProjectSpaceTypePlannedDeviceRef()
////                ));
//            //    viewModel.addMatchedConfiguration(matchedConfigurations);
//
//                // Logging the size of matchedConfigurations
////                int sizeOfMatchedConfigurations = matchedConfigurations.size();
////                Log.d("MatchedConfigurations", "Size of matchedConfigurations list: " + sizeOfMatchedConfigurations);
//                // Assuming you are doing something with your ViewHolder here
//                holder.deviceNameCheckBox.setChecked(true);
//           //     holder.deviceNameCheckBox.setChecked(control.isChecked());
//                control.setChecked(true);
//
//                // Exit the loop once a match is found
//             //   break;
//            }
//        }

        ///////////////////////////



 //       int sizeOfMatchedConfigurations = matchedConfigurations.size();
 //       Log.d("MatchedConfigurations", "Size of matchedConfigurations list: " + sizeOfMatchedConfigurations);


        // Set OnCheckedChangeListener for checkbox
        holder.deviceNameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update isChecked state in Controls object
                control.setChecked(isChecked);
                editFragment = new EditSceneFragment(); // or however you create your fragment

                Log.e(TAG, "onCheckedChanged : "+control.isChecked());
                Log.e(TAG, "onCheckedChanged : "+position);
                Log.e(TAG, "GAA_P_REF : "+ control.getgAAProjectSpaceTypePlannedDeviceRef());
                long abc = control.getgAAProjectSpaceTypePlannedDeviceRef();
                Log.e(TAG, "GAAProjectSpaceTypePlannedDeviceRef: "+abc);
                if(control.isChecked() == false) {
                   // editFragment.abc(abc);

                    sceneEditDataViewModel.getObjectScenesList().observe((LifecycleOwner) context, (Observer<? super List<com.gladiance.ui.models.sceneEdit.Configuration>>) new Observer<List<com.gladiance.ui.models.sceneEdit.Configuration>>() {
                        @Override
                        public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {
                            if (objectScenesList != null) {
                                AppConstants.DataEdit = true;


                                List<com.gladiance.ui.models.sceneEdit.Configuration> listCopy = new ArrayList<>(objectScenesList);

                                for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : listCopy) {
                                    Log.e(TAG, abc + " == " + objectScenes.getgAAProjectSpaceTypePlannedDeviceRef());

                                    if (abc == (objectScenes.getgAAProjectSpaceTypePlannedDeviceRef())) {
                                        Log.e(TAG, abc + " == " + objectScenes.getgAAProjectSpaceTypePlannedDeviceRef());

                                        // Remove the object from the original list
                                        objectScenesList.remove(objectScenes);
                                    } else {
                                        // Handle other cases if needed
                                    }

                                    Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
                                }
                                notifyDataSetChanged(); // Notify the adapter of data changes
                            }
                        }
                    });
                }

                try {
                    if (prefs != null) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isChecked_" + position, isChecked);
                        editor.apply();
                        Log.e(TAG, "shared: ");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e);
                }
            }
        });

        // Handle click listener for CardView
        holder.CdScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle CardView click event here
                boolean isChecked = holder.deviceNameCheckBox.isChecked();

                if (isChecked) {
                    // Handle actions when checkbox is checked
                    Long GaaProjectSpaceTypePlannedDeviceRef = control.getgAAProjectSpaceTypePlannedDeviceRef();

                    String projectSpaceTypePlannedDeviceName = control.getgAAProjectSpaceTypePlannedDeviceName();
                    // Example: Saving data to SharedPreferences
                    SharedPreferences sharedPreference_dyn2 = view.getContext().getSharedPreferences("PROJECT_SPACE_TYPE_PLANNED_DEVICE_REF_Dyn", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_dyn = sharedPreference_dyn2.edit();
                    editor_dyn.putLong("GAA_PROJECT_SPACE_TYPE_PLANNED_DEVICE_REF", GaaProjectSpaceTypePlannedDeviceRef);
                    editor_dyn.apply();

                    SharedPreferences sharedPreference_dyn3 = view.getContext().getSharedPreferences("PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF_Dyn", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_dyn3 = sharedPreference_dyn3.edit();
                    editor_dyn3.putString("GAA_PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF", projectSpaceTypePlannedDeviceName);
                    editor_dyn3.apply();

                    // Example: Storing data to a dynamic key in SharedPreferences
                    SharedPreferences sharedPreference_dyn = view.getContext().getSharedPreferences("my_shared_pref_dyn", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor5 = sharedPreference_dyn.edit();
                    editor5.putLong("dynamic_key_" + String.valueOf(GaaProjectSpaceTypePlannedDeviceRef), GaaProjectSpaceTypePlannedDeviceRef);
                    editor5.apply();

                    // Example: Setting global constants or variables
                    AppConstants.GaaProjectSpaceTypePlannedDeviceRef = String.valueOf(GaaProjectSpaceTypePlannedDeviceRef);
                  //  AppConstants.Ref_Scene_Object = String.valueOf(control.getRef());
                    AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef = String.valueOf(GaaProjectSpaceTypePlannedDeviceRef);
                    Log.e(TAG, "Create gaa: "+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);

                    // Example: Logging constant list data
                    for (String item : AppConstants.MY_CONSTANT_LIST) {
                        Log.d("NewListData", "Item: " + item);
                    }

                    // Example: Storing additional data to SharedPreferences
                    SharedPreferences sharedPreferences2 = view.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    editor.putString("KEY_USERNAMEs", control.getNodeId());
                    editor.apply();

                    SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    editor1.putString("KEY_USERNAMEs", control.getLabel());
                    editor1.apply();

                    // Example: Opening a fragment on click
                    Bundle bundle = new Bundle();
                    bundle.putLong("LONG_VALUE_KEY", GaaProjectSpaceTypePlannedDeviceRef);
                    Fragment destinationFragment = new SceneDeviceCardFragment();
                    destinationFragment.setArguments(bundle);

                    FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.set_mood, destinationFragment, String.valueOf(destinationFragment)).addToBackStack(null).commit();
                } else {
                    // Handle actions when checkbox is not checked
                    Toast.makeText(view.getContext(), "Checkbox is not checked!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ConArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        CheckBox deviceNameCheckBox;
        CardView CdScene;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CdScene = itemView.findViewById(R.id.CdScene);
            deviceNameTextView = itemView.findViewById(R.id.Device_name_text_view);
            deviceNameCheckBox = itemView.findViewById(R.id.DN_checkbox);
        }
    }
}