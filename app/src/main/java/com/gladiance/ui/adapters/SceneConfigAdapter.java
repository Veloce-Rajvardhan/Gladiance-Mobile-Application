package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.AppConstants;
import com.gladiance.R;
//import com.gladiance.ui.fragment.MyProfile.EditSceneFragment;
import com.gladiance.ui.activities.MyProfile.EditMoodActivity;
import com.gladiance.ui.fragment.MyProfile.EditSceneFragment;
import com.gladiance.ui.models.scenelist.ObjectTag;

import java.util.ArrayList;
import java.util.List;

public class SceneConfigAdapter extends RecyclerView.Adapter<SceneConfigAdapter.SceneViewHolder> {
    private List<ObjectTag> arraylist;
    Long ref;


    public SceneConfigAdapter(ArrayList<ObjectTag> arraylist) {
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public SceneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scene, parent, false);
        return new SceneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SceneViewHolder holder, int position) {
        ObjectTag scene = arraylist.get(position);

        holder.sceneNameTextView.setText(scene.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long Ref_dyn = arraylist.get(position).getRef();
                String Name_dyn = arraylist.get(position).getName();
                Long Space_dyn = arraylist.get(position).getgAAProjectSpaceTypeRef();
                Long SceneRef = Space_dyn;

                AppConstants.Ref_dyn = String.valueOf(Ref_dyn);
                AppConstants.Name_dyn = Name_dyn;
                AppConstants.SceneRef = String.valueOf(Ref_dyn);
                AppConstants.Space_dyn = String.valueOf(Space_dyn);


                Log.e(TAG, "onClick: "+Ref_dyn);

                // Final Ref in sharedpref for post scene api (Ref)
                SharedPreferences sharedPreferences =  v.getContext().getSharedPreferences("Ref_Dyn", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("REF_DYN_KEY", Ref_dyn);
                editor.apply(); // or editor.commit(); if you want immediate effect
                Log.e(TAG, "REF_DYN_KEY: "+Ref_dyn );


                // Final Ref in sharedpref for post scene api (Name)
                SharedPreferences sharedPreferences2 =  v.getContext().getSharedPreferences("Name_Dyn", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putString("NAME_DYN_KEY", Name_dyn);
                editor2.apply(); // or editor.commit(); if you want immediate effect
                Log.e(TAG, "NAME_DYN_KEY: "+Name_dyn );



                // Final Ref in sharedpref for post scene api (gaaProjectSpaceTypeRef)
                SharedPreferences sharedPreferences3 =  v.getContext().getSharedPreferences("PROJECT_SPACE_REF_Dyn", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                editor3.putLong("PROJECT_SPACE_TYPE_REF_DYN_KEY", Space_dyn);
                editor3.apply(); // or editor.commit(); if you want immediate effect
                Log.e(TAG, "PROJECT_SPACE_TYPE_REF_DYN_KEY: "+Space_dyn );



                // -- uncomment for figma New Ui (Used Activity instead of fragment)-- //

//                Long sceneRef = scene.getRef();
//                String sceneRefString = String.valueOf(sceneRef);
//
//// Create an Intent to launch the EditSceneActivity
//                Intent intent = new Intent(holder.itemView.getContext(), EditMoodActivity.class);
//
//// Put the sceneRefString as an extra to pass to the activity
//                intent.putExtra("SCENE_REF", sceneRefString);
//
//// Start the EditSceneActivity
//                holder.itemView.getContext().startActivity(intent);



                //write post api here sceneclickapi

                Long sceneRef = scene.getRef();
                String sceneRefString = String.valueOf(sceneRef);

                Fragment fragment = new EditSceneFragment();

                Bundle bundle = new Bundle();
                // Pass the sceneRef to the next fragment using fragment arguments
                //   Bundle bundle = new Bundle();
                bundle.putString("SCENE_REF", sceneRefString);
                fragment.setArguments(bundle);


                //   bundle.putStringArrayList("myArrayList", newList);

                // Create the destination fragment instance
                Fragment destinationFragment = new EditSceneFragment();
                destinationFragment.setArguments(bundle);
                //destinationFragment.

                FragmentTransaction transaction = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.set_mood, fragment, String.valueOf(destinationFragment)).addToBackStack(null)
                        .commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class SceneViewHolder extends RecyclerView.ViewHolder {
        TextView sceneNameTextView;

        public SceneViewHolder(@NonNull View itemView) {
            super(itemView);
            sceneNameTextView = itemView.findViewById(R.id.scene_name_text_view);
        }
    }
}