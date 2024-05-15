package com.gladiance.ui.fragment.DeviceControls;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gladiance.NetworkApiManager;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.models.ResponseModel;

public class RGBLightFragment extends Fragment {

    private static final String TAG = "RGBLightFragment";

    Switch rgbLightSwitch;
    String nodeId;
    NetworkApiManager networkApiManager;
    private EspApplication espApp;
    Context context;
    SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5;
    TextView textView1, textView2, textView3, textView4, textView5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_rgblight, container, false);

        context = getContext();
        espApp = new EspApplication(context);
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);
        SharedPreferences preferences2 = context.getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        rgbLightSwitch = view.findViewById(R.id.switchButtonFan);
        seekBar1 = view.findViewById(R.id.seekBarDimmer);
        seekBar2 = view.findViewById(R.id.seekBarHue);
        seekBar3 = view.findViewById(R.id.seekBarSaturation);
        seekBar4 = view.findViewById(R.id.seekBarCCT);
        seekBar5 = view.findViewById(R.id.seekBarWhiteBrightness);

        textView1 = view.findViewById(R.id.tv_brightness);
        textView2 = view.findViewById(R.id.tv_hue);
        textView3 = view.findViewById(R.id.tv_saturation);
        textView4 = view.findViewById(R.id.tv_CCT);
        textView5 = view.findViewById(R.id.tv_Whitebrightness);

        disableSeekBars();

        //Dimmer ON/OFF Code
        rgbLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                rgbLightState(isChecked);
                if (isChecked) {
                    enableSeekBars();
                } else {
                    disableSeekBars();
                }
            }
        });

        //Seek Bar Brightness
        seekBar1.setMax(99);
        seekBar1.setProgress(0);
        textView1.setText("0");

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView1.setText(String.valueOf(progress + 1));

                // Send value to server
                rgbBrightness(progress + 1);
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

        //Seek Bar Hue
        seekBar2.setMax(360);
        seekBar2.setProgress(0);
        textView2.setText("0");

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView2.setText(String.valueOf(progress + 1));
                int hue = progress; // Hue ranges from 0 to 360
                int color = Color.HSVToColor(new float[]{hue, 1, 1}); // Convert to RGB color
                // Set the thumb color
                seekBar.getThumb().mutate().setTint(color);
                // Set the progress bar background color
                seekBar.getProgressDrawable().mutate().setTint(color);
                // Send value to server
                rgbHue(progress + 1);
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


        //Seek Bar Saturation
        seekBar3.setMax(99);
        seekBar3.setProgress(1);
        textView3.setText("0");

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView3.setText(String.valueOf(progress + 1));

                // Send value to server
                rgbSaturation(progress + 1);
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

        //Seek Bar CCT
        seekBar4.setMax(6500);
        seekBar4.setProgress(2700);
        textView4.setText("0");

        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView4.setText(String.valueOf(progress + 1));

                // Send value to server
                rgbCCT(progress + 1);
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

        //Seek White Brightness
        seekBar5.setMax(99);
        seekBar5.setProgress(0);
        textView5.setText("0");

        seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView5.setText(String.valueOf(progress + 1));

                // Send value to server
                rgbWhiteBrightness(progress + 1);
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

        return view;
    }

    private void rgbLightState(boolean powerState) {
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

    private void rgbBrightness(int progress){

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"Brightness\": " + progress + "}}";

        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbHue(int progress){

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Hue\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbSaturation(int progress){

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Saturation\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbCCT(int progress){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"CCT\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void rgbWhiteBrightness(int progress){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"White Brightness\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void disableSeekBars() {
        seekBar1.setEnabled(false);
        seekBar2.setEnabled(false);
        seekBar3.setEnabled(false);
        seekBar4.setEnabled(false);
        seekBar5.setEnabled(false);
    }

    // Function to enable all SeekBars
    private void enableSeekBars() {
        seekBar1.setEnabled(true);
        seekBar2.setEnabled(true);
        seekBar3.setEnabled(true);
        seekBar4.setEnabled(true);
        seekBar5.setEnabled(true);
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
