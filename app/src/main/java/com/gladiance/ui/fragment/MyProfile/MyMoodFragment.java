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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.adapters.SceneConfigAdapter;
import com.gladiance.ui.models.saveScene.Configuration;
import com.gladiance.ui.models.saveScene.ConfigurationViewModel;
import com.gladiance.ui.models.scenelist.ObjectTag;
import com.gladiance.ui.models.scenelist.SceneListResModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyMoodFragment extends Fragment {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String USER_ID_KEY = "userId";

    RecyclerView recyclerView;

    private ArrayList<ObjectTag> arrayList;
    Context context;
    Button setScheduling;
    private ConfigurationViewModel viewModel;



    public MyMoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_mood, container, false);

        // get list here

        try {
          //  List<Configuration> configurations = viewModel.getMatchedConfigurationsLiveData();
        }
        catch (Exception e){
            Log.e(TAG, "onCreateView: "+e );
        }
      //  setScheduling = view.findViewById(R.id.scheduling);
//        setScheduling.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new AutomationFragment();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
//                        .beginTransaction();
//
//                transaction.replace(R.id.fragment_scheduling, fragment).addToBackStack(null)
//                        .commit();
//
//            }
//        });

        recyclerView = view.findViewById(R.id.recycler_view_sceneList);

        arrayList = new ArrayList<>();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "SceneList LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();

        //Login Token
        SharedPreferences  sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginToken = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "SceneList Login Token: "+savedLoginToken );
        String loginToken = savedLoginToken.trim();


        SharedPreferences  sharedPreferences4 = requireContext().getSharedPreferences("MyPrefsPSTR", MODE_PRIVATE);
        String saveProjectSpaceTypeRef = sharedPreferences4.getString("Project_Space_Type_Ref", "");
        Log.e(TAG, "Project Space Type Ref: "+saveProjectSpaceTypeRef );
        String gaaProjectSpaceTypeRef = saveProjectSpaceTypeRef.trim();


        getSceneList(gaaProjectSpaceTypeRef,loginToken,loginDeviceId);

        return view;
    }
    private void getSceneList(String gaaProjectSpaceTypeRef,String loginToken,String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SceneListResModel> call = apiService.getSceneList(gaaProjectSpaceTypeRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<SceneListResModel>() {
            @Override
            public void onResponse(Call<SceneListResModel> call, Response<SceneListResModel> response) {
                if (response.isSuccessful()) {
                    SceneListResModel sceneListResModel = response.body();
                    if (sceneListResModel != null && sceneListResModel.getSuccessful()) {
                        List<ObjectTag> sceneList = sceneListResModel.getObjectTag();

                        for (ObjectTag objectTag : sceneList) {
                            Log.e(TAG, "onResponse SceneName: " + objectTag.getName());
                            arrayList.add(new ObjectTag(objectTag.getRef(),objectTag.getName(),objectTag.getgAAProjectRef(),objectTag.getCode(),objectTag.getIsSystemDefinedScene(),objectTag.getgAAProjectSpaceTypeRef(),objectTag.getgAAProjectSpaceTypeName(),objectTag.getgAAProjectName(),objectTag.getConfigurations()));
                        }

                        SceneConfigAdapter spaceAdapter = new SceneConfigAdapter(arrayList);
                        recyclerView.setAdapter(spaceAdapter);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(gridLayoutManager1);
                        //If any error change adapter class
                    } else {
                        Log.e("MainActivity", "Unsuccessful response: " + sceneListResModel.getMessage());
                    }
                } else {
                    Log.e("MainActivity", "Failed to get response");
                }
            }

            @Override
            public void onFailure(Call<SceneListResModel> call, Throwable t) {
                Log.e("SetMood Fragment", "Failed to get response onFailure ");
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