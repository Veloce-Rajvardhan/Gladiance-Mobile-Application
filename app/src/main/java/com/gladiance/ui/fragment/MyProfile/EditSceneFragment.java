package com.gladiance.ui.fragment.MyProfile;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gladiance.AppConstants;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.AreaSpinnerAdapter;
import com.gladiance.ui.adapters.SceneCheckAdapter;
import com.gladiance.ui.models.SceneStoreData.ConfigurationSceneEditData;
import com.gladiance.ui.models.SceneStoreData.ObjectSceneEditData;
import com.gladiance.ui.models.SceneViewModel;
import com.gladiance.ui.models.ScheduleViewModel;
import com.gladiance.ui.models.arealandingmodel.Area;
import com.gladiance.ui.models.arealandingmodel.ProjectAreaLandingResModel;
import com.gladiance.ui.models.guestlandingpage.Controls;
import com.gladiance.ui.models.guestlandingpage.GuestControls;
import com.gladiance.ui.models.guestlandingpage.GuestLandingResModel;
import com.gladiance.ui.models.lnstallerlandingpage.Data;
import com.gladiance.ui.models.lnstallerlandingpage.InstallerControl;
import com.gladiance.ui.models.lnstallerlandingpage.InstallerLandingResModel;
import com.gladiance.ui.models.saveScene.ConfigurationViewModel;
import com.gladiance.ui.models.saveScene.SaveSceneRequest;
import com.gladiance.ui.models.saveScene.SceneConfig;
import com.gladiance.ui.models.scene.Configuration;
import com.gladiance.ui.models.scene.ObjectSceneCreate;
import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.scene.ObjectTag;
import com.gladiance.ui.models.scene.SceneResModel;
import com.gladiance.ui.models.scenelist.ObjectSchedule;
import com.gladiance.ui.viewModels.SceneCreateViewModel;
import com.gladiance.ui.viewModels.SceneEditDataViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditSceneFragment extends Fragment implements AreaSpinnerAdapter.OnAreaSelectedListener  {

    private static final String ARG_SELECTED_AREA_REF = "selectedAreaRef";

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String USER_ID_KEY = "userId";

    private ArrayList<Configuration> ConfigArrayList;

    private ArrayList<Controls> ConArrayList;

    private ArrayList<Area> arrayList2;
    private static final String PREF_SELECTED_AREA_REF = "selectedAreaRef";

    EditText editTextProjectName, editTextSpaceName, editTextSceneName;

    RecyclerView recyclerView;

    Spinner spinner;
    Button buttonSave;

    private List<ConfigurationSceneEditData> configurationSceneEditData;
    private List<com.gladiance.ui.models.sceneEdit.Configuration> configurationData;

    Long gAAProjectSceneRef_object;
    Long gAAProjectSpaceTypePlannedDeviceConnectionRef_object;
    String nodeConfigParamName_object;
    String value_object;
    String nodeConfigDeviceName_object;
    String gAAProjectSceneName_object;
    String gAAProjectSceneCode_object;
    Long gAAProjectSpaceTypeRef_object;
    String gAAProjectSpaceTypeName_object;
    Integer gAAProjectSpaceTypeAreaRef2_object;
    String gAAProjectSpaceTypeAreaName_object;
    Long gAAProjectSpaceTypePlannedDeviceRef_object;
    String gAAProjectSpaceTypePlannedDeviceName_object;
    String label_object;
    Long outputDriverChannelRef_object;
    String outputDriverChannelName_object;
    Long gAAProjectRef_object;
    String gAAProjectName_object;

    private com.gladiance.ui.models.saveScene.Configuration configuration2;


    private ConfigurationViewModel viewModel;

    public EditSceneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_scene, container, false);

        ConfigArrayList = new ArrayList<>();
        ConArrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        spinner = view.findViewById(R.id.sceneAreaSpinner);
        buttonSave = view.findViewById(R.id.saveBtn);
        editTextProjectName = view.findViewById(R.id.EMProjectName);
        editTextSceneName = view.findViewById(R.id.SMSceneName);
        recyclerView = view.findViewById(R.id.recycleViewDeviceName);

        configurationSceneEditData = new ArrayList<>();
        configurationData = new ArrayList<>();

        viewModel = new ViewModelProvider(requireActivity()).get(ConfigurationViewModel.class);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "SceneList LoginDeviceId: " + GUID);
        String loginDeviceId = GUID.trim();

        //Login Token
        SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginToken = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "SceneList Login Token: " + savedLoginToken);
        String loginToken = savedLoginToken.trim();

        SharedPreferences sharedPreferences5 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
        String projectSpaceRef = saveProjectSpaceRef.trim();

        SharedPreferences sharedPreferences4 = requireContext().getSharedPreferences("MyPrefsPN", Context.MODE_PRIVATE);
        String saveProjectName = sharedPreferences4.getString("ProjectName", "");
        editTextProjectName.setText(saveProjectName);
        Log.e(TAG, "ProjectSpaceGroupActivity Project Name : "+saveProjectName );

        // Retrieve SharedPreferences object (Ref get)
        SharedPreferences sharedPreferences_ref = requireContext().getSharedPreferences("Ref_Dyn", Context.MODE_PRIVATE);
        // Retrieve the long value stored with the key "REF_DYN_KEY"
        long retrievedValue = sharedPreferences_ref.getLong("REF_DYN_KEY", 0L);
        Log.e(TAG, "REF_DYN_KEY2: "+retrievedValue);


        // Retrieve SharedPreferences object (Ref get)
        SharedPreferences sharedPreferences_name = requireContext().getSharedPreferences("Name_Dyn", Context.MODE_PRIVATE);
        String retrievedName = sharedPreferences_name.getString("NAME_DYN_KEY", "");
        Log.e(TAG, "NAME_DYN_KEY2: "+retrievedName);



        // Retrieve SharedPreferences object (Ref get)
        SharedPreferences sharedPreferences_projectspace = requireContext().getSharedPreferences("PROJECT_SPACE_REF_Dyn", Context.MODE_PRIVATE);
        long retrievedSpace = sharedPreferences_projectspace.getLong("PROJECT_SPACE_TYPE_REF_DYN_KEY", 0L);
        Log.e(TAG, "PROJECT_SPACE_TYPE_REF_DYN_KEY2: "+retrievedSpace);

        /*Intent intent = getActivity().getIntent();
        ObjScenes objScenes = (ObjScenes)intent.getSerializableExtra("objScene");
        Log.e(TAG, "Sample-- "+objScenes.getRef_dyn());*/

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

        long gAAProjectSpaceTypeAreaRef = getSelectedAreaRefFromPreferences();

        // Bundle bundle = getArguments();
        if (bundle != null) {
            String sceneRefString = bundle.getString("SCENE_REF");
            if (sceneRefString != null) {
                Long sceneRef = Long.parseLong(sceneRefString);
                Log.e(TAG, "SceneRef: " + sceneRef);
                getScene(sceneRef, loginToken, loginDeviceId);
            } else {
                Log.e(TAG, "SceneRef is null");
                // Handle null sceneRefString
            }
        } else {
            Log.e(TAG, "Bundle is null");
            // Handle null bundle
        }



        fetchGuestControlsType(projectSpaceRef,gAAProjectSpaceTypeAreaRef,loginToken,loginDeviceId);


        fetchAreas(projectSpaceRef,loginToken,loginDeviceId);



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        // Added data of Configuration
        viewModel = new ViewModelProvider(requireActivity()).get(ConfigurationViewModel.class);

        viewModel.getMatchedConfigurations().observe(getViewLifecycleOwner(), configurations -> {
            // Log the size of configurations
            Log.d("", "Configurations size: " + configurations.size());

            // Iterate through configurations and print each one
            for (com.gladiance.ui.models.saveScene.Configuration configuration : configurations) {
                Log.d("ConfigurationFragment", "Configuration: " + configuration.toString());
            }
        });


        SharedPreferences sharedPreferencesPowerState = requireContext().getSharedPreferences("MyPreferencesPS", Context.MODE_PRIVATE);
        boolean PowerState = sharedPreferencesPowerState.getBoolean("PowerState", false);
        Log.e(TAG, "EditSceneFragment Power onResume : "+PowerState);

        SharedPreferences sharedPreferencesFanSpeed = requireContext().getSharedPreferences("MyPreferencesFS", Context.MODE_PRIVATE);
        int FanSpeed = sharedPreferencesFanSpeed.getInt("FanSpeed", 0);
        Log.e(TAG, "EditSceneFragment FanSpeed onResume : "+FanSpeed);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SceneEditDataViewModel sceneViewModel2 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                LiveData<List<com.gladiance.ui.models.sceneEdit.Configuration>> objectScenesListLiveData2 = sceneViewModel2.getObjectScenesList();
                objectScenesListLiveData2.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.sceneEdit.Configuration>>() {
                            @Override
                            public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {

                                if (objectScenesList != null) {
                                    for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : objectScenesList) {

                                        Log.e(TAG, "Edit Scene Ref Final: "+objectScenes.getRef());


                                        objectScenesListLiveData2.removeObserver(this);
                                    }

                                }
                            }
                        });

