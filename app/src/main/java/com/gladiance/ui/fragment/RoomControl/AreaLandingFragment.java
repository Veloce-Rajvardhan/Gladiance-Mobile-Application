package com.gladiance.ui.fragment.RoomControl;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.adapters.AreaLandingAdapter;
import com.gladiance.ui.adapters.AreaSpinnerAdapter;

import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.ProjectSpaceNameAdapter;
import com.gladiance.ui.models.arealandingmodel.Area;
import com.gladiance.ui.models.arealandingmodel.ProjectAreaLandingResModel;

import com.gladiance.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AreaLandingFragment extends Fragment  {

    private ArrayList<Area> arrayList;
    MeowBottomNavigation bottomNavigation;

    RecyclerView recyclerView;

    SharedPreferences sharedPreferences;

    TextView textViewSpaceName;


    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String USER_ID_KEY = "userId";

    public AreaLandingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_area_landing, container, false);

        arrayList = new ArrayList<>();
        textViewSpaceName = view.findViewById(R.id.spaceName);
        recyclerView = view.findViewById(R.id.rVAreaName);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences  sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();


        SharedPreferences  sharedPreferences3 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
        String projectSpaceRef = saveProjectSpaceRef.trim();

        SharedPreferences  sharedPreferences4 = requireContext().getSharedPreferences("MyPrefsPSN", MODE_PRIVATE);
        String saveProjectSpaceName = sharedPreferences4.getString("Project_Space_Name", "");
        Log.e(TAG, "Project Space Name: "+saveProjectSpaceName );
        textViewSpaceName.setText(saveProjectSpaceName);


        fetchAreas(projectSpaceRef,loginToken,loginDeviceId);

        MeowBottomNavigation bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation);
        bottomNavigation.show(2, true);

        return view;
    }


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
                        arrayList.add(new Area(area.getGAAProjectSpaceTypeAreaRef(), area.getGAAProjectSpaceTypeAreaName(), area.getWifiSSID(), area.getWifiPassword(), area.getGuestControls(), area.getInstallerControls()));
                    }

                    if (areas.size() == 1){

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String selectedAreaRef = String.valueOf(areas.get(0).getGAAProjectSpaceTypeAreaRef());
                                Fragment newFragment = DeviceLandingFragment.newInstance(getContext(), Long.valueOf((selectedAreaRef)));
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();

                            }
                        }, 700);

                    }else {
                        AreaLandingAdapter areaLandingAdapter = new AreaLandingAdapter(arrayList,getContext());
                        recyclerView.setAdapter(areaLandingAdapter);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(gridLayoutManager1);
                    }

                    AreaLandingAdapter areaLandingAdapter = new AreaLandingAdapter(arrayList,getContext());
                    recyclerView.setAdapter(areaLandingAdapter);
                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(gridLayoutManager1);
                    }
            }

            @Override
            public void onFailure(Call<ProjectAreaLandingResModel> call, Throwable t) {
                // Handle API call failure
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
//                Intent intent = new Intent(requireActivity(), ProjectSpaceLandingActivity.class);
//                startActivity(intent);

                //ikde lava
                MeowBottomNavigation bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation);

                bottomNavigation.show(3, true);
                requireActivity().onBackPressed();
                return true; // Consumes the back button press event
            }
            return false; // Otherwise, let the system handle it
        });
    }




}