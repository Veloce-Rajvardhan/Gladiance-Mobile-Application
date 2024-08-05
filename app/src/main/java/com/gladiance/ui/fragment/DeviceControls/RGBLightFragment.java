package com.gladiance.ui.fragment.DeviceControls;

import static android.content.Context.MODE_PRIVATE;
import static org.greenrobot.eventbus.EventBus.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gladiance.AppConstants;
import com.gladiance.NetworkApiManager;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.models.RefObject;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.ui.models.SceneViewModel;
import com.gladiance.ui.models.ScheduleViewModel;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.saveSchedule.ObjectScheduleEdit;
import com.gladiance.ui.models.scene.ObjectSceneCreate;
import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.sceneEdit.Configuration;
import com.gladiance.ui.models.scenelist.ObjectSchedule;
import com.gladiance.ui.viewModels.SceneCreateViewModel;
import com.gladiance.ui.viewModels.SceneEditDataViewModel;
import com.gladiance.ui.viewModels.ScheduleEditDataViewModel;
import com.gladiance.ui.viewModels.ScheduleEditViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RGBLightFragment extends Fragment {
    Switch rgbLightSwitch;
    String nodeId;
    NetworkApiManager networkApiManager;
    private EspApplication espApp;
    SeekBar seekBar1,seekBar2,seekBar3,seekBar4,seekBar5;
    TextView textView1,textView2,textView3,textView4,textView5,textView6;
    private View view;
    ProgressBar progressBar;
    private SceneCreateViewModel sharedViewModel;

    private Context context;
    public RGBLightFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_r_g_b_light, container, false);


        espApp = new EspApplication(context.getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);
        progressBar = view.findViewById(R.id.progressBar); // Initialize ProgressBar

        // Load preferences
        SharedPreferences preferences2 = requireActivity().getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", MODE_PRIVATE);
        String Label = preferences.getString("LABEL_NAME", "");
        Log.d(TAG, "Label : " + Label);

        rgbLightSwitch = view.findViewById(R.id.switchButtonFan);
        seekBar1 = view.findViewById(R.id.seekBarDimmer);
        seekBar2 = view.findViewById(R.id.seekBarHue);
        seekBar3 = view.findViewById(R.id.seekBarSaturation);
        seekBar4 = view.findViewById(R.id.seekBarCCT);
        seekBar5 = view.findViewById(R.id.seekBarWhiteBrightness);

        textView1 = view.findViewById(R.id.tv_brightness);
        textView2 = view.findViewById(R.id.tv_hue);
        textView3 = view.findViewById(R.id.tv_saturation);
        textView4 = view.findViewById(R.id.tv_CCT);
        textView5 = view.findViewById(R.id.tv_Whitebrightness);
        textView6 = view.findViewById(R.id.DeviceName);

        textView6.setText(Label);

        disableSeekBars();

        // Dimmer ON/OFF Code
        rgbLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                int progress = 100;
                rgbLightState(isChecked);
                if (isChecked) {
                    enableSeekBars();
//                    rgbBrightness(progress);
//                    rgbSaturation(progress);
                } else {
                    disableSeekBars();
                }
            }
        });

        // Seek Bar Brightness
        seekBar1.setMax(99);
        seekBar1.setProgress(100);
        textView1.setText("100");

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
//                textView1.setText(String.valueOf(progress + 1));
//
//                // Send value to server
//                rgbBrightness(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
                textView1.setText(String.valueOf(seekBar.getProgress() + 1));

                // Send value to server
                rgbBrightness(seekBar.getProgress() + 1);
            }
        });

        // Seek Bar Hue
        int[] colors = {
                0xFFFF0000, // Red
                0xFFFF6F00, // Orange
                0xFFF7FF00, // Yellow
                0xFF00FF00, // Green
                0xFF00FFC4, // Cyan
                0xFF0055FF, // Blue
                0xFFBB00FF, // Purple
                0xFFFF0000  // Red (repeated to loop back)
        };

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gradientDrawable.setCornerRadius(10f);

        seekBar2.setProgressDrawable(gradientDrawable);
        seekBar2.setMax(359);
        seekBar2.setProgress(0);
        textView2.setText("0");

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textView2.setText(String.valueOf(progress + 1));
//                rgbHue(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
                textView2.setText(String.valueOf(seekBar.getProgress() + 1));
                rgbHue(seekBar.getProgress() + 1);
            }
        });

        // Seek Bar Saturation
        seekBar3.setMax(99);
        seekBar3.setProgress(100);
        textView3.setText("100");

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textView3.setText(String.valueOf(progress + 1));
//                rgbSaturation(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
                textView3.setText(String.valueOf(seekBar.getProgress() + 1));
                rgbSaturation(seekBar.getProgress() + 1);
            }
        });

        // Seek Bar CCT
        seekBar4.setMax(6500);
        seekBar4.setProgress(2700);
        textView4.setText("0");

        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textView4.setText(String.valueOf(progress + 1));
//                rgbCCT(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
                textView4.setText(String.valueOf(seekBar.getProgress() + 1));
                rgbCCT(seekBar.getProgress() + 1);
            }
        });

        // Seek White Brightness
        seekBar5.setMax(99);
        seekBar5.setProgress(0);
        textView5.setText("0");

        seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textView5.setText(String.valueOf(progress + 1));
//                rgbWhiteBrightness(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
                textView5.setText(String.valueOf(seekBar.getProgress() + 1));
                rgbWhiteBrightness(seekBar.getProgress() + 1);
            }
        });

        return view;
    }

    //RGB ON/OFF
    private void rgbLightState(boolean powerState) {
        // Create a RequestModel with the required data
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", MODE_PRIVATE);
        String name = preferences.getString("LABEL_NAME", "");
        Log.d(TAG, "Label : " + name);
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
//        String name = sharedPreferences.getString("Name", "");
//        Log.e(ContentValues.TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Power\": "+powerState+"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
            if(AppConstants.Ref_dyn != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = "Power";
            AppConstants.power = String.valueOf(powerState);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);

                        SceneEditDataViewModel sceneViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                        LiveData<List<Configuration>> objectScenesListLiveData1 = sceneViewModel1.getObjectScenesList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEdit = true;
                                    for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : objectScenesList) {

                                        if(Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef) == objectScenes.getgAAProjectSpaceTypePlannedDeviceRef()
                                                && AppConstants.powerState.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEdit = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEdit == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                Configuration objectScenes1 = new Configuration(Long.parseLong(AppConstants.Ref_Scene_Object),Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power);
                                                SceneEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditScenes(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);

                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }

