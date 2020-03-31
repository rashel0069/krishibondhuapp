package com.example.krishibondhuapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AgriOfficerViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView name,designation,location,phone;
    CardView cardView;

    public AgriOfficerViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.agriculure_cardrid);

        imageView = itemView.findViewById(R.id.agri_SampleImage_VIewId);
        name = itemView.findViewById(R.id.agri_Sample_NameId);
        designation = itemView.findViewById(R.id.agri_SampleDesignation_Id);
        location = itemView.findViewById(R.id.agri_SampleLocation_Id);
        phone = itemView.findViewById(R.id.agri_SamplePhone_Id);



    }
}
