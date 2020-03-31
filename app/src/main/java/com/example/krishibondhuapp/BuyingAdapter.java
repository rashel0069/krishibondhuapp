package com.example.krishibondhuapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class BuyingAdapter extends RecyclerView.Adapter<BuyingViewHolder> {

    private List<BuyingPosoClass> MyItem;
    private Context context;

    public BuyingAdapter(List<BuyingPosoClass> myItem, Context context) {
        MyItem = myItem;
        this.context = context;
    }


    @NonNull
    @Override
    public BuyingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buying_page_sample_design,viewGroup,false);
        BuyingViewHolder MVH = new BuyingViewHolder(view);
        return MVH;


    }

    @Override
    public void onBindViewHolder(@NonNull final BuyingViewHolder buyingViewHolder, final int i) {
        final BuyingPosoClass itemPosition = MyItem.get(i);

//        commentViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        final String sellerPhoneNumber = itemPosition.getUserPhoneNumber();

        buyingViewHolder.sellerFulname.setText(itemPosition.getUserName());
        buyingViewHolder.date.setText(itemPosition.getDate());
        buyingViewHolder.time.setText(itemPosition.getTime());

        buyingViewHolder.category.setText(itemPosition.getCategory());
        buyingViewHolder.productName.setText(itemPosition.getProductName());
        buyingViewHolder.jat.setText(itemPosition.getJat());
        buyingViewHolder.quantity.setText(itemPosition.getQuantity());
        buyingViewHolder.price.setText(itemPosition.getPrice());
        buyingViewHolder.place.setText(itemPosition.getPlaceName());


        Glide.with(context)
                .load(itemPosition.getUserPic())
                .into(buyingViewHolder.sellerCircleImageView);

        Glide.with(context)
                .load(itemPosition.getImageUrl())
                .into(buyingViewHolder.sellingImageView);

        buyingViewHolder.callSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + sellerPhoneNumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return MyItem.size();
    }
}