//                ScheduleViewModel scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
//                LiveData<ObjectSchedule> objectScheduleLiveData = scheduleViewModel.getObjectSchedule();
//                // Observe the LiveData to get the ObjectSchedule
//                objectScheduleLiveData.observe(getViewLifecycleOwner(), new Observer<ObjectSchedule>() {
//                    @Override
//                    public void onChanged(ObjectSchedule objectSchedule) {
//                        // Now you can use objectSchedule
//                        // For example:
//                        // doSomethingWithObjectSchedule(objectSchedule);
//                        Log.d("ObjectSchedule", objectSchedule.toString());
//                        // Remember to remove the observer if necessary
//                        objectScheduleLiveData.removeObserver(this);
//                    }
//                });

//                SceneViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                LiveData<ObjectScenes> objectScheduleLiveData = sharedViewModel.getObjectSchedule();
//
//                objectScheduleLiveData.observe(getViewLifecycleOwner(), new Observer<ObjectScenes>() {
//                    @Override
//                    public void onChanged(ObjectScenes objectSchedule) {
//                        if (objectSchedule != null) {
//                            // Now you can use objectSchedule
//                            Log.d("ObjectSchedule", objectSchedule.toString());
//
//                            // Remember to remove the observer if necessary
//                            objectScheduleLiveData.removeObserver(this);
//                        }
//                    }
//                });

                viewModel.getMatchedConfigurations().observe(getViewLifecycleOwner(), configurations -> {
                    Log.d(TAG, "onChanged triggered with configurations size: " + configurations.size());
                    for (com.gladiance.ui.models.saveScene.Configuration configuration : configurations) {
                        Log.d(TAG, "Configuration: " + configuration.toString());
                        Log.d(TAG, "Configuration22: " + configuration.getRef());

                    }
                });


                // Retrieve the arguments bundle
                Bundle args = getArguments();
                if (args != null) {
                    ArrayList<String> mData = (ArrayList<String>) args.getSerializable("myArrayList");
                    if (mData != null) {
                        for (String item : mData) {
                            System.out.println("ArrayList Data2: "+item); // or use Log.d() for logging in Android
                        }
                    }
                }
