package com.gladiance.ui.activities.ControlBouquet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gladiance.R;
import com.gladiance.ui.adapters.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity {


    RecyclerView recyclerViewMessaging;
    MessageAdapter adapterMessaging;
    private List<String> tvtitle;
    private List<String> tvmessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        recyclerViewMessaging = findViewById(R.id.rv_messaging);

        tvtitle = new ArrayList<>();
        tvmessaging = new ArrayList<>();

        adapterMessaging = new MessageAdapter(this, tvtitle, tvmessaging);

        tvtitle.add("From : Reception Desk");
        tvtitle.add("From : Reception Desk");
        tvtitle.add("From : Reception Desk");
        tvtitle.add("From : Reception Desk");
        tvtitle.add("From : Reception Desk");
        tvtitle.add("From : Reception Desk");

        tvmessaging.add("Welcome to Taj Blue Diamond! We are excited to have you stay with us and hope you have a comfortable and enjoyable visit.");
        tvmessaging.add("Welcome to Taj Blue Diamond! We are excited to have you stay with us and hope you have a comfortable and enjoyable visit.");
        tvmessaging.add("Welcome to Taj Blue Diamond! We are excited to have you stay with us and hope you have a comfortable and enjoyable visit.");
        tvmessaging.add("Welcome to Taj Blue Diamond! We are excited to have you stay with us and hope you have a comfortable and enjoyable visit.");
        tvmessaging.add("Welcome to Taj Blue Diamond! We are excited to have you stay with us and hope you have a comfortable and enjoyable visit.");
        tvmessaging.add("Welcome to Taj Blue Diamond! We are excited to have you stay with us and hope you have a comfortable and enjoyable visit.");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewMessaging.setLayoutManager(gridLayoutManager);
        recyclerViewMessaging.setHasFixedSize(true);
        recyclerViewMessaging.setAdapter(adapterMessaging);
    }
}