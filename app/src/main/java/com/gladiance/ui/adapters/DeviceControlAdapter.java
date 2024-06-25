package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import static com.gladiance.AppConstants.GaaProjectSpaceTypePlannedDeviceRef;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.NetworkApiManager;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.EspMainActivity;
import com.gladiance.ui.activities.Home.ProjectSpaceGroupActivity;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.activities.RoomControl.DeviceCardActivity;
import com.gladiance.ui.fragment.MyProfile.ProfileDeviceCardFragment;
import com.gladiance.ui.fragment.RoomControl.DeviceCardFragment;
import com.gladiance.ui.models.AddSpaceUserFavourite;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.RemoveSpaceUserFavorite;
import com.gladiance.ui.models.guestlandingpage.Controls;

import com.gladiance.R;

import java.util.List;

import retrofit2.Call;

public class DeviceControlAdapter extends RecyclerView.Adapter<DeviceControlAdapter.ViewHolder> {
    private List<Controls> controls;
    private Context context;
    private NetworkApiManager networkApiManager;
    private EspApplication espApp;



    public DeviceControlAdapter(List<Controls> controls, Context context) {
        this.controls = controls;
        this.context = context;
        this.espApp = new EspApplication(context.getApplicationContext());
        this.networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);

    }

    @NonNull
    @Override
    public DeviceControlAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_control, parent, false);
        return new DeviceControlAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceControlAdapter.ViewHolder holder, int position) {
        Controls control = controls.get(position);
        holder.deviceNameTextView.setText(control.getgAAProjectSpaceTypePlannedDeviceName());


        holder.fevImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                Long GaaProjectSpaceTypePlannedDeviceConRef = Long.valueOf(control.getgAAProjectSpaceTypePlannedDeviceConnectionRef());
                SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences.edit();
                Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceName11: " + GaaProjectSpaceTypePlannedDeviceConRef);
                editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceConRef);
                editor3.apply();

                SharedPreferences sharedPreferencesPSPDR = context.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                Long gaaProjectSpaceTypePlannedDeviceConRef = sharedPreferencesPSPDR.getLong("KEY_USERNAME", 0);
                Log.e(TAG, "Retrieved GaaProjectSpaceTypePlannedDeviceRef: " + gaaProjectSpaceTypePlannedDeviceConRef);

                SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String GUID = LoginActivity.getUserId(sharedPreferences1);
                Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
                String loginDeviceId = GUID.trim();


                SharedPreferences  sharedPreferences2 = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
                Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
                String loginToken = savedLoginDeviceId.trim();

                SharedPreferences  sharedPreferences3 = context.getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
                String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
                Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
                String projectSpaceRef = saveProjectSpaceRef.trim();

                callAddSpaceUserFavouriteApi(projectSpaceRef, String.valueOf(gaaProjectSpaceTypePlannedDeviceConRef),loginToken,loginDeviceId);
                //callRemoveSpaceUserFavouriteApi(projectSpaceRef, String.valueOf(gaaProjectSpaceTypePlannedDeviceRef),loginToken,loginDeviceId);
           }
        });

        holder.deviceNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (control.getInternalDeviceName().equals("Switch 1")) {
                    control.setPowerState(!control.isPowerState());
                    updateUI(holder, control.isPowerState());

                    LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                    String nodeId = control.getNodeId();
                    SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    Log.e(TAG, "Node Id: " + nodeId);
                    editor.putString("KEY_USERNAMEs", nodeId);
                    editor.apply();

                    String name = control.getInternalDeviceName();

                    sendSwitchState(control.isPowerState(), name, nodeId);
                }else if (control.getInternalDeviceName().equals("Light 1")) {
                        control.setPowerState(!control.isPowerState());
                        updateUI(holder, control.isPowerState());

                        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                        String nodeId = control.getNodeId();
                        SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences2.edit();
                        Log.e(TAG, "Node Id: " + nodeId);
                        editor.putString("KEY_USERNAMEs", nodeId);
                        editor.apply();

                        String name = control.getInternalDeviceName();

                        sendSwitchState(control.isPowerState(), name, nodeId);

                }else if (control.getInternalDeviceName().equals("Light 2")) {
                    control.setPowerState(!control.isPowerState());
                    updateUI(holder, control.isPowerState());

                    LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                    String nodeId = control.getNodeId();
                    SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    Log.e(TAG, "Node Id: " + nodeId);
                    editor.putString("KEY_USERNAMEs", nodeId);
                    editor.apply();

                    String name = control.getInternalDeviceName();

                    sendSwitchState(control.isPowerState(), name, nodeId);

                }else if (control.getInternalDeviceName().equals("Power Socket 1")) {
                    control.setPowerState(!control.isPowerState());
                    updateUI(holder, control.isPowerState());

                    LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                    String nodeId = control.getNodeId();
                    SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    Log.e(TAG, "Node Id: " + nodeId);
                    editor.putString("KEY_USERNAMEs", nodeId);
                    editor.apply();

                    String name = control.getInternalDeviceName();

                    sendSwitchState(control.isPowerState(), name, nodeId);

                }
                else {
                    LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                    Long GaaProjectSpaceTypePlannedDeviceRef = Long.valueOf(control.getgAAProjectSpaceTypePlannedDeviceRef());
                    SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sharedPreferences.edit();
                    Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceName11: " + GaaProjectSpaceTypePlannedDeviceRef);
                    editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceRef);
                    editor3.apply();

                    String Label = control.getLabel();
                    SharedPreferences sharedPreferences1 = inflater.getContext().getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    Log.e(TAG, "Label: " + Label);
                    editor1.putString("KEY_USERNAMEs", Label);
                    editor1.apply();

                    String nodeId = control.getNodeId();
                    SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    Log.e(TAG, "Node Id: " + nodeId);
                    editor.putString("KEY_USERNAMEs", nodeId);
                    editor.apply();

                    FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    DeviceCardFragment newFragment = new DeviceCardFragment();
                    transaction.replace(R.id.DeviceCard, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
        });
    }

    private void updateUI(ViewHolder holder, boolean powerState) {
        int borderColorRes = powerState ? R.drawable.transparent_orange_switch_bg : R.drawable.transparent_backgraund;
        holder.llGuestControl.setBackground(ContextCompat.getDrawable(context, borderColorRes));
    }

    @Override
    public int getItemCount() {
        return controls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        LinearLayout llGuestControl;
        ImageView fevImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceNameTextView = itemView.findViewById(R.id.btnTitle);
            llGuestControl = itemView.findViewById(R.id.llGuestControl);
            fevImage = itemView.findViewById(R.id.fev_image);
            deviceNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        }
    }



    public void sendSwitchState(boolean powerState,String name,String nodeId) {
        String commandBody = "{\"" + name + "\": {\"" + "Power" + "\": " + powerState + "}}";
        String message = powerState ? "on" : "off";
        Toast.makeText(context, "Switch is " + message, Toast.LENGTH_SHORT).show();
        boolean shPowerState = powerState;
        SharedPreferences sharedPreferencesPowerState = context.getSharedPreferences("MyPreferencesPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesPowerState.edit();
        editor.putBoolean("PowerState", shPowerState);
        editor.apply();
        Log.e(TAG, "Device Fragment PowerState:" + shPowerState);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        String remoteCommandTopic = "node/" + nodeId + "/params/remote";
        Log.e(TAG, "Device Fragment Node Id:" + nodeId);

        networkApiManager.updateParamValue(nodeId, commandBody, apiService, remoteCommandTopic);
    }


    private void callAddSpaceUserFavouriteApi(String gaaProjectSpaceRef,String gaaProjectSpaceTypePlannedDeviceConnectionRef,String loginToken,String loginDeviceId) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<AddSpaceUserFavourite> call = apiService.addSpaceUserFavourite(gaaProjectSpaceRef, gaaProjectSpaceTypePlannedDeviceConnectionRef, loginToken, loginDeviceId);
        call.enqueue(new retrofit2.Callback<AddSpaceUserFavourite>() {
            @Override
            public void onResponse(Call<AddSpaceUserFavourite> call, retrofit2.Response<AddSpaceUserFavourite> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Added to favourites successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to add to favourites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddSpaceUserFavourite> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRemoveSpaceUserFavouriteApi(String gaaProjectSpaceRef,String gaaProjectSpaceTypePlannedDeviceConnectionRef,String loginToken,String loginDeviceId) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RemoveSpaceUserFavorite> call = apiService.removeSpaceUserFavourite(gaaProjectSpaceRef, gaaProjectSpaceTypePlannedDeviceConnectionRef, loginToken, loginDeviceId);
        call.enqueue(new retrofit2.Callback<RemoveSpaceUserFavorite>() {
            @Override
            public void onResponse(Call<RemoveSpaceUserFavorite> call, retrofit2.Response<RemoveSpaceUserFavorite> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Remove to favourites successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to Remove to favourites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveSpaceUserFavorite> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}







