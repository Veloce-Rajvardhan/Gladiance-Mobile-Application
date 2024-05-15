package com.gladiance.ui.fragment.DeviceControls;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gladiance.NetworkApiManager;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.models.RequestModel;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BellFragment extends Fragment {

    private Switch bellswitch, serviceswitch;
    private Button button;
    private String nodeId;
    private NetworkApiManager networkApiManager;
    private Context context;
    private EspApplication espApp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bell, container, false);

        context = getContext();
        espApp = new EspApplication(context.getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);

        button = view.findViewById(R.id.btn_true);
        bellswitch = view.findViewById(R.id.btn_switch);
        serviceswitch = view.findViewById(R.id.btn_switch2);

        SharedPreferences preferences2 = context.getSharedPreferences("MyPrefse", Context.MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean powerState = true;
                sendSwitchStateTrue(powerState);
            }
        });

        // Set a listener on the switch button
        bellswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                sendSwitchState(isChecked);
            }
        });

        serviceswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                sendServiceState(isChecked);
            }
        });

        return view;
    }

    private void sendSwitchStateTrue(boolean powerState) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "node id: " + nodeId2);

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefsPrimary", Context.MODE_PRIVATE);
        String primary = sharedPreferences1.getString("Primary", "");

        RequestModel requestModel = new RequestModel();
        requestModel.setSenderLoginToken(0);
        requestModel.setTopic("node/" + nodeId2 + "/params/remote");
        requestModel.setMessage("{\""+name+"\": {\""+primary+"\": "+powerState+"}}");

        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel responseModel = response.body();
                    handleApiResponse(responseModel);
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(context, "Failed to make the API call", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                // Handle failure
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSwitchState(boolean powerState) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefsPrimary", Context.MODE_PRIVATE);
        String primary = sharedPreferences1.getString("Primary", "");

        String commandBody = "{\""+name+"\": {\""+primary+"\": "+powerState+"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }

    private void sendServiceState(boolean powerState) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefsPrimary", Context.MODE_PRIVATE);
        String primary = sharedPreferences1.getString("Primary", "");

        String commandBody = "{\""+name+"\": {\""+primary+"\": "+powerState+"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }

    private void handleApiResponse(ResponseModel responseModel) {
        if (responseModel != null) {
            Log.d(TAG, "handleApiResponse: " + responseModel.getSuccessful());
            Log.d(TAG, "handleApiResponse: " + responseModel.getTag());

            Toast.makeText(context, "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }
}
