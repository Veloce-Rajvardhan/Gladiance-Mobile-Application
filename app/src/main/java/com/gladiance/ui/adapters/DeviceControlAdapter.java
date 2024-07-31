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
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.gladiance.ui.models.favoritelist.FavoriteListRes;
import com.gladiance.ui.models.guestlandingpage.Controls;

import com.gladiance.R;
import com.gladiance.ui.models.guestlandingpage.GuestControls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceControlAdapter extends RecyclerView.Adapter<DeviceControlAdapter.ViewHolder> {

    private static final String TAG = "DeviceControlAdapter";

    private List<Controls> controls;
    private Context context;
    private NetworkApiManager networkApiManager;
    private EspApplication espApp;
    private List<com.gladiance.ui.models.favoritelist.ObjectTag> favoriteList;



    public DeviceControlAdapter(List<Controls> controls, Context context,List<com.gladiance.ui.models.favoritelist.ObjectTag> favoriteList) {
        this.controls = controls;
        this.context = context;
        this.espApp = new EspApplication(context.getApplicationContext());
        this.networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);
        this.favoriteList = favoriteList;

    }

    public void updateFavoriteList(List<com.gladiance.ui.models.favoritelist.ObjectTag> newFavoriteList) {
        this.favoriteList = newFavoriteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceControlAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_control, parent, false);
        return new DeviceControlAdapter.ViewHolder(view);
    }

    private void handleDeviceClick(Controls control, ViewHolder holder) {
        control.setPowerState(!control.isPowerState());
        updateUI(holder, control.isPowerState());

        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
        String nodeId = control.getNodeId();
        SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.e(TAG, "Node Id: " + nodeId);
        editor.putString("KEY_USERNAMEs", nodeId);
        editor.apply();

        String name = control.getInternalDeviceName();
        sendSwitchState(control.isPowerState(), name, nodeId);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceControlAdapter.ViewHolder holder, int position) {
        Controls control = controls.get(position);
        holder.deviceNameTextView.setText(control.getgAAProjectSpaceTypePlannedDeviceName());

        final boolean[] isFavorite = {false};
        for (com.gladiance.ui.models.favoritelist.ObjectTag favorite : favoriteList) {
            if (favorite.getNodeId().equals(control.getNodeId())) {
                isFavorite[0] = true;
                break;
            }
        }
        if (isFavorite[0]) {
            holder.fevImage.setImageResource(R.drawable.likered); // Set favorite image
        } else {
            holder.fevImage.setImageResource(R.drawable.likewhite); // Set non-favorite image
        }

        holder.fevImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite[0]) {
                    callRemoveSpaceUserFavouriteApi(control, holder);
                } else {
                    callAddSpaceUserFavouriteApi(control, holder);
                }
                // Update the favorite state after API call
                isFavorite[0] = !isFavorite[0];
                updateFevImage(holder.fevImage, isFavorite[0]);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (control.getInternalDeviceName().equals("Switch 1") ||
                        control.getInternalDeviceName().equals("Switch 2") ||
                        control.getInternalDeviceName().equals("Switch 3") ||
                        control.getInternalDeviceName().equals("Switch 4") ||
                        control.getInternalDeviceName().equals("Switch 5") ||
                        control.getInternalDeviceName().equals("Switch 6") ||
                        control.getInternalDeviceName().equals("Switch 7") ||
                        control.getInternalDeviceName().equals("Switch 8") ||
                        control.getInternalDeviceName().equals("Switch 9") ||
                        control.getInternalDeviceName().equals("Switch 10") ||
                        control.getInternalDeviceName().equals("Switch 11") ||
                        control.getInternalDeviceName().equals("Switch 12") ||
                        control.getInternalDeviceName().equals("Light 1") ||
                        control.getInternalDeviceName().equals("Light 2") ||
                        control.getInternalDeviceName().equals("Light 3") ||
                        control.getInternalDeviceName().equals("Light 4") ||
                        control.getInternalDeviceName().equals("Light 5") ||
                        control.getInternalDeviceName().equals("Light 6") ||
                        control.getInternalDeviceName().equals("Light 7") ||
                        control.getInternalDeviceName().equals("Light 8") ||
                        control.getInternalDeviceName().equals("Light 9") ||
                        control.getInternalDeviceName().equals("Light 10") ||
                        control.getInternalDeviceName().equals("Light 11") ||
                        control.getInternalDeviceName().equals("Light 12") ||
                        control.getInternalDeviceName().equals("Power Socket 1") ||
                        control.getInternalDeviceName().equals("Power Socket 2") ||
                        control.getInternalDeviceName().equals("Power Socket 3") ||
                        control.getInternalDeviceName().equals("Power Socket 4") ||
                        control.getInternalDeviceName().equals("Power Socket 5") ||
                        control.getInternalDeviceName().equals("Power Socket 6")) {
                    handleDeviceClick(control, holder);
                } else {
                    navigateToDeviceCardFragment(view, control);
                }
            }
        });

        holder.deviceNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (control.getInternalDeviceName().equals("Switch 1") ||
                        control.getInternalDeviceName().equals("Switch 2") ||
                        control.getInternalDeviceName().equals("Switch 3") ||
                        control.getInternalDeviceName().equals("Switch 4") ||
                        control.getInternalDeviceName().equals("Switch 5") ||
                        control.getInternalDeviceName().equals("Switch 6") ||
                        control.getInternalDeviceName().equals("Switch 7") ||
                        control.getInternalDeviceName().equals("Switch 8") ||
                        control.getInternalDeviceName().equals("Switch 9") ||
                        control.getInternalDeviceName().equals("Switch 10") ||
                        control.getInternalDeviceName().equals("Switch 11") ||
                        control.getInternalDeviceName().equals("Switch 12") ||
                        control.getInternalDeviceName().equals("Light 1") ||
                        control.getInternalDeviceName().equals("Light 2") ||
                        control.getInternalDeviceName().equals("Light 3") ||
                        control.getInternalDeviceName().equals("Light 4") ||
                        control.getInternalDeviceName().equals("Light 5") ||
                        control.getInternalDeviceName().equals("Light 6") ||
                        control.getInternalDeviceName().equals("Light 7") ||
                        control.getInternalDeviceName().equals("Light 8") ||
                        control.getInternalDeviceName().equals("Light 9") ||
                        control.getInternalDeviceName().equals("Light 10") ||
                        control.getInternalDeviceName().equals("Light 11") ||
                        control.getInternalDeviceName().equals("Light 12") ||
                        control.getInternalDeviceName().equals("Power Socket 1") ||
                        control.getInternalDeviceName().equals("Power Socket 2") ||
                        control.getInternalDeviceName().equals("Power Socket 3") ||
                        control.getInternalDeviceName().equals("Power Socket 4") ||
                        control.getInternalDeviceName().equals("Power Socket 5") ||
                        control.getInternalDeviceName().equals("Power Socket 6")) {
                    handleDeviceClick(control, holder);
                } else {
                    navigateToDeviceCardFragment(view, control);
                }
            }
        });

        if(control.getControlTypeName().equals("Fans")){
            holder.imageViewDevice.setImageResource(R.drawable.fan_white);
        }else if (control.getControlTypeName().equals("Power Sockets")){
            holder.imageViewDevice.setImageResource(R.drawable.lighting);
        }else if (control.getControlTypeName().equals("Lights")){
            holder.imageViewDevice.setImageResource(R.drawable.lighting);
        }else if (control.getControlTypeName().equals("Air Conditioners")){
            holder.imageViewDevice.setImageResource(R.drawable.airconditionwhite);
        }else if (control.getControlTypeName().equals("Bells")){
            holder.imageViewDevice.setImageResource(R.drawable.notificationbell);
        }else if (control.getControlTypeName().equals("Curtains")){
            holder.imageViewDevice.setImageResource(R.drawable.cutrainwhite);
        }else{
            holder.imageViewDevice.setImageResource(R.drawable.lighting);
        }

        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = (nightModeFlags == Configuration.UI_MODE_NIGHT_YES);

        if (isNightMode) {


            if (control.getNodeId().equals("67GzSa3JJk2UyAOL_vc1GQ")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_1);
            } else if (control.getNodeId().equals("t4pUbUEPikePM0ktp_U2lQ")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_2);
            } else if (control.getNodeId().equals("muTtA_hbXUeQTnWea0ioHg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_3);
            } else if (control.getNodeId().equals("aMMrBGCIA0m59yn3FlwBXg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_1);
            } else if (control.getNodeId().equals("u08RcExKskq9jsIJhB1Kdg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_2);
            } else if (control.getNodeId().equals("GmgTuVtHvEeBy0CXvJpTJg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_3);
            } else if (control.getNodeId().equals("ElmKriPDaEGoXTjkngt89A")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_1);
            } else if (control.getNodeId().equals("h54wL3cTwUOJ5EGYIRx4rQ")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_2);
            } else if (control.getNodeId().equals("VBRaTWYykEqB7Jj-yuKdCg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_3);
            } else if (control.getNodeId().equals("bQBSuPy7XE6KO2cd7MRVWQ")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_1);
            } else if (control.getNodeId().equals("DYksB1pG9EqpF5XOITZyHg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_2);
            } else if (control.getNodeId().equals("uXxcNG6HV0avLt3mlg8FNw")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_3);
            }
        }
        else{
            if (control.getNodeId().equals("67GzSa3JJk2UyAOL_vc1GQ")) {
                holder.imageViewDevice.setImageResource(R.drawable.lighting_wl_black);
            } else if (control.getNodeId().equals("t4pUbUEPikePM0ktp_U2lQ")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_black);
            } else if (control.getNodeId().equals("muTtA_hbXUeQTnWea0ioHg")) {
                holder.imageViewDevice.setImageResource(R.drawable.reading_right_black);
            } else if (control.getNodeId().equals("aMMrBGCIA0m59yn3FlwBXg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lighting_wl_black);
            } else if (control.getNodeId().equals("u08RcExKskq9jsIJhB1Kdg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_black);
            } else if (control.getNodeId().equals("GmgTuVtHvEeBy0CXvJpTJg")) {
                holder.imageViewDevice.setImageResource(R.drawable.reading_right_black);
            } else if (control.getNodeId().equals("ElmKriPDaEGoXTjkngt89A")) {
                holder.imageViewDevice.setImageResource(R.drawable.lighting_wl_black);
            } else if (control.getNodeId().equals("h54wL3cTwUOJ5EGYIRx4rQ")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_black);
            } else if (control.getNodeId().equals("VBRaTWYykEqB7Jj-yuKdCg")) {
                holder.imageViewDevice.setImageResource(R.drawable.reading_right_black);
            } else if (control.getNodeId().equals("bQBSuPy7XE6KO2cd7MRVWQ")) {
                holder.imageViewDevice.setImageResource(R.drawable.lighting_wl_black);
            } else if (control.getNodeId().equals("DYksB1pG9EqpF5XOITZyHg")) {
                holder.imageViewDevice.setImageResource(R.drawable.lamp_black);
            } else if (control.getNodeId().equals("uXxcNG6HV0avLt3mlg8FNw")) {
                holder.imageViewDevice.setImageResource(R.drawable.reading_right_black);
            }
        }
        }


    private void updateFevImage(ImageView fevImage, boolean isFavorite) {
        int imageRes = isFavorite ? R.drawable.likered : R.drawable.likewhite;
        fevImage.setImageResource(imageRes);
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
        ImageView fevImage,imageViewDevice;
        CardView cardView;
        //boolean isFavorite = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceNameTextView = itemView.findViewById(R.id.btnTitle);
            llGuestControl = itemView.findViewById(R.id.llGuestControl);
            fevImage = itemView.findViewById(R.id.fev_image);
            cardView = itemView.findViewById(R.id.card_view);
            imageViewDevice = itemView.findViewById(R.id.imageViewDevice);
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


    private void callAddSpaceUserFavouriteApi(Controls control, ViewHolder holder) {
        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
        Long GaaProjectSpaceTypePlannedDeviceConRef = Long.valueOf(control.getgAAProjectSpaceTypePlannedDeviceConnectionRef());
        SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPreferences.edit();
        editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceConRef);
        editor3.apply();

        SharedPreferences sharedPreferencesPSPDR = context.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        Long gaaProjectSpaceTypePlannedDeviceConRef = sharedPreferencesPSPDR.getLong("KEY_USERNAME", 0);

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences1);
        String loginDeviceId = GUID.trim();

        SharedPreferences sharedPreferences2 = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferences3 = context.getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
        String projectSpaceRef = saveProjectSpaceRef.trim();

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<AddSpaceUserFavourite> call = apiService.addSpaceUserFavourite(projectSpaceRef, String.valueOf(gaaProjectSpaceTypePlannedDeviceConRef), loginToken, loginDeviceId);
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

    private void callRemoveSpaceUserFavouriteApi(Controls control, ViewHolder holder) {
        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
        Long GaaProjectSpaceTypePlannedDeviceConRef = Long.valueOf(control.getgAAProjectSpaceTypePlannedDeviceConnectionRef());
        SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPreferences.edit();
        editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceConRef);
        editor3.apply();

        SharedPreferences sharedPreferencesPSPDR = context.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        Long gaaProjectSpaceTypePlannedDeviceConRef = sharedPreferencesPSPDR.getLong("KEY_USERNAME", 0);

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences1);
        String loginDeviceId = GUID.trim();

        SharedPreferences sharedPreferences2 = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferences3 = context.getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
        String projectSpaceRef = saveProjectSpaceRef.trim();

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RemoveSpaceUserFavorite> call = apiService.removeSpaceUserFavourite(projectSpaceRef, String.valueOf(gaaProjectSpaceTypePlannedDeviceConRef), loginToken, loginDeviceId);
        call.enqueue(new retrofit2.Callback<RemoveSpaceUserFavorite>() {
            @Override
            public void onResponse(Call<RemoveSpaceUserFavorite> call, retrofit2.Response<RemoveSpaceUserFavorite> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Removed from favourites successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to remove from favourites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveSpaceUserFavorite> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToDeviceCardFragment(View view, Controls control) {
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        Long GaaProjectSpaceTypePlannedDeviceRef = Long.valueOf(control.getgAAProjectSpaceTypePlannedDeviceRef());
        SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPreferences.edit();
        editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceRef);
        editor3.apply();

        String label = control.getLabel();
        SharedPreferences sharedPreferences1 = inflater.getContext().getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor1.putString("KEY_USERNAMEs", label);
        editor1.apply();

        String nodeId = control.getNodeId();
        SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.putString("KEY_USERNAMEs", nodeId);
        editor2.apply();

        FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DeviceCardFragment newFragment = new DeviceCardFragment();
        transaction.replace(R.id.DeviceCard, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}







