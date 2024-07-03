package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Home.ProjectSpaceGroupActivity;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.AmenitiesAdapter;
import com.gladiance.ui.adapters.KeyContactAdapter;
import com.gladiance.ui.adapters.ProjectSpaceGroupListAdapter;
import com.gladiance.ui.adapters.TelephoneAdapter;
import com.gladiance.ui.models.ProjectSpaceGroupReqModel;
import com.gladiance.ui.models.SpaceGroup;
import com.gladiance.ui.models.amenities.AmenitiesRes;
import com.gladiance.ui.models.keycontacts.KeyContactsRes;
import com.gladiance.ui.models.keycontacts.ObjectTag;
import com.gladiance.ui.models.telephonenumber.TelephoneNumberRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelInfoActivity extends AppCompatActivity {

    LinearLayout btn_amenities, layout_hide_show, btn_telephone, layout_hide_show1, btn_key_contact, layout_hide_show2;

    RecyclerView recycleViewCB, recyclerViewTelephone, recyclerViewAmenities, recyclerViewKeyContacts;

    ImageView imageView;

    private ArrayList<ObjectTag> arrayList;
    private ArrayList<com.gladiance.ui.models.telephonenumber.ObjectTag> arrayListTN;
    private ArrayList<com.gladiance.ui.models.amenities.ObjectTag> arrayListAmenities;


    private boolean contentChanged = false;
    ImageView imageViewArrow1, imageViewArrow2, imageViewArrow3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);

        btn_amenities = findViewById(R.id.btn_amenities);
        layout_hide_show = findViewById(R.id.layout_hide_show);
        btn_telephone = findViewById(R.id.btn_telephone);
        layout_hide_show1 = findViewById(R.id.layout_hide_show1);
        btn_key_contact = findViewById(R.id.btn_key_contact);
        layout_hide_show2 = findViewById(R.id.layout_hide_show2);
        recyclerViewKeyContacts = findViewById(R.id.rv_keyContact);
        recyclerViewTelephone = findViewById(R.id.rv_telephone);
        recyclerViewAmenities = findViewById(R.id.rv_amenities);


        imageViewArrow1 = findViewById(R.id.imgarrow1);
        imageViewArrow2 = findViewById(R.id.imgarrow2);
        imageViewArrow3 = findViewById(R.id.imgarrow3);

        arrayList = new ArrayList<>();
        arrayListTN = new ArrayList<>();
        arrayListAmenities = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences  sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferences5 = getSharedPreferences("MyPrefsPR", Context.MODE_PRIVATE);
        String ProjectRef = sharedPreferences5.getString("ProjectRef", "");
        String gAAProjectRef = ProjectRef.trim();

        getKeyContactsList(gAAProjectRef,loginToken,loginDeviceId);
        getTelephoneNumber(gAAProjectRef,loginToken,loginDeviceId);
        getAmenities(gAAProjectRef,loginToken,loginDeviceId);


        btn_amenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isvisible = layout_hide_show.getVisibility();
                if (isvisible == View.VISIBLE) {
                    layout_hide_show.setVisibility(View.GONE);
                } else {
                    layout_hide_show.setVisibility(View.VISIBLE);
                }

                if (contentChanged) {
                    imageViewArrow1.setImageResource(R.drawable.arrowdown2);

                } else {
                    imageViewArrow1.setImageResource(R.drawable.arrowup);

                }
                contentChanged = !contentChanged;
            }


        });


        btn_telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isvisible = layout_hide_show1.getVisibility();
                if (isvisible == View.VISIBLE) {
                    layout_hide_show1.setVisibility(View.GONE);
                } else {
                    layout_hide_show1.setVisibility(View.VISIBLE);
                }

                if (contentChanged) {
                    imageViewArrow2.setImageResource(R.drawable.arrowdown2);

                } else {
                    imageViewArrow2.setImageResource(R.drawable.arrowup);

                }
                contentChanged = !contentChanged;
            }
        });

        btn_key_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isvisible = layout_hide_show2.getVisibility();
                if (isvisible == View.VISIBLE) {
                    layout_hide_show2.setVisibility(View.GONE);
                } else {
                    layout_hide_show2.setVisibility(View.VISIBLE);
                }

                if (contentChanged) {
                    imageViewArrow3.setImageResource(R.drawable.arrowdown2);

                } else {
                    imageViewArrow3.setImageResource(R.drawable.arrowup);

                }
                contentChanged = !contentChanged;
            }
        });


    }

    public void getKeyContactsList(String gAAProject,String loginToken,String loginDeviceId){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<KeyContactsRes> call = apiService.getKeyContacts(gAAProject,loginToken,loginDeviceId);

        call.enqueue(new Callback<KeyContactsRes>() {
            @Override
            public void onResponse(Call<KeyContactsRes> call, Response<KeyContactsRes> response) {
                if(response.isSuccessful()){
                    KeyContactsRes keyContactsRes = response.body();
                    if(keyContactsRes.getSuccessful() && keyContactsRes != null){
                        List<ObjectTag> keyContactList = keyContactsRes.getObjectTag();

                        for(ObjectTag objectTag : keyContactList){
                            Log.e(TAG, "onResponse SpaceGroupName: " + objectTag.getName());
                            arrayList.add(new ObjectTag(objectTag.getRef(),objectTag.getName(),objectTag.getGoogleLocation(),objectTag.getTelephoneNumber(),objectTag.getgAAProjectRef(),objectTag.getgAAProjectName()));
                        }
                        KeyContactAdapter KeyContactAdapter  = new KeyContactAdapter(HotelInfoActivity.this,arrayList);
                        recyclerViewKeyContacts.setAdapter(KeyContactAdapter);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(HotelInfoActivity.this,1, GridLayoutManager.VERTICAL,false);
                        recyclerViewKeyContacts.setLayoutManager(gridLayoutManager1);
                    }
                }
            }
            @Override
            public void onFailure(Call<KeyContactsRes> call, Throwable t) {
            }
        });

    }

    public void getTelephoneNumber(String gAAProject,String loginToken,String loginDeviceId){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<TelephoneNumberRes> call = apiService.getTelephoneNumber(gAAProject,loginToken,loginDeviceId);

        call.enqueue(new Callback<TelephoneNumberRes>() {
            @Override
            public void onResponse(Call<TelephoneNumberRes> call, Response<TelephoneNumberRes> response) {
                if(response.isSuccessful()){
                    TelephoneNumberRes telephoneNumberRes = response.body();
                    if(telephoneNumberRes.getSuccessful() && telephoneNumberRes != null){
                        List<com.gladiance.ui.models.telephonenumber.ObjectTag> telephoneList = telephoneNumberRes.getObjectTag();

                        for(com.gladiance.ui.models.telephonenumber.ObjectTag objectTag : telephoneList){
                            Log.e(TAG, "onResponse Telephone: " + objectTag.getTelephoneNumber());
                            arrayListTN.add(new com.gladiance.ui.models.telephonenumber.ObjectTag(objectTag.getRef(),objectTag.getName(),objectTag.getTelephoneNumber(),objectTag.getgAAProjectRef(),objectTag.getgAAProjectName()));
                        }
                        TelephoneAdapter telephoneAdapter  = new TelephoneAdapter(arrayListTN);
                        recyclerViewTelephone.setAdapter(telephoneAdapter);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(HotelInfoActivity.this,1, GridLayoutManager.VERTICAL,false);
                        recyclerViewTelephone.setLayoutManager(gridLayoutManager1);
                    }
                }
            }
            @Override
            public void onFailure(Call<TelephoneNumberRes> call, Throwable t) {
            }
        });
    }

    public void getAmenities(String gAAProject,String loginToken,String loginDeviceId){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<AmenitiesRes> call = apiService.getAmenities(gAAProject,loginToken,loginDeviceId);

        call.enqueue(new Callback<AmenitiesRes>() {
            @Override
            public void onResponse(Call<AmenitiesRes> call, Response<AmenitiesRes> response) {
                if(response.isSuccessful()){
                    AmenitiesRes amenitiesRes = response.body();
                    if(amenitiesRes.getSuccessful() && amenitiesRes != null){
                        List<com.gladiance.ui.models.amenities.ObjectTag> telephoneList = amenitiesRes.getObjectTag();

                        for(com.gladiance.ui.models.amenities.ObjectTag objectTag : telephoneList){
                            Log.e(TAG, "onResponse Telephone: " + objectTag.getDescription());
                            arrayListAmenities.add(new com.gladiance.ui.models.amenities.ObjectTag(objectTag.getRef(),objectTag.getVideoUrl(),objectTag.getName(),objectTag.getFromTime(),objectTag.getToTime(),objectTag.getDescription(),objectTag.getgAAProjectRef(),objectTag.getgAAProjectName()));
                        }
                        AmenitiesAdapter amenitiesAdapter  = new AmenitiesAdapter(arrayListAmenities);
                        recyclerViewAmenities.setAdapter(amenitiesAdapter);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(HotelInfoActivity.this,1, GridLayoutManager.VERTICAL,false);
                        recyclerViewAmenities.setLayoutManager(gridLayoutManager1);
                    }
                }
            }
            @Override
            public void onFailure(Call<AmenitiesRes> call, Throwable t) {
            }
        });
    }


}