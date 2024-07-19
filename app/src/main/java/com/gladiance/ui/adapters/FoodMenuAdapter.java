package com.gladiance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.ui.fragment.ControlBouquet.AddFoodItemFragment;
import com.gladiance.R;
import com.gladiance.ui.models.foodmoodlist.ObjectTag;

import java.util.List;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.ViewHolder> {
    private List<ObjectTag> foodItemList;

    public FoodMenuAdapter(List<ObjectTag> foodItemList) {
        this.foodItemList = foodItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_menu_rc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectTag foodItem = foodItemList.get(position);
        holder.tvMenuName.setText(foodItem.getName());
        holder.tvMenuPrice.setText(String.valueOf(foodItem.getPrice()));

        // Set the Veg/Non-Veg image
        boolean isVeg = foodItem.getgAAProjectRBItemCategoryName().equalsIgnoreCase("Veg");
        if (isVeg) {
            holder.imgVegNonVeg.setImageResource(R.drawable.vegimg);
        } else {
            holder.imgVegNonVeg.setImageResource(R.drawable.nonvegimg);
        }

        // Set the Spicy/Sweet image
        if (foodItem.getgAAProjectRBItemTasteName().equalsIgnoreCase("Sweet")) {
            holder.imgSpicySweet.setImageResource(R.drawable.sweet);
        } else {
            holder.imgSpicySweet.setImageResource(R.drawable.spicy);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                AddFoodItemFragment addFoodItemFragment = AddFoodItemFragment.newInstance(
                        foodItem.getName(),
                        foodItem.getDescription(),
                        String.valueOf(foodItem.getPrice()),
                        foodItem.getVideoURL(),
                        String.valueOf(foodItem.getRef()),
                        isVeg
                );
                addFoodItemFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), addFoodItemFragment.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenuName, tvMenuPrice;
        ImageView imgMenu, imgVegNonVeg, imgSpicySweet;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuName = itemView.findViewById(R.id.tv_menu_name);
            tvMenuPrice = itemView.findViewById(R.id.tv_menu_price);
            imgMenu = itemView.findViewById(R.id.imgmenu);
            imgVegNonVeg = itemView.findViewById(R.id.imgVegNonVeg);
            imgSpicySweet = itemView.findViewById(R.id.imgSweetSpicy);
            button = itemView.findViewById(R.id.btnAddItem);
        }
    }
}
