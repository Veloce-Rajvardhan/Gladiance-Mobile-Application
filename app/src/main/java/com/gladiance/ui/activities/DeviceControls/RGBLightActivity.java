package com.gladiance.ui.activities.DeviceControls;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
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
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.ui.models.SceneViewModel;
import com.gladiance.ui.models.ScheduleViewModel;
import com.gladiance.ui.models.saveSchedule.ObjectScheduleEdit;
import com.gladiance.ui.models.scene.ObjectSceneCreate;
import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.scenelist.ObjectSchedule;
import com.gladiance.ui.viewModels.SceneCreateViewModel;
import com.gladiance.ui.viewModels.ScheduleEditViewModel;

public class RGBLightActivity extends AppCompatActivity {

    Switch rgbLightSwitch;
    String nodeId;
    NetworkApiManager networkApiManager;
    private EspApplication espApp;
    Context context = this;
    SeekBar seekBar1,seekBar2,seekBar3,seekBar4,seekBar5;


    TextView textView1,textView2,textView3,textView4,textView5,textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgblight);

        espApp = new EspApplication(getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);
        SharedPreferences preferences2 = getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        SharedPreferences preferences = getSharedPreferences("my_shared_prefe_label", MODE_PRIVATE);
        String Label = preferences.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "Label : " +Label);


        rgbLightSwitch = findViewById(R.id.switchButtonFan);
        seekBar1 = findViewById(R.id.seekBarDimmer);
        seekBar2 = findViewById(R.id.seekBarHue);
        seekBar3 = findViewById(R.id.seekBarSaturation);
        seekBar4 = findViewById(R.id.seekBarCCT);
        seekBar5 = findViewById(R.id.seekBarWhiteBrightness);


        textView1 = findViewById(R.id.tv_brightness);
        textView2= findViewById(R.id.tv_hue);
        textView3 = findViewById(R.id.tv_saturation);
        textView4 = findViewById(R.id.tv_CCT);
        textView5 = findViewById(R.id.tv_Whitebrightness);
        textView6 = findViewById(R.id.DeviceName);

        textView6.setText(Label);

        disableSeekBars();

        //Dimmer ON/OFF Code
        rgbLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: "+isChecked);
                int progress = 100;
                rgbLightState(isChecked);
                if (isChecked) {
                    enableSeekBars();
                    rgbBrightness(progress);
                    rgbSaturation(progress);
                } else {
                    disableSeekBars();
                }


            }
        });


        //Seek Bar Brightness
        seekBar1.setMax(99);
        seekBar1.setProgress(100);
        textView1.setText("100");

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView1.setText(String.valueOf(progress + 1));

                // Send value to server
                rgbBrightness(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });


  //    Seek Bar Hue
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

        // Create the gradient drawable
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gradientDrawable.setCornerRadius(10f); // Set corner radius if needed

        // Set the ClipDrawable as the progress drawable for the SeekBar
        seekBar2.setProgressDrawable(gradientDrawable);

        seekBar2.setMax(360);
        seekBar2.setProgress(0);
        textView2.setText("0");

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView2.setText(String.valueOf(progress + 1));
                rgbHue(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });




        //Seek Bar Saturation
        seekBar3.setMax(99);
        seekBar3.setProgress(100);
        textView3.setText("100");

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView3.setText(String.valueOf(progress + 1));

                // Send value to server
                rgbSaturation(progress + 1);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });

        //Seek Bar CCT
        seekBar4.setMax(6500);
        seekBar4.setProgress(2700);
        textView4.setText("0");

        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView4.setText(String.valueOf(progress + 1));

                // Send value to server
                rgbCCT(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });

        //Seek White Brightness
        seekBar5.setMax(99);
        seekBar5.setProgress(0);
        textView5.setText("0");

        seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView5.setText(String.valueOf(progress + 1));

                // Send value to server
                rgbWhiteBrightness(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });


    }

    //RGB ON/OFF
    private void rgbLightState(boolean powerState) {
        // Create a RequestModel with the required data
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Power\": "+powerState+"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
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

//            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Ref_dyn_Schedule);
//            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Name_dyn_Schedule);
//            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.ScheduleRef_Schedule);
//            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Space_dyn_Schedule);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.projectSpaceTypePlannedDeviceName_Schedule);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef_Schedule);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);

            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider((this)).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
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


            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider((this)).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Edit Schedule
        try {

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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);


            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbBrightness(int progress){

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"Brightness\": " + progress + "}}";

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());

        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
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

            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider((this)).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);
//            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Ref_dyn_Schedule);
//            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Name_dyn_Schedule);
//            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.ScheduleRef_Schedule);
//            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Space_dyn_Schedule);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.projectSpaceTypePlannedDeviceName_Schedule);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef_Schedule);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);

//            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
//            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
//            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
//            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
//            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
//            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
//            Log.e("APPCONSTS",""+AppConstants.powerState);
//            Log.e("APPCONSTS",""+AppConstants.power);
//
//
//            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//
//            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
//            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.Create_SceneRef),Long.parseLong(AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef),AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_powerState,AppConstants.Create_power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
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

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider((this)).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
//            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
//
//            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
//            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Edit Schedule
        try {

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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
            //  sharedViewModel.addObjectScenes(objectScenes);


            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }



        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbHue(int progress){

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Hue\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
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


            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider((this)).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
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

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider((this)).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {

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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
            //  sharedViewModel.addObjectScenes(objectScenes);

          //  Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbSaturation(int progress){

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Saturation\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
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


            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider((this)).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
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

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider((this)).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {

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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
            //  sharedViewModel.addObjectScenes(objectScenes);

            //  Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbCCT(int progress){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"CCT\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
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


            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider((this)).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
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

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider((this)).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {

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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
            //  sharedViewModel.addObjectScenes(objectScenes);

          //  Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }
        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbWhiteBrightness(int progress){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"White Brightness\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        // Edit Scene
        try {
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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
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


            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider((this)).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
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

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider((this)).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
            // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {

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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider((this)).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
            //  sharedViewModel.addObjectScenes(objectScenes);

            //  Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
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

    // Function to enable all SeekBars
    private void enableSeekBars() {
        seekBar1.setEnabled(true);
        seekBar2.setEnabled(true);
        seekBar3.setEnabled(true);
        seekBar4.setEnabled(true);
        seekBar5.setEnabled(true);
    }



    private void handleApiResponse(ResponseModel responseModel) {
        // Handle the response as needed
        if (responseModel != null) {
            // API call was successful
            // Access other fields from responseModel if needed
            Log.d(TAG, "handleApiResponse: " +responseModel.getSuccessful());
            Log.d(TAG, "handleApiResponse: " +responseModel.getTag());

            Toast.makeText(this, "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Handle unsuccessful response
            Toast.makeText(this, "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }
}


