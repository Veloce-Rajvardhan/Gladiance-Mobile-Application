package com.gladiance.ui.activities.ControlBouquet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gladiance.R;
import com.gladiance.ui.adapters.FoodMenuAdapter;
import com.gladiance.ui.fragment.ControlBouquet.PlaceOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceActivity extends AppCompatActivity {

    RecyclerView recyclerViewFood;

    private List<Integer> imageVegNonveg;
    private List<String> tvMenuName;
    private List<String> tvMenuPrice;
    private List<Integer> imageMenu;
    private List<String> btnAdd;
    ImageView imageViewPlaceOrder;
    private FoodMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service);

        recyclerViewFood = findViewById(R.id.rv_food_menu);
        imageViewPlaceOrder = findViewById(R.id.imageViewPlaceOrder);


        imageVegNonveg = new ArrayList<>();
        tvMenuName = new ArrayList<>();
        tvMenuPrice = new ArrayList<>();
        imageMenu = new ArrayList<>();
        btnAdd = new ArrayList<>();

        adapter = new FoodMenuAdapter(this, imageVegNonveg, tvMenuName, tvMenuPrice, imageMenu, btnAdd);

        imageVegNonveg.add(R.drawable.veg_img);
        imageVegNonveg.add(R.drawable.veg_img);
        imageVegNonveg.add(R.drawable.veg_img);
        imageVegNonveg.add(R.drawable.veg_img);
        imageVegNonveg.add(R.drawable.veg_img);
        imageVegNonveg.add(R.drawable.veg_img);


        tvMenuName.add("Chicken Manchow Soup");
        tvMenuName.add("Chicken Dum Biryani (Full)");
        tvMenuName.add("Chicken Manchow Soup");
        tvMenuName.add("Chicken Manchow Soup");
        tvMenuName.add("Chicken Dum Biryani (Full)");
        tvMenuName.add("Chicken Manchow Soup");

        tvMenuPrice.add("Rs 200");
        tvMenuPrice.add("Rs 200");
        tvMenuPrice.add("Rs 200");
        tvMenuPrice.add("Rs 200");
        tvMenuPrice.add("Rs 200");
        tvMenuPrice.add("Rs 200");

        imageMenu.add(R.drawable.biryaniimg);
        imageMenu.add(R.drawable.biryaniimg);
        imageMenu.add(R.drawable.biryaniimg);
        imageMenu.add(R.drawable.biryaniimg);
        imageMenu.add(R.drawable.biryaniimg);
        imageMenu.add(R.drawable.biryaniimg);

        btnAdd.add("Add");
        btnAdd.add("Add");
        btnAdd.add("Add");
        btnAdd.add("Add");
        btnAdd.add("Add");
        btnAdd.add("Add");

        // Set up RecyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewFood.setLayoutManager(gridLayoutManager);
        recyclerViewFood.setHasFixedSize(true);
        recyclerViewFood.setAdapter(adapter);

        imageViewPlaceOrder = findViewById(R.id.imageViewPlaceOrder);

        imageViewPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceOrderFragment placeOrderFragment = new PlaceOrderFragment();
                placeOrderFragment.show(getSupportFragmentManager(), placeOrderFragment.getTag());

            }
        });

    }
}