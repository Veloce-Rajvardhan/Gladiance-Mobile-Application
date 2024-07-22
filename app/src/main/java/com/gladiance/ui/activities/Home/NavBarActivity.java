package com.gladiance.ui.activities.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gladiance.ui.fragment.ControlBouquet.ControlBouquetHorizontalParentFragment;
import com.gladiance.ui.fragment.DoNotDisturb.DoNotDisturbFragment;
import com.gladiance.ui.fragment.Home.HomeFragment;
import com.gladiance.ui.fragment.MyProfile.MyProfileFragment;
import com.gladiance.ui.fragment.RoomControl.AreaLandingFragment;
import com.gladiance.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class NavBarActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    ProgressBar progressBar; // Add ProgressBar field

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        progressBar = findViewById(R.id.progressBar); // Initialize ProgressBar

        // Replace with HomeFragment initially
        replace(new HomeFragment());

        bottomNavigation.show(3, true);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.privacy));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.smartphone));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.temperature_control));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.my_profile));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()) {
                    case 1:
                        handleFragmentChange(new DoNotDisturbFragment());
                        break;
                    case 2:
                        handleFragmentChange(new AreaLandingFragment());
                        break;
                    case 3:
                        handleFragmentChange(new HomeFragment());
                        break;
                    case 4:
                        handleFragmentChange(new ControlBouquetHorizontalParentFragment());
                        break;
                    case 5:
                        handleFragmentChange(new MyProfileFragment());
                        break;
                }

                return null;
            }
        });
    }

    private void handleFragmentChange(final Fragment fragment) {
        // Show the progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Delay the fragment replacement by 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                replace(fragment);
                // Hide the progress bar after replacing the fragment
                progressBar.setVisibility(View.GONE);
            }
        }, 2000); // 2000 milliseconds = 2 seconds
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment).addToBackStack(null);
        transaction.commit();
    }
}
