package com.gladiance.ui.fragment.DeviceControls;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gladiance.NetworkApiManager;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.R;

public class DimmerFragment extends Fragment {

    private static final String TAG = "DimmerFragment";

    private Switch dimmerswitch;
    private String nodeId;
    private TextView textView;
    private SeekBar seekBar;
    private NetworkApiManager networkApiManager;
    private ImageView lampImg;
    private Context context;
    private EspApplication espApp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        espApp = new EspApplication(context.getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dimmer, container, false);

        SharedPreferences preferences2 = context.getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        dimmerswitch = view.findViewById(R.id.switchButtonDimmer);
        seekBar = view.findViewById(R.id.seekBarDimmer);
        textView = view.findViewById(R.id.textView);
        lampImg = view.findViewById(R.id.dimmer1);
        textView.setVisibility(View.GONE);

        disableSeekBars();

        //Dimmer ON/OFF Code
        dimmerswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                dimmerState(isChecked);
                if (isChecked) {
                    enableSeekBars();
                    textView.setVisibility(View.VISIBLE);
                } else {
                    disableSeekBars();
                    textView.setVisibility(View.GONE);
                }
            }
        });

        seekBar.setMax(99);
        seekBar.setProgress(0);
        textView.setText("0");

        // SeekBar change listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress + 1));
                int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                if (isDarkTheme) {
                    if (progress % 10 == 0) {
                        setLampImageDark(progress);
                    }
                } else {
                    setLampImageLight(progress);
                }
                dimmerProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return view;
    }

    private void setLampImageLight(int progress) {
        switch (progress) {
            case 0:
                lampImg.setImageResource(R.drawable.lampblack1);
                break;
            case 10:
                lampImg.setImageResource(R.drawable.lampblack2);
                break;
            case 20:
                lampImg.setImageResource(R.drawable.lampblack3);
                break;
            case 30:
                lampImg.setImageResource(R.drawable.lampblack4);
                break;
            case 40:
                lampImg.setImageResource(R.drawable.lampblack5);
                break;
            case 50:
                lampImg.setImageResource(R.drawable.lampblack6);
                break;
            case 60:
                lampImg.setImageResource(R.drawable.lampblack7);
                break;
            case 70:
                lampImg.setImageResource(R.drawable.lampblack8);
                break;
            case 80:
                lampImg.setImageResource(R.drawable.lampblack9);
                break;
            case 90:
                lampImg.setImageResource(R.drawable.lampblack10);
                break;
        }
    }

    private void setLampImageDark(int progress) {
        switch (progress) {
            case 0:
                lampImg.setImageResource(R.drawable.lamp1);
                break;
            case 10:
                lampImg.setImageResource(R.drawable.lamp2);
                break;
            case 20:
                lampImg.setImageResource(R.drawable.lamp3);
                break;
            case 30:
                lampImg.setImageResource(R.drawable.lamp4);
                break;
            case 40:
                lampImg.setImageResource(R.drawable.lamp5);
                break;
            case 50:
                lampImg.setImageResource(R.drawable.lamp6);
                break;
            case 60:
                lampImg.setImageResource(R.drawable.lamp7);
                break;
            case 70:
                lampImg.setImageResource(R.drawable.lamp8);
                break;
            case 80:
                lampImg.setImageResource(R.drawable.lamp9);
                break;
            case 90:
                lampImg.setImageResource(R.drawable.lamp10);
                break;
        }
    }

    private void dimmerProgress(int progress) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Intensity\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }

    private void dimmerState(boolean powerState) {
        // Create a RequestModel with the required data

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"Power\": "+powerState+"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void disableSeekBars() {
        seekBar.setEnabled(false);
    }

    // Function to enable all SeekBars
    private void enableSeekBars() {
        seekBar.setEnabled(true);
    }

    private void handleApiResponse(ResponseModel responseModel) {
        // Handle the response as needed
        if (responseModel != null) {
            // API call was successful
            // Access other fields from responseModel if needed
            Log.d(TAG, "handleApiResponse: " +responseModel.getSuccessful());
            Log.d(TAG, "handleApiResponse: " +responseModel.getTag());

            Toast.makeText(requireContext(), "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Handle unsuccessful response
            Toast.makeText(requireContext(), "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }
}