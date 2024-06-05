package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.activities.DeviceControls.AirContiningActivity;
import com.gladiance.ui.activities.DeviceControls.BellActivity;
import com.gladiance.ui.activities.DeviceControls.CurtainActivity;
import com.gladiance.ui.activities.DeviceControls.DimmerActivity;
import com.gladiance.ui.activities.DeviceControls.FanActivity;
import com.gladiance.ui.activities.DeviceControls.RGBLightActivity;
import com.gladiance.ui.activities.EspMainActivity;
import com.gladiance.ui.fragment.MyProfile.ProfileDeviceCardFragment;
import com.gladiance.ui.fragment.RoomControl.DeviceCardFragment;
import com.gladiance.ui.models.Devices;

import java.util.ArrayList;
import java.util.List;

public class ProfileCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static List<Devices> arrayList;

    EspMainActivity espMainActivity;
    private static final int VIEW_TYPE_ONE = 1;
    private static final int VIEW_TYPE_TWO = 2;
    private static final int VIEW_TYPE_THREE = 3;
    private static final int VIEW_TYPE_FOUR = 4;
    private static final int VIEW_TYPE_FIVE = 5;
    private static final int VIEW_TYPE_SIX = 6;
    private static final int VIEW_TYPE_SEVEN = 7;
    private static final int VIEW_TYPE_EIGHT = 8;
    private static final int VIEW_TYPE_NINE = 9;
    private static final int VIEW_TYPE_TEN = 10;

    public ProfileCardAdapter(ArrayList<Devices> arrayList) {
        this.arrayList = arrayList;
    }
    @Override
    public int getItemViewType(int position) {
        String title = arrayList.get(position).getType();
        Log.d(TAG, "getItemViewType: " + title);

        if (title.equals("esp.device.fan")) {
            return VIEW_TYPE_ONE;
        } else if (title.equals("esp.device.switch")) {
            return VIEW_TYPE_TWO;
        } else if (title.equals("esp.device.curtain")) {
            return VIEW_TYPE_THREE;
        } else if (title.equals("esp.device.light")) {
            return VIEW_TYPE_FOUR;
        } else if (title.equals("esp.device.lightbulb")) {
            return VIEW_TYPE_FIVE;
        } else if (title.equals("esp.device.bellcontrol")) {
            return VIEW_TYPE_SIX;
        } else if (title.equals("e.d.ther")) {
            return VIEW_TYPE_SEVEN;
        } else if (title.equals("e.d.bell")) {
            return VIEW_TYPE_EIGHT;
        } else if (title.equals("e.d.curt")) {
            return VIEW_TYPE_NINE;
        } else if (title.equals("e.d.ther")) {
            return VIEW_TYPE_TEN;
        }
        else {
            return VIEW_TYPE_FOUR;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == VIEW_TYPE_ONE) {
            view = layoutInflater.inflate(R.layout.card_fan, parent, false);
            return new ProfileCardAdapter.TypeOneViewHolder(view);
        } else if (viewType == VIEW_TYPE_TWO) {
            view = layoutInflater.inflate(R.layout.card_toggle, parent, false);
            return new ProfileCardAdapter.TypeTwoViewHolder(view);
        } else if (viewType == VIEW_TYPE_THREE) {
            view = layoutInflater.inflate(R.layout.card_curtain, parent, false);
            return new ProfileCardAdapter.TypeThreeViewHolder(view);
        } else if (viewType == VIEW_TYPE_FOUR) {
            view = layoutInflater.inflate(R.layout.card_dimmer, parent, false);
            return new ProfileCardAdapter.TypeFourViewHolder(view);
        } else if (viewType == VIEW_TYPE_FIVE) {
            view = layoutInflater.inflate(R.layout.card_rgblight, parent, false);
            return new ProfileCardAdapter.TypeFiveViewHolder(view);
        } else if (viewType == VIEW_TYPE_SIX) {
            view = layoutInflater.inflate(R.layout.card_bell, parent, false);
            return new ProfileCardAdapter.TypeSixViewHolder(view);
        } else if (viewType == VIEW_TYPE_SEVEN) {
            view = layoutInflater.inflate(R.layout.card_aircondition, parent, false);
            return new ProfileCardAdapter.TypeSevenViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_EIGHT) {
            view = layoutInflater.inflate(R.layout.card_bell, parent, false);
            return new ProfileCardAdapter.TypeSixViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_NINE) {
            view = layoutInflater.inflate(R.layout.card_curtain, parent, false);
            return new ProfileCardAdapter.TypeThreeViewHolder(view);
        } else if (viewType == VIEW_TYPE_TEN) {
            view = layoutInflater.inflate(R.layout.card_aircondition, parent, false);
            return new ProfileCardAdapter.TypeSevenViewHolder(view);
        }

        else {
            view = layoutInflater.inflate(R.layout.card_curtain, parent, false);
            return new ProfileCardAdapter.TypeThreeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Devices devices = arrayList.get(position);

        if (holder.getItemViewType() == VIEW_TYPE_ONE) {
            ProfileCardAdapter.TypeOneViewHolder typeOneViewHolder = (ProfileCardAdapter.TypeOneViewHolder) holder;
            // Bind data for type one view
            typeOneViewHolder.title.setText(devices.getName());
        } else if (holder.getItemViewType() == VIEW_TYPE_TWO) {
            ProfileCardAdapter.TypeTwoViewHolder typeTwoViewHolder = (ProfileCardAdapter.TypeTwoViewHolder) holder;
            // Bind data for type two view
            typeTwoViewHolder.title.setText(devices.getName());
        } else if (holder.getItemViewType() == VIEW_TYPE_THREE) {
            ProfileCardAdapter.TypeThreeViewHolder typeThreeViewHolder = (ProfileCardAdapter.TypeThreeViewHolder) holder;
            // Bind data for type two view
            typeThreeViewHolder.title.setText(devices.getName());
        } else if (holder.getItemViewType() == VIEW_TYPE_FOUR) {
            ProfileCardAdapter.TypeFourViewHolder typeFourViewHolder = (ProfileCardAdapter.TypeFourViewHolder) holder;
            // Bind data for type two view
            typeFourViewHolder.title.setText(devices.getName());
        } else if (holder.getItemViewType() == VIEW_TYPE_FIVE) {
            ProfileCardAdapter.TypeFiveViewHolder typeFiveViewHolder = (ProfileCardAdapter.TypeFiveViewHolder) holder;
            // Bind data for type two view
            typeFiveViewHolder.title.setText(devices.getName());
        } else if (holder.getItemViewType() == VIEW_TYPE_SIX) {
            ProfileCardAdapter.TypeSixViewHolder typeSixViewHolder = (ProfileCardAdapter.TypeSixViewHolder) holder;
            // Bind data for type two view
            typeSixViewHolder.title.setText(devices.getName());
        } else if (holder.getItemViewType() == VIEW_TYPE_SEVEN) {
            ProfileCardAdapter.TypeSevenViewHolder typeSevenViewHolder = (ProfileCardAdapter.TypeSevenViewHolder) holder;
            // Bind data for type two view
            typeSevenViewHolder.title.setText(devices.getName());
        }
        else if (holder.getItemViewType() == VIEW_TYPE_EIGHT) {
            ProfileCardAdapter.TypeSixViewHolder typeSixViewHolder = (ProfileCardAdapter.TypeSixViewHolder) holder;
            // Bind data for type two view
            typeSixViewHolder.title.setText(devices.getName());
        } else if (holder.getItemViewType() == VIEW_TYPE_NINE) {
            ProfileCardAdapter.TypeThreeViewHolder typeThreeViewHolder = (ProfileCardAdapter.TypeThreeViewHolder) holder;
            // Bind data for type two view
            typeThreeViewHolder.title.setText(devices.getName());
        } else if (holder.getItemViewType() == VIEW_TYPE_TEN) {
            ProfileCardAdapter.TypeSevenViewHolder typeSevenViewHolder = (ProfileCardAdapter.TypeSevenViewHolder) holder;
            // Bind data for type two view
            typeSevenViewHolder.title.setText(devices.getName());
        }

        else {
            ProfileCardAdapter.TypeFourViewHolder typeFourViewHolder = (ProfileCardAdapter.TypeFourViewHolder) holder;
            // Bind data for type two view
            typeFourViewHolder.title.setText(devices.getName());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class TypeOneViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Switch switch_btn;

        TypeOneViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click for the second card view
                    // Change the activity here
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Devices clickedCard = arrayList.get(position);
                        String name = clickedCard.getName();

                        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Name", name);
                        editor.apply();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, FanActivity.class);
                        context.startActivity(intent);

//                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        FanFragment newFragment = new FanFragment();
//                        transaction.replace(R.id.ControlsContainer, newFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }
                }
            });

        }


    }

    static class TypeTwoViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CardView cd;
        LinearLayout linearLayout;
        DeviceCardFragment deviceCardFragment = DeviceCardFragment.getInstance();
        ProfileDeviceCardFragment profileDeviceCardFragment = ProfileDeviceCardFragment.getInstance();
        // MainActivity mainActivity = MainActivity.getInstance();
        final String[] a = {"1"};
        //boolean a = true;

        TypeTwoViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TextView);
            cd = itemView.findViewById(R.id.node_cardView);
            linearLayout = itemView.findViewById(R.id.llLight);


            itemView.setOnClickListener(new View.OnClickListener() {
                boolean powerState = false;

                @Override
                public void onClick(View v) {

                    powerState = !powerState;
                    if(powerState == true){
                        String hexColor = "#FF6434";
                        int color = Color.parseColor(hexColor);
                        title.setTextColor(color);
                        linearLayout.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.border_highlight));
                    }
                    else
                    {
                        title.setTextAppearance(R.style.TEXT);
                        //cd.setCardBackgroundColor(Color.TRANSPARENT);
                        linearLayout.setBackgroundColor(Color.parseColor("#A6A6A6"));
                    }
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Devices clickedCard = arrayList.get(position);
                        String name = clickedCard.getName();
                        String power = clickedCard.getPrimary();

                        profileDeviceCardFragment.sendSwitchState(powerState,name,power);

                    }
                }
            });
        }

    }

    static class TypeThreeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Switch switch_btn;

        TypeThreeViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click for the second card view
                    // Change the activity here
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Devices clickedCard = arrayList.get(position);
                        String name = clickedCard.getName();

                        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Name", name);
                        editor.apply();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, CurtainActivity.class);
                        context.startActivity(intent);

//                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        CurtainFragment newFragment = new CurtainFragment();
//                        transaction.replace(R.id.ControlsContainer, newFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }
                }
            });

        }
    }

    static class TypeFourViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Switch switch_btn;

        TypeFourViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Devices clickedCard = arrayList.get(position);
                        String name = clickedCard.getName();

                        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Name", name);
                        editor.apply();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, DimmerActivity.class);
                        context.startActivity(intent);

//                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        DimmerFragment newFragment = new DimmerFragment();
//                        transaction.replace(R.id.ControlsContainer, newFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }
                }
            });

        }
    }

    static class TypeFiveViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Switch switch_btn;

        TypeFiveViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click for the second card view
                    // Change the activity here
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Devices clickedCard = arrayList.get(position);
                        String name = clickedCard.getName();

                        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Name", name);
                        editor.apply();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, RGBLightActivity.class);
                        context.startActivity(intent);

//                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        RGBLightFragment newFragment = new RGBLightFragment();
//                        transaction.replace(R.id.ControlsContainer, newFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }
                }
            });

        }
    }

    //Bell
    static class TypeSixViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        EspMainActivity espMainActivity = EspMainActivity.getInstance();
        final String[] a = {"1"};
        //boolean a = true;

        TypeSixViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TextView);
            //  switch_btn = itemView.findViewById(R.id.switch_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                boolean powerState = true;

                @Override
                public void onClick(View v) {


                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Devices clickedCard = arrayList.get(position);
                        String name = clickedCard.getName();
                        String primary = clickedCard.getPrimary();

                        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Name", name);
                        editor.apply();

                        SharedPreferences sharedPreferences1 = v.getContext().getSharedPreferences("MyPrefsPrimary", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("Primary", primary);
                        editor1.apply();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, BellActivity.class);
                        context.startActivity(intent);

//                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        BellFragment newFragment = new BellFragment();
//                        transaction.replace(R.id.ControlsContainer, newFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();

                    }
                }
            });
        }
    }

    static class TypeSevenViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Switch switch_btn;

        TypeSevenViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click for the second card view
                    // Change the activity here
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Devices clickedCard = arrayList.get(position);
                        String name = clickedCard.getName();

                        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Name", name);
                        editor.apply();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, AirContiningActivity.class);
                        context.startActivity(intent);

//                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        AirContiningFragment newFragment = new AirContiningFragment();
//                        transaction.replace(R.id.ControlsContainer, newFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }
                }
            });

        }
    }


}
