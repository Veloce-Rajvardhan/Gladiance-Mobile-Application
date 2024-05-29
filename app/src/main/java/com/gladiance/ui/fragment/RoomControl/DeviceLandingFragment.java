package com.gladiance.ui.fragment.RoomControl;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.adapters.ControlAdapter;
import com.gladiance.ui.adapters.DeviceControlAdapter;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.models.guestlandingpage.GuestLandingResModel;
import com.gladiance.ui.models.guestlandingpage.GuestControls;
import com.gladiance.ui.models.guestlandingpage.Controls;

import com.gladiance.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceLandingFragment extends Fragment implements ControlAdapter.OnControlTypeClickListener {

    private static final String ARG_SELECTED_AREA_REF = "selectedAreaRef";

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String USER_ID_KEY = "userId";

    private static final String PREF_SELECTED_AREA_REF = "selectedAreaRef";


    RecyclerView recyclerView, guestRecyclerView;
    //ControlAdapter.OnControlTypeClickListener OnControlTypeClickListener ;

    Context context;
    TextView DeviceName;

    public DeviceLandingFragment() {
        // Required empty public constructor
    }

    public static DeviceLandingFragment newInstance(Context context, Long selectedAreaRef) {
        DeviceLandingFragment fragment = new DeviceLandingFragment();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(ARG_SELECTED_AREA_REF, selectedAreaRef);
        editor.apply();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_device_landing, container, false);

        recyclerView = view.findViewById(R.id.controlTypeRecyclerView);
        guestRecyclerView = view.findViewById(R.id.guestControlTypeRecyclerView);

        List<GuestControls> controlsList = new ArrayList<>();

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
        Log.e(TAG, "Project Space Ref: " + saveProjectSpaceRef);
        String projectSpaceRef = saveProjectSpaceRef.trim();

        SharedPreferences sharedPreferences3 = requireContext().getSharedPreferences("MyPrefsPSAR", MODE_PRIVATE);
        String saveProjectSpaceAreRef = sharedPreferences3.getString("Project_Space_Area_Ref", "");
        Log.e(TAG, "Project Space Area Ref: " + saveProjectSpaceAreRef);
        Long projectSpaceAreaRef = Long.valueOf(saveProjectSpaceAreRef.trim());

        fetchGuestControlsType(projectSpaceRef, projectSpaceAreaRef, loginToken, loginDeviceId);

        return view;
    }


    //Call guest controls
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
                            List<Controls> allControls = new ArrayList<>();
                            for (GuestControls guestControls : controlsList) {
                                allControls.addAll(guestControls.getControls());
                            }
                            // Set up DeviceControlName RecyclerView
                            guestRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2,GridLayoutManager.VERTICAL, false));
                            DeviceControlAdapter deviceControlAdapter = new DeviceControlAdapter(allControls, requireContext());
                            guestRecyclerView.setAdapter(deviceControlAdapter);
                            // Set up ControlTypeName RecyclerView
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            ControlAdapter controlAdapter = new ControlAdapter(controlsList, requireContext(), new ControlAdapter.OnControlTypeClickListener() {
                                @Override
                                public void onControlTypeClicked(GuestControls control) {
                                    Log.d("ControlAdapter", "Control clicked: " + control.getControlTypeName());
                                    List<Controls> filteredControls = control.getControls();

                                    guestRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2,GridLayoutManager.VERTICAL, false));
                                    DeviceControlAdapter deviceControlAdapter = new DeviceControlAdapter(filteredControls, requireContext());
                                    guestRecyclerView.setAdapter(deviceControlAdapter);
                                }
                            });
                            recyclerView.setAdapter(controlAdapter);

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

    @Override
    public void onControlTypeClicked(GuestControls control) {

    }

    private long getSelectedAreaRefFromPreferences() {
        // Get an instance of SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getLong(PREF_SELECTED_AREA_REF, 0L);
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
                requireActivity().onBackPressed();
                return true; // Consumes the back button press event
            }
            return false; // Otherwise, let the system handle it
        });
    }
}