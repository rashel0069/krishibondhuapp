package com.example.krishibondhuapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class AgriOfficerAdapter extends RecyclerView.Adapter<AgriOfficerViewHolder> {

    private List<AgriOfficerPosoClass> MyItem;
    Context context;

    public AgriOfficerAdapter(List<AgriOfficerPosoClass> myItem, Context context) {
        MyItem = myItem;
        this.context = context;
    }


    @NonNull
    @Override
    public AgriOfficerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agriculture_officer_sample_design,viewGroup,false);
        AgriOfficerViewHolder MVH = new AgriOfficerViewHolder(view);
        return MVH;


    }

    @Override
    public void onBindViewHolder(@NonNull final AgriOfficerViewHolder agriOfficerViewHolder, final int i) {
        final AgriOfficerPosoClass itemPosition = MyItem.get(i);

//        commentViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        agriOfficerViewHolder.name.setText(itemPosition.getName());
        agriOfficerViewHolder.designation.setText(itemPosition.getDesignation());
        agriOfficerViewHolder.location.setText(itemPosition.getLocation());
        agriOfficerViewHolder.phone.setText(itemPosition.getPhone());


        Glide.with(context)
                .load(itemPosition.getImageUrl())
                .into(agriOfficerViewHolder.imageView);

    }


    @Override
    public int getItemCount() {
        return MyItem.size();
    }
}