//            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//            // sharedViewModel.setObjecatSchedule(objectScenes);
//            sharedViewModelEdit.addObjectScenes(objectScenes);

            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
            if(AppConstants.Create_Ref_dyn != null) {

//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "Power";
            AppConstants.Create_power = String.valueOf(powerState);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
            Log.d("TAG", "Power2: " + AppConstants.Create_power);

            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn);
            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Create_Name_dyn);
            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.Create_SceneRef);
            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Create_Space_dyn);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);
            Log.e("APPCONSTS2 Object_Ref",""+AppConstants.Create_Ref_Scene);
            Log.e("APPCONSTS2 Object_Ref",""+AppConstants.Create_Ref_Scene);

//            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Ref_dyn_Schedule);
//            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Name_dyn_Schedule);
//            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.ScheduleRef_Schedule);
//            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Space_dyn_Schedule);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.projectSpaceTypePlannedDeviceName_Schedule);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef_Schedule);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);

                    SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);

                    int size = sceneViewModel.getObjectScenesListSize();
                    if (size == 0) {
                        Log.e(TAG, "list is 0");
                        AppConstants.DataCreateScene = true;
                        Log.e(EventBus.TAG, "run: "+AppConstants.Create_Ref_Scene );
                        getRefObjectValue();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                    else{

                        ///////////////////
                        LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
                        objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
                            @Override
                            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataCreateSceneInternal = true;
                                    for (ObjectSceneCreate objectScenes : objectScenesList) {

                                        if (AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef.equals(objectScenes.getProjectSpaceTypePlannedDeviceName())
                                                && AppConstants.Create_powerState.equals(objectScenes.getNodeConfigParamName())) {
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: " + objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Create_power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: " + objectScenes.getValue());
                                            AppConstants.DataCreateSceneInternal = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }

//                                            Log.d("ObjectScenes2", objectScenes.getSceneRef());
//                                            Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
//                                            Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
//                                            Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
//                                            Log.d("getValue", objectScenes.getValue());
                                    }
                                    if (AppConstants.DataCreateSceneInternal == true) {
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                                sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                            }
                                        }, 1000);
                                    }
                                    ////////////////////

                                    progressBar.setVisibility(View.GONE);
                                    objectScenesListLiveData.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });
                    }


            }
            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
            getRefObjectValue();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "Power";
            AppConstants.Create_power_Schedule = String.valueOf(powerState);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState_Schedule);
            Log.d("TAG", "Power2: " + AppConstants.Create_power_Schedule);

            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn_Schedule);
            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Create_Name_dyn_Schedule);
            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.Create_ScheduleRef_Schedule);
            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Create_Space_dyn_Schedule);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState_Schedule);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power_Schedule);
            Log.e("APPCONSTS2 Create_Schedule_Object_Ref",""+AppConstants.Create_Ref_Schedule);


            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule, AppConstants.Create_Ref_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(ContentValues.TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
                    progressBar.setVisibility(View.GONE);

            ////////////
                }
            }, 1000);

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        // Edit Schedule
        try {
            if (AppConstants.Edit_Ref_dyn_Schedule != null) {
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = "Power";
            AppConstants.Edit_power_Schedule = String.valueOf(powerState);
            Log.d("TAG", "PowerState: " + AppConstants.Edit_powerState_Schedule);
            Log.d("TAG", "Power: " + AppConstants.Edit_power_Schedule);

            Log.e("APPCONSTS1"," Edit schedule "+AppConstants.Edit_Ref_dyn_Schedule);
            Log.e("APPCONSTS2"," Edit schedule "+AppConstants.Edit_Name_dyn_Schedule);
            Log.e("APPCONSTS3", " Edit schedule "+AppConstants.Edit_ScheduleRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_Space_dyn_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_powerState_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_power_Schedule);


                        ScheduleEditDataViewModel scheduleViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                        LiveData<List<com.gladiance.ui.models.scheduleEdit.Configuration>> objectScenesListLiveData1 = scheduleViewModel1.getObjectScheduleList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.scheduleEdit.Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//                       for(int i = 0; i <ConArrayList.size(); i++) {
//                           if (ConArrayList.get(i).isChecked() == true) {
//                               Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEditSchedule = true;
                                    for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : objectScenesList) {
                                        Log.e(TAG, "Before Edit NodeConfigParamName1: " + objectScenes.getNodeConfigParamName() + " == " + AppConstants.Edit_powerState_Schedule);
                                        Log.e(TAG, "Before Edit NodeConfigParamName1: " + objectScenes.getGAAProjectSpaceTypePlannedDeviceRef() + " == " + AppConstants.GaaProjectSpaceTypePlannedDeviceRef);

                                        if (Long.parseLong(AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule) == objectScenes.getGAAProjectSpaceTypePlannedDeviceRef() && AppConstants.Edit_powerState_Schedule.equals(objectScenes.getNodeConfigParamName())) {
                                            //   objectScenes.getRef();
                                            Log.e(TAG, "Before Edit NodeConfigParamName1: " + objectScenes.getNodeConfigParamName());
                                            Log.e(TAG, "Before Edit power1: " + objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Edit_power_Schedule);
                                            Log.e(TAG, "After edit NodeConfigParamName: " + objectScenes.getNodeConfigParamName());
                                            Log.e(TAG, "After Edit power: " + objectScenes.getValue());
                                            AppConstants.DataEditSchedule = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        } else {

                                        }

//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if (AppConstants.DataEditSchedule == true) {
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(TAG, "Create new Ref Because the device is not present into Configuration " + AppConstants.Edit_Ref_Schedule);
                                                com.gladiance.ui.models.scheduleEdit.Configuration objectScenes1 = new com.gladiance.ui.models.scheduleEdit.Configuration(AppConstants.Edit_Ref_Schedule, Long.parseLong(AppConstants.Edit_ScheduleRef_Schedule), Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef), AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule, AppConstants.Edit_powerState_Schedule, AppConstants.Edit_power_Schedule);
                                                ScheduleEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditSchedule(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void getRefObjectValueConfigRef() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        SharedPreferences preferences9 = getContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId3 = preferences9.getString("KEY_USERNAMEs", "");
        Log.d(EventBus.TAG, "node id3: " + nodeId3);
        // Make API call
        Call<AllocateSingleIdResponse> call = apiService.allocateSingleId();
        call.enqueue(new Callback<AllocateSingleIdResponse>() {
            @Override
            public void onResponse(Call<AllocateSingleIdResponse> call, Response<AllocateSingleIdResponse> response) {
                if (response.isSuccessful()) {
                    AllocateSingleIdResponse responseModel = response.body();
                    if (responseModel != null) {
                        boolean success = responseModel.getSuccessful();
                        String message = responseModel.getMessage();
                        AppConstants.Ref_Scene_Object = responseModel.getTag();
                        AppConstants.Edit_Ref_Schedule = Long.valueOf(responseModel.getTag());
                        Log.d(EventBus.TAG, "Success2: " + success + ", Message2: " + message+ " Tag2: "+AppConstants.Ref_Scene_Object);

                    }
                } else {
                    Log.e(EventBus.TAG, "API call failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AllocateSingleIdResponse> call, Throwable t) {
                Log.e(EventBus.TAG, "API call failed: " + t.getMessage());
            }
        });
    }

    private void getRefObjectValue() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        //      SharedPreferences preferences9 = getContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        //    String nodeId3 = preferences9.getString("KEY_USERNAMEs", "");
        //      Log.d(EventBus.TAG, "node id3: " + nodeId3);
        // Make API call
        Call<AllocateSingleIdResponse> call = apiService.allocateSingleId();
        call.enqueue(new Callback<AllocateSingleIdResponse>() {
            @Override
            public void onResponse(Call<AllocateSingleIdResponse> call, Response<AllocateSingleIdResponse> response) {
                if (response.isSuccessful()) {
                    AllocateSingleIdResponse responseModel = response.body();
                    if (responseModel != null) {
                        boolean success = responseModel.getSuccessful();
                        String message = responseModel.getMessage();
                        String Ref = responseModel.getTag();

                        RefObject refObject = new RefObject(Ref);
                        AppConstants.Create_Ref_Schedule = responseModel.getTag();
                        AppConstants.Create_Ref_Scene = responseModel.getTag();



                        //     saveRefToSharedPreferences(Ref);

// Later in your code, fetch the RefValue from SharedPreferences
                        //       String savedRef = getRefFromSharedPreferences();
//                        if (savedRef != null) {
//                            Log.d(EventBus.TAG, "Retrieved RefValue from SharedPreferences: " + savedRef);
//                            // Use savedRef as needed
//                        } else {
//                            Log.e(EventBus.TAG, "RefValue not found in SharedPreferences");
//                            // Handle case where RefValue is not found in SharedPreferences
//                        }
                        AppConstants.Create_Ref_Schedule = responseModel.getTag();
                        Log.e(EventBus.TAG, "Create Reffff: "+AppConstants.Create_Ref_Schedule);

                        Log.d(EventBus.TAG, "Success2: " + success + ", Message2: " + message+ " Tag2: "+AppConstants.Create_Ref_Schedule);

                    }
                } else {
                    Log.e(EventBus.TAG, "API call failed with code: " + response.code());
                }
            }

//            private String getRefFromSharedPreferences() {
//                SharedPreferences preferences = getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
//                return preferences.getString("RefValue", null);
//            }
//
//            private void saveRefToSharedPreferences(String refValue) {
//                SharedPreferences preferences = getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("RefValue", refValue);
//                editor.apply(); // Apply changes asynchronously
//                Log.d(EventBus.TAG, "Saved RefValue to SharedPreferences: " + refValue);
//            }

            @Override
            public void onFailure(Call<AllocateSingleIdResponse> call, Throwable t) {
                Log.e(EventBus.TAG, "API call failed: " + t.getMessage());
            }
        });
    }


    private void rgbBrightness(int progress){
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", MODE_PRIVATE);
        String name = preferences.getString("LABEL_NAME", "");
        Log.d(TAG, "Label : " + name);

//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
//        String name = sharedPreferences.getString("Name", "");
//        Log.e(ContentValues.TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"Brightness\": " + progress + "}}";

        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
            if(AppConstants.Ref_dyn != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = "Brightness";
            AppConstants.power = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);

                        SceneEditDataViewModel sceneViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                        LiveData<List<Configuration>> objectScenesListLiveData1 = sceneViewModel1.getObjectScenesList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEdit = true;
                                    for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : objectScenesList) {

                                        if(Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef) == objectScenes.getgAAProjectSpaceTypePlannedDeviceRef()
                                                && AppConstants.powerState.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEdit = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEdit == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                Configuration objectScenes1 = new Configuration(Long.parseLong(AppConstants.Ref_Scene_Object),Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power);
                                                SceneEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditScenes(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }
//            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//            // sharedViewModel.setObjecatSchedule(objectScenes);
//            sharedViewModelEdit.addObjectScenes(objectScenes);
//
//            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
            if(AppConstants.Create_Ref_dyn != null) {

//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "Brightness";
            AppConstants.Create_power = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
            Log.d("TAG", "Power2: " + AppConstants.Create_power);

            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn);
            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Create_Name_dyn);
            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.Create_SceneRef);
            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Create_Space_dyn);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);

                    SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);

                    int size = sceneViewModel.getObjectScenesListSize();
                    if (size == 0) {
                        Log.e(TAG, "list is 0");
                        AppConstants.DataCreateScene = true;
                        Log.e(EventBus.TAG, "run: " + AppConstants.Create_Ref_Scene);
                        getRefObjectValue();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                    else{

                        ///////////////////
                        LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
                        objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
                            @Override
                            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataCreateSceneInternal = true;
                                    for (ObjectSceneCreate objectScenes : objectScenesList) {

                                        if (AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef.equals(objectScenes.getProjectSpaceTypePlannedDeviceName())
                                                && AppConstants.Create_powerState.equals(objectScenes.getNodeConfigParamName())) {
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: " + objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Create_power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: " + objectScenes.getValue());
                                            AppConstants.DataCreateSceneInternal = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
//                                            Log.d("ObjectScenes2", objectScenes.getSceneRef());
//                                            Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
//                                            Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
//                                            Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
//                                            Log.d("getValue", objectScenes.getValue());
                                    }
                                    if (AppConstants.DataCreateSceneInternal == true) {
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                                sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                            }
                                        }, 1000);
                                    }
                                    ////////////////////

                                    progressBar.setVisibility(View.GONE);
                                    objectScenesListLiveData.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });
                    }


            }
            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
            getRefObjectValue();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "Brightness";
            AppConstants.Create_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState_Schedule);
            Log.d("TAG", "Power2: " + AppConstants.Create_power_Schedule);

            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn_Schedule);
            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Create_Name_dyn_Schedule);
            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.Create_ScheduleRef_Schedule);
            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Create_Space_dyn_Schedule);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState_Schedule);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power_Schedule);

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule, AppConstants.Create_Ref_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(ContentValues.TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
//            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
//
//            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
//            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());
                    progressBar.setVisibility(View.GONE);

            ////////////
                }
            }, 1000);

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        // Edit Schedule
        try {
            if(AppConstants.Edit_Ref_dyn_Schedule != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = "Brightness";
            AppConstants.Edit_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.Edit_powerState_Schedule);
            Log.d("TAG", "Power: " + AppConstants.Edit_power_Schedule);

            Log.e("APPCONSTS1"," Edit schedule "+AppConstants.Edit_Ref_dyn_Schedule);
            Log.e("APPCONSTS2"," Edit schedule "+AppConstants.Edit_Name_dyn_Schedule);
            Log.e("APPCONSTS3", " Edit schedule "+AppConstants.Edit_ScheduleRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_Space_dyn_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_powerState_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_power_Schedule);


                        ScheduleEditDataViewModel scheduleViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                        LiveData<List<com.gladiance.ui.models.scheduleEdit.Configuration>> objectScenesListLiveData1 = scheduleViewModel1.getObjectScheduleList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.scheduleEdit.Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//                       for(int i = 0; i <ConArrayList.size(); i++) {
//                           if (ConArrayList.get(i).isChecked() == true) {
//                               Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEditSchedule = true;
                                    for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : objectScenesList) {
                                        if(Long.parseLong(AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule) == objectScenes.getGAAProjectSpaceTypePlannedDeviceRef()
                                                && AppConstants.Edit_powerState_Schedule.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before Edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Edit_power_Schedule);
                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEditSchedule = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEditSchedule == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                com.gladiance.ui.models.scheduleEdit.Configuration objectScenes1 = new com.gladiance.ui.models.scheduleEdit.Configuration(AppConstants.Edit_Ref_Schedule,Long.parseLong(AppConstants.Edit_ScheduleRef_Schedule),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
                                                ScheduleEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditSchedule(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }



        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbHue(int progress){
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", MODE_PRIVATE);
        String name = preferences.getString("LABEL_NAME", "");
        Log.d(TAG, "Label : " + name);
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
//        String name = sharedPreferences.getString("Name", "");
//        Log.e(ContentValues.TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Hue\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
            if(AppConstants.Ref_dyn != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = "Hue";
            AppConstants.power = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);

                        SceneEditDataViewModel sceneViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                        LiveData<List<Configuration>> objectScenesListLiveData1 = sceneViewModel1.getObjectScenesList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEdit = true;
                                    for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : objectScenesList) {

                                        if(Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef) == objectScenes.getgAAProjectSpaceTypePlannedDeviceRef()
                                                && AppConstants.powerState.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEdit = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEdit == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                Configuration objectScenes1 = new Configuration(Long.parseLong(AppConstants.Ref_Scene_Object),Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power);
                                                SceneEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditScenes(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }
//            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//            // sharedViewModel.setObjecatSchedule(objectScenes);
//            sharedViewModelEdit.addObjectScenes(objectScenes);
//
//            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
            if(AppConstants.Create_Ref_dyn != null) {

            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "Hue";
            AppConstants.Create_power = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
            Log.d("TAG", "Power2: " + AppConstants.Create_power);

            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);
                    SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);

                    int size = sceneViewModel.getObjectScenesListSize();
                    if (size == 0) {
                        Log.e(TAG, "list is 0");
                        AppConstants.DataCreateScene = true;
                        Log.e(EventBus.TAG, "run: "+AppConstants.Create_Ref_Scene );
                        getRefObjectValue();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                    else{

                        ///////////////////
                        LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
                        objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
                            @Override
                            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataCreateSceneInternal = true;
                                    for (ObjectSceneCreate objectScenes : objectScenesList) {

                                        if (AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef.equals(objectScenes.getProjectSpaceTypePlannedDeviceName())
                                                && AppConstants.Create_powerState.equals(objectScenes.getNodeConfigParamName())) {
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: " + objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Create_power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: " + objectScenes.getValue());
                                            AppConstants.DataCreateSceneInternal = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }

//                                            Log.d("ObjectScenes2", objectScenes.getSceneRef());
//                                            Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
//                                            Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
//                                            Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
//                                            Log.d("getValue", objectScenes.getValue());
                                    }
                                    if (AppConstants.DataCreateSceneInternal == true) {
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                                sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                            }
                                        }, 1000);
                                    }
                                    ////////////////////

                                    progressBar.setVisibility(View.GONE);
                                    objectScenesListLiveData.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });
                    }

        }
        ////////////


    }
        catch (Exception e){
        Log.e(ContentValues.TAG, "sendSwitchState: "+e);
    }


        //// Create Schedule
        try {
            getRefObjectValue();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "Hue";
            AppConstants.Create_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState_Schedule);
            Log.d("TAG", "Power2: " + AppConstants.Create_power_Schedule);

