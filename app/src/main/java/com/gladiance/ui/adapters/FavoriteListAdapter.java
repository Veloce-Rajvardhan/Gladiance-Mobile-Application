package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.activities.MyProfile.BasicInfoActivity;
import com.gladiance.ui.models.RemoveSpaceUserFavorite;
import com.gladiance.ui.models.favoritelist.ObjectTag;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder> {


    private List<ObjectTag> arraylistFav;
    private Context context;

    public FavoriteListAdapter(ArrayList<ObjectTag> arraylistFav, Context context) {
        this.arraylistFav = arraylistFav;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteListAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_control, parent, false);
        return new FavoriteListAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.FavoriteViewHolder holder, int position) {
        ObjectTag objectTag = arraylistFav.get(position);
        holder.FavTextView.setText(objectTag.getLabel());

        holder.ImageFav.setImageResource(R.drawable.likered);
        holder.ImageFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                Long GaaProjectSpaceTypePlannedDeviceConRef = Long.valueOf(objectTag.getgAAProjectSpaceTypePlannedDeviceConnectionRef());
                SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences.edit();
                Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceConRef: " + GaaProjectSpaceTypePlannedDeviceConRef);
                editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceConRef);
                editor3.apply();

                String message = "Are you sure you want to remove this item from favorites?";
                showCustomDialogBox(message);

            }
        });

    }



    @Override
    public int getItemCount() {
        return arraylistFav.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{

        TextView FavTextView;
        ImageView ImageFav;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            FavTextView = itemView.findViewById(R.id.btnTitle);
            ImageFav = itemView.findViewById(R.id.fev_image);
        }
    }


    TextView tvMessage;
    Button btnYes, btnNo;

    private void showCustomDialogBox(String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.favorite_custom_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvMessage = dialog.findViewById(R.id.tvMessage);
        btnYes = dialog.findViewById(R.id.btn_Yes);
        btnNo = dialog.findViewById(R.id.btn_No);

        tvMessage.setText(message);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferencesPSPDR = context.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                Long gaaProjectSpaceTypePlannedDeviceConRef = sharedPreferencesPSPDR.getLong("KEY_USERNAME", 0);
                Log.e(TAG, "Retrieved GaaProjectSpaceTypePlannedDeviceRef: " + gaaProjectSpaceTypePlannedDeviceConRef);

                SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String GUID = LoginActivity.getUserId(sharedPreferences1);
                Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
                String loginDeviceId = GUID.trim();


                SharedPreferences  sharedPreferences2 = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
                Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
                String loginToken = savedLoginDeviceId.trim();

                SharedPreferences  sharedPreferences3 = context.getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
                String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
                Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
                String projectSpaceRef = saveProjectSpaceRef.trim();


                callRemoveSpaceUserFavouriteApi(projectSpaceRef, String.valueOf(gaaProjectSpaceTypePlannedDeviceConRef),loginToken,loginDeviceId);
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callRemoveSpaceUserFavouriteApi(String gaaProjectSpaceRef,String gaaProjectSpaceTypePlannedDeviceConnectionRef,String loginToken,String loginDeviceId) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RemoveSpaceUserFavorite> call = apiService.removeSpaceUserFavourite(gaaProjectSpaceRef, gaaProjectSpaceTypePlannedDeviceConnectionRef, loginToken, loginDeviceId);
        call.enqueue(new retrofit2.Callback<RemoveSpaceUserFavorite>() {
            @Override
            public void onResponse(Call<RemoveSpaceUserFavorite> call, retrofit2.Response<RemoveSpaceUserFavorite> response) {
                if (response.isSuccessful()) {
                    RemoveSpaceUserFavorite removeSpaceUserFavorite = response.body();
                    Toast.makeText(context, "Remove to favourites successfully", Toast.LENGTH_SHORT).show();
                    Log.e("Successful", "Success: " + removeSpaceUserFavorite.getSuccessful());
                    Log.e("Remove", "Message: " + removeSpaceUserFavorite.getMessage());

                } else {
                    Toast.makeText(context, "Failed to Remove to favourites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveSpaceUserFavorite> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
