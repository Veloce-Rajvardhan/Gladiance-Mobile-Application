package com.gladiance.ui.fragment.MyProfile;

import static android.content.Context.MODE_PRIVATE;
import static org.greenrobot.eventbus.EventBus.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.activities.MyProfile.EditScheduleActivity;
import com.gladiance.ui.adapters.AreaSpinnerAdapter;
import com.gladiance.ui.adapters.ControlAdapter;
import com.gladiance.ui.adapters.DayAdapter;
import com.gladiance.ui.adapters.DeviceControlAdapter;
import com.gladiance.ui.adapters.MonthAdapter;
import com.gladiance.ui.adapters.SceneCheckAdapter;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.arealandingmodel.Area;
import com.gladiance.ui.models.arealandingmodel.ProjectAreaLandingResModel;
import com.gladiance.ui.models.guestlandingpage.GuestControls;
import com.gladiance.ui.models.guestlandingpage.GuestLandingResModel;
import com.gladiance.ui.models.lnstallerlandingpage.Controls;
import com.gladiance.ui.models.lnstallerlandingpage.Data;
import com.gladiance.ui.models.lnstallerlandingpage.InstallerControl;
import com.gladiance.ui.models.lnstallerlandingpage.InstallerLandingResModel;
import com.gladiance.ui.models.scene.Configuration;
import com.gladiance.ui.models.scene.ObjectTag;
import com.gladiance.ui.models.scene.SceneResModel;


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
    Button btnSave;

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


    public CreateScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_schedule, container, false);

        recyclerViewDay = view.findViewById(R.id.recyclerViewDay);
        recyclerViewMonth = view.findViewById(R.id.recyclerViewMonth);
        CBWeek = view.findViewById(R.id.CBWeek);
        CBMonth = view.findViewById(R.id.CBMonth);
        CBYear = view.findViewById(R.id.CBYear);
        buttonSave = view.findViewById(R.id.btnSave);
        spinner = view.findViewById(R.id.sceneAreaSpinner);
        recyclerView = view.findViewById(R.id.recycleViewDeviceName);

        ConArrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        ConfigArrayList = new ArrayList<>();

        abc();


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

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(ContentValues.TAG, "SceneList LoginDeviceId: " + GUID);
        String loginDeviceId = GUID.trim();


        EditText scheduleName = view.findViewById(R.id.ETScheduleName);
        EditText projectName = view.findViewById(R.id.ETProjectName);


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


        // Initialize minute picker
        NumberPicker datePicker = view.findViewById(R.id.DatePicker);
        datePicker.setMinValue(0);
        datePicker.setMaxValue(31); // 60 minutes
        // Set initial value
        datePicker.setValue(0); // Default value 0

        //For year
        // Initialize year picker
        NumberPicker yearPicker = view.findViewById(R.id.yearPicker);

        // Get current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Set range of years (from current year to next 10 years)
        yearPicker.setMinValue(currentYear);
        yearPicker.setMaxValue(currentYear + 10);

        // Set initial value to current year
        yearPicker.setValue(currentYear);


        //Day Recycle View
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false);
        recyclerViewDay.setLayoutManager(gridLayoutManager);
        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        dayAdapter = new DayAdapter(days);
        recyclerViewDay.setAdapter(dayAdapter);

        //Month Recycle View

        GridLayoutManager gridLayoutManagerMonth = new GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false);
        recyclerViewMonth.setLayoutManager(gridLayoutManagerMonth);
        List<String> months = Arrays.asList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );
        monthAdapter = new MonthAdapter(months);
        recyclerViewMonth.setAdapter(monthAdapter);

        // Add listener to get the selected value
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Get the selected value
                int selectedYear = yearPicker.getValue();
                Log.e(TAG, "onValueChange: "+selectedYear);
                // Do something with the selected year
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Access TextView
                //extView textView = findViewById(R.id.TVProjectName);
                // Get text from TextView
                String ScheduleName = scheduleName.getText().toString();
                String ProjectName = projectName.getText().toString();

                Log.e(TAG, "onCreateView: "+ScheduleName+" "+ProjectName);
            }
        });

        long gAAProjectSpaceTypeAreaRef = getSelectedAreaRefFromPreferences();
        Bundle bundle = getArguments();

        // Bundle bundle = getArguments();
        if (bundle != null) {
            String sceneRefString = bundle.getString("SCENE_REF");
            if (sceneRefString != null) {
                Long sceneRef = Long.parseLong(sceneRefString);
                Log.e(ContentValues.TAG, "SceneRef: " + sceneRef);
                getScene(sceneRef, loginToken, loginDeviceId);
            } else {
                Log.e(ContentValues.TAG, "SceneRef is null");
                // Handle null sceneRefString
            }
        } else {
            Log.e(ContentValues.TAG, "Bundle is null");
            // Handle null bundle
        }

        fetchGuestControls(projectSpaceRef,gAAProjectSpaceTypeAreaRef,loginToken,loginDeviceId);


        fetchAreas(projectSpaceRef,loginToken,loginDeviceId);

        return view;
    }

        private void abc() {
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

    private void getScene(Long gaaProjectSceneRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SceneResModel> call = apiService.getScene(gaaProjectSceneRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<SceneResModel>() {
            @Override
            public void onResponse(Call<SceneResModel> call, Response<SceneResModel> response) {
                if (response.isSuccessful()) {
                    SceneResModel apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getSuccessful()) {
                        ObjectTag scene = apiResponse.getObjectTag();
                        // Update EditText fields with ObjectTag data
                        editTextProjectName.setText(scene.getgAAProjectName());
                        //  editTextSpaceName.setText(scene.getGAAProjectSpaceTypeName());
                        editTextSceneName.setText(scene.getName());

                        List<Configuration> configurations = scene.getConfigurations();
                        for (Configuration configuration : configurations) {
                            Log.e(ContentValues.TAG, "Scene Planed Device Name: " + configuration.getgAAProjectSpaceTypePlannedDeviceName());

                            ConfigArrayList.add(new Configuration(configuration.getgAAProjectSceneRef(),configuration.getgAAProjectSpaceTypePlannedDeviceConnectionRef(),configuration.getNodeConfigParamName(),configuration.getgAAProjectSceneCode(),configuration.getgAAProjectSceneName(),configuration.getgAAProjectSceneCode(),configuration.getgAAProjectSpaceTypeRef(),configuration.getgAAProjectSpaceTypeName(),configuration.getgAAProjectSpaceTypeAreaRef(),configuration.getgAAProjectSpaceTypeAreaName(),configuration.getgAAProjectSpaceTypePlannedDeviceRef(),configuration.getgAAProjectSpaceTypePlannedDeviceName(),configuration.getLabel(),configuration.getOutputDriverChannelRef(),configuration.getOutputDriverChannelName(),configuration.getgAAProjectRef(),configuration.getgAAProjectName()));
                        }


//                        Bundle bundleArrayList = getArguments();
//
//                            ArrayList<String> receivedList = bundleArrayList.getStringArrayList("myArrayList");
//                            if (receivedList != null) {
//                                for (String element : receivedList) {
//                                    System.out.println("ArrayList: "+element); // Prints each element on a new line
//                                }
//                            } else {
//                                // Handle case where ArrayList is null
//                            }
                        Bundle bundle = getArguments();
                        if (bundle != null) {
                            // Retrieve data from bundle
                            String key1Value = bundle.getString("key1");
                            String key2Value = bundle.getString("key2");
                            String key3Value = bundle.getString("key3");
                            String key4Value = bundle.getString("key4");
                            // Print the values using Log statements
                            Log.d("NextFragment", "Key 1 Value: " + key1Value);
                            Log.d("NextFragment", "Key 2 Value: " + key2Value);
                            Log.d("NextFragment", "Key 3 Value: " + key3Value);
                            Log.d("NextFragment", "Key 4 Value: " + key4Value);
                            // Do something with the data
                        }

                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(gridLayoutManager1);

                        SceneCheckAdapter sceneCheckAdapter = new SceneCheckAdapter(ConArrayList,ConfigArrayList);
                        recyclerView.setAdapter(sceneCheckAdapter);

                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<SceneResModel> call, Throwable t) {
                Log.e("MainActivity", "Failed to get response");
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
                            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2,GridLayoutManager.VERTICAL, false));
                            DeviceControlAdapter deviceControlAdapter = new DeviceControlAdapter(allControls, requireContext());
                            recyclerView.setAdapter(deviceControlAdapter);

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