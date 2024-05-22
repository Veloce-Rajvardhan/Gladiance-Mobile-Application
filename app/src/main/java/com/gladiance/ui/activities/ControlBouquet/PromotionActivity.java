package com.gladiance.ui.activities.ControlBouquet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gladiance.R;
import com.gladiance.ui.adapters.PromotionAdapter;

import java.util.ArrayList;
import java.util.List;

public class PromotionActivity extends AppCompatActivity {

    RecyclerView recyclerViewPromotion;
    private List<Integer> imgProm;
    private List<String> txtProm;
    private PromotionAdapter adapterPromotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        recyclerViewPromotion = findViewById(R.id.rv_promotion);

        imgProm = new ArrayList<>();
        txtProm = new ArrayList<>();

        imgProm.add(R.drawable.promotion_img);
        imgProm.add(R.drawable.promotion_img);
        imgProm.add(R.drawable.promotion_img);

        txtProm.add("Get ready to indulge in your favorite street foods from various cities and states of India at the Streets of India Food Festival.Get ready to indulge in your favorite street foods from various cities and states of India at the Streets of India Food Festival.");
        txtProm.add("Get ready to indulge in your favorite street foods from various cities and states of India at the Streets of India Food Festival.");
        txtProm.add("Get ready to indulge in your favorite street foods from various cities and states of India at the Streets of India Food Festival.");

        adapterPromotion = new PromotionAdapter(this, imgProm, txtProm);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewPromotion.setLayoutManager(gridLayoutManager);
        recyclerViewPromotion.setHasFixedSize(true);
        recyclerViewPromotion.setAdapter(adapterPromotion);
    }
}