//            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
//            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
//            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
//            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);


            Log.e("APPCONSTS2 Create Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn_Schedule);
            Log.e("APPCONSTS2 Create Name_dyn_Schedule",""+AppConstants.Create_Name_dyn_Schedule);
            Log.e("APPCONSTS2 Create Space_dyn_Schedule",""+AppConstants.Create_Space_dyn_Schedule);
            Log.e("APPCONSTS2 Create SceneRef_Schedule",""+AppConstants.Create_ScheduleRef_Schedule);
            Log.e("APPCONSTS2 Create GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS2 Create projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS2 Create powerState_Schedule",""+AppConstants.Create_powerState_Schedule);
            Log.e("APPCONSTS2 Create power_Schedule",""+AppConstants.Create_power_Schedule);
            Log.e("APPCONSTS2 Object_Ref", "" + AppConstants.Create_Ref_Schedule);

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule, AppConstants.Create_Ref_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(ContentValues.TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());
                    progressBar.setVisibility(View.GONE);

            ////////////
                }
            }, 1000);

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {
            if(AppConstants.Edit_Ref_dyn_Schedule != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = "Hue";
            AppConstants.Edit_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.Edit_powerState_Schedule);
            Log.d("TAG", "Power: " + AppConstants.Edit_power_Schedule);

            Log.e("APPCONSTS1"," Edit schedule "+AppConstants.Edit_Ref_dyn_Schedule);
            Log.e("APPCONSTS2"," Edit schedule "+AppConstants.Edit_Name_dyn_Schedule);
            Log.e("APPCONSTS3", " Edit schedule "+AppConstants.Edit_ScheduleRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_Space_dyn_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_powerState_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_power_Schedule);

            ScheduleEditDataViewModel scheduleViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
            LiveData<List<com.gladiance.ui.models.scheduleEdit.Configuration>> objectScenesListLiveData1 = scheduleViewModel1.getObjectScheduleList();
            objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.scheduleEdit.Configuration>>() {
                @Override
                public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//                       for(int i = 0; i <ConArrayList.size(); i++) {
//                           if (ConArrayList.get(i).isChecked() == true) {
//                               Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                    if (objectScenesList != null) {
                        AppConstants.DataEditSchedule = true;
                        for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : objectScenesList) {
                            if(Long.parseLong(AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule) == objectScenes.getGAAProjectSpaceTypePlannedDeviceRef()
                                    && AppConstants.Edit_powerState_Schedule.equals(objectScenes.getNodeConfigParamName())){
                                objectScenes.getRef();
                                Log.e(ContentValues.TAG, "Before Edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                objectScenes.setValue(AppConstants.Edit_power_Schedule);
                                Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                AppConstants.DataEditSchedule = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                            }
                            else{

                            }


//
                            Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                        }
                        if(AppConstants.DataEditSchedule == true){
                            getRefObjectValueConfigRef();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                    com.gladiance.ui.models.scheduleEdit.Configuration objectScenes1 = new com.gladiance.ui.models.scheduleEdit.Configuration(AppConstants.Edit_Ref_Schedule,Long.parseLong(AppConstants.Edit_ScheduleRef_Schedule),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
                                    ScheduleEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                                    // sharedViewModel.setObjectSchedule(objectScenes);
                                    sharedViewModel1.addObjectEditSchedule(objectScenes1);
                                }
                            }, 1000);
                        }

                        progressBar.setVisibility(View.GONE);


                        // Remember to remove the observer if necessary
                        objectScenesListLiveData1.removeObserver(this);
                    }
                    //   }
                    //}
                }


            });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////
        }
    }, 1000);
}

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbSaturation(int progress){
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", MODE_PRIVATE);
        String name = preferences.getString("LABEL_NAME", "");
        Log.d(TAG, "Label : " + name);
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
//        String name = sharedPreferences.getString("Name", "");
//        Log.e(ContentValues.TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Saturation\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
            if(AppConstants.Ref_dyn != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = "Saturation";
            AppConstants.power = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);


//            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//            // sharedViewModel.setObjecatSchedule(objectScenes);
//            sharedViewModelEdit.addObjectScenes(objectScenes);
//
//            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());
                        SceneEditDataViewModel sceneViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                        LiveData<List<Configuration>> objectScenesListLiveData1 = sceneViewModel1.getObjectScenesList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEdit = true;
                                    for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : objectScenesList) {

                                        if(Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef) == objectScenes.getgAAProjectSpaceTypePlannedDeviceRef()
                                                        && AppConstants.powerState.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEdit = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEdit == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                Configuration objectScenes1 = new Configuration(Long.parseLong(AppConstants.Ref_Scene_Object),Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power);
                                                SceneEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditScenes(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }
            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
            if(AppConstants.Create_Ref_dyn != null) {

            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "Saturation";
            AppConstants.Create_power = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
            Log.d("TAG", "Power2: " + AppConstants.Create_power);

            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);
            Log.e("APPCONSTS2 Object Ref", "" +   AppConstants.Create_Ref_Scene);


                    SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);

                    int size = sceneViewModel.getObjectScenesListSize();
                    if (size == 0) {
                        Log.e(TAG, "list is 0");
                        AppConstants.DataCreateScene = true;
                        Log.e(EventBus.TAG, "run: "+AppConstants.Create_Ref_Scene );
                        getRefObjectValue();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                    else{

                        ///////////////////
                        LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
                        objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
                            @Override
                            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataCreateSceneInternal = true;
                                    for (ObjectSceneCreate objectScenes : objectScenesList) {

                                        if (AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef.equals(objectScenes.getProjectSpaceTypePlannedDeviceName())
                                                && AppConstants.Create_powerState.equals(objectScenes.getNodeConfigParamName())) {
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: " + objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Create_power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: " + objectScenes.getValue());
                                            AppConstants.DataCreateSceneInternal = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        } else {

                                        }

//                                            Log.d("ObjectScenes2", objectScenes.getSceneRef());
//                                            Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
//                                            Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
//                                            Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
//                                            Log.d("getValue", objectScenes.getValue());
                                    }
                                    if (AppConstants.DataCreateSceneInternal == true) {
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                                sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                            }
                                        }, 1000);
                                    }
                                    ////////////////////

                                    progressBar.setVisibility(View.GONE);
                                    objectScenesListLiveData.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });
                    }


            }
            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }



        //// Create Schedule
        try {
            getRefObjectValue();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "Saturation";
            AppConstants.Create_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState_Schedule);
            Log.d("TAG", "Power2: " + AppConstants.Create_power_Schedule);

