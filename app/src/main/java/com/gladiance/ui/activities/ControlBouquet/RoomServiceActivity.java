package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.FoodMenuAdapter;
import com.gladiance.ui.adapters.LaundryItemListAdapter;
import com.gladiance.ui.fragment.ControlBouquet.AddFoodItemFragment;
import com.gladiance.ui.fragment.ControlBouquet.PlaceOrderFragment;
import com.gladiance.ui.models.PlaceOrderItem;
import com.gladiance.ui.models.foodmoodlist.FoodMenuResponse;
import com.gladiance.ui.models.foodmoodlist.ObjectTag;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomServiceActivity extends AppCompatActivity implements AddFoodItemFragment.OnAddFoodItemListener {

    RecyclerView recyclerViewFood;
    ImageView imageViewPlaceOrder;
    TextView textViewItemCount;
    CardView buttonVeg,buttonNonVeg;
    private ArrayList<ObjectTag> arrayList;
    private List<PlaceOrderItem> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service);

        recyclerViewFood = findViewById(R.id.rv_food_menu);
        imageViewPlaceOrder = findViewById(R.id.imageViewPlaceOrder);
        textViewItemCount = findViewById(R.id.textViewItemCount);
        buttonVeg = findViewById(R.id.button_Veg);
        buttonNonVeg = findViewById(R.id.button_Veg_NonVeg);
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
        Log.e(TAG, "Project Ref : "+gAAProjectRef );

        
        fetchFoodMenu(gAAProjectRef,loginToken,loginDeviceId);

        imageViewPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlaceOrderFragment();
            }
        });

        updateItemCount();

    }


    private void fetchFoodMenu(String gaaProjectRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<FoodMenuResponse> call = apiService.getFoodMenu(gaaProjectRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<FoodMenuResponse>() {
            @Override
            public void onResponse(Call<FoodMenuResponse> call, Response<FoodMenuResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ObjectTag> foodItemList = response.body().getObjectTag();
                    
                    for(ObjectTag objectTag : foodItemList){
                        Log.e(TAG, "onResponse Food Name: " + objectTag.getName());
                        arrayList.add(new ObjectTag(objectTag.getRef(),objectTag.getName(),objectTag.getVideoURL(),objectTag.getgAAProjectRBItemCategory(),objectTag.getgAAProjectRBItemTaste(),objectTag.getSpiceOrSweetLevel(),objectTag.getDescription(),objectTag.getPrice(),objectTag.getgAAProjectRef(),objectTag.getgAAProjectName(),objectTag.getgAAProjectRBItemTasteName(),objectTag.getgAAProjectRBItemCategoryName()));
                    }

                    FoodMenuAdapter foodMenuAdapter = new FoodMenuAdapter(arrayList);
                    recyclerViewFood.setAdapter(foodMenuAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(RoomServiceActivity.this,1, GridLayoutManager.VERTICAL,false);
                    recyclerViewFood.setLayoutManager(gridLayoutManager);


                } else {
                    Log.e("MainActivity", "Failed to fetch data: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FoodMenuResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });
    }

    private void updateItemCount() {
        int itemCount = orderList.size();
        if (itemCount > 0) {
            textViewItemCount.setText(String.valueOf(itemCount));
            textViewItemCount.setVisibility(View.VISIBLE);
        } else {
            textViewItemCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAddFoodItem(PlaceOrderItem item) {
        orderList.add(item);
        updateItemCount();
    }

    private void openPlaceOrderFragment() {
        PlaceOrderFragment fragment = PlaceOrderFragment.newInstance(new ArrayList<>(orderList));
        fragment.show(getSupportFragmentManager(), "PlaceOrderFragment");
    }
}