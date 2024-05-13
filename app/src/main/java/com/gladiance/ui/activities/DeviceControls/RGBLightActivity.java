package com.gladiance.ui.activities.DeviceControls;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gladiance.NetworkApiManager;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.models.ResponseModel;

public class RGBLightActivity extends AppCompatActivity {

    Switch rgbLightSwitch;
    String nodeId;
    NetworkApiManager networkApiManager;
    private EspApplication espApp;
    Context context = this;
    SeekBar seekBar1,seekBar2,seekBar3,seekBar4,seekBar5;
    TextView textView1,textView2,textView3,textView4,textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgblight);

        espApp = new EspApplication(getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);
        SharedPreferences preferences2 = getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        rgbLightSwitch = findViewById(R.id.switchButtonFan);
        seekBar1 = findViewById(R.id.seekBarDimmer);
        seekBar2 = findViewById(R.id.seekBarHue);
        seekBar3 = findViewById(R.id.seekBarSaturation);
        seekBar4 = findViewById(R.id.seekBarCCT);
        seekBar5 = findViewById(R.id.seekBarWhiteBrightness);


        textView1 = findViewById(R.id.tv_brightness);
        textView2= findViewById(R.id.tv_hue);
        textView3 = findViewById(R.id.tv_saturation);
        textView4 = findViewById(R.id.tv_CCT);
        textView5 = findViewById(R.id.tv_Whitebrightness);

        disableSeekBars();

        //Dimmer ON/OFF Code
        rgbLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: "+isChecked);
                rgbLightState(isChecked);
                if (isChecked) {
                    enableSeekBars();
                } else {
                    disableSeekBars();
                }


            }
        });


        //Seek Bar Brightness
        seekBar1.setMax(100);
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
        seekBar3.setMax(100);
        seekBar3.setProgress(0);
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
        seekBar5.setMax(100);
        seekBar5.setProgress(0);
        textView5.setText("1");

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


    }

    //RGB ON/OFF
    private void rgbLightState(boolean powerState) {
        // Create a RequestModel with the required data
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Power\": "+powerState+"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


        //        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        //
        //        Intent intent = getIntent();
        //        String message = intent.getStringExtra("MESSAGE_KEY");
        //        Log.e(TAG, "curtainAction: "+message );
        //        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        //        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        //
        //        RequestModel requestModel = new RequestModel();
        //        requestModel.setSenderLoginToken(0);
        //        requestModel.setTopic("node/"+ nodeId2 +"/params/remote");
        //
        //        requestModel.setMessage("{\""+ message +"\": {\"Power\": "+powerState+"}}");
        //        Log.d(TAG, "sendSwitchState: "+powerState);
        //        //  requestModel.setQosLevel(0);
        //        // Make the API call
        //        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
        //        call.enqueue(new Callback<ResponseModel>() {
        //            @Override
        //            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
        //                if (response.isSuccessful()) {
        //                    ResponseModel responseModel = response.body();
        //                    Log.d(TAG, "onResponse: "+responseModel);
        //                    handleApiResponse(responseModel);
        //                } else {
        //                    // Handle unsuccessful response
        //                    Toast.makeText(RGBLightActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
        //                }
        //            }
        //
        //            @Override
        //            public void onFailure(Call<ResponseModel> call, Throwable t) {
        //                // Handle failure
        //                Toast.makeText(RGBLightActivity.this, "Network error", Toast.LENGTH_SHORT).show();
        //            }
        //        });
    }

    private void rgbBrightness(int progress){

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"Brightness\": " + progress + "}}";

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

        ////////////////////////////

        //        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        //
        //        Intent intent = getIntent();
        //        String message = intent.getStringExtra("MESSAGE_KEY");
        //        Log.e(TAG, "curtainAction: "+message );
        //
        //
        //        RequestModel requestModel = new RequestModel();
        //        requestModel.setSenderLoginToken(0);
        //        requestModel.setTopic("node/"+ nodeId +"/params/remote");
        //        Log.d(TAG, "sendBrightness: "+progress);
        //        requestModel.setMessage("{\""+ message +"\": {\"Brightness\": " + progress + "}}");
        //        Log.e(TAG, "sendBrightness: "+progress );
        //
        //        //requestModel.setQosLevel(0);
        //        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
        //        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
        //        call.enqueue(new Callback<ResponseModel>() {
        //            @Override
        //            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
        //                if (response.isSuccessful()) {
        //                    ResponseModel responseModel = response.body();
        //                    handleApiResponse(responseModel);
        //                } else {
        //                    // Handle unsuccessful response
        //                    Toast.makeText(RGBLightActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
        //                }
        //            }
        //            @Override
        //            public void onFailure(Call<ResponseModel> call, Throwable t) {
        //                // Handle failure
        //                Toast.makeText(RGBLightActivity.this, "Network error", Toast.LENGTH_SHORT).show();
        //            }
        //        });
    }

    private void rgbHue(int progress){

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Hue\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
        //////////////////////////////


        //        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        //
        //        Intent intent = getIntent();
        //        String message = intent.getStringExtra("MESSAGE_KEY");
        //        Log.e(TAG, "curtainAction: "+message );
        //
        //        RequestModel requestModel = new RequestModel();
        //        requestModel.setSenderLoginToken(0);
        //        requestModel.setTopic("node/"+ nodeId +"/params/remote");
        //        Log.d(TAG, "sendFanSpeed: "+progress);
        //        requestModel.setMessage("{\""+ message +"\": {\"Hue\": " + progress + "}}");
        //        Log.e(TAG, "dimmerProgress: "+progress );
        //
        //        //requestModel.setQosLevel(0);
        //        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
        //        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
        //        call.enqueue(new Callback<ResponseModel>() {
        //            @Override
        //            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
        //                if (response.isSuccessful()) {
        //                    ResponseModel responseModel = response.body();
        //                    handleApiResponse(responseModel);
        //                } else {
        //                    // Handle unsuccessful response
        //                    Toast.makeText(RGBLightActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
        //                }
        //            }
        //            @Override
        //            public void onFailure(Call<ResponseModel> call, Throwable t) {
        //                // Handle failure
        //                Toast.makeText(RGBLightActivity.this, "Network error", Toast.LENGTH_SHORT).show();
        //            }
        //        });
    }

    private void rgbSaturation(int progress){

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);
        String commandBody = "{\""+ name +"\": {\"Saturation\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

        /////////////////////////////
        //        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        //
        //        Intent intent = getIntent();
        //        String message = intent.getStringExtra("MESSAGE_KEY");
        //        Log.e(TAG, "curtainAction: "+message );
        //
        //        RequestModel requestModel = new RequestModel();
        //        requestModel.setSenderLoginToken(0);
        //        requestModel.setTopic("node/"+ nodeId +"/params/remote");
        //        Log.d(TAG, "sendFanSpeed: "+progress);
        //        requestModel.setMessage("{\""+ message +"\": {\"Saturation\": " + progress + "}}");
        //        Log.e(TAG, "dimmerProgress: "+progress );
        //
        //        //requestModel.setQosLevel(0);
        //        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
        //        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
        //        call.enqueue(new Callback<ResponseModel>() {
        //            @Override
        //            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
        //                if (response.isSuccessful()) {
        //                    ResponseModel responseModel = response.body();
        //                    handleApiResponse(responseModel);
        //                } else {
        //                    // Handle unsuccessful response
        //                    Toast.makeText(RGBLightActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
        //                }
        //            }
        //            @Override
        //            public void onFailure(Call<ResponseModel> call, Throwable t) {
        //                // Handle failure
        //                Toast.makeText(RGBLightActivity.this, "Network error", Toast.LENGTH_SHORT).show();
        //            }
        //        });
    }

    private void rgbCCT(int progress){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"CCT\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

        ///////////////////////////////////////
        //        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        //
        //        Intent intent = getIntent();
        //        String message = intent.getStringExtra("MESSAGE_KEY");
        //        Log.e(TAG, "curtainAction: "+message );
        //
        //
        //        RequestModel requestModel = new RequestModel();
        //        requestModel.setSenderLoginToken(0);
        //        requestModel.setTopic("node/"+ nodeId +"/params/remote");
        //        Log.d(TAG, "sendFanSpeed: "+progress);
        //        requestModel.setMessage("{\""+ message +"\": {\"CCT\": " + progress + "}}");
        //        Log.e(TAG, "dimmerProgress: "+progress );
        //
        //        //requestModel.setQosLevel(0);
        //        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
        //        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
        //        call.enqueue(new Callback<ResponseModel>() {
        //            @Override
        //            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
        //                if (response.isSuccessful()) {
        //                    ResponseModel responseModel = response.body();
        //                    handleApiResponse(responseModel);
        //                } else {
        //                    // Handle unsuccessful response
        //                    Toast.makeText(RGBLightActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
        //                }
        //            }
        //            @Override
        //            public void onFailure(Call<ResponseModel> call, Throwable t) {
        //                // Handle failure
        //                Toast.makeText(RGBLightActivity.this, "Network error", Toast.LENGTH_SHORT).show();
        //            }
        //        });
    }

    private void rgbWhiteBrightness(int progress){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"White Brightness\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

        ////////////////////////////////////
        //        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        //
        //        Intent intent = getIntent();
        //        String message = intent.getStringExtra("MESSAGE_KEY");
        //        Log.e(TAG, "curtainAction: "+message );
        //
        //
        //        RequestModel requestModel = new RequestModel();
        //        requestModel.setSenderLoginToken(0);
        //        requestModel.setTopic("node/"+ nodeId +"/params/remote");
        //        Log.d(TAG, "sendFanSpeed: "+progress);
        //        requestModel.setMessage("{\""+ message +"\": {\"White Brightness\": " + progress + "}}");
        //        Log.e(TAG, "dimmerProgress: "+progress );
        //
        //        //requestModel.setQosLevel(0);
        //        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
        //        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
        //        call.enqueue(new Callback<ResponseModel>() {
        //            @Override
        //            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
        //                if (response.isSuccessful()) {
        //                    ResponseModel responseModel = response.body();
        //                    handleApiResponse(responseModel);
        //                } else {
        //                    // Handle unsuccessful response
        //                    Toast.makeText(RGBLightActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
        //                }
        //            }
        //            @Override
        //            public void onFailure(Call<ResponseModel> call, Throwable t) {
        //                // Handle failure
        //                Toast.makeText(RGBLightActivity.this, "Network error", Toast.LENGTH_SHORT).show();
        //            }
        //        });
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

            Toast.makeText(this, "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Handle unsuccessful response
            Toast.makeText(this, "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }
}


