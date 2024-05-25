package com.gladiance.ui.fragment.MyProfile;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Home.ProjectSpaceActivity;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.activities.MyProfile.EditProjectActivity;
import com.gladiance.ui.adapters.MyProjectAdapter;
import com.gladiance.ui.adapters.ProjectListAdapter;
import com.gladiance.ui.models.Project;
import com.gladiance.ui.models.ProjectSpaceRequestModel;
import com.gladiance.ui.models.ProjectSpaceResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyProjectFragment extends Fragment {


    public MyProjectFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerViewMyProject;
    private ArrayList<Project> arrayList;
    ImageView imageViewEdit,imageViewDelete;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_project, container, false);

        recyclerViewMyProject = view.findViewById(R.id.rv_myProfile_myProject);


        arrayList = new ArrayList<>();

        // Retrieve GUID ID from SharedPreferences (loginDeviceId)
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();



        getProjectName(loginToken,loginDeviceId);

        return view;
    }

    private void getProjectName(String loginToken, String loginDeviceId) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ProjectSpaceResponseModel> call = apiService.getLoginData(loginToken, loginDeviceId);

        call.enqueue(new Callback<ProjectSpaceResponseModel>() {
            @Override
            public void onResponse(Call<ProjectSpaceResponseModel> call, Response<ProjectSpaceResponseModel> response) {
                if (response.isSuccessful()) {
                    ProjectSpaceResponseModel projectSpaceResponseModel = response.body();
                    if (projectSpaceResponseModel != null && projectSpaceResponseModel.isSuccessful()) {
                        List<ProjectSpaceRequestModel.Project> projects = projectSpaceResponseModel.getData().getProjects();

                            // If there are multiple projects, display them in the list
                            for (ProjectSpaceRequestModel.Project project1 : projects) {
                                Log.e(TAG, "onResponse Project Name: " + project1.getGAAProjectName());
                                Log.e(TAG, "onResponse Project Ref : " + project1.getGAAProjectRef());
                                arrayList.add(new Project(project1.getGAAProjectRef(), project1.getGAAProjectName()));
                            }

                            MyProjectAdapter myProjectAdapter = new MyProjectAdapter(arrayList,context);
                        recyclerViewMyProject.setAdapter(myProjectAdapter);
                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false);
                        recyclerViewMyProject.setLayoutManager(gridLayoutManager1);
                            // If any error change adapter class

                    } else {
                        Log.e("MainActivity", "Unsuccessful response: " + projectSpaceResponseModel.getMessage());
                    }
                } else {
                    Log.e("MainActivity", "Failed to get response");
                }
            }

            @Override
            public void onFailure(Call<ProjectSpaceResponseModel> call, Throwable t) {
                // Handle failure
            }
        });
    }
}