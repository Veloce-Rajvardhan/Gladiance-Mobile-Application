package com.gladiance.ui.fragment.MyProfile;

import static android.content.Context.MODE_PRIVATE;
import static org.greenrobot.eventbus.EventBus.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.activities.MyProfile.SetYourMoodActivity;
import com.gladiance.ui.adapters.AreaSpinnerAdapter;
import com.gladiance.ui.adapters.CreateSceneCheckAdapter;
import com.gladiance.ui.adapters.SceneCheckAdapter;
import com.gladiance.ui.adapters.SceneCreateAdapter;
import com.gladiance.ui.models.SceneStoreData.ConfigurationSceneEditData;
import com.gladiance.ui.models.SceneViewModel;
import com.gladiance.ui.models.ScheduleViewModel;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.arealandingmodel.Area;
import com.gladiance.ui.models.arealandingmodel.ProjectAreaLandingResModel;
import com.gladiance.ui.models.guestlandingpage.Controls;
import com.gladiance.ui.models.guestlandingpage.GuestControls;
import com.gladiance.ui.models.guestlandingpage.GuestLandingResModel;

import com.gladiance.ui.models.lnstallerlandingpage.Data;
import com.gladiance.ui.models.lnstallerlandingpage.InstallerControl;
import com.gladiance.ui.models.lnstallerlandingpage.InstallerLandingResModel;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMoodFragment extends Fragment implements AreaSpinnerAdapter.OnAreaSelectedListener {

    private static final String ARG_SELECTED_AREA_REF = "selectedAreaRef";

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String USER_ID_KEY = "userId";

    private ArrayList<Configuration> ConfigArrayList;

    private ArrayList<Controls> ConArrayList;

    private ArrayList<Area> arrayList2;
    private static final String PREF_SELECTED_AREA_REF = "selectedAreaRef";

    EditText editTextProjectName, editTextSpaceName, editTextSceneName;

    RecyclerView recyclerView;
    private List<com.gladiance.ui.models.sceneEdit.Configuration> sharedViewModelEditData;


    Spinner spinner;
    Button buttonSave;

    SetYourMoodActivity setYourMoodActivity;
    private EditSceneFragment editSceneFragment;

    private SceneCreateViewModel sceneCreateViewModel;
    private List<ConfigurationSceneEditData> configurationSceneEditData;

    public CreateMoodFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SetYourMoodActivity) {
            setYourMoodActivity = (SetYourMoodActivity) context;
        } else {
            throw new ClassCastException("Activity must be SetYourMoodActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_mood, container, false);



        ConfigArrayList = new ArrayList<>();
        ConArrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        spinner = view.findViewById(R.id.sceneAreaSpinner);
        buttonSave = view.findViewById(R.id.saveBtn);
        editTextProjectName = view.findViewById(R.id.CMProjectName);
        editTextSceneName = view.findViewById(R.id.SceneName);
        recyclerView = view.findViewById(R.id.recycleViewDeviceName);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "SceneList LoginDeviceId: " + GUID);
        String loginDeviceId = GUID.trim();

        //Login Token
        SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginToken = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "SceneList Login Token: " + savedLoginToken);
        String loginToken = savedLoginToken.trim();

        SharedPreferences sharedPreferences6 = requireContext().getSharedPreferences("MyPrefsPN", Context.MODE_PRIVATE);
        String saveProjectName = sharedPreferences6.getString("ProjectName", "");
        editTextProjectName.setText(saveProjectName);
        Log.e(ContentValues.TAG, "Create Mood Fragment Project Name : "+saveProjectName );

        SharedPreferences sharedPreferences5 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: " + saveProjectSpaceRef);
        String projectSpaceRef = saveProjectSpaceRef.trim();


        // Retrieve SharedPreferences object (Ref get)
        SharedPreferences sharedPreferences_ref = requireContext().getSharedPreferences("Ref_Dyn", Context.MODE_PRIVATE);
        // Retrieve the long value stored with the key "REF_DYN_KEY"
        long retrievedValue = sharedPreferences_ref.getLong("REF_DYN_KEY", 0L);
        Log.e(TAG, "REF_DYN_KEY2: " + retrievedValue);


        // Retrieve SharedPreferences object (Ref get)
        SharedPreferences sharedPreferences_name = requireContext().getSharedPreferences("Name_Dyn", Context.MODE_PRIVATE);
        String retrievedName = sharedPreferences_name.getString("NAME_DYN_KEY", "");
        Log.e(TAG, "NAME_DYN_KEY2: " + retrievedName);


        // Retrieve SharedPreferences object (Ref get)
        SharedPreferences sharedPreferences_projectspace = requireContext().getSharedPreferences("PROJECT_SPACE_REF_Dyn", Context.MODE_PRIVATE);
        long retrievedSpace = sharedPreferences_projectspace.getLong("PROJECT_SPACE_TYPE_REF_DYN_KEY", 0L);
        Log.e(TAG, "PROJECT_SPACE_TYPE_REF_DYN_KEY2: " + retrievedSpace);

        // GAAProjectSpaceTypeRef
        SharedPreferences sharedPreferences4 = requireContext().getSharedPreferences("MyPrefsPSTR", MODE_PRIVATE);
        String saveProjectSpaceTypeRef = sharedPreferences4.getString("Project_Space_Type_Ref", "");
        Log.e(ContentValues.TAG, "Project Space Type Ref: " + saveProjectSpaceTypeRef);
        String gaaProjectSpaceTypeRef = saveProjectSpaceTypeRef.trim();
        AppConstants.Create_Space_dyn = gaaProjectSpaceTypeRef;


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
             //   getScene(sceneRef, loginToken, loginDeviceId);
            } else {
                Log.e(TAG, "SceneRef is null");
                // Handle null sceneRefString
            }
        } else {
            Log.e(TAG, "Bundle is null");
            // Handle null bundle
        }

        if(AppConstants.Create_Ref_dyn == "null") {
            getRef();
        }
        fetchGuestControlsType(projectSpaceRef, gAAProjectSpaceTypeAreaRef, loginToken, loginDeviceId);


        fetchAreas(projectSpaceRef, loginToken, loginDeviceId);

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
                        AppConstants.Create_Ref_dyn = responseModel.getTag();
                        AppConstants.Create_SceneRef = responseModel.getTag();

                        Log.d(TAG, "Success: " + success + ", Message: " + message + " Tag: " + Ref);

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
        setYourMoodActivity = (SetYourMoodActivity) requireActivity();
        SharedPreferences sharedPreferencesPowerState = requireContext().getSharedPreferences("MyPreferencesPS", Context.MODE_PRIVATE);
        boolean PowerState = sharedPreferencesPowerState.getBoolean("PowerState", false);
        Log.e(TAG, "EditSceneFragment Power onResume : " + PowerState);

        SharedPreferences sharedPreferencesFanSpeed = requireContext().getSharedPreferences("MyPreferencesFS", Context.MODE_PRIVATE);
        int FanSpeed = sharedPreferencesFanSpeed.getInt("FanSpeed", 0);
        Log.e(TAG, "EditSceneFragment FanSpeed onResume : " + FanSpeed);


        SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
        ///////////////////////////////////////////////

        int size = sceneViewModel.getObjectScenesListSize();
        if (size == 0) {
            Log.e(TAG, "list is 0" );
            AppConstants.DataCreateScene = true;
            // Handle the case where the list size is 0
        } else if (size == 1) {
            Log.e(TAG, "list is 1" );

            // Handle the case where the list size is 1
        } else {
            Log.e(TAG, "list is else" );

            // Handle the case where the list size is greater than 1
        }



        sceneViewModel.getObjectScenesList().observe(this, new Observer<List<ObjectSceneCreate>>() {
            @Override
            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
                // Check if the list is null
                if (objectScenesList != null) {
                    // Check the size of the list
                    switch (objectScenesList.size()) {
                        case 0:
                            // Handle the case where the list size is 0
                            System.out.println("The list is empty.");
                            break;
                        case 1:
                            // Handle the case where the list size is 1
                            System.out.println("The list contains one item.");
                            break;
                        default:
                            // Handle the case where the list size is greater than 1
                            System.out.println("The list contains more than one item.");
                            break;
                    }
                } else {
                    // Handle the case where the list is null
                    System.out.println("The list is null.");
                }
            }
        });
        //////////////////////////////////////////////










        LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
        objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
            @Override
            public void onChanged(List<ObjectSceneCreate> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                if (objectScenesList != null) {
                    for (ObjectSceneCreate objectScenes : objectScenesList) {

                    }

                    // Remember to remove the observer if necessary
                    objectScenesListLiveData.removeObserver(this);
                }
                //   }
                //}
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the arguments bundle
                Bundle args = getArguments();
                if (args != null) {
                    ArrayList<String> mData = (ArrayList<String>) args.getSerializable("myArrayList");
                    if (mData != null) {
                        for (String item : mData) {
                            System.out.println("ArrayList Data2: " + item); // or use Log.d() for logging in Android
                        }
                    }
                }
//                for (String item : MY_CONSTANT_LIST) {
//                    System.out.println("NewList4: "+item);
//                }
                AppConstants.Create_Name_dyn = String.valueOf(editTextSceneName.getText());

                // Retrieve data from the bundle
//        ArrayList<String> constantData = args.getStringArrayList("constantData");
//        // Use the retrieved data as needed
//        if (constantData != null) {
//        for (String data : constantData) {
//        // Do something with the data
//        System.out.println("NewList5: "+data);
//        }
//        }

                // Retrieve SharedPreferences object (Ref get)
                SharedPreferences sharedPreferences_ref = requireContext().getSharedPreferences("Ref_Dyn", Context.MODE_PRIVATE);
                // Retrieve the long value stored with the key "REF_DYN_KEY"
                long retrievedValue = sharedPreferences_ref.getLong("REF_DYN_KEY", 0L);
                Log.e(TAG, "REF_DYN_KEY2: " + retrievedValue);


                // Retrieve SharedPreferences object (Ref get)
                SharedPreferences sharedPreferences_name = requireContext().getSharedPreferences("Name_Dyn", Context.MODE_PRIVATE);
                String retrievedName = sharedPreferences_name.getString("NAME_DYN_KEY", "");
                Log.e(TAG, "NAME_DYN_KEY2: " + retrievedName);


                // Retrieve SharedPreferences object (Ref get)
                SharedPreferences sharedPreferences_projectspace = requireContext().getSharedPreferences("PROJECT_SPACE_REF_Dyn", Context.MODE_PRIVATE);
                long retrievedSpace = sharedPreferences_projectspace.getLong("PROJECT_SPACE_TYPE_REF_DYN_KEY", 0L);
                Log.e(TAG, "PROJECT_SPACE_TYPE_REF_DYN_KEY2: " + retrievedSpace);


                Log.e("APPCONSTS", "" + AppConstants.Create_Ref_dyn);
                Log.e("APPCONSTS", "" + AppConstants.Create_Name_dyn);
                Log.e("APPCONSTS", "" + AppConstants.Create_Space_dyn);
                Log.e("APPCONSTS", "" + AppConstants.Create_SceneRef);
                Log.e("APPCONSTS", "" + AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
                Log.e("APPCONSTS", "" + AppConstants.Create_projectSpaceTypePlannedDeviceName);
                Log.e("APPCONSTS", "" + AppConstants.Create_powerState);
                Log.e("APPCONSTS", "" + AppConstants.Create_power);

              //  List<SceneConfig> list = new ArrayList<>();

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
                List<SceneConfig> list = new ArrayList<>();
                SceneCreateViewModel sceneViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                LiveData<List<ObjectSceneCreate>> objectScenesListLiveData = sceneViewModel.getObjectScenesList();
                objectScenesListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ObjectSceneCreate>>() {
                    @Override
                    public void onChanged(List<ObjectSceneCreate> objectScenesList) {
//                        for(int i = 0; i <ConArrayList.size(); i++) {
//                            if (ConArrayList.get(i).isChecked() == true) {
//                                Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                        if (objectScenesList != null) {
                            for (ObjectSceneCreate objectScenes : objectScenesList) {

                                list.add(new SceneConfig(
                                        Long.parseLong(objectScenes.getRef()),
                                        Long.parseLong(objectScenes.getSceneRef()),
                                        Long.parseLong(objectScenes.getProjectSpaceTypePlannedDeviceName()),
                                        objectScenes.getGaaProjectSpaceTypePlannedDeviceRef(),
                                        objectScenes.getNodeConfigParamName(),
                                        objectScenes.getValue()
                                ));
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

                SaveSceneRequest saveScene = new SaveSceneRequest(
                        Long.parseLong(AppConstants.Create_Ref_dyn),
                        AppConstants.Create_Name_dyn,
                        Long.parseLong(AppConstants.Create_Space_dyn),
                        list);
                if(list!=null) {
          //          sendSaveSceneRequest(saveScene);
                }
                else{
                    Toast.makeText(requireContext(), "List is Empty", Toast.LENGTH_SHORT).show();

                }/////

//                SaveSceneRequest saveScene = new SaveSceneRequest(
//                        Long.parseLong(AppConstants.Create_Ref_dyn),
//                        AppConstants.Create_Name_dyn,
//                        Long.parseLong(AppConstants.Create_SceneRef),
//                        list);
//                sendSaveSceneRequest(saveScene);
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

                            if(sceneResModel.getSuccessful() == true) {
                                Toast.makeText(getContext().getApplicationContext(), "Scene Created Successfully!", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Done ");
                                Log.e("Successful", "Success: " + sceneResModel.getSuccessful());
                                Log.e("Create Schedule", "Message: " + sceneResModel.getMessage());


                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(null, null, null, null, null, null, null, null, null);


// Reset the object using one of the methods above
                                objectSceneCreate.clear(); // Option 1
                                AppConstants.Create_Ref_dyn = "null";
// Or use setters if available
                                objectSceneCreate.setRef_dyn("");
                                objectSceneCreate.setName_dyn("");
                                objectSceneCreate.setSpace_dyn("");
                                objectSceneCreate.setSceneRef("");
                                objectSceneCreate.setProjectSpaceTypePlannedDeviceName("");
                                objectSceneCreate.setGaaProjectSpaceTypePlannedDeviceRef("");
                                objectSceneCreate.setNodeConfigParamName("");
                                objectSceneCreate.setValue("");

// Set other fields as needed

// Add to sharedViewModel
                                SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                //      sharedViewModel.addObjectSchedule(objectSchedule);

                                // Example of clearing all ObjectSchedule instances
                                sharedViewModel.clearObjectCreateScene();
                            }else {
                                Toast.makeText(getContext().getApplicationContext(), "Scene Create Failed! Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                                ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(null, null, null, null, null, null, null, null, null);


// Reset the object using one of the methods above
                                objectSceneCreate.clear(); // Option 1
                                AppConstants.Create_Ref_dyn = "null";
// Or use setters if available
                                objectSceneCreate.setRef_dyn("");
                                objectSceneCreate.setName_dyn("");
                                objectSceneCreate.setSpace_dyn("");
                                objectSceneCreate.setSceneRef("");
                                objectSceneCreate.setProjectSpaceTypePlannedDeviceName("");
                                objectSceneCreate.setGaaProjectSpaceTypePlannedDeviceRef("");
                                objectSceneCreate.setNodeConfigParamName("");
                                objectSceneCreate.setValue("");

// Set other fields as needed

// Add to sharedViewModel
                                SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
                                //      sharedViewModel.addObjectSchedule(objectSchedule);

                                // Example of clearing all ObjectSchedule instances
                                sharedViewModel.clearObjectCreateScene();
                            }
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
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//        Call<SceneResModel> call = apiService.getScene(gaaProjectSceneRef, loginToken, loginDeviceId);
//        call.enqueue(new Callback<SceneResModel>() {
//@Override
//public void onResponse(Call<SceneResModel> call, Response<SceneResModel> response) {
//        if (response.isSuccessful()) {
//        SceneResModel apiResponse = response.body();
//        if (apiResponse != null && apiResponse.getSuccessful()) {
//        ObjectTag scene = apiResponse.getObjectTag();
//        // Update EditText fields with ObjectTag data
//        editTextProjectName.setText(scene.getgAAProjectName());
//        //  editTextSpaceName.setText(scene.getGAAProjectSpaceTypeName());
//        editTextSceneName.setText(scene.getName());
//
//        List<Configuration> configurations = scene.getConfigurations();
//        for (Configuration configuration : configurations) {
//        Log.e(TAG, "Scene Planed Device Name: " + configuration.getgAAProjectSpaceTypePlannedDeviceName());
//
//        ConfigArrayList.add(new Configuration(configuration.getgAAProjectSceneRef(),configuration.getgAAProjectSpaceTypePlannedDeviceConnectionRef(),configuration.getNodeConfigParamName(),configuration.getgAAProjectSceneCode(),configuration.getgAAProjectSceneName(),configuration.getgAAProjectSceneCode(),configuration.getgAAProjectSpaceTypeRef(),configuration.getgAAProjectSpaceTypeName(),configuration.getgAAProjectSpaceTypeAreaRef(),configuration.getgAAProjectSpaceTypeAreaName(),configuration.getgAAProjectSpaceTypePlannedDeviceRef(),configuration.getgAAProjectSpaceTypePlannedDeviceName(),configuration.getLabel(),configuration.getOutputDriverChannelRef(),configuration.getOutputDriverChannelName(),configuration.getgAAProjectRef(),configuration.getgAAProjectName()));
//        }
//
//
////                        Bundle bundleArrayList = getArguments();
////
////                            ArrayList<String> receivedList = bundleArrayList.getStringArrayList("myArrayList");
////                            if (receivedList != null) {
////                                for (String element : receivedList) {
////                                    System.out.println("ArrayList: "+element); // Prints each element on a new line
////                                }
////                            } else {
////                                // Handle case where ArrayList is null
////                            }
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//        // Retrieve data from bundle
//        String key1Value = bundle.getString("key1");
//        String key2Value = bundle.getString("key2");
//        String key3Value = bundle.getString("key3");
//        String key4Value = bundle.getString("key4");
//        // Print the values using Log statements
//        Log.d("NextFragment", "Key 1 Value: " + key1Value);
//        Log.d("NextFragment", "Key 2 Value: " + key2Value);
//        Log.d("NextFragment", "Key 3 Value: " + key3Value);
//        Log.d("NextFragment", "Key 4 Value: " + key4Value);
//        // Do something with the data
//        }
//
//        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(gridLayoutManager1);
//
//        SceneCheckAdapter sceneCheckAdapter = new SceneCheckAdapter(ConArrayList,ConfigArrayList);
//        recyclerView.setAdapter(sceneCheckAdapter);
//
//        }
//        } else {
//        // Handle unsuccessful response
//        }
//        }
//
//@Override
//public void onFailure(Call<SceneResModel> call, Throwable t) {
//        Log.e("MainActivity", "Failed to get response");
//        }
//        });
    }
///
//    private void fetchInstallerControls(String GAAProjectSpaceRef, Long AreaRef, String LoginToken, String LoginDeviceId) {
//
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        Call<InstallerLandingResModel> call = apiService.getDevices(GAAProjectSpaceRef, AreaRef, LoginToken, LoginDeviceId);
//
//        call.enqueue(new Callback<InstallerLandingResModel>() {
//            @Override
//            public void onResponse(Call<InstallerLandingResModel> call, Response<InstallerLandingResModel> response) {
//                if (response.isSuccessful()) {
//                    InstallerLandingResModel installerLandingResModel = response.body();
//                    if (installerLandingResModel != null && installerLandingResModel.getSuccessful()) {
//                        Data data = installerLandingResModel.getData();
//                        List<InstallerControl> installerControls = data.getInstallerControls();
//                        //    editTextSpaceName.setText(data.getGaaProjectSpaceName());
//
//                        List<Controls> controlsDevice = data.getInstallerControls().get(0).getControls();
//                        for (Controls controls : controlsDevice) {
//                            Log.e(TAG, "onResponse Plan Device Name: " + controls.getGaaProjectSpaceTypePlannedDeviceName());
//                            ConArrayList.add(new Controls(controls.getNodeId(), controls.getDisplayOrder(), controls.getGaaProjectSpaceTypePlannedDeviceRef(), controls.getGaaProjectSpaceTypePlannedDeviceName(), controls.isProvisioned()));
//                        }
//
////                        Bundle bundleArrayList = getArguments();
////
////                        ArrayList<String> receivedList = bundleArrayList.getStringArrayList("myArrayList");
////                        if (receivedList != null) {
////                            for (String element : receivedList) {
////                                System.out.println("ArrayList: "+element); // Prints each element on a new line
////                            }
////                        } else {
////                            // Handle case where ArrayList is null
////                        }
//
//                        /*Bundle bundle = getArguments();
//                        if (bundle != null) {
//                            // Retrieve data from bundle
//                            String key1Value = bundle.getString("key1");
//                            String key2Value = bundle.getString("key2");
//                            String key3Value = bundle.getString("key3");
//                            String key4Value = bundle.getString("key4");
//                            // Print the values using Log statements
//                            Log.d("NextFragment", "Key 1 Value: " + key1Value);
//                            Log.d("NextFragment", "Key 2 Value: " + key2Value);
//                            Log.d("NextFragment", "Key 3 Value: " + key3Value);
//                            Log.d("NextFragment", "Key 4 Value: " + key4Value);
//                            // Do something with the data
//                        }*/
//
//                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
//                        recyclerView.setLayoutManager(gridLayoutManager1);
//                        SceneCreateAdapter sceneCreateAdapter = new SceneCreateAdapter(ConArrayList);
//                        recyclerView.setAdapter(sceneCreateAdapter);
//
//                    }
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<InstallerLandingResModel> call, Throwable t) {
//
//            }
//        });
//
//    }
    /////
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
                        sceneCreateViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);

                        CreateSceneCheckAdapter sceneCheckAdapter = new CreateSceneCheckAdapter(ConArrayList,ConfigArrayList, null, requireContext(),editSceneFragment,getViewLifecycleOwner(),sceneCreateViewModel,configurationSceneEditData,sharedViewModelEditData);
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

                    for (Area area : areas) {
                        Log.e(TAG, "onResponse AreaName: " + area.getGAAProjectSpaceTypeAreaName());
                        Log.e(TAG, "onResponse Area Ref: " + area.getGAAProjectSpaceTypeAreaRef());
                        arrayList2.add(new Area(area.getGAAProjectSpaceTypeAreaRef(), area.getGAAProjectSpaceTypeAreaName(), area.getWifiSSID(), area.getWifiPassword(), area.getGuestControls(), area.getInstallerControls()));

                        if (area.getGAAProjectSpaceTypeAreaRef() == 0) {

                        }
                    }
                    // Create custom spinner adapter
                    AreaSpinnerAdapter adapter = new AreaSpinnerAdapter(requireContext(), spinner, areas);

                    // Set the listener for the adapter
                    adapter.setOnAreaSelectedListener(CreateMoodFragment.this); // Replace YourFragmentClassName with the name of your fragment class

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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
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
        Log.e(TAG, "Project Space GUID/LoginDeviceId: " + GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: " + savedLoginDeviceId);
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferences5 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: " + savedLoginDeviceId);
        String projectSpaceRef = saveProjectSpaceRef.trim();

        long gAAProjectSpaceTypeAreaRef = getSelectedAreaRefFromPreferences2(Long.valueOf(selectedAreaRef));
        fetchGuestControlsType(projectSpaceRef, gAAProjectSpaceTypeAreaRef, loginToken, loginDeviceId);
    }

    private final OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            requireActivity().finish();
        }
    };
}



