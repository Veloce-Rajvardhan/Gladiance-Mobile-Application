package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.NetworkApiManager;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.activities.MyProfile.BasicInfoActivity;
import com.gladiance.ui.fragment.RoomControl.DeviceCardFragment;
import com.gladiance.ui.models.RemoveSpaceUserFavorite;
import com.gladiance.ui.models.favoritelist.ObjectTag;
import com.gladiance.ui.models.guestlandingpage.Controls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder> {


    private List<ObjectTag> arraylistFav;
    private Context context;
    private NetworkApiManager networkApiManager;
    private EspApplication espApp;


    public FavoriteListAdapter(ArrayList<ObjectTag> arraylistFav, Context context) {
        this.arraylistFav = arraylistFav;
        this.context = context;
        this.espApp = new EspApplication(context.getApplicationContext());
        this.networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);
    }

    @NonNull
    @Override
    public FavoriteListAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_control, parent, false);
        return new FavoriteListAdapter.FavoriteViewHolder(view);
    }

    public void handleDeviceClick(ObjectTag objectTag,FavoriteViewHolder favoriteViewHolder){

        objectTag.setPowerState(!objectTag.isPowerState());
        updateUI(favoriteViewHolder, objectTag.isPowerState());

        LayoutInflater inflater = LayoutInflater.from(favoriteViewHolder.itemView.getContext());
        String nodeId = objectTag.getNodeId();
        SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        Log.e(TAG, "Node Id: " + nodeId);
        editor.putString("KEY_USERNAMEs", nodeId);
        editor.apply();

        String name = objectTag.getInternalDeviceName();

        sendSwitchState(objectTag.isPowerState(), name, nodeId);

    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.FavoriteViewHolder holder, int position) {
        ObjectTag objectTag = arraylistFav.get(position);
        holder.FavTextView.setText(objectTag.getLabel());


        holder.ImageFav.setImageResource(R.drawable.likered);
        holder.ImageFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                Long GaaProjectSpaceTypePlannedDeviceConRef = Long.valueOf(objectTag.getgAAProjectSpaceTypePlannedDeviceConnectionRef());
                SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences.edit();
                Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceConRef: " + GaaProjectSpaceTypePlannedDeviceConRef);
                editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceConRef);
                editor3.apply();

                String message = "Are you sure you want to remove this item from favorites?";
                showCustomDialogBox(message,holder.getAdapterPosition());

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (objectTag.getInternalDeviceName().equals("Switch 1")) {
                    handleDeviceClick(objectTag,holder);
                }else if (objectTag.getInternalDeviceName().equals("Light 1")) {
                    handleDeviceClick(objectTag,holder);
                }else if (objectTag.getInternalDeviceName().equals("Light 2")) {
                    handleDeviceClick(objectTag,holder);
                }else if (objectTag.getInternalDeviceName().equals("Power Socket 1")) {
                    handleDeviceClick(objectTag,holder);
                }
                else {
                    navigateToDeviceCardFragment(view,objectTag);
                }

            }
        });

        holder.FavTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (objectTag.getInternalDeviceName().equals("Switch 1")) {
                    handleDeviceClick(objectTag,holder);
                }else if (objectTag.getInternalDeviceName().equals("Light 1")) {
                    handleDeviceClick(objectTag,holder);
                }else if (objectTag.getInternalDeviceName().equals("Light 2")) {
                    handleDeviceClick(objectTag,holder);
                }else if (objectTag.getInternalDeviceName().equals("Power Socket 1")) {
                    handleDeviceClick(objectTag,holder);
                }
                else {
                    navigateToDeviceCardFragment(view,objectTag);
                }

            }
        });

        if(objectTag.getNodeId().equals("67GzSa3JJk2UyAOL_vc1GQ")){
            holder.imageViewDevice.setImageResource(R.drawable.lamp_1);
        }else if(objectTag.getNodeId().equals("t4pUbUEPikePM0ktp_U2lQ")){
            holder.imageViewDevice.setImageResource(R.drawable.lamp_2);
        }else if(objectTag.getNodeId().equals("muTtA_hbXUeQTnWea0ioHg")){
            holder.imageViewDevice.setImageResource(R.drawable.lamp_3);
        }else if(objectTag.getNodeId().equals("ElmKriPDaEGoXTjkngt89A")){
            holder.imageViewDevice.setImageResource(R.drawable.lamp_4);
        }

    }

    private void updateUI(FavoriteViewHolder holder, boolean powerState) {
        int borderColorRes = powerState ? R.drawable.transparent_orange_switch_bg : R.drawable.transparent_backgraund;
        holder.llGuestControl.setBackground(ContextCompat.getDrawable(context, borderColorRes));
    }

    @Override
    public int getItemCount() {
        return arraylistFav.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{

        TextView FavTextView;
        ImageView ImageFav,imageViewDevice;
        LinearLayout llGuestControl;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            FavTextView = itemView.findViewById(R.id.btnTitle);
            ImageFav = itemView.findViewById(R.id.fev_image);
            llGuestControl = itemView.findViewById(R.id.llGuestControl);
            imageViewDevice = itemView.findViewById(R.id.imageViewDevice);
        }
    }


    TextView tvMessage;
    Button btnYes, btnNo;

    private void showCustomDialogBox(String message, int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.favorite_custom_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvMessage = dialog.findViewById(R.id.tvMessage);
        btnYes = dialog.findViewById(R.id.btn_Yes);
        btnNo = dialog.findViewById(R.id.btn_No);

        tvMessage.setText(message);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferencesPSPDR = context.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                Long gaaProjectSpaceTypePlannedDeviceConRef = sharedPreferencesPSPDR.getLong("KEY_USERNAME", 0);
                Log.e(TAG, "Retrieved GaaProjectSpaceTypePlannedDeviceRef: " + gaaProjectSpaceTypePlannedDeviceConRef);

                SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String GUID = LoginActivity.getUserId(sharedPreferences1);
                Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
                String loginDeviceId = GUID.trim();

                SharedPreferences sharedPreferences2 = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
                Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
                String loginToken = savedLoginDeviceId.trim();

                SharedPreferences sharedPreferences3 = context.getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
                String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
                Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
                String projectSpaceRef = saveProjectSpaceRef.trim();

                callRemoveSpaceUserFavouriteApi(projectSpaceRef, String.valueOf(gaaProjectSpaceTypePlannedDeviceConRef), loginToken, loginDeviceId);
                removeItem(position);

                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callRemoveSpaceUserFavouriteApi(String gaaProjectSpaceRef,String gaaProjectSpaceTypePlannedDeviceConnectionRef,String loginToken,String loginDeviceId) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RemoveSpaceUserFavorite> call = apiService.removeSpaceUserFavourite(gaaProjectSpaceRef, gaaProjectSpaceTypePlannedDeviceConnectionRef, loginToken, loginDeviceId);
        call.enqueue(new retrofit2.Callback<RemoveSpaceUserFavorite>() {
            @Override
            public void onResponse(Call<RemoveSpaceUserFavorite> call, retrofit2.Response<RemoveSpaceUserFavorite> response) {
                if (response.isSuccessful()) {
                    RemoveSpaceUserFavorite removeSpaceUserFavorite = response.body();
                    Toast.makeText(context, "Remove to favourites successfully", Toast.LENGTH_SHORT).show();
                    Log.e("Successful", "Success: " + removeSpaceUserFavorite.getSuccessful());
                    Log.e("Remove", "Message: " + removeSpaceUserFavorite.getMessage());

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

    private void navigateToDeviceCardFragment(View view, ObjectTag objectTag) {
        LayoutInflater inflater = LayoutInflater.from(view.getContext());

        String Label = objectTag.getLabel();
        SharedPreferences sharedPreferences1 = inflater.getContext().getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        Log.e(TAG, "Label: " + Label);
        editor1.putString("KEY_USERNAMEs", Label);
        editor1.apply();

        String nodeId = objectTag.getNodeId();
        SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        Log.e(TAG, "Node Id: " + nodeId);
        editor.putString("KEY_USERNAMEs", nodeId);
        editor.apply();

        FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DeviceCardFragment newFragment = new DeviceCardFragment();
        transaction.replace(R.id.favoriteContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void removeItem(int position) {
        if (position < 0 || position >= arraylistFav.size()) {
            return;
        }
        arraylistFav.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arraylistFav.size());
    }


}