//            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
//            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
//            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
//            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);


            Log.e("APPCONSTS2 Create Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn_Schedule);
            Log.e("APPCONSTS2 Create Name_dyn_Schedule",""+AppConstants.Create_Name_dyn_Schedule);
            Log.e("APPCONSTS2 Create Space_dyn_Schedule",""+AppConstants.Create_Space_dyn_Schedule);
            Log.e("APPCONSTS2 Create SceneRef_Schedule",""+AppConstants.Create_ScheduleRef_Schedule);
            Log.e("APPCONSTS2 Create GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS2 Create projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS2 Create powerState_Schedule",""+AppConstants.Create_powerState_Schedule);
            Log.e("APPCONSTS2 Create power_Schedule",""+AppConstants.Create_power_Schedule);
            Log.e("APPCONSTS2 Object_Ref", "" + AppConstants.Create_Ref_Schedule);

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule, AppConstants.Create_Ref_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(ContentValues.TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());
                    progressBar.setVisibility(View.GONE);

            ////////////
                }
            }, 1000);


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {
            if(AppConstants.Edit_Ref_dyn_Schedule != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = "Saturation";
            AppConstants.Edit_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.Edit_powerState_Schedule);
            Log.d("TAG", "Power: " + AppConstants.Edit_power_Schedule);

            Log.e("APPCONSTS1"," Edit schedule "+AppConstants.Edit_Ref_dyn_Schedule);
            Log.e("APPCONSTS2"," Edit schedule "+AppConstants.Edit_Name_dyn_Schedule);
            Log.e("APPCONSTS3", " Edit schedule "+AppConstants.Edit_ScheduleRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_Space_dyn_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_powerState_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_power_Schedule);


                        ScheduleEditDataViewModel scheduleViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                        LiveData<List<com.gladiance.ui.models.scheduleEdit.Configuration>> objectScenesListLiveData1 = scheduleViewModel1.getObjectScheduleList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.scheduleEdit.Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//                       for(int i = 0; i <ConArrayList.size(); i++) {
//                           if (ConArrayList.get(i).isChecked() == true) {
//                               Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEditSchedule = true;
                                    for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : objectScenesList) {
                                        if(Long.parseLong(AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule) == objectScenes.getGAAProjectSpaceTypePlannedDeviceRef()
                                                && AppConstants.Edit_powerState_Schedule.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before Edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Edit_power_Schedule);
                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEditSchedule = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEditSchedule == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                com.gladiance.ui.models.scheduleEdit.Configuration objectScenes1 = new com.gladiance.ui.models.scheduleEdit.Configuration(AppConstants.Edit_Ref_Schedule,Long.parseLong(AppConstants.Edit_ScheduleRef_Schedule),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
                                                ScheduleEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditSchedule(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }



        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbCCT(int progress){
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", MODE_PRIVATE);
        String name = preferences.getString("LABEL_NAME", "");
        Log.d(TAG, "Label : " + name);
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
//        String name = sharedPreferences.getString("Name", "");
//        Log.e(ContentValues.TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"CCT\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
            if(AppConstants.Ref_dyn != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = "CCT";
            AppConstants.power = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);

            SceneEditDataViewModel sceneViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
            LiveData<List<Configuration>> objectScenesListLiveData1 = sceneViewModel1.getObjectScenesList();
            objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<Configuration>>() {
                @Override
                public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {
//                       for(int i = 0; i <ConArrayList.size(); i++) {
//                           if (ConArrayList.get(i).isChecked() == true) {
//                               Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                    if (objectScenesList != null) {
                        AppConstants.DataEdit = true;
                        for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : objectScenesList) {
                                        if(Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef) == objectScenes.getgAAProjectSpaceTypePlannedDeviceRef()
                                                && AppConstants.powerState.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEdit = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEdit == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                Configuration objectScenes1 = new Configuration(Long.parseLong(AppConstants.Ref_Scene_Object),Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power);
                                                SceneEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditScenes(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }
//            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//            // sharedViewModel.setObjecatSchedule(objectScenes);
//            sharedViewModelEdit.addObjectScenes(objectScenes);
//
//            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
            if(AppConstants.Create_Ref_dyn != null) {

            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "CCT";
            AppConstants.Create_power = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
            Log.d("TAG", "Power2: " + AppConstants.Create_power);

            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);
            Log.e("APPCONSTS2 Object Ref", "" +   AppConstants.Create_Ref_Scene);

                    SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);

                    int size = sceneViewModel.getObjectScenesListSize();
                    if (size == 0) {
                        Log.e(TAG, "list is 0");
                        AppConstants.DataCreateScene = true;
                        Log.e(EventBus.TAG, "run: "+AppConstants.Create_Ref_Scene );
                        getRefObjectValue();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                    else{

                        ///////////////////
                        LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
                        objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
                            @Override
                            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataCreateSceneInternal = true;
                                    for (ObjectSceneCreate objectScenes : objectScenesList) {

                                        if (AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef.equals(objectScenes.getProjectSpaceTypePlannedDeviceName())
                                                && AppConstants.Create_powerState.equals(objectScenes.getNodeConfigParamName())) {
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: " + objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Create_power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: " + objectScenes.getValue());
                                            AppConstants.DataCreateSceneInternal = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }

//                                            Log.d("ObjectScenes2", objectScenes.getSceneRef());
//                                            Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
//                                            Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
//                                            Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
//                                            Log.d("getValue", objectScenes.getValue());
                                    }
                                    if (AppConstants.DataCreateSceneInternal == true) {
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                                sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                            }
                                        }, 1000);
                                    }
                                    ////////////////////

                                    progressBar.setVisibility(View.GONE);
                                    objectScenesListLiveData.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });
                    }

        }
        ////////////


    }
        catch (Exception e){
        Log.e(ContentValues.TAG, "sendSwitchState: "+e);
    }



        //// Create Schedule
        try {
            getRefObjectValue();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "CCT";
            AppConstants.Create_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState_Schedule);
            Log.d("TAG", "Power2: " + AppConstants.Create_power_Schedule);

