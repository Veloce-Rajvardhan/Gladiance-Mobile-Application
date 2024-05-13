package com.gladiance.ui.fragment.Home;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Home.ProjectSpaceGroupActivity;
import com.gladiance.ui.adapters.ProjectSpaceNameAdapter;
import com.gladiance.ui.adapters.SceneAdapter;
import com.gladiance.ui.adapters.SpaceGroupSpinnerAdapter;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.models.ProjectSpaceGroupReqModel;
import com.gladiance.ui.models.ProjectSpaceGroupResModel;
import com.gladiance.ui.models.ProjectSpaceLandingReqModel;
import com.gladiance.ui.models.ProjectSpaceLandingResModel;
import com.gladiance.ui.models.SpaceGroup;
import com.gladiance.ui.models.SpaceLanding;
import com.gladiance.ui.models.scenelist.ObjectTag;
import com.gladiance.ui.models.scenelist.SceneListResModel;
import com.gladiance.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment  {

    TextView textViewProjectName,textViewUserName;
    TextView mood;

    RecyclerView recyclerView,recyclerViewSpaceName;
    private ArrayList<SpaceLanding> arrayList;

    private ArrayList<ObjectTag> arrayList1;
    Context context;

    LinearLayout linearLayout;

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        textViewProjectName = view.findViewById(R.id.tv_project_name);
        textViewUserName = view.findViewById(R.id.tvUserName);
        mood = view.findViewById(R.id.mood);
        recyclerView = view.findViewById(R.id.recycler_view_sceneList_home);
        recyclerViewSpaceName = view.findViewById(R.id.rVProjectSpaceNameHome);
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();

        SharedPreferences sharedPreferencesProName = requireActivity().getSharedPreferences("MyPrefsPN", Context.MODE_PRIVATE);
        String ProjectName = sharedPreferencesProName.getString("ProjectName", "");
        textViewProjectName.setText(ProjectName);
        Log.e(TAG, "Home Fragment Project Name : "+ ProjectName );

        SharedPreferences sharedPreferences3 = requireActivity().getSharedPreferences("MyPreferencesDN", Context.MODE_PRIVATE);
        String savedUserDeviceName = sharedPreferences3.getString("UserDisplayName", "");
        textViewUserName.setText("Hello "+savedUserDeviceName);
        Log.e(TAG, "Home Fragment User Name: "+savedUserDeviceName );


        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences  sharedPreferences2 = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferencesProRef = requireActivity().getSharedPreferences("MyPrefsPR", Context.MODE_PRIVATE);
        String ProjectRef = sharedPreferencesProRef.getString("ProjectRef", "");
        Log.e(TAG, "ProjectSpaceGroupActivity Project Ref : "+ ProjectRef );
        String gAAProjectRef = ProjectRef.trim();

        SharedPreferences  sharedPreferences4 = requireContext().getSharedPreferences("MyPrefsPSTR", MODE_PRIVATE);
        String saveProjectSpaceTypeRef = sharedPreferences4.getString("Project_Space_Type_Ref", "");
        Log.e(TAG, "Project Space Type Ref: "+saveProjectSpaceTypeRef );
        String gaaProjectSpaceTypeRef = saveProjectSpaceTypeRef.trim();

        //For Single Project
        SharedPreferences  sharedPreferencesProjectRef = requireContext().getSharedPreferences("MyPrefsProjectRefOne", MODE_PRIVATE);
        String projectRefOne = sharedPreferencesProjectRef.getString("projectRefOne", "");
        Log.e(TAG, "Project Ref One: "+projectRefOne );

        SharedPreferences  sharedPreferencesProjectName = requireContext().getSharedPreferences("MyPrefsProjectNameOne", MODE_PRIVATE);
        String projectNameOne = sharedPreferencesProjectName.getString("projectNameOne", "");
       // textViewProjectName.setText(projectNameOne);
        Log.e(TAG, "Project Ref Name: "+projectNameOne );



        SharedPreferences sharedPreferences5 = requireContext().getSharedPreferences("MyPrefsPSGR", Context.MODE_PRIVATE);
        String ProjectSpaceGroupRef = sharedPreferences5.getString("SPACE_GROUP_REF", "");
        Log.e(TAG, "get Project Space Group Ref: "+ProjectSpaceGroupRef);

        getSpaceName(ProjectSpaceGroupRef,loginToken,loginDeviceId);
        getSceneList(gaaProjectSpaceTypeRef,loginToken,loginDeviceId);

        return view;
    }

    private void  getSpaceName(String ProjectSpaceGroupRef, String loginToken,String loginDeviceId) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ProjectSpaceLandingResModel> call = apiService.getSpaceNameData(ProjectSpaceGroupRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<ProjectSpaceLandingResModel>() {
            @Override
            public void onResponse(Call<ProjectSpaceLandingResModel> call, Response<ProjectSpaceLandingResModel> response) {
                if (response.isSuccessful()) {
                    ProjectSpaceLandingResModel projectSpaceLandingResModel = response.body();
                    if (projectSpaceLandingResModel != null && projectSpaceLandingResModel.isSuccessful()) {
                        List<ProjectSpaceLandingReqModel.Space> space = projectSpaceLandingResModel.getData().getSpaces();

                        for (ProjectSpaceLandingReqModel.Space space1 : space) {
                            Log.e(TAG, "onResponse SpaceGroupName: " + space1.getGAAProjectSpaceName());
                            Log.e(TAG, "onResponse getGAAProjectSpaceRef: "+space1.getGAAProjectSpaceRef());
                            Log.e(TAG, "onResponse getGAAProjectSpaceTypeRef: "+space1.getGAAProjectSpaceTypeRef());

                            arrayList.add(new SpaceLanding(space1.getGAAProjectSpaceRef(), space1.getGAAProjectSpaceName(),space1.getGAAProjectSpaceTypeRef(),space1.getGAAProjectSpaceTypeName(), space1.getDisplayOrder(), space1.getDescription()));

                        }

                        //add arraylist code and create space group class

                        ProjectSpaceNameAdapter projectSpaceNameAdapter = new ProjectSpaceNameAdapter(arrayList,getContext());
                        recyclerViewSpaceName.setAdapter(projectSpaceNameAdapter);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false);
                        recyclerViewSpaceName.setLayoutManager(gridLayoutManager1);
                    }
                }
            }
            @Override
            public void onFailure(Call<ProjectSpaceLandingResModel> call, Throwable t) {
            }
        });
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
                            arrayList1.add(new ObjectTag(objectTag.getRef(),objectTag.getName(),objectTag.getgAAProjectRef(),objectTag.getCode(),objectTag.getIsSystemDefinedScene(),objectTag.getgAAProjectSpaceTypeRef(),objectTag.getgAAProjectSpaceTypeName(),objectTag.getgAAProjectName(),objectTag.getConfigurations()));
                        }

                        SceneAdapter spaceAdapter = new SceneAdapter(arrayList1,getContext());
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

            }
        });

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                // Handle back button press here
                Intent intent = new Intent(requireActivity(), ProjectSpaceGroupActivity.class);
                startActivity(intent);
                return true; // Consumes the back button press event
            }
            return false; // Otherwise, let the system handle it
        });
    }



}
