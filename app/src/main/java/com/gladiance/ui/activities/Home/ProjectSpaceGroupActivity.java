package com.gladiance.ui.activities.Home;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.adapters.ProjectSpaceGroupListAdapter;

import com.gladiance.ui.fragment.RoomControl.DeviceLandingFragment;
import com.gladiance.ui.models.ProjectSpaceGroupReqModel;
import com.gladiance.ui.models.ProjectSpaceGroupResModel;
import com.gladiance.ui.models.SpaceGroup;
import com.gladiance.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectSpaceGroupActivity extends AppCompatActivity {

    RecyclerView rvProjectSpaceGroupList;
    ProjectSpaceGroupListAdapter projectSpaceGroupListAdapter;
    String gAAProjectRef,loginToken,loginDeviceId;
    TextView userName,projectName;
    private ArrayList<SpaceGroup>arrayList;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_space_group);

        userName = findViewById(R.id.user_name);
        projectName = findViewById(R.id.projectName);
        rvProjectSpaceGroupList = findViewById(R.id.rVProjectSpaceGroupName);

        arrayList = new ArrayList<>();

        // Retrieve GUID ID from SharedPreferences (loginDeviceId)
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();

        //Login Token
        SharedPreferences  sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginToken = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "ProjectSpaceGroupActivity Login Token: "+savedLoginToken );
        String loginToken = savedLoginToken.trim();

        //Use Name
        SharedPreferences sharedPreferences3 = getSharedPreferences("MyPreferencesDN", Context.MODE_PRIVATE);
        String savedUserDisplayName = sharedPreferences3.getString("UserDisplayName", "");
        userName.setText(savedUserDisplayName);
        Log.e(TAG, "ProjectSpaceGroupActivity User Display Name: "+savedUserDisplayName );

        SharedPreferences sharedPreferences4 = getSharedPreferences("MyPrefsPN", Context.MODE_PRIVATE);
        String saveProjectName = sharedPreferences4.getString("ProjectName", "");
        projectName.setText(saveProjectName);
        Log.e(TAG, "ProjectSpaceGroupActivity Project Name : "+saveProjectName );

        SharedPreferences sharedPreferences5 = getSharedPreferences("MyPrefsPR", Context.MODE_PRIVATE);
        String ProjectRef = sharedPreferences5.getString("ProjectRef", "");
        String gAAProjectRef = ProjectRef.trim();
        Log.e(TAG, "ProjectSpaceGroupActivity Project Ref : "+gAAProjectRef );


        SharedPreferences  sharedPreferencesProjectName = getSharedPreferences("MyPrefsProjectNameOne", MODE_PRIVATE);
        String projectNameOne = sharedPreferencesProjectName.getString("projectNameOne", "");
        // textViewProjectName.setText(projectNameOne);
        Log.e(TAG, "Project Ref Name: "+projectNameOne );




        getSpaceGroupName(gAAProjectRef,loginToken,loginDeviceId);


    }

    private void  getSpaceGroupName(String gAAProjectRef2, String loginToken,String loginDeviceId){

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ProjectSpaceGroupResModel> call = apiService.getProjectData(gAAProjectRef2,loginToken,loginDeviceId);

        call.enqueue(new Callback<ProjectSpaceGroupResModel>() {
            @Override
            public void onResponse(Call<ProjectSpaceGroupResModel> call, Response<ProjectSpaceGroupResModel> response) {
                if(response.isSuccessful()){
                    ProjectSpaceGroupResModel projectSpaceGroupResModel = response.body();
                    if (projectSpaceGroupResModel != null && projectSpaceGroupResModel.isSuccessful()) {
                        List<ProjectSpaceGroupReqModel.SpaceGroup> spaceGroups = projectSpaceGroupResModel.getData().getSpaceGroups();

                        if (spaceGroups.size() == 1) {
                            // If there is only one project, navigate to the next activity directly
                            navigateToNextActivity(spaceGroups.get(0).getGAAProjectSpaceGroupRef(), spaceGroups.get(0).getGAAProjectSpaceGroupName());
                        } else {

                            for (ProjectSpaceGroupReqModel.SpaceGroup spaceGroup1 : spaceGroups) {
                                Log.e(TAG, "onResponse SpaceGroupName: " + spaceGroup1.getGAAProjectSpaceGroupName());
                                arrayList.add(new SpaceGroup(spaceGroup1.getGAAProjectSpaceGroupRef(), spaceGroup1.getGAAProjectSpaceGroupName(), spaceGroup1.getDisplayOrder()));
                            }

                            //add arraylist code and create space group class
                            ProjectSpaceGroupListAdapter projectSpaceGroupListAdapter = new ProjectSpaceGroupListAdapter(arrayList);
                            rvProjectSpaceGroupList.setAdapter(projectSpaceGroupListAdapter);
                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(ProjectSpaceGroupActivity.this, 2, GridLayoutManager.VERTICAL, false);
                            rvProjectSpaceGroupList.setLayoutManager(gridLayoutManager1);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProjectSpaceGroupResModel> call, Throwable t) {

            }
        });

    }

    private void navigateToNextActivity(String projectRef,String projectName) {
        storeProjectSpaceGroupRef(projectRef);
        storeProjectSpaceGroupName(projectName);
        Intent intent = new Intent(ProjectSpaceGroupActivity.this, ProjectSpaceLandingActivity.class);
        startActivity(intent);
        // finish();

    }

    private void storeProjectSpaceGroupRef(String projectRef) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsPSGR", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SPACE_GROUP_REF", projectRef);
        editor.apply();
    }

    private void storeProjectSpaceGroupName(String projectName) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsProjectSpaceGroupOne", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("projectSpaceGroupOne", projectName);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        // Handle back button press
        super.onBackPressed();
        Intent intent = new Intent(this, ProjectSpaceActivity.class);
        startActivity(intent);
        // Optionally, finish() the current activity to remove it from the back stack
        // finish();
    }
}
