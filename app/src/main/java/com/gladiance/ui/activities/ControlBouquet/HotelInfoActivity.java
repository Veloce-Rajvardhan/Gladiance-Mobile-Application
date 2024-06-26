package com.gladiance.ui.activities.ControlBouquet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gladiance.R;
import com.gladiance.ui.adapters.AmenitiesAdapter;
import com.gladiance.ui.adapters.ControlBouquetHorizontalAdapter;
import com.gladiance.ui.adapters.KeyContactAdapter;
import com.gladiance.ui.adapters.TelephoneAdapter;

import java.util.ArrayList;
import java.util.List;

public class HotelInfoActivity extends AppCompatActivity {

    LinearLayout btn_amenities, layout_hide_show, btn_telephone, layout_hide_show1, btn_key_contact, layout_hide_show2;

    RecyclerView recycleViewCB, recyclerViewTelephone, recyclerViewAmenities, recyclerViewKeyContacts;

    ImageView imageView;

    ControlBouquetHorizontalAdapter adapterCB;
    TelephoneAdapter adapterTelephone;
    AmenitiesAdapter adapterAmenities;
    KeyContactAdapter adapterKeyContact;
    private List<String> titlesControlBouquet;

    private List<String> tvtitle;
    private List<Integer> imgcall;
    private List<String> tvnumber;
    private List<Integer> imgthim;

    private List<String> tvtitle1;
    private List<String> tvtime;
    private List<String> tvNumber;
    private List<String> tvseemore;
    private List<Integer> imgcall1;
    private List<Integer> imgthim1;

    private List<String> tvtitle2;
    private List<Integer> imgcall2;
    private List<String> tvnumber2;
    private List<Integer> imgmap;
    private List<Integer> imgthim2;

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


        imageViewArrow1 = findViewById(R.id.imgarrow1);
        imageViewArrow2 = findViewById(R.id.imgarrow2);
        imageViewArrow3 = findViewById(R.id.imgarrow3);

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


        // //Recycle view Telephone
        recyclerViewTelephone = findViewById(R.id.rv_telephone);

        tvtitle = new ArrayList<>();
        imgcall = new ArrayList<>();
        tvnumber = new ArrayList<>();
        imgthim = new ArrayList<>();


        adapterTelephone = new TelephoneAdapter(this, tvtitle, imgcall, tvnumber, imgthim);

        tvtitle.add("Reception Desk");
        tvtitle.add("HouseKeeping");
        tvtitle.add("Police");
        tvtitle.add("Police");
        tvtitle.add("Police");
        tvtitle.add("Police");

        imgcall.add(R.drawable.telephone);
        imgcall.add(R.drawable.telephone);
        imgcall.add(R.drawable.telephone);
        imgcall.add(R.drawable.telephone);
        imgcall.add(R.drawable.telephone);
        imgcall.add(R.drawable.telephone);


        tvnumber.add("1000");
        tvnumber.add("1001  ");
        tvnumber.add("100");
        tvnumber.add("100");
        tvnumber.add("100");
        tvnumber.add("100");

        imgthim.add(R.drawable.housekeeping);
        imgthim.add(R.drawable.housekeeping);
        imgthim.add(R.drawable.emergancy1);
        imgthim.add(R.drawable.emergancy1);
        imgthim.add(R.drawable.emergancy1);
        imgthim.add(R.drawable.emergancy1);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewTelephone.setLayoutManager(gridLayoutManager);
        recyclerViewTelephone.setHasFixedSize(true);
        recyclerViewTelephone.setAdapter(new TelephoneAdapter(this, tvtitle, imgcall, tvnumber, imgthim));

        //Amenities
        recyclerViewAmenities = findViewById(R.id.rv_amenities);


        tvtitle1 = new ArrayList<>();
        tvtime = new ArrayList<>();
        imgcall1 = new ArrayList<>();
        tvNumber = new ArrayList<>();
        tvseemore = new ArrayList<>();
        imgthim1 = new ArrayList<>();


        adapterAmenities = new AmenitiesAdapter(this, tvtitle1, tvtime, imgcall1, tvNumber, tvseemore, imgthim1);

        tvtitle1.add("Fitness Center");
        tvtitle1.add("Fitness Center");
        tvtitle1.add("Fitness Center");
        tvtitle1.add("Fitness Center");
        tvtitle1.add("Fitness Center");
        tvtitle1.add("Fitness Center");

        tvtime.add("Time : 05:00 am to 21:00 pm");
        tvtime.add("Time : 05:00 am to 21:00 pm");
        tvtime.add("Time : 05:00 am to 21:00 pm");
        tvtime.add("Time : 05:00 am to 21:00 pm");
        tvtime.add("Time : 05:00 am to 21:00 pm");

        imgcall1.add(R.drawable.telephone);
        imgcall1.add(R.drawable.telephone);
        imgcall1.add(R.drawable.telephone);
        imgcall1.add(R.drawable.telephone);
        imgcall1.add(R.drawable.telephone);
        imgcall1.add(R.drawable.telephone);


        tvNumber.add("0231-2522195");
        tvNumber.add("0231-2522195");
        tvNumber.add("0231-2522195");
        tvNumber.add("0231-2522195");
        tvNumber.add("0231-2522195");
        tvNumber.add("0231-2522195");

        tvseemore.add("see more...");
        tvseemore.add("see more...");
        tvseemore.add("see more...");
        tvseemore.add("see more...");
        tvseemore.add("see more...");
        tvseemore.add("see more...");

        imgthim1.add(R.drawable.emergancy1);
        imgthim1.add(R.drawable.emergancy1);
        imgthim1.add(R.drawable.emergancy1);
        imgthim1.add(R.drawable.emergancy1);
        imgthim1.add(R.drawable.emergancy1);
        imgthim1.add(R.drawable.emergancy1);


        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewAmenities.setLayoutManager(gridLayoutManager2);
        recyclerViewAmenities.setHasFixedSize(true);
        recyclerViewAmenities.setAdapter(new AmenitiesAdapter(this, tvtitle1, tvtime, imgcall1, tvNumber, tvseemore, imgthim1));

        //Key Contact
        recyclerViewKeyContacts = findViewById(R.id.rv_keyContact);

        tvtitle2 = new ArrayList<>();
        imgcall2 = new ArrayList<>();
        tvnumber2 = new ArrayList<>();
        imgmap = new ArrayList<>();
        imgthim2 = new ArrayList<>();

        adapterKeyContact = new KeyContactAdapter(this, tvtitle2, imgcall2, tvnumber2, imgmap, imgthim2);

        tvtitle2.add("Airport");
        tvtitle2.add("Ruhi Hospital");
        tvtitle2.add("Police");

        imgcall2.add(R.drawable.telephone);
        imgcall2.add(R.drawable.telephone);
        imgcall2.add(R.drawable.telephone);

        tvnumber2.add("0222-918273");
        tvnumber2.add("0222-918273");
        tvnumber2.add("100");

        imgmap.add(R.drawable.map);
        imgmap.add(R.drawable.map);
        imgmap.add(R.drawable.map);

        imgthim2.add(R.drawable.emergancy1);
        imgthim2.add(R.drawable.emergancy1);
        imgthim2.add(R.drawable.emergancy1);

        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewKeyContacts.setLayoutManager(gridLayoutManager3);
        recyclerViewKeyContacts.setHasFixedSize(true);
        recyclerViewKeyContacts.setAdapter(new KeyContactAdapter(this, tvtitle2, imgcall2, tvnumber2, imgmap, imgthim2));

    }
}