//            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
//            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
//            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
//            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);


            Log.e("APPCONSTS2 Create Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn_Schedule);
            Log.e("APPCONSTS2 Create Name_dyn_Schedule",""+AppConstants.Create_Name_dyn_Schedule);
            Log.e("APPCONSTS2 Create Space_dyn_Schedule",""+AppConstants.Create_Space_dyn_Schedule);
            Log.e("APPCONSTS2 Create SceneRef_Schedule",""+AppConstants.Create_ScheduleRef_Schedule);
            Log.e("APPCONSTS2 Create GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS2 Create projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS2 Create powerState_Schedule",""+AppConstants.Create_powerState_Schedule);
            Log.e("APPCONSTS2 Create power_Schedule",""+AppConstants.Create_power_Schedule);
            Log.e("APPCONSTS2 Object_Ref", "" + AppConstants.Create_Ref_Schedule);

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule, AppConstants.Create_Ref_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(ContentValues.TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
                    progressBar.setVisibility(View.GONE);

                    //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());
                }
            }, 1000);
            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {
            if(AppConstants.Edit_Ref_dyn_Schedule != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = "CCT";
            AppConstants.Edit_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.Edit_powerState_Schedule);
            Log.d("TAG", "Power: " + AppConstants.Edit_power_Schedule);

            Log.e("APPCONSTS1"," Edit schedule "+AppConstants.Edit_Ref_dyn_Schedule);
            Log.e("APPCONSTS2"," Edit schedule "+AppConstants.Edit_Name_dyn_Schedule);
            Log.e("APPCONSTS3", " Edit schedule "+AppConstants.Edit_ScheduleRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_Space_dyn_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_powerState_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_power_Schedule);

            ScheduleEditDataViewModel scheduleViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
            LiveData<List<com.gladiance.ui.models.scheduleEdit.Configuration>> objectScenesListLiveData1 = scheduleViewModel1.getObjectScheduleList();
            objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.scheduleEdit.Configuration>>() {
                @Override
                public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//                       for(int i = 0; i <ConArrayList.size(); i++) {
//                           if (ConArrayList.get(i).isChecked() == true) {
//                               Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                    if (objectScenesList != null) {
                        AppConstants.DataEditSchedule = true;
                        for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : objectScenesList) {
                            if(Long.parseLong(AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule) == objectScenes.getGAAProjectSpaceTypePlannedDeviceRef()
                                    && AppConstants.Edit_powerState_Schedule.equals(objectScenes.getNodeConfigParamName())){
                                objectScenes.getRef();
                                Log.e(ContentValues.TAG, "Before Edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                objectScenes.setValue(AppConstants.Edit_power_Schedule);
                                Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                AppConstants.DataEditSchedule = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                            }
                            else{

                            }


//
                            Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                        }
                        if(AppConstants.DataEditSchedule == true){
                            getRefObjectValueConfigRef();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                    com.gladiance.ui.models.scheduleEdit.Configuration objectScenes1 = new com.gladiance.ui.models.scheduleEdit.Configuration(AppConstants.Edit_Ref_Schedule,Long.parseLong(AppConstants.Edit_ScheduleRef_Schedule),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
                                    ScheduleEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                                    // sharedViewModel.setObjectSchedule(objectScenes);
                                    sharedViewModel1.addObjectEditSchedule(objectScenes1);
                                }
                            }, 1000);
                        }

                        progressBar.setVisibility(View.GONE);


                        // Remember to remove the observer if necessary
                        objectScenesListLiveData1.removeObserver(this);
                    }
                    //   }
                    //}
                }


            });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////
        }
    }, 1000);
}

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }
        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbWhiteBrightness(int progress){
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", MODE_PRIVATE);
        String name = preferences.getString("LABEL_NAME", "");
        Log.d(TAG, "Label : " + name);
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
//        String name = sharedPreferences.getString("Name", "");
//        Log.e(ContentValues.TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"White Brightness\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
            if(AppConstants.Ref_dyn != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = "White Brightness";
            AppConstants.power = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);


//            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//            // sharedViewModel.setObjecatSchedule(objectScenes);
//            sharedViewModelEdit.addObjectScenes(objectScenes);
//
//            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());
                        SceneEditDataViewModel sceneViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                        LiveData<List<Configuration>> objectScenesListLiveData1 = sceneViewModel1.getObjectScenesList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEdit = true;
                                    for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : objectScenesList) {

                                        if(Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef) == objectScenes.getgAAProjectSpaceTypePlannedDeviceRef()
                                                && AppConstants.powerState.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEdit = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEdit == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                Configuration objectScenes1 = new Configuration(Long.parseLong(AppConstants.Ref_Scene_Object),Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power);
                                                SceneEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditScenes(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }
            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
            if(AppConstants.Create_Ref_dyn != null) {

            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "White Brightness";
            AppConstants.Create_power = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
            Log.d("TAG", "Power2: " + AppConstants.Create_power);

            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);
            Log.e("APPCONSTS2 Object Ref", "" +   AppConstants.Create_Ref_Scene);


                    SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);

                    int size = sceneViewModel.getObjectScenesListSize();
                    if (size == 0) {
                        Log.e(TAG, "list is 0");
                        AppConstants.DataCreateScene = true;
                        Log.e(EventBus.TAG, "run: "+AppConstants.Create_Ref_Scene );
                        getRefObjectValue();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                    else{

                        ///////////////////
                        LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
                        objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
                            @Override
                            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataCreateSceneInternal = true;
                                    for (ObjectSceneCreate objectScenes : objectScenesList) {

                                        if (AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef.equals(objectScenes.getProjectSpaceTypePlannedDeviceName())
                                                && AppConstants.Create_powerState.equals(objectScenes.getNodeConfigParamName())) {
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before edit NodeConfigParamName1: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: " + objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Create_power);

                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: " + objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: " + objectScenes.getValue());
                                            AppConstants.DataCreateSceneInternal = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }

//                                            Log.d("ObjectScenes2", objectScenes.getSceneRef());
//                                            Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
//                                            Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
//                                            Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
//                                            Log.d("getValue", objectScenes.getValue());
                                    }
                                    if (AppConstants.DataCreateSceneInternal == true) {
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn, AppConstants.Create_Name_dyn, AppConstants.Create_SceneRef, AppConstants.Create_Space_dyn, AppConstants.Create_projectSpaceTypePlannedDeviceName, AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef, AppConstants.Create_powerState, AppConstants.Create_power, AppConstants.Create_Ref_Scene);
                                                sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                                sharedViewModel.addObjectScenes(objectSceneCreate);
                                            }
                                        }, 1000);
                                    }
                                    ////////////////////

                                    progressBar.setVisibility(View.GONE);
                                    objectScenesListLiveData.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });
                    }


            }
            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
            getRefObjectValue();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "White Brightness";
            AppConstants.Create_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState_Schedule);
            Log.d("TAG", "Power2: " + AppConstants.Create_power_Schedule);

