package com.gladiance.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.promotionlist.ObjectTag;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.MyViewHolder>{

    private static List<ObjectTag> arraylist;

   public PromotionAdapter(List<ObjectTag> arraylist){
       this.arraylist = arraylist;
   }

    @NonNull
    @Override
    public PromotionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_recycler, parent, false);
        return new PromotionAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.MyViewHolder holder, int position) {
        ObjectTag objectTag = arraylist.get(position);

        holder.titleName.setText(objectTag.getEventName());
        holder.mTextView.setText(objectTag.getDescription());
        holder.textVideoURL.setText(objectTag.getVideoUrl());

        holder.textVideoURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(v.getContext(), objectTag.getVideoUrl());
            }
        });


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTextView,titleName,textVideoURL;
        Button buttonSeeMore;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imgpromotion);
            mTextView = itemView.findViewById(R.id.tvpromoinfo);
            buttonSeeMore = itemView.findViewById(R.id.buttonSeeMore);
            titleName = itemView.findViewById(R.id.titleName);
            textVideoURL = itemView.findViewById(R.id.tvVideoURL);

            buttonSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleSeeMore(mTextView, buttonSeeMore);
                }
            });

        }
    }

    private static void toggleSeeMore(TextView textView, Button button) {
        if (textView.getMaxLines() == 3) {
            // Expand the text
            textView.setMaxLines(Integer.MAX_VALUE);
            button.setText("See Less");
        } else {
            // Collapse the text
            textView.setMaxLines(3);
            button.setText("See More...");
        }
    }

    private void openUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

}
