package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.KeyContactAdapter;
import com.gladiance.ui.adapters.PromotionAdapter;
import com.gladiance.ui.models.keycontacts.KeyContactsRes;
import com.gladiance.ui.models.promotionlist.ObjectTag;
import com.gladiance.ui.models.promotionlist.PromotionRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionActivity extends AppCompatActivity {

    RecyclerView recyclerViewPromotion;
    private PromotionAdapter adapterPromotion;
    private ArrayList<com.gladiance.ui.models.promotionlist.ObjectTag> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        recyclerViewPromotion = findViewById(R.id.rv_promotion);
        arrayList = new ArrayList<>();

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

        getPromotion(gAAProjectRef,loginToken,loginDeviceId);


    }

    public void getPromotion(String gAAProject,String loginToken,String loginDeviceId){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<PromotionRes> call = apiService.getPromotion(gAAProject,loginToken,loginDeviceId);

        call.enqueue(new Callback<PromotionRes>() {
            @Override
            public void onResponse(Call<PromotionRes> call, Response<PromotionRes> response) {
                if(response.isSuccessful()){
                    PromotionRes promotionRes = response.body();
                    if(promotionRes.getSuccessful() && promotionRes != null){
                        List<com.gladiance.ui.models.promotionlist.ObjectTag> promotionList = promotionRes.getObjectTag();

                        for(com.gladiance.ui.models.promotionlist.ObjectTag objectTag : promotionList){
                            Log.e(TAG, "onResponse EventName: " + objectTag.getEventName());
                            arrayList.add(new ObjectTag(objectTag.getRef(),objectTag.getEventName(),objectTag.getDescription(),objectTag.getImageUrl(),objectTag.getVideoUrl(),objectTag.getgAAProjectRef(),objectTag.getgAAProjectName()));
                        }
                        PromotionAdapter promotionAdapter  = new PromotionAdapter(arrayList);
                        recyclerViewPromotion.setAdapter(promotionAdapter);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(PromotionActivity.this,1, GridLayoutManager.VERTICAL,false);
                        recyclerViewPromotion.setLayoutManager(gridLayoutManager1);
                    }
                }
            }
            @Override
            public void onFailure(Call<PromotionRes> call, Throwable t) {
            }
        });

    }
}