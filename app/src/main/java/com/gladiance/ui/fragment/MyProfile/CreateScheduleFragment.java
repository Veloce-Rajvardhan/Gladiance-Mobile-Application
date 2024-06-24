package com.gladiance.ui.fragment.MyProfile;

import static android.content.Context.MODE_PRIVATE;
import static org.greenrobot.eventbus.EventBus.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.gladiance.AppConstants;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.adapters.AreaSpinnerAdapter;
import com.gladiance.ui.adapters.DayAdapter;
import com.gladiance.ui.adapters.DeviceControlScheduleAdapter;
import com.gladiance.ui.adapters.MonthAdapter;
import com.gladiance.ui.models.ScheduleViewModel;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.arealandingmodel.Area;
import com.gladiance.ui.models.arealandingmodel.ProjectAreaLandingResModel;
import com.gladiance.ui.models.guestlandingpage.GuestControls;
import com.gladiance.ui.models.guestlandingpage.GuestLandingResModel;
import com.gladiance.ui.models.lnstallerlandingpage.Controls;
import com.gladiance.ui.models.saveSchedule.SaveScheduleRequest;
import com.gladiance.ui.models.saveSchedule.Trigger;
import com.gladiance.ui.models.scene.Configuration;
import com.gladiance.ui.models.scene.SceneResModel;
import com.gladiance.ui.models.scenelist.ObjectSchedule;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateScheduleFragment extends Fragment implements AreaSpinnerAdapter.OnAreaSelectedListener {

    private RecyclerView recyclerViewDay,recyclerViewMonth;
    private DayAdapter dayAdapter;
    private MonthAdapter monthAdapter;
    CheckBox CBWeek;
    CheckBox CBMonth;
    CheckBox CBYear;
    NumberPicker datePicker;
    NumberPicker yearPicker;

    Button btnSave;
    Trigger trigger;
    EditText scheduleName;
    EditText projectName;
    private View view;
    private ArrayList<Configuration> ConfigArrayList;

    private ArrayList<Controls> ConArrayList;

    private ArrayList<Area> arrayList2;
    private static final String PREF_SELECTED_AREA_REF = "selectedAreaRef";
    private static final String ARG_SELECTED_AREA_REF = "selectedAreaRef";
    private static final String PREFS_NAME = "MyPrefsFile";

    EditText editTextProjectName, editTextSpaceName, editTextSceneName;

    RecyclerView recyclerView;

    Spinner spinner;
    Button buttonSave;

    ObjectSchedule objectSchedule;

    public CreateScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_create_schedule, container, false);

        recyclerViewDay = view.findViewById(R.id.recyclerViewDay);
        recyclerViewMonth = view.findViewById(R.id.recyclerViewMonth);
        CBWeek = view.findViewById(R.id.CBWeek);
        CBMonth = view.findViewById(R.id.CBMonth);
        CBYear = view.findViewById(R.id.CBYear);
        buttonSave = view.findViewById(R.id.btnSave);
        spinner = view.findViewById(R.id.sceneAreaSpinner);
        recyclerView = view.findViewById(R.id.recycleViewDeviceName);
        datePicker = view.findViewById(R.id.DatePicker);
        scheduleName = view.findViewById(R.id.ETScheduleName);
        projectName = view.findViewById(R.id.ETProjectName);
        yearPicker = view.findViewById(R.id.yearPicker);


        ConArrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        ConfigArrayList = new ArrayList<>();

        datePicker.setEnabled(false);
        CBMonth.setChecked(false);
        recyclerViewMonth.setEnabled(false);
        recyclerViewMonth.setAlpha(0.3f);// To visually show it's disabled
        yearPicker.setEnabled(false);
        yearPicker.setAlpha(0.3f);
        datePicker.setEnabled(false);
        datePicker.setAlpha(0.3f);
        CBWeek.setChecked(false);
        recyclerViewDay.setEnabled(false);
        recyclerViewDay.setAlpha(0.5f);
        CBYear.setEnabled(false);
        CBYear.setAlpha(0.5f);


        CBWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CBMonth.setChecked(false);