//                for (String item : MY_CONSTANT_LIST) {
//                    System.out.println("NewList4: "+item);
//                }

                // Retrieve data from the bundle
                ArrayList<String> constantData = args.getStringArrayList("constantData");
                // Use the retrieved data as needed
                if (constantData != null) {
                    for (String data : constantData) {
                        // Do something with the data
                        System.out.println("NewList5: "+data);
                    }
                }

                // Retrieve SharedPreferences object (Ref get)
                SharedPreferences sharedPreferences_ref = requireContext().getSharedPreferences("Ref_Dyn", Context.MODE_PRIVATE);
                // Retrieve the long value stored with the key "REF_DYN_KEY"
                long retrievedValue = sharedPreferences_ref.getLong("REF_DYN_KEY", 0L);
                Log.e(TAG, "REF_DYN_KEY2: "+retrievedValue);


                // Retrieve SharedPreferences object (Ref get)
                SharedPreferences sharedPreferences_name = requireContext().getSharedPreferences("Name_Dyn", Context.MODE_PRIVATE);
                String retrievedName = sharedPreferences_name.getString("NAME_DYN_KEY", "");
                Log.e(TAG, "NAME_DYN_KEY2: "+retrievedName);


                // Retrieve SharedPreferences object (Ref get)
                SharedPreferences sharedPreferences_projectspace = requireContext().getSharedPreferences("PROJECT_SPACE_REF_Dyn", Context.MODE_PRIVATE);
                long retrievedSpace = sharedPreferences_projectspace.getLong("PROJECT_SPACE_TYPE_REF_DYN_KEY", 0L);
                Log.e(TAG, "PROJECT_SPACE_TYPE_REF_DYN_KEY2: "+retrievedSpace);


                Log.e("APPCONSTS",""+ AppConstants.Ref_dyn);
                Log.e("APPCONSTS",""+AppConstants.Name_dyn);
                Log.e("APPCONSTS",""+AppConstants.SceneRef);
                Log.e("APPCONSTS",""+AppConstants.Space_dyn);
                Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
                Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
                Log.e("APPCONSTS",""+ AppConstants.powerState);
                Log.e("APPCONSTS",""+AppConstants.power);

