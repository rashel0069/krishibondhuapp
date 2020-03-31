package com.example.krishibondhuapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<CommentPosoClass> MyItem;
    Context context;

    public CommentAdapter(List<CommentPosoClass> myItem, Context context) {
        MyItem = myItem;
        this.context = context;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_sample,viewGroup,false);
        CommentViewHolder MVH = new CommentViewHolder(view);
        return MVH;


    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder commentViewHolder, final int i) {
        final CommentPosoClass itemPosition = MyItem.get(i);

//        commentViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));


        commentViewHolder.fulname.setText(itemPosition.getName());
        commentViewHolder.comment.setText(itemPosition.getComment());

        Glide.with(context)
                .load(itemPosition.getImgUrl())
                .into(commentViewHolder.circleImageView);

//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Good Job !", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return MyItem.size();
    }
}
