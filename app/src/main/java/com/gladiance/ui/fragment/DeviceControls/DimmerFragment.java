package com.gladiance.ui.fragment.DeviceControls;

import static org.greenrobot.eventbus.EventBus.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.gladiance.NetworkApiManager;
import com.gladiance.R;
import com.gladiance.ui.activities.EspApplication;


public class DimmerFragment extends Fragment {

    Switch dimmerswitch;
    String nodeId;
    TextView textView, textViewDeviceName;
    SeekBar seekBar;
    NetworkApiManager networkApiManager;
    ImageView lampImg;
    Context context;
    private EspApplication espApp;

    public DimmerFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_dimmer2, container, false);
//        espApp = new EspApplication(context.getApplicationContext());
//        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);
//
//        SharedPreferences preferences2 = context.getSharedPreferences("MyPrefse", Context.MODE_PRIVATE);
//        nodeId = preferences2.getString("nodeId", "");
//
//        SharedPreferences preferences = context.getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
//        String Label = preferences.getString("KEY_USERNAMEs", "");
//        Log.d(TAG, "Label : " + Label);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dimmer, container, false);

//        textViewDeviceName = view.findViewById(R.id.DeviceName);
//        dimmerswitch = view.findViewById(R.id.switchButtonDimmer);
//        seekBar = view.findViewById(R.id.seekBarDimmer);
//        textView = view.findViewById(R.id.textView);
//        lampImg = view.findViewById(R.id.dimmer1);
//        textView.setVisibility(View.GONE);
//
//        textViewDeviceName.setText(Label);
//
//        disableSeekBars();
//
//        //Dimmer ON/OFF Code
//        dimmerswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // Handle switch state change
//                Log.d(TAG, "onCheckedChanged: " + isChecked);
//                dimmerState(isChecked);
//                if (isChecked) {
//                    enableSeekBars();
//                    int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                    boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
//                    if (isDarkTheme) {
//                        lampImg.setImageResource(R.drawable.lamp1);
//                    } else {
//                        lampImg.setImageResource(R.drawable.lampblack1);
//                    }
//                    textView.setVisibility(View.VISIBLE);
//                } else {
//                    disableSeekBars();
//                }
//            }
//        });
//
//        seekBar.setMax(99);
//
//        // Setting initial progress
//        seekBar.setProgress(0);
//        textView.setText("0");
//
//        // SeekBar change listener
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // Display current progress value
//                textView.setText(String.valueOf(progress + 1));
//                int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//
//                // Check if it's night mode (dark theme)
//                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
//                if (isDarkTheme) {
//                    if (progress % 10 == 0) {
//                        if (progress == 0) {
//                            lampImg.setImageResource(R.drawable.lamp1);
//                        } else if (progress == 10) {
//                            lampImg.setImageResource(R.drawable.lamp2);
//                        } else if (progress == 20) {
//                            lampImg.setImageResource(R.drawable.lamp3);
//                        } else if (progress == 30) {
//                            lampImg.setImageResource(R.drawable.lamp4);
//                        } else if (progress == 40) {
//                            lampImg.setImageResource(R.drawable.lamp5);
//                        } else if (progress == 50) {
//                            lampImg.setImageResource(R.drawable.lamp6);
//                        } else if (progress == 60) {
//                            lampImg.setImageResource(R.drawable.lamp7);
//                        } else if (progress == 70) {
//                            lampImg.setImageResource(R.drawable.lamp8);
//                        } else if (progress == 80) {
//                            lampImg.setImageResource(R.drawable.lamp9);
//                        } else if (progress == 90) {
//                            lampImg.setImageResource(R.drawable.lamp10);
//                        }
//                    }
//                } else {
//                    if (progress == 0) {
//                        lampImg.setImageResource(R.drawable.lampblack1);
//                    } else if (progress == 10) {
//                        lampImg.setImageResource(R.drawable.lampblack2);
//                    } else if (progress == 20) {
//                        lampImg.setImageResource(R.drawable.lampblack3);
//                    } else if (progress == 30) {
//                        lampImg.setImageResource(R.drawable.lampblack4);
//                    } else if (progress == 40) {
//                        lampImg.setImageResource(R.drawable.lampblack5);
//                    } else if (progress == 50) {
//                        lampImg.setImageResource(R.drawable.lampblack6);
//                    } else if (progress == 60) {
//                        lampImg.setImageResource(R.drawable.lampblack7);
//                    } else if (progress == 70) {
//                        lampImg.setImageResource(R.drawable.lampblack8);
//                    } else if (progress == 80) {
//                        lampImg.setImageResource(R.drawable.lampblack9);
//                    } else if (progress == 90) {
//                        lampImg.setImageResource(R.drawable.lampblack10);
//                    }
//                }
//                dimmerProgress(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // Not needed
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // Not needed
//            }
//        });

        return view;
    }

    private void dimmerState(boolean isChecked) {
        // Implement your dimmer state handling logic
    }

    private void disableSeekBars() {
        // Implement disable seek bars logic
    }

    private void enableSeekBars() {
        // Implement enable seek bars logic
    }

    private void dimmerProgress(int progress) {
        // Implement dimmer progress handling logic
    }
}