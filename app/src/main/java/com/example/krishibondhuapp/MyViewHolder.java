package com.example.krishibondhuapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    CircleImageView circleImageView;
    TextView fulname,date,desc,time,audioText;
    ImageView imageView;
    ImageButton playNow;
    Button likeButton,commentButton;
    CardView cardView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.cardViewId);

        circleImageView = itemView.findViewById(R.id.feedUserImageId);
        fulname = itemView.findViewById(R.id.feedUserFullnameId);
        date = itemView.findViewById(R.id.feedDateId);
        time = itemView.findViewById(R.id.feedTimeId);
        desc = itemView.findViewById(R.id.feedDescId);
        audioText = itemView.findViewById(R.id.audio_text_id);
        imageView = itemView.findViewById(R.id.feedImageId);
        likeButton = itemView.findViewById(R.id.feedLikeId);
        commentButton = itemView.findViewById(R.id.feedCommentId);
        playNow = itemView.findViewById(R.id.play_audio_id);


    }
}