//                List<SceneConfig> list = new ArrayList<>();
//                for(int i = 0; i <ConArrayList.size(); i++){
//                    if(ConArrayList.get(i).isChecked() == true){
//                        Log.e("ConArrayList","Selected -- "+ConArrayList.get(i).getgAAProjectSpaceTypePlannedDeviceName());
//                        list.add(new SceneConfig(
//                                Long.parseLong(AppConstants.SceneRef),
//                                ConArrayList.get(i).getgAAProjectSpaceTypePlannedDeviceRef(),
//                                ConArrayList.get(i).getgAAProjectSpaceTypePlannedDeviceName(),
//                                AppConstants.powerState,
//                                AppConstants.power
//                        ));
//                    }
//                }

                //     List<SceneConfig> list = new ArrayList<>();
//                for(int i = 0; i <ConArrayList.size(); i++){
//                    if(ConArrayList.get(i).isChecked() == true){
//                        Log.e("ConArrayList","Selected -- "+ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
//                        list.add(new SceneConfig(
//                                Long.parseLong(AppConstants.SceneRef),
//                                ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceRef(),
//                                ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName(),
//                                AppConstants.powerState,
//                                AppConstants.power
//                        ));
//                    }
//                }

                List<SceneConfig> list = new ArrayList<>();
//                SceneViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                LiveData<List<ObjectScenes>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
//                objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectScenes>>() {
//                    @Override
//                    public void onChanged(List<ObjectScenes> objectScenesList) {
////                        for(int i = 0; i <ConArrayList.size(); i++) {
////                            if (ConArrayList.get(i).isChecked() == true) {
////                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
//                        if (objectScenesList != null) {
//                            for (ObjectScenes objectScenes : objectScenesList) {
//
//                                list.add(new SceneConfig(
//                                        Long.parseLong(objectScenes.getRef()),
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

                SceneEditDataViewModel sceneViewModel1 = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
                LiveData<List<com.gladiance.ui.models.sceneEdit.Configuration>> objectScenesListLiveData1 = sceneViewModel1.getObjectScenesList();
                objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.sceneEdit.Configuration>>() {
                    @Override
                    public void onChanged(List<com.gladiance.ui.models.sceneEdit.Configuration> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                        if (objectScenesList != null) {
                            for (com.gladiance.ui.models.sceneEdit.Configuration objectScenes : objectScenesList) {

                        list.add(new SceneConfig(
                                objectScenes.getRef(),
                                objectScenes.getgAAProjectSceneRef(),
                                objectScenes.getgAAProjectSpaceTypePlannedDeviceRef(),
                                objectScenes.getNodeConfigDeviceName(),
                                objectScenes.getNodeConfigParamName(),
                                objectScenes.getValue()
                        ));
                                Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//                        Log.d("getProjectSpaceTypePlannedDeviceName", objectScenes.getProjectSpaceTypePlannedDeviceName());
//                        Log.d("getGaaProjectSpaceTypePlannedDeviceRef", objectScenes.getGaaProjectSpaceTypePlannedDeviceRef());
//                        Log.d("getNodeConfigParamName", objectScenes.getNodeConfigParamName());
//                        Log.d("getValue", objectScenes.getValue());
                            }

                            // Remember to remove the observer if necessary
                            objectScenesListLiveData1.removeObserver(this);
                        }
                        //   }
                        //}
                    }
                });

                SaveSceneRequest saveScene = new SaveSceneRequest(
                        Long.parseLong(AppConstants.Ref_dyn),
                        AppConstants.Name_dyn,
                        Long.parseLong(AppConstants.Space_dyn),
                        list);
                // uncomment to hit api
                sendSaveSceneRequest(saveScene);

            }


            private void sendSaveSceneRequest(SaveSceneRequest saveScene) {
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

                Call<SceneResModel> call = apiService.saveScene(saveScene);
                call.enqueue(new Callback<SceneResModel>() {
                    @Override
                    public void onResponse(Call<SceneResModel> call, Response<SceneResModel> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response
                            SceneResModel sceneResModel = response.body();
                            Log.e("Successful", "Success: " + sceneResModel.getSuccessful());
                            Toast.makeText(getContext().getApplicationContext(), "Scene Edited Successfully!", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Done ");
                            Log.e("Edit Schedule", "Message: " + sceneResModel.getMessage());

                            ObjectScenes objectScenes = new ObjectScenes(null,null,null,null,null,null,null,null,null);


// Reset the object using one of the methods above
                            objectScenes.clear(); // Option 1
                            AppConstants.Create_Ref_dyn_Schedule = "null";
// Or use setters if available
                            objectScenes.setRef_dyn("");
                            objectScenes.setName_dyn("");
                            objectScenes.setSpace_dyn("");
                            objectScenes.setSceneRef("");
                            objectScenes.setProjectSpaceTypePlannedDeviceName("");
                            objectScenes.setGaaProjectSpaceTypePlannedDeviceRef("");
                            objectScenes.setNodeConfigParamName("");
                            objectScenes.setValue("");

// Set other fields as needed

// Add to sharedViewModel
                            SceneViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
                            //      sharedViewModel.addObjectSchedule(objectSchedule);

                            // Example of clearing all ObjectSchedule instances
                            sharedViewModel.clearObjectScene();


                        } else {
                            // Handle unsuccessful response
                        }
                    }

                    @Override
                    public void onFailure(Call<SceneResModel> call, Throwable t) {
                        // Handle API call failure
                        Log.e(TAG, "Failure");
                    }
                });
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
                            Log.e(TAG, "Scene Planed Device Name: " + configuration.getgAAProjectSpaceTypePlannedDeviceName());

                            ConfigArrayList.add(new Configuration(configuration.getgAAProjectSceneRef(),configuration.getgAAProjectSpaceTypePlannedDeviceConnectionRef(),configuration.getNodeConfigParamName(),configuration.getgAAProjectSceneCode(),configuration.getgAAProjectSceneName(),configuration.getgAAProjectSceneCode(),configuration.getgAAProjectSpaceTypeRef(),configuration.getgAAProjectSpaceTypeName(),configuration.getgAAProjectSpaceTypeAreaRef(),configuration.getgAAProjectSpaceTypeAreaName(),configuration.getgAAProjectSpaceTypePlannedDeviceRef(),configuration.getgAAProjectSpaceTypePlannedDeviceName(),configuration.getLabel(),configuration.getOutputDriverChannelRef(),configuration.getOutputDriverChannelName(),configuration.getgAAProjectRef(),configuration.getgAAProjectName(), configuration.getRef()));

                            ////  Data stored
//                            gAAProjectSceneRef = configuration.getgAAProjectSceneRef();
//                            gAAProjectSpaceTypePlannedDeviceConnectionRef = configuration.getgAAProjectSpaceTypePlannedDeviceConnectionRef();
//                            nodeConfigParamName = configuration.getNodeConfigParamName();
//                            value = configuration.getValue();
//                            nodeConfigDeviceName = configuration.getgAAProjectSpaceTypePlannedDeviceName();
//                            gAAProjectSceneName = configuration.getgAAProjectSceneName();
//                            gAAProjectSceneCode = configuration.getgAAProjectSceneCode();
//                            gAAProjectSpaceTypeRef = configuration.getgAAProjectSpaceTypeRef();
//                            gAAProjectSpaceTypeName = configuration.getgAAProjectSpaceTypeName();
//                            gAAProjectSpaceTypeAreaRef = configuration.getgAAProjectSpaceTypeAreaRef();
//                            gAAProjectSpaceTypeAreaName = configuration.getgAAProjectSpaceTypeAreaName();
//                            gAAProjectSpaceTypePlannedDeviceRef = configuration.getgAAProjectSpaceTypePlannedDeviceRef();
//                            gAAProjectSpaceTypePlannedDeviceName = configuration.getgAAProjectSpaceTypePlannedDeviceName();
//                            label = configuration.getLabel();
//                            outputDriverChannelRef = configuration.getOutputDriverChannelRef();
//                            outputDriverChannelName = configuration.getOutputDriverChannelName();
//                            gAAProjectRef = configuration.getgAAProjectRef();
//                            gAAProjectName = configuration.getgAAProjectName();

                            configurationSceneEditData.add(new ConfigurationSceneEditData(configuration.getgAAProjectSceneRef(),
                                    configuration.getgAAProjectSpaceTypePlannedDeviceConnectionRef(),
                                    configuration.getNodeConfigParamName(),
                                    configuration.getValue(),
                                    configuration.getgAAProjectSpaceTypePlannedDeviceName(),
                                    configuration.getgAAProjectSceneName(),
                                    configuration.getgAAProjectSceneCode(),
                                    configuration.getgAAProjectSpaceTypeRef(),
                                    configuration.getgAAProjectSpaceTypeName(),
                                    configuration.getgAAProjectSpaceTypeAreaRef(),
                                    configuration.getgAAProjectSpaceTypeAreaName(),
                                    configuration.getgAAProjectSpaceTypePlannedDeviceRef(),
                                    configuration.getgAAProjectSpaceTypePlannedDeviceName(),
                                    configuration.getLabel(),
                                    configuration.getOutputDriverChannelRef(),
                                    configuration.getOutputDriverChannelName(),
                                    configuration.getgAAProjectRef(),
                                    configuration.getgAAProjectName()));


//                            configurationData.add(new com.gladiance.ui.models.sceneEdit.Configuration(configuration.getRef(),
//                                    configuration.getgAAProjectSceneRef(),
//                                    configuration.getgAAProjectSpaceTypePlannedDeviceRef(),
//                                    configuration.getNodeConfigDeviceName(),
//                                    configuration.getNodeConfigParamName(),
//                                    configuration.getValue()));

//                            if(AppConstants.DataEnterIntoViewModel == true) {
//                                com.gladiance.ui.models.sceneEdit.Configuration configuration1 = new com.gladiance.ui.models.sceneEdit.Configuration(
//                                        configuration.getRef(),
//                                        configuration.getgAAProjectSceneRef(),
//                                        configuration.getgAAProjectSpaceTypePlannedDeviceRef(),
//                                        configuration.getNodeConfigDeviceName(),
//                                        configuration.getNodeConfigParamName(),
//                                        configuration.getValue());
//
//                                SceneEditDataViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
//                                // sharedViewModel.setObjecatSchedule(objectScenes);
//                                sharedViewModelEdit.addObjectEditScenes(configuration1);
//                                Log.e(TAG, "V Ref: " + configuration1.getRef());
//                                Log.e(TAG, "V SceneRef: " + configuration1.getgAAProjectSceneRef());
//                                Log.e(TAG, "V GaaProjectSpaceTypePlannedDeviceRef: " + configuration1.getgAAProjectSpaceTypePlannedDeviceRef());
//                                Log.e(TAG, "V NodeConfigDeviceName: " + configuration1.getNodeConfigDeviceName());
//                                Log.e(TAG, "V NodeConfigParamName: " + configuration1.getNodeConfigParamName());
//                                Log.e(TAG, "V Value: " + configuration1.getValue());
//                                Log.e(TAG, "------------------------------------------------");
//
////                            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
////                            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
////                            // sharedViewModel.setObjecatSchedule(objectScenes);
////                            sharedViewModelEdit.addObjectScenes(objectScenes);
////                            for (com.gladiance.ui.models.scheduleStoreData.Configuration config : configurationsData) {
////                                // Print the GAAProjectSpaceTypePlannedDeviceRef for each configuration
////                                System.out.println("dataaa: "+config.getGAAProjectSpaceTypePlannedDeviceRef());
////                            }
//                                AppConstants.DataEnterIntoViewModel = false;
//                            }
                        }


                        if(AppConstants.DataEnterIntoViewModel == true) {
                            for (Configuration configuration : configurations) {

                                if (AppConstants.DataEnterIntoViewModel == true) {
                                    com.gladiance.ui.models.sceneEdit.Configuration configuration1 = new com.gladiance.ui.models.sceneEdit.Configuration(
                                            configuration.getRef(),
                                            configuration.getgAAProjectSceneRef(),
                                            configuration.getgAAProjectSpaceTypePlannedDeviceRef(),
                                            configuration.getNodeConfigDeviceName(),
                                            configuration.getNodeConfigParamName(),
                                            configuration.getValue());

                                    SceneEditDataViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);

                                    sharedViewModelEdit.addObjectEditScenes(configuration1);
                                    Log.e(TAG, "V Ref: " + configuration1.getRef());
                                    Log.e(TAG, "V SceneRef: " + configuration1.getgAAProjectSceneRef());
                                    Log.e(TAG, "V GaaProjectSpaceTypePlannedDeviceRef: " + configuration1.getgAAProjectSpaceTypePlannedDeviceRef());
                                    Log.e(TAG, "V NodeConfigDeviceName: " + configuration1.getNodeConfigDeviceName());
                                    Log.e(TAG, "V NodeConfigParamName: " + configuration1.getNodeConfigParamName());
                                    Log.e(TAG, "V Value: " + configuration1.getValue());
                                    Log.e(TAG, "------------------------------------------------");

                                }
                            }
                        }

                        AppConstants.DataEnterIntoViewModel = false;

                        for (ConfigurationSceneEditData config : configurationSceneEditData) {
                            // Print the GAAProjectSpaceTypePlannedDeviceRef for each configuration
                            System.out.println("dataaa: "+config.getGAAProjectSpaceTypePlannedDeviceRef());
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

                        ConfigurationViewModel viewModel = new ViewModelProvider(requireActivity()).get(ConfigurationViewModel.class);

                        for (Configuration configuration : ConfigArrayList) {
                            //    if (configuration.getgAAProjectSpaceTypePlannedDeviceRef().equals(control.getgAAProjectSpaceTypePlannedDeviceRef())) {

                            // Log the values for debugging
                            //  Log.e(TAG, "onBindViewHolder: " + configuration.getgAAProjectSpaceTypePlannedDeviceRef() + " " + control.getgAAProjectSpaceTypePlannedDeviceRef());

                //            if (viewModel.getMatchedConfigurations() == null) {
                                viewModel.addMatchedConfiguration(new com.gladiance.ui.models.saveScene.Configuration(
                                        configuration.getgAAProjectSceneRef(),
                                        configuration.getNodeConfigParamName(),
                                        configuration.getValue(),
                                        configuration.getgAAProjectSpaceTypePlannedDeviceName(),
                                        configuration.getRef(),
                                        configuration.getgAAProjectSpaceTypePlannedDeviceRef()
                                ));

                                //    viewModel.addMatchedConfiguration(matchedConfigurations);

                                // Logging the size of matchedConfigurations
//                int sizeOfMatchedConfigurations = matchedConfigurations.size();
//                Log.d("MatchedConfigurations", "Size of matchedConfigurations list: " + sizeOfMatchedConfigurations);
                                // Assuming you are doing something with your ViewHolder here
                                //  holder.deviceNameCheckBox.setChecked(true);

                                // Exit the loop once a match is found
                                //   break;
                                //       }
                    //        }
                        }

//                        SceneEditDataViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneEditDataViewModel.class);
//                        LiveData<List<ObjectSceneEditData>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
//                        objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
//                            @Override
//                            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
////                        for(int i = 0; i <ConArrayList.size(); i++) {
////                            if (ConArrayList.get(i).isChecked() == true) {
////                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
//                                if (objectScenesList != null) {
//                                    for (ObjectSceneCreate objectScenes : objectScenesList) {
//
//                                    }


                        SceneCheckAdapter sceneCheckAdapter = new SceneCheckAdapter(ConArrayList,ConfigArrayList,viewModel, requireContext());
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

    private void fetchGuestControlsType(String GAAProjectSpaceRef,Long AreaRef,String LoginToken, String LoginDeviceId) {
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
                                ConArrayList.addAll(guestControls.getControls());
                            }

                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(gridLayoutManager1);
                            SceneCheckAdapter sceneCheckAdapter = new SceneCheckAdapter(ConArrayList,ConfigArrayList,viewModel, requireContext());
                            recyclerView.setAdapter(sceneCheckAdapter);

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
                        Log.e(TAG, "onResponse AreaName: " + area.getGAAProjectSpaceTypeAreaName());
                        Log.e(TAG, "onResponse Area Ref: " + area.getGAAProjectSpaceTypeAreaRef());
                        arrayList2.add(new Area(area.getGAAProjectSpaceTypeAreaRef(), area.getGAAProjectSpaceTypeAreaName(), area.getWifiSSID(), area.getWifiPassword(), area.getGuestControls(), area.getInstallerControls()));

                        if (area.getGAAProjectSpaceTypeAreaRef() == 0) {

                        }
                    }
                    // Create custom spinner adapter
                    AreaSpinnerAdapter adapter = new AreaSpinnerAdapter(requireContext(),spinner,areas);

                    // Set the listener for the adapter
                    adapter.setOnAreaSelectedListener(EditSceneFragment.this); // Replace YourFragmentClassName with the name of your fragment class

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
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();

        SharedPreferences  sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferences5 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: "+savedLoginDeviceId );
        String projectSpaceRef = saveProjectSpaceRef.trim();

        long gAAProjectSpaceTypeAreaRef = getSelectedAreaRefFromPreferences2(Long.valueOf(selectedAreaRef));
        fetchGuestControlsType(projectSpaceRef,gAAProjectSpaceTypeAreaRef,loginToken,loginDeviceId);
    }

    private final OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

// Replace the current fragment with the first fragment
            transaction.replace(R.id.set_mood, new MyMoodFragment());

// Add the transaction to the back stack
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    };
}