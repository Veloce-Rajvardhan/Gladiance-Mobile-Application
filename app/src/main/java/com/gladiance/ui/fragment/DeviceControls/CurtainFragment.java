package com.gladiance.ui.fragment.DeviceControls;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.gladiance.NetworkApiManager;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.R;

public class CurtainFragment extends Fragment {

    private CardView curtainOpen, curtainClose, curtainStop;
    private String nodeId;
    private String open;
    private NetworkApiManager networkApiManager;
    private SeekBar seekBar;
    private TextView textView;
    private Button setTimeBtn;
    private Context context;
    private EspApplication espApp;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curtain, container, false);

        context = getContext();
        espApp = new EspApplication(context.getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);

        curtainOpen = view.findViewById(R.id.curtainOpen);
        curtainClose = view.findViewById(R.id.curtainClose);
        curtainStop = view.findViewById(R.id.curtainStop);
        seekBar = view.findViewById(R.id.seekBar);
        textView = view.findViewById(R.id.textView);
        setTimeBtn = view.findViewById(R.id.setTimeBtn);

        SharedPreferences preferences2 = context.getSharedPreferences("MyPrefse", Context.MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        curtainOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open = "Open";
                curtainAction(open);
            }
        });

        curtainClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open = "Close";
                curtainAction(open);
            }
        });

        curtainStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open = "Stop";
                curtainAction(open);
            }
        });

        seekBar.setMax(300);
        seekBar.setProgress(1);
        textView.setText("0");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });

        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valueToSend = seekBar.getProgress() + 1;
                curtainCount(valueToSend);
                Log.e(TAG, "onClick: " + seekBar.getProgress());
            }
        });

        return view;
    }

    private void curtainAction(String open) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");

        String commandBody = "{\"" + name + "\": {\"Action\": \"" + open + "\"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }

    private void curtainCount(int count) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");

        String commandBody = "{\"" + name + "\": {\"Transition\": " + count + "}}";

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
