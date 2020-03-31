package com.example.krishibondhuapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    CircleImageView circleImageView;
    TextView fulname,comment;
    CardView cardView;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.cardViewId);

        circleImageView = itemView.findViewById(R.id.commentCimageId);
        fulname = itemView.findViewById(R.id.commentNameTextViewId);
        comment = itemView.findViewById(R.id.commentShowTextViewId);


    }
}
