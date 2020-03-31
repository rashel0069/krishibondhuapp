package com.example.krishibondhuapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<PostPosoClass> MyItem;
    Context context;

    public MyAdapter(List<PostPosoClass> myItem, Context context) {
        MyItem = myItem;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newsfeed_sample,viewGroup,false);
        MyViewHolder MVH = new MyViewHolder(view);
        boolean flag = false;
        return MVH;


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final PostPosoClass itemPosition = MyItem.get(i);
        final String postkey = itemPosition.getPostKey();

        myViewHolder.fulname.setText(itemPosition.getUserName());
        myViewHolder.date.setText(itemPosition.getDate());
        myViewHolder.time.setText(itemPosition.getTime());
        myViewHolder.desc.setText(itemPosition.getText());
        myViewHolder.audioText.setText(itemPosition.getAudiolink());
        myViewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "শীঘ্রই আসছে", Toast.LENGTH_SHORT).show();
            }
        });


        Glide.with(context)
                .load(itemPosition.getUserPic())
                .into(myViewHolder.circleImageView);

        Glide.with(context)
                .load(itemPosition.getImageurl())
                .into(myViewHolder.imageView);

//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Good Job !", Toast.LENGTH_SHORT).show();
//            }
//        });

        myViewHolder.playNow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final MediaPlayer mediaPlayer = new MediaPlayer();
                String link = itemPosition.getAudiolink();
                if (link==null){
                    Toast.makeText(context, "No Audio Found", Toast.LENGTH_SHORT).show();
                }else {
                    int l = link.length();
                    if (l < 15 ){
                        Toast.makeText(context, "No Audio Found", Toast.LENGTH_SHORT).show();
                    }else {

                        try {
//                            mediaPlayer.stop();
//                            mediaPlayer.release();
                            mediaPlayer.setDataSource(link);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //mediaPlayer.stop();
                        //mediaPlayer.release();

                    }

                }
                int eventtouchpad = event.getAction();
                switch (eventtouchpad){
                    case MotionEvent.ACTION_DOWN:

                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();

                            }

                        });
                        return true;
                    case MotionEvent.ACTION_UP:
//                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            @Override
//                            public void onPrepared(MediaPlayer mp) {
//                                mp.stop();
//                            }
//                        });
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        return true;
                }
                return false;
            }
        });


//        myViewHolder.playNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final MediaPlayer mediaPlayer = new MediaPlayer();
//
//
//                String link = itemPosition.getAudiolink();
//                if (link==null){
//                    Toast.makeText(context, "No Audio Found", Toast.LENGTH_SHORT).show();
//                }else {
//                    int l = link.length();
//                    if (l < 15 ){
//                        Toast.makeText(context, "No Audio Found", Toast.LENGTH_SHORT).show();
//                    }else {
//
//                        try {
////                            mediaPlayer.stop();
////                            mediaPlayer.release();
//                            mediaPlayer.setDataSource(link);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            mediaPlayer.prepare();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            @Override
//                            public void onPrepared(MediaPlayer mp) {
//                                mp.start();
//
//                            }
//
//                        });
//                        //mediaPlayer.stop();
//                        //mediaPlayer.release();
//
//                    }
//
//
//                }
//
//
//            }
//        });
        
        myViewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("key",postkey);
                Comment_Bottom_Sheet bottomSheetDialogFragment = new Comment_Bottom_Sheet();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

    }



    @Override
    public int getItemCount() {
        return MyItem.size();
    }
}