//            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
//            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
//            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
//            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);


            Log.e("APPCONSTS2 Create Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn_Schedule);
            Log.e("APPCONSTS2 Create Name_dyn_Schedule",""+AppConstants.Create_Name_dyn_Schedule);
            Log.e("APPCONSTS2 Create Space_dyn_Schedule",""+AppConstants.Create_Space_dyn_Schedule);
            Log.e("APPCONSTS2 Create SceneRef_Schedule",""+AppConstants.Create_ScheduleRef_Schedule);
            Log.e("APPCONSTS2 Create GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS2 Create projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS2 Create powerState_Schedule",""+AppConstants.Create_powerState_Schedule);
            Log.e("APPCONSTS2 Create power_Schedule",""+AppConstants.Create_power_Schedule);
            Log.e("APPCONSTS2 Object_Ref", "" + AppConstants.Create_Ref_Schedule);

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule, AppConstants.Create_Ref_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(ContentValues.TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
                    progressBar.setVisibility(View.GONE);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());
                }
            }, 1000);
            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {
            if(AppConstants.Edit_Ref_dyn_Schedule != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
            AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = "White Brightness";
            AppConstants.Edit_power_Schedule = String.valueOf(progress);
            Log.d("TAG", "PowerState: " + AppConstants.Edit_powerState_Schedule);
            Log.d("TAG", "Power: " + AppConstants.Edit_power_Schedule);

            Log.e("APPCONSTS1"," Edit schedule "+AppConstants.Edit_Ref_dyn_Schedule);
            Log.e("APPCONSTS2"," Edit schedule "+AppConstants.Edit_Name_dyn_Schedule);
            Log.e("APPCONSTS3", " Edit schedule "+AppConstants.Edit_ScheduleRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_Space_dyn_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_powerState_Schedule);
            Log.e("APPCONSTS"," Edit schedule "+AppConstants.Edit_power_Schedule);

                        ScheduleEditDataViewModel scheduleViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                        LiveData<List<com.gladiance.ui.models.scheduleEdit.Configuration>> objectScenesListLiveData1 = scheduleViewModel1.getObjectScheduleList();
                        objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.scheduleEdit.Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//                       for(int i = 0; i <ConArrayList.size(); i++) {
//                           if (ConArrayList.get(i).isChecked() == true) {
//                               Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                                if (objectScenesList != null) {
                                    AppConstants.DataEditSchedule = true;
                                    for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : objectScenesList) {
                                        if(Long.parseLong(AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule) == objectScenes.getGAAProjectSpaceTypePlannedDeviceRef()
                                                && AppConstants.Edit_powerState_Schedule.equals(objectScenes.getNodeConfigParamName())){
                                            objectScenes.getRef();
                                            Log.e(ContentValues.TAG, "Before Edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "Before Edit power1: "+ objectScenes.getValue());
                                            // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                            // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                            // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                            objectScenes.setValue(AppConstants.Edit_power_Schedule);
                                            Log.e(ContentValues.TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                            Log.e(ContentValues.TAG, "After Edit power: "+ objectScenes.getValue());
                                            AppConstants.DataEditSchedule = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                        }
                                        else{

                                        }


//
                                        Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                    }
                                    if(AppConstants.DataEditSchedule == true){
                                        getRefObjectValueConfigRef();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e(ContentValues.TAG, "Create new Ref Because the device is not present into Configuration");
                                                com.gladiance.ui.models.scheduleEdit.Configuration objectScenes1 = new com.gladiance.ui.models.scheduleEdit.Configuration(AppConstants.Edit_Ref_Schedule,Long.parseLong(AppConstants.Edit_ScheduleRef_Schedule),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
                                                ScheduleEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                                                // sharedViewModel.setObjectSchedule(objectScenes);
                                                sharedViewModel1.addObjectEditSchedule(objectScenes1);
                                            }
                                        }, 1000);
                                    }

                                    progressBar.setVisibility(View.GONE);


                                    // Remember to remove the observer if necessary
                                    objectScenesListLiveData1.removeObserver(this);
                                }
                                //   }
                                //}
                            }


                        });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                        //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                        ////////////
                    }
                }, 1000);
            }


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void disableSeekBars() {
        seekBar1.setEnabled(false);
        seekBar2.setEnabled(false);
        seekBar3.setEnabled(false);
        seekBar4.setEnabled(false);
        seekBar5.setEnabled(false);
    }

    private void enableSeekBars() {
        seekBar1.setEnabled(true);
        seekBar2.setEnabled(true);
        seekBar3.setEnabled(true);
        seekBar4.setEnabled(true);
        seekBar5.setEnabled(true);
    }

}