//                    recyclerViewMonth.setEnabled(false);
//                    recyclerViewMonth.setAlpha(0.3f);// To visually show it's disabled
                    datePicker.setEnabled(false);
                    datePicker.setAlpha(0.3f);
                    recyclerViewDay.setEnabled(true);
                    recyclerViewDay.setAlpha(1.0f);



                    recyclerViewMonth.setEnabled(false);
                    recyclerViewMonth.setAlpha(0.3f);
                    yearPicker.setEnabled(false);
                    yearPicker.setAlpha(0.3f);
                    CBYear.setEnabled(false);
                    CBYear.setAlpha(0.3f);

                } else {
                    recyclerViewMonth.setEnabled(true);
                    recyclerViewMonth.setAlpha(1.0f);
                    datePicker.setEnabled(true);
                    datePicker.setAlpha(1.0f);
                }
            }
        });

        CBMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CBWeek.setChecked(false);
                    recyclerViewDay.setEnabled(false);
                    recyclerViewDay.setAlpha(0.5f); // To visually show it's disabled
//                    recyclerViewMonth.setEnabled(true);
//                    recyclerViewMonth.setAlpha(1.0f);
                    datePicker.setEnabled(true);
                    datePicker.setAlpha(1.0f);

                    recyclerViewMonth.setEnabled(true);
                    recyclerViewMonth.setAlpha(1.0f);
                    yearPicker.setEnabled(true);
                    yearPicker.setAlpha(1.0f);
                    CBYear.setEnabled(true);
                    CBYear.setAlpha(1.0f);
                } else {
                    recyclerViewDay.setEnabled(true);
                    recyclerViewDay.setAlpha(1.0f);
                }
            }
        });

        SharedPreferences sharedPreferencesProName = requireActivity().getSharedPreferences("MyPrefsPN", Context.MODE_PRIVATE);
        String ProjectName = sharedPreferencesProName.getString("ProjectName", "");
        projectName.setText(ProjectName);

        Log.e(TAG, "Home Fragment Project Name : "+ ProjectName );

        if(AppConstants.Create_Ref_dyn_Schedule == "null") {
            getRef();
        }

        // Ref
        SharedPreferences sharedPreferences5 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(ContentValues.TAG, "Project Space Ref: "+saveProjectSpaceRef );
        String projectSpaceRef = saveProjectSpaceRef.trim();

        SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginToken = sharedPreferences2.getString("LoginToken", "");
        Log.e(ContentValues.TAG, "SceneList Login Token: " + savedLoginToken);
        String loginToken = savedLoginToken.trim();

        // GAAProjectSpaceTypeRef
        SharedPreferences  sharedPreferences4 = requireContext().getSharedPreferences("MyPrefsPSTR", MODE_PRIVATE);
        String saveProjectSpaceTypeRef = sharedPreferences4.getString("Project_Space_Type_Ref", "");
        Log.e(ContentValues.TAG, "Project Space Type Ref: "+saveProjectSpaceTypeRef );
        String gaaProjectSpaceTypeRef = saveProjectSpaceTypeRef.trim();
        AppConstants.Create_Space_dyn_Schedule = gaaProjectSpaceTypeRef;

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(ContentValues.TAG, "SceneList LoginDeviceId: " + GUID);
        String loginDeviceId = GUID.trim();





        /// time ///
        // Initialize hour picker
        NumberPicker hourPicker = view.findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23); // 24 hours format
        // Set initial value
        hourPicker.setValue(12); // Default value 12


        // Initialize minute picker
        NumberPicker minutePicker = view.findViewById(R.id.minutePicker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59); // 60 minutes
        // Set initial value
        minutePicker.setValue(0); // Default value 0

        // Initialize Second picker
        NumberPicker secondsPicker = view.findViewById(R.id.secondsPicker);
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(59); // 60 minutes
        // Set initial value
        secondsPicker.setValue(0); // Default value 0


        // Initialize minute picker
        // NumberPicker datePicker = view.findViewById(R.id.DatePicker);
        datePicker.setMinValue(0);
        datePicker.setMaxValue(31); // 60 minutes
        // Set initial value
        datePicker.setValue(0); // Default value 0

        //For year
        // Initialize year picker

        // Get current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Set range of years (from current year to next 10 years)
        yearPicker.setMinValue(currentYear);
        yearPicker.setMaxValue(currentYear + 10);

        // Set initial value to current year
        yearPicker.setValue(currentYear);



        // Add listener to get the selected value
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Get the selected value
                int selectedHour = hourPicker.getValue();
                Log.e(TAG, "hour: "+selectedHour);
                AppConstants.hour = selectedHour;
                // Do something with the selected year
            }
        });

        // Add listener to get the selected value
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Get the selected value
                int selectedMinute = minutePicker.getValue();
                Log.e(TAG, "minutes: "+selectedMinute);
                AppConstants.minute = selectedMinute;
                // Do something with the selected year
            }
        });


        // Add listener to get the selected value
        secondsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Get the selected value
                int selectedSeconds = secondsPicker.getValue();
                Log.e(TAG, "seconds: "+selectedSeconds);
                AppConstants.second = selectedSeconds;
                // Do something with the selected year
            }
        });

        // Add listener to get the selected value
        datePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Get the selected value
                int selectedDate = datePicker.getValue();
                Log.e(TAG, "date: "+selectedDate);
                AppConstants.dayofmonth = selectedDate;
                // Do something with the selected year
            }
        });

        // Add listener to get the selected value
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Get the selected value
                int selectedYear = yearPicker.getValue();
                Log.e(TAG, "year: "+selectedYear);
                AppConstants.Year = selectedYear;
                // Do something with the selected year
            }
        });

        CBYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // When the CheckBox is checked, isChecked will be true; otherwise, false
                if (isChecked) {
                    // If checked, set the value to true
                    AppConstants.RepeatEveryYear = true;
                } else {
                    // If unchecked, set the value to false
                    AppConstants.RepeatEveryYear = false;
                }
            }
        });

        //Day Recycle View
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false);
        recyclerViewDay.setLayoutManager(gridLayoutManager);
        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        dayAdapter = new DayAdapter(days, new DayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String day, boolean isChecked) {
                Log.e(TAG, "onItemClick: " + day + " isChecked: " + isChecked);
                logDataList();
            }

            private void logDataList() {
                List<Pair<String, Boolean>> dataList = dayAdapter.getAllDataWithCheckedStatus();
                for (Pair<String, Boolean> pair : dataList) {
                    Log.e(TAG, "Day: " + pair.first + ", isChecked: " + pair.second);

                    String day = pair.first;
                    boolean isChecked = pair.second;
                    Trigger trigger = new Trigger(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
                    // Set the corresponding field in the Trigger object based on the day name
                    switch (day) {
                        case "Monday":
                            trigger.setMonday(isChecked);
                            AppConstants.Monday = trigger.getMonday();
                            Log.e(TAG, "logDataList: "+ trigger.getMonday());
                            break;
                        case "Tuesday":
                            trigger.setTuesday(isChecked);
                            AppConstants.Tuesday = trigger.getTuesday();

                            break;
                        case "Wednesday":
                            trigger.setWednesday(isChecked);
                            AppConstants.Wednesday = trigger.getWednesday();

                            break;
                        case "Thursday":
                            trigger.setThursday(isChecked);
                            AppConstants.Thursday = trigger.getThursday();

                            break;
                        case "Friday":
                            trigger.setFriday(isChecked);
                            AppConstants.Friday = trigger.getFriday();

                            break;
                        case "Saturday":
                            trigger.setSaturday(isChecked);
                            AppConstants.Saturday = trigger.getSaturday();

                            break;
                        case "Sunday":
                            trigger.setSunday(isChecked);
                            AppConstants.Sunday = trigger.getSunday();

                            break;
                        default:
                            // Handle if necessary
                            break;
                    }

                }
            }

        });
        recyclerViewDay.setAdapter(dayAdapter);


        //Month Recycle View

        GridLayoutManager gridLayoutManagerMonth = new GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false);
        recyclerViewMonth.setLayoutManager(gridLayoutManagerMonth);
        List<String> months = Arrays.asList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );
        monthAdapter = new MonthAdapter(months, new MonthAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String month, boolean isChecked) {
                Log.e(TAG, "onItemClick: " + month + " isChecked: " + isChecked);
                logDataList2();
            }

            private void logDataList2() {
                List<Pair<String, Boolean>> dataList = monthAdapter.getAllDataWithCheckedStatus();
                for (Pair<String, Boolean> pair : dataList) {
                    Log.e(TAG, "Day: " + pair.first + ", isChecked: " + pair.second);

                    String day = pair.first;
                    boolean isChecked = pair.second;
                    Trigger trigger = new Trigger(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

                    // Set the corresponding field in the Trigger object based on the day name
                    switch (day) {
                        case "Jan":
                            trigger.setJanuary(isChecked);
                            AppConstants.January = trigger.getJanuary();
                            Log.e(TAG, "logDataList3: "+AppConstants.January);

                            Log.e(TAG, "logDataList4: "+ trigger.getJanuary());
                            break;
                        case "Feb":
                            trigger.setFebruary(isChecked);
                            AppConstants.February = trigger.getFebruary();

                            break;
                        case "Mar":
                            trigger.setMarch(isChecked);
                            AppConstants.March = trigger.getMarch();

                            break;
                        case "Apr":
                            trigger.setThursday(isChecked);
                            AppConstants.Thursday = trigger.getThursday();

                            break;
                        case "May":
                            trigger.setMay(isChecked);
                            AppConstants.May = trigger.getMay();

                            break;
                        case "Jun":
                            trigger.setJune(isChecked);
                            AppConstants.June = trigger.getJune();

                            break;
                        case "Jul":
                            trigger.setJuly(isChecked);
                            AppConstants.July = trigger.getJuly();

                            break;
                        case "Aug":
                            trigger.setAugust(isChecked);
                            AppConstants.August = trigger.getAugust();

                            break;
                        case "Sep":
                            trigger.setSeptember(isChecked);
                            AppConstants.September = trigger.getSeptember();

                            break;
                        case "Oct":
                            trigger.setOctober(isChecked);
                            AppConstants.October = trigger.getOctober();

                            break;
                        case "Nov":
                            trigger.setNovember(isChecked);
                            AppConstants.November = trigger.getNovember();
                            break;
                        case "Dec":
                            trigger.setDecember(isChecked);
                            AppConstants.December = trigger.getDecember();
                            break;
                        default:
                            // Handle if necessary
                            break;
                    }

                }
            }

        });
        recyclerViewMonth.setAdapter(monthAdapter);






        long gAAProjectSpaceTypeAreaRef = getSelectedAreaRefFromPreferences();
        Bundle bundle = getArguments();

        // Bundle bundle = getArguments();
