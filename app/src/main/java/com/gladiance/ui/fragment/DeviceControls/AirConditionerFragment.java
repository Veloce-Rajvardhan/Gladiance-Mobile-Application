package com.gladiance.ui.fragment.DeviceControls;

import static android.content.Context.MODE_PRIVATE;
import static org.greenrobot.eventbus.EventBus.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import com.gladiance.ui.activities.DeviceControls.CircularSeekBar;
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


public class AirConditionerFragment extends Fragment implements CircularSeekBar.OnProgressChangeListener {

    Switch switchAirConditioning;

    String nodeId;
    private ProgressBar progressBar;
    private Button incrementButton, decrementButton;

    // private int progress = 64;
    NetworkApiManager networkApiManager;

    private CircularSeekBar circularSeekBar;
    private int progress = 0;


    TextView textView, textViewDeviceName,tvCel,tvFer;
    ImageView imgCool,imgHot;
    SeekBar seekbarAirCond;
    private String[] progressStates = {"Off", "Low", "Mid", "High"};

    Button buttonCool, buttonHeat, buttonCon, buttonfah;

    Context context;
    private EspApplication espApp;
    private ObjectScenes objectScenes;


    public AirConditionerFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_air_conditioner, container, false);
        SharedPreferences preferences2 = context.getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        SharedPreferences preferences = context.getSharedPreferences("my_shared_prefe_label", MODE_PRIVATE);
        String Label = preferences.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "Label : " + Label);

        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String mode = sharedPref.getString("Mode", "");
        String unit = sharedPref.getString("Unit", "");

        Log.e(TAG, "Mode from SharedPreferences: " + mode);
        Log.e(TAG, "Unit from SharedPreferences: " + unit);

        SharedPreferences sharedPrefRange = context.getSharedPreferences("MyPrefsRange", MODE_PRIVATE);
        int min = sharedPrefRange.getInt("SetRangeMin", 0);
        int max = sharedPrefRange.getInt("SetRangeMax", 0);

        Log.e(TAG, "Range Min : " + min);
        Log.e(TAG, "Range Max: " + max);

        if ("Centigrade".equalsIgnoreCase(mode)) {
            min = convertCentigradeToFahrenheit(min);
            max = convertCentigradeToFahrenheit(max);

            Log.e(TAG, "Converted Range Min to Fahrenheit: " + min);
            Log.e(TAG, "Converted Range Max to Fahrenheit: " + max);
        }

        espApp = new EspApplication(context.getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);

        circularSeekBar = view.findViewById(R.id.circularSeekBar);
        circularSeekBar.setProgress(progress);
        circularSeekBar.setOnProgressChangeListener(this);

        circularSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // Finger touches the screen
                        Log.d("Progress", "Progress when touch released: " + progress);
                        Toast.makeText(context, "Progress when touch released: " + progress, Toast.LENGTH_SHORT).show();
                        // You can do whatever you want with the progress value here
                        sendTemperature(progress);
                        return true;
                }
                return false;
            }
        });


        textViewDeviceName = view.findViewById(R.id.DeviceName);
        textViewDeviceName.setText(Label);

        seekbarAirCond = view.findViewById(R.id.seekBarAirCond);
        disableSeekBars();

        switchAirConditioning = view.findViewById(R.id.switchButtonAirCon);
        switchAirConditioning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                sendSwitchState(isChecked);
                if (isChecked) {
                    enableSeekBars();
                } else {
                    disableSeekBars();
                }
            }
        });

        textView = view.findViewById(R.id.textViewFan);
        seekbarAirCond.setMax(progressStates.length - 1);
        seekbarAirCond.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(progressStates[progress]);
                if (progress == 0) {
                    String progress1 = "Off";
                    airConditioningProgress(progress1);

                } else if (progress == 1) {
                    String progress1 = "Low";
                    airConditioningProgress(progress1);

                } else if (progress == 2) {
                    String progress1 = "Mid";
                    airConditioningProgress(progress1);

                } else {
                    String progress1 = "High";
                    airConditioningProgress(progress1);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }
        });

        tvCel = view.findViewById(R.id.tvCel);
        tvFer = view.findViewById(R.id.tvFer);
        if (unit.equals("Fahrenheit")) {
            tvFer.setBackgroundResource(R.drawable.trasparent_orange_botton_bg);
        } else {
            tvCel.setBackgroundResource(R.drawable.trasparent_orange_top_bg);
        }

        tvCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCel.setBackgroundResource(R.drawable.trasparent_orange_top_bg);
                tvFer.setBackground(null);
            }
        });

        tvFer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFer.setBackgroundResource(R.drawable.trasparent_orange_botton_bg);
                tvCel.setBackground(null);
            }
        });

        imgHot = view.findViewById(R.id.imgHot);
        imgCool = view.findViewById(R.id.imgCool);
        if (mode.equals("Cool")) {
            imgCool.setBackgroundResource(R.drawable.trasparent_orange_top_bg);
        } else {
            imgHot.setBackgroundResource(R.drawable.trasparent_orange_botton_bg);
        }

        return view;
    }

    //Air Condition  Progress
    private void airConditioningProgress(String progress1) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(ContentValues.TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"AC Speed\":\"" + progress1 + "\"}}";
        Log.e(ContentValues.TAG, "1: "+commandBody );
        Log.e(ContentValues.TAG, "sendTemperature: "+commandBody );
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        // Edit Scene
        try {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = "Speed";
            AppConstants.power = progress1;
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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
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
            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "Speed";
            AppConstants.Create_power = progress1;
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


            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power, AppConstants.Create_Ref_Scene);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "Speed";
            AppConstants.Create_power_Schedule = progress1;
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

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule,  AppConstants.Create_Ref_Schedule);
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

            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {

            AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = "Speed";
            AppConstants.Edit_power_Schedule = progress1;
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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_Ref_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
            //  sharedViewModel.addObjectScenes(objectScenes);

            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
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


        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    //AirCondition Temp Speed Method
    private void sendTemperature(int fanSpeed) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(ContentValues.TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Set\": " + fanSpeed + "}}";
        Log.e(ContentValues.TAG, "sendTemperature: "+commandBody );
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        // Edit Scene
        try {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = "Set";
            AppConstants.power = String.valueOf(fanSpeed);
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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
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
            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "Set";
            AppConstants.Create_power = String.valueOf(fanSpeed);
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


            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power, AppConstants.Create_Ref_Scene);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "Set";
            AppConstants.Create_power_Schedule = String.valueOf(fanSpeed);
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

            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {

            AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = "Set";
            AppConstants.Edit_power_Schedule = String.valueOf(fanSpeed);
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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_Ref_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
            //  sharedViewModel.addObjectScenes(objectScenes);

            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
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


        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void sendSwitchState(boolean powerState) {
        // Create a RequestModel with the required data

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(ContentValues.TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Power\": " + powerState + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

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


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
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
            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            AppConstants.Create_powerState = "Power";
            AppConstants.Create_power = String.valueOf(powerState);
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


            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power, AppConstants.Create_Ref_Scene);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = "Power";
            AppConstants.Create_power_Schedule = String.valueOf(powerState);
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

            ////////////


        }
        catch (Exception e){
            Log.e(ContentValues.TAG, "sendSwitchState: "+e);
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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_Ref_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
            //  sharedViewModel.addObjectScenes(objectScenes);

            Log.e(ContentValues.TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
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


        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void disableSeekBars() {
        seekbarAirCond.setEnabled(false);
        circularSeekBar.setEnabled(false);
    }

    private void enableSeekBars() {
        seekbarAirCond.setEnabled(true);
        circularSeekBar.setEnabled(true);
    }

    private int convertCentigradeToFahrenheit(int centigrade) {
        return (int) ((centigrade * 9.0 / 5) + 32);
    }

    private void handleApiResponse(ResponseModel responseModel) {
        // Handle the response as needed
        if (responseModel != null) {
            // API call was successful
            // Access other fields from responseModel if needed
            Log.d(ContentValues.TAG, "handleApiResponse: " + responseModel.getSuccessful());
            Log.d(ContentValues.TAG, "handleApiResponse: " + responseModel.getTag());

            Toast.makeText(context, "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Handle unsuccessful response
            Toast.makeText(context, "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_UP:
//                // Here, you have the progress value when touch is released
//                Log.d("Progress", "Progress when touch released: " + progress);
//                Toast.makeText(context, "Progress when touch released: " + progress, Toast.LENGTH_SHORT).show();
//                // You can do whatever you want with the progress value here
//                sendTemperature(progress);
//                return true;
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    public void onProgressChanged(String progressText) {
        // Here you receive the progress text
        // Parse it to a float and update the progress variable
        try {
            progress = (int) Float.parseFloat(progressText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


}