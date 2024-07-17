package com.gladiance.ui.fragment.ControlBouquet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.gladiance.R;
import com.gladiance.ui.models.PlaceOrderItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddFoodItemFragment extends BottomSheetDialogFragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_RATE = "rate";
    private static final String ARG_VIDEO_URL = "video_url";
    private static final String ARG_IS_VEG = "is_veg";

    private String name;
    private String description;
    private String rate;
    private String quantity;
    private String videoUrl;
    private boolean isVeg;

    public AddFoodItemFragment() {
        // Required empty public constructor
    }

    public static AddFoodItemFragment newInstance(String name, String description, String rate, String videoUrl, boolean isVeg) {
        AddFoodItemFragment fragment = new AddFoodItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_RATE, rate);
        args.putString(ARG_VIDEO_URL, videoUrl);
        args.putBoolean(ARG_IS_VEG, isVeg);
        fragment.setArguments(args);
        return fragment;
    }

    private OnAddFoodItemListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddFoodItemListener) {
            mListener = (OnAddFoodItemListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnAddFoodItemListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            description = getArguments().getString(ARG_DESCRIPTION);
            rate = getArguments().getString(ARG_RATE);
            videoUrl = getArguments().getString(ARG_VIDEO_URL);
            isVeg = getArguments().getBoolean(ARG_IS_VEG);
        }
    }

    TextView scoreText;
    int score = 1;
    ImageView incrementButton, decrementButton;
    Button addItem;
    TextView textViewName, textViewDescription, textViewRate, textViewVideoUrl,textViewQuantity;
    ImageView imgVegNonVeg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food_item, container, false);

        scoreText = view.findViewById(R.id.scoreText);
        scoreText.setText(String.valueOf(score));
        incrementButton = view.findViewById(R.id.incrementButton);
        decrementButton = view.findViewById(R.id.decrementButton);

        textViewName = view.findViewById(R.id.textViewName);
        textViewDescription = view.findViewById(R.id.textViewDescription);
        textViewRate = view.findViewById(R.id.textViewRate);
        textViewVideoUrl = view.findViewById(R.id.textViewVideoUrl);
        imgVegNonVeg = view.findViewById(R.id.imageViewVegNonVeg);


        addItem = view.findViewById(R.id.btn_add_order);

        // Set the received values
        textViewName.setText(name);
        textViewDescription.setText("Description : " + description);
        textViewRate.setText(rate);
        textViewVideoUrl.setText("VideoUrl : " + videoUrl);

        // Set the Veg/Non-Veg image
        if (isVeg) {
            imgVegNonVeg.setImageResource(R.drawable.vegimg);
        } else {
            imgVegNonVeg.setImageResource(R.drawable.nonvegimg);
        }

        double baseRate = Double.parseDouble(rate);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                scoreText.setText(String.valueOf(score));

                // Increment the rate based on the score
                double newRate = baseRate * score;
                textViewRate.setText(String.valueOf(newRate));
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score > 1) {  // Ensuring the score doesn't go below 1
                    score--;
                    scoreText.setText(String.valueOf(score));

                    // Decrement the rate based on the score
                    double newRate = baseRate * score;
                    textViewRate.setText(String.valueOf(newRate));
                }
            }
        });

        textViewVideoUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!videoUrl.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                    startActivity(intent);
                }
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send data back to the activity
                double finalRate = baseRate * score;
                if (mListener != null) {
                    mListener.onAddFoodItem(new PlaceOrderItem(name, String.valueOf(finalRate),String.valueOf(score),isVeg));
                    dismiss();
                }
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnAddFoodItemListener {
        void onAddFoodItem(PlaceOrderItem item);
    }
}