//        if (bundle != null) {
//            String sceneRefString = bundle.getString("SCENE_REF");
//            if (sceneRefString != null) {
//                Long sceneRef = Long.parseLong(sceneRefString);
//                Log.e(ContentValues.TAG, "SceneRef: " + sceneRef);
//                getScene(sceneRef, loginToken, loginDeviceId);
//            } else {
//                Log.e(ContentValues.TAG, "SceneRef is null");
//                // Handle null sceneRefString
//            }
//        } else {
//            Log.e(ContentValues.TAG, "Bundle is null");
//            // Handle null bundle
//        }


        //start here

//        if(AppConstants.StoredData == false){
            fetchGuestControls(projectSpaceRef,gAAProjectSpaceTypeAreaRef,loginToken,loginDeviceId);
//            AppConstants.StoredData = true;
//        }else{
//
//        }



        fetchAreas(projectSpaceRef,loginToken,loginDeviceId);

        return view;
    }

    // to Get Ref
    private void getRef() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        SharedPreferences preferences9 = getContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId3 = preferences9.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "node id2: " + nodeId3);
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
                        AppConstants.Create_Ref_dyn_Schedule = responseModel.getTag();
                        AppConstants.Create_ScheduleRef_Schedule = responseModel.getTag();

                        Log.d(TAG, "Success: " + success + ", Message: " + message+ " Tag: "+Ref);


                    }
                } else {
                    Log.e(TAG, "API call failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AllocateSingleIdResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

//        ScheduleViewModel scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
//
//        ObjectSchedule objectSchedule = scheduleViewModel.getObjectSchedule();

        // Retrieve RecyclerView from the layout
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewDay);

        // Set up RecyclerView adapter and layout manager
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("your_shared_preferences_name", Context.MODE_PRIVATE);
        int numberOfItems = sharedPreferences.getInt("number_of_items", 0);
        // Iterate through saved selection states and text colors
        for (int i = 0; i < numberOfItems; i++) { // numberOfItems should be the same number used when saving the data
            boolean isChecked = sharedPreferences.getBoolean("selection_" + i, false); // default value false
            int textColor = sharedPreferences.getInt("text_color_" + i, ContextCompat.getColor(requireContext(), R.color.white)); // default color if not found
//            textView.setTextColor(textColor);
//            yourAdapter.updateTextColor(i, textColor);
            // Handle the fetched data, maybe update your UI accordingly
            // For example, if you have a list of items, you might want to update their states and colors
            // item.setChecked(isChecked);
            // item.setTextColor(textColor);
        }


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

////                boolean januaryValue = trigger.getJanuary();
//                Log.e(TAG, "onClick: "+januaryValue );
//
//
//                Log.e(TAG, "onClick2: "+januaryValue );

                //    Log.e(TAG, "logDataList2: "+AppConstants.January);

                AppConstants.Create_Name_dyn_Schedule = String.valueOf(scheduleName.getText());

                Log.e("APPCONSTS_SH"," "+AppConstants.Create_Ref_dyn_Schedule);
                Log.e("APPCONSTS_SH"," "+AppConstants.Create_Name_dyn_Schedule);
                Log.e("APPCONSTS_SH"," "+AppConstants.Create_ScheduleRef_Schedule);
                Log.e("APPCONSTS_SH"," "+AppConstants.Create_Space_dyn_Schedule);
                Log.e("APPCONSTS_SH"," "+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
                Log.e("APPCONSTS_SH"," "+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
                Log.e("APPCONSTS_SH"," "+AppConstants.Create_powerState_Schedule);
                Log.e("APPCONSTS_SH"," "+AppConstants.Create_power_Schedule);



                /////////////////////////////////////////////////////////////////
                //     List<SceneConfig> list = new ArrayList<>();
//                for (int i = 0; i < ConArrayList.size(); i++) {
//                    if (ConArrayList.get(i).isChecked() == true) {
//                        Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getgAAProjectSpaceTypePlannedDeviceName());
//                        list.add(new SceneConfig(
//                                Long.parseLong(AppConstants.Create_SceneRef),
//                                ConArrayList.get(i).getgAAProjectSpaceTypePlannedDeviceRef(),
//                                ConArrayList.get(i).getgAAProjectSpaceTypePlannedDeviceName(),
//                                AppConstants.Create_powerState,
//                                AppConstants.Create_power
//                        ));
//                    }
//                }

                //////
//
//                SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//                LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
//                objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
//                    @Override
//                    public void onChanged(List<ObjectSceneCreate> objectScenesList) {
////                        for(int i = 0; i <ConArrayList.size(); i++) {
////                            if (ConArrayList.get(i).isChecked() == true) {
////                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
//                        if (objectScenesList != null) {
//                            for (ObjectSceneCreate objectScenes : objectScenesList) {
//
//                                list.add(new SceneConfig(
//                                        Long.parseLong(objectScenes.getSceneRef()),
//                                        Long.parseLong(objectScenes.getProjectSpaceTypePlannedDeviceName()),
//                                        objectScenes.getGaaProjectSpaceTypePlannedDeviceRef(),
//                                        objectScenes.getNodeConfigParamName(),
//                                        objectScenes.getValue()
//                                ));
//                                Log.d("ObjectScenes2", objectScenes.getSceneRef());
//                                Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
//                                Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
//                                Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
//                                Log.d("getValue", objectScenes.getValue());
//                            }
//
//                            // Remember to remove the observer if necessary
//                            objectScenesListLiveData.removeObserver(this);
//                        }
//                        //   }
//                        //}
//                    }
//                });
                ///////////////////////////////////////////////////////////////////


                List<com.gladiance.ui.models.saveSchedule.Configuration> list = new ArrayList<>();
                ScheduleViewModel scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
                LiveData<List<ObjectSchedule>> objectScenesListLiveData = scheduleViewModel.getObjectScenesList();
                objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSchedule>>() {
                    @Override
                    public void onChanged(List<ObjectSchedule> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                        if (objectScenesList != null) {
                            for (ObjectSchedule objectScenes : objectScenesList) {
                                list.add(new com.gladiance.ui.models.saveSchedule.Configuration(
                                        Long.parseLong(objectScenes.getSceneRef()),
                                        Long.parseLong(objectScenes.getProjectSpaceTypePlannedDeviceName()),
                                        objectScenes.getGaaProjectSpaceTypePlannedDeviceRef(),
                                        objectScenes.getNodeConfigParamName(),
                                        objectScenes.getValue()
                                ));

//                                list.add(new com.gladiance.ui.models.saveSchedule.Configuration(
//                                        Long.parseLong(AppConstants.Create_ScheduleRef_Schedule),
//                                        Long.parseLong(AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule),
//                                        AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,
////                                ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceRef(),
////                                ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName(),
//                                        AppConstants.Create_powerState_Schedule,
//                                        AppConstants.Create_power_Schedule
//                                ));
                                Log.d("ObjectScenes2", objectScenes.getSceneRef());
                                Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
                                Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
                                Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
                                Log.d("getValue", objectScenes.getValue());
                            }

                            // Remember to remove the observer if necessary
                            objectScenesListLiveData.removeObserver(this);
                        }
                        //   }
                        //}
                    }
                });

//                for(int i = 0; i <ConArrayList.size(); i++){
//                    if(ConArrayList.get(i).isChecked() == true){
                //    Log.e("ConArrayList","Selected -- "+ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
//                list.add(new com.gladiance.ui.models.saveSchedule.Configuration(
//                        Long.parseLong(AppConstants.Create_ScheduleRef_Schedule),
//                        Long.parseLong(AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule),
//                        AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,
////                                ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceRef(),
////                                ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName(),
//                        AppConstants.Create_powerState_Schedule,
//                        AppConstants.Create_power_Schedule
//                ));
//                    }
//                }

//                for(int i = 0; i <ConArrayList.size(); i++){
//                    if(ConArrayList.get(i).isChecked() == true){
//                        Log.e("ConArrayList","Selected -- "+ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                List<Trigger> triggerList = new ArrayList<>();

                if (CBWeek.isChecked()) {
                    triggerList.add(new Trigger(
                            Long.parseLong(AppConstants.Create_ScheduleRef_Schedule),
                            AppConstants.Monday,
                            AppConstants.Tuesday,
                            AppConstants.Wednesday,
                            AppConstants.Thursday,
                            AppConstants.Friday,
                            AppConstants.Saturday,
                            AppConstants.Sunday,
                            AppConstants.hour,
                            AppConstants.minute,
                            AppConstants.second,
                            AppConstants.dayofmonth = 0,
                            AppConstants.January = false,
                            AppConstants.February = false,
                            AppConstants.March = false,
                            AppConstants.April = false,
                            AppConstants.May = false,
                            AppConstants.June = false,
                            AppConstants.July = false,
                            AppConstants.August = false,
                            AppConstants.September = false,
                            AppConstants.October = false,
                            AppConstants.November = false,
                            AppConstants.December = false,
//                            AppConstants.January,
//                            AppConstants.February,
//                            AppConstants.March,
//                            AppConstants.April,
//                            AppConstants.May,
//                            AppConstants.June,
//                            AppConstants.July,
//                            AppConstants.August,
//                            AppConstants.September,
//                            AppConstants.October,
//                            AppConstants.November,
//                            AppConstants.December,
                            AppConstants.Year=0,
                            AppConstants.RepeatEveryYear = false
                    ));
                } else if(CBMonth.isChecked()){
                    triggerList.add(new Trigger(
                            Long.parseLong(AppConstants.Create_ScheduleRef_Schedule),
                            AppConstants.Monday = false,
                            AppConstants.Tuesday = false,
                            AppConstants.Wednesday = false,
                            AppConstants.Thursday = false,
                            AppConstants.Friday = false,
                            AppConstants.Saturday = false,
                            AppConstants.Sunday = false,
                            AppConstants.hour,
                            AppConstants.minute,
                            AppConstants.second,
                            AppConstants.dayofmonth,
                            AppConstants.January,
                            AppConstants.February,
                            AppConstants.March,
                            AppConstants.April,
                            AppConstants.May,
                            AppConstants.June,
                            AppConstants.July,
                            AppConstants.August,
                            AppConstants.September,
                            AppConstants.October,
                            AppConstants.November,
                            AppConstants.December,
                            AppConstants.Year,
                            AppConstants.RepeatEveryYear
                    ));
                }
                for (Trigger trigger : triggerList) {
                    System.out.println("Trigger data:");
                    System.out.println("Monday: " + trigger.getMonday());
                    System.out.println("Tuesday: " + trigger.getTuesday());
                    System.out.println("Wednesday: " + trigger.getWednesday());
                    System.out.println("Thursday: " + trigger.getThursday());
                    System.out.println("Friday: " + trigger.getFriday());
                    System.out.println("Saturday: " + trigger.getSaturday());
                    System.out.println("Sunday: " + trigger.getSunday());
                    System.out.println("Hour: " + trigger.getHour());
                    System.out.println("Minute: " + trigger.getMinute());
                    System.out.println("Second: " + trigger.getSecond());
                    System.out.println("DayOfMonth: " + trigger.getDayOfMonth());
                    System.out.println("January: " + trigger.getJanuary());
                    System.out.println("February: " + trigger.getFebruary());
                    System.out.println("March: " + trigger.getMarch());
                    System.out.println("April: " + trigger.getApril());
                    System.out.println("May: " + trigger.getMay());
                    System.out.println("June: " + trigger.getJune());
                    System.out.println("July: " + trigger.getJuly());
                    System.out.println("August: " + trigger.getAugust());
                    System.out.println("September: " + trigger.getSeptember());
                    System.out.println("October: " + trigger.getOctober());
                    System.out.println("November: " + trigger.getNovember());
                    System.out.println("December: " + trigger.getDecember());
                    System.out.println("Year: " + trigger.getYear());
                    System.out.println("RepeatEveryYear: " + trigger.getRepeatEveryYear());
                }
                SaveScheduleRequest saveScene = new SaveScheduleRequest(
                        Long.parseLong(AppConstants.Create_Ref_dyn_Schedule),
                        AppConstants.Create_Name_dyn_Schedule,
                        //    Long.parseLong(AppConstants.Create_ScheduleRef_Schedule),Create_Space_dyn
                        Long.parseLong(AppConstants.Create_Space_dyn_Schedule),
                        list, triggerList);
                SaveScheduleRequest(saveScene);

//                    }
//                }

//                SaveScheduleRequest saveScene = new SaveScheduleRequest(
//                        Long.parseLong(AppConstants.Ref_dyn_Schedule),
//                        AppConstants.Name_dyn_Schedule,
//                        Long.parseLong(AppConstants.ScheduleRef_Schedule),
//                        list);
                //             sendSaveSceneRequest(saveScene);

                // Access TextView
                //extView textView = findViewById(R.id.TVProjectName);
                // Get text from TextView
//                String ScheduleName = scheduleName.getText().toString();
//                String ProjectName = projectName.getText().toString();
//
//                Log.e(TAG, "onCreateView: "+ScheduleName+" "+ProjectName);
            }

            private void SaveScheduleRequest(SaveScheduleRequest saveScene) {
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

                Call<SceneResModel> call = apiService.saveSchedule(saveScene);
                call.enqueue(new Callback<SceneResModel>() {
                    @Override
                    public void onResponse(Call<SceneResModel> call, Response<SceneResModel> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response
                            SceneResModel sceneResModel = response.body();
                            Log.e("Successful", "Success: " + sceneResModel.getSuccessful());
                            Toast.makeText(getContext().getApplicationContext(), "Schedule Edited Successfully!", Toast.LENGTH_SHORT).show();
                            Log.e(ContentValues.TAG, "Done ");
                            Log.e("Create Schedule", "Message: " + sceneResModel.getMessage());


                            ObjectSchedule objectSchedule = new ObjectSchedule(null,null,null,null,null,null,null,null);


// Reset the object using one of the methods above
                            objectSchedule.clear(); // Option 1
                            AppConstants.Create_Ref_dyn_Schedule = "null";
// Or use setters if available
                            objectSchedule.setRef_dyn("");
                            objectSchedule.setName_dyn("");
                            objectSchedule.setSpace_dyn("");
                            objectSchedule.setSceneRef("");
                            objectSchedule.setProjectSpaceTypePlannedDeviceName("");
                            objectSchedule.setGaaProjectSpaceTypePlannedDeviceRef("");
                            objectSchedule.setNodeConfigParamName("");
                            objectSchedule.setValue("");

// Set other fields as needed

// Add to sharedViewModel
                            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
                      //      sharedViewModel.addObjectSchedule(objectSchedule);

                            // Example of clearing all ObjectSchedule instances
                            sharedViewModel.clearObjectSchedules();

// Example of removing a specific ObjectSchedule
                      //      sharedViewModel.removeObjectSchedule(objectSchedule);
//                            ObjectSchedule objectSchedule = new ObjectSchedule(null,null,null,null,null,null,null,null);
//
////                            if (objectSchedule != null) {
//                                objectSchedule.clear();
//                                System.out.println("objectSchedule " + objectSchedule);
//                            } else {
//                                // Handle the case where objectSchedule is null
//                            }
                            // ObjectTag objectTag = SceneResModel.getObjectTag();
                            //  SceneResModel = new SceneResModel.getSuccessful();
                        } else {
                            // Handle unsuccessful response
                        }
                    }

                    @Override
                    public void onFailure(Call<SceneResModel> call, Throwable t) {
                        // Handle API call failure
                        Log.e(ContentValues.TAG, "Failure");
                    }
                });
            }
        });
    }



    // For Area
    //for spinner
    private void fetchAreas(String GAAProjectSpaceRef, String LoginToken, String LoginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ProjectAreaLandingResModel> call = apiService.getAreaLandingPageData(GAAProjectSpaceRef, LoginToken, LoginDeviceId);
        call.enqueue(new Callback<ProjectAreaLandingResModel>() {
            @Override
            public void onResponse(Call<ProjectAreaLandingResModel> call, Response<ProjectAreaLandingResModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProjectAreaLandingResModel dataResponse = response.body();
                    List<Area> areas = dataResponse.getData().getAreas();

                    for(Area area : areas) {
                        Log.e(ContentValues.TAG, "onResponse AreaName: " + area.getGAAProjectSpaceTypeAreaName());
                        Log.e(ContentValues.TAG, "onResponse Area Ref: " + area.getGAAProjectSpaceTypeAreaRef());
                        arrayList2.add(new Area(area.getGAAProjectSpaceTypeAreaRef(), area.getGAAProjectSpaceTypeAreaName(), area.getWifiSSID(), area.getWifiPassword(), area.getGuestControls(), area.getInstallerControls()));

                        if (area.getGAAProjectSpaceTypeAreaRef() == 0) {

                        }
                    }
                    // Create custom spinner adapter
                    AreaSpinnerAdapter adapter = new AreaSpinnerAdapter(requireContext(),spinner,areas);

                    // Set the listener for the adapter
                    adapter.setOnAreaSelectedListener(CreateScheduleFragment.this); // Replace YourFragmentClassName with the name of your fragment class

                    spinner.setAdapter(adapter);

                    // Handle item selection
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ProjectAreaLandingResModel> call, Throwable t) {
                // Handle API call failure
            }
        });
    }

    private long getSelectedAreaRefFromPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getLong(PREF_SELECTED_AREA_REF, 0L);
    }



    private long getSelectedAreaRefFromPreferences2(Long selectedAreaRef) {
        // Get an instance of SharedPreferences
        SharedPreferences sharedPreferences =  requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //  Long selectedAreaRef = null;
        editor.putLong(ARG_SELECTED_AREA_REF, selectedAreaRef);
        editor.apply();

        return sharedPreferences.getLong(PREF_SELECTED_AREA_REF, 0L);
    }

    @Override
    public void onAreaSelected(String selectedAreaRef) {

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(ContentValues.TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences  sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(ContentValues.TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferences5 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(ContentValues.TAG, "Project Space Ref: "+savedLoginDeviceId );
        String projectSpaceRef = saveProjectSpaceRef.trim();

        long gAAProjectSpaceTypeAreaRef = getSelectedAreaRefFromPreferences2(Long.valueOf(selectedAreaRef));
        fetchGuestControls(projectSpaceRef,gAAProjectSpaceTypeAreaRef,loginToken,loginDeviceId);
    }

    private void fetchGuestControls(String GAAProjectSpaceRef,Long AreaRef,String LoginToken, String LoginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<GuestLandingResModel> call = apiService.getControlTypeName(GAAProjectSpaceRef,AreaRef,LoginToken,LoginDeviceId);
        call.enqueue(new Callback<GuestLandingResModel>() {
            @Override
            public void onResponse(Call<GuestLandingResModel> call, Response<GuestLandingResModel> response) {
                if (response.isSuccessful()) {
                    GuestLandingResModel responseModel = response.body();
                    if (responseModel != null && responseModel.getData() != null) {
                        List<GuestControls> controlsList = responseModel.getData().getGuestControls();
                        if (controlsList != null && !controlsList.isEmpty()) {
                            List<com.gladiance.ui.models.guestlandingpage.Controls> allControls = new ArrayList<>();
                            for (GuestControls guestControls : controlsList) {
                                allControls.addAll(guestControls.getControls());
                            }
                            // Set up ControlTypeName RecyclerView
                            // Set up ControlTypeName RecyclerView
                            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2,GridLayoutManager.VERTICAL, false));
                            DeviceControlScheduleAdapter deviceControlScheduleAdapter = new DeviceControlScheduleAdapter(allControls, requireContext());
                            recyclerView.setAdapter(deviceControlScheduleAdapter);

                        }

                    }
                } else {
                    Log.e("API Response", "Unsuccessful response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GuestLandingResModel> call, Throwable t) {
                Log.e("API Error", "Error fetching controls: " + t.getMessage());
            }
        });
    }




    private final OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            AutomationActivity activity = (AutomationActivity) requireActivity();

            // Call the method in the activity
            activity.recreate(); // Replace "yourMethod()" with the actual method name you want to call
        }
    };
}