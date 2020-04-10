package com.example.krishibondhuapp.Adaptar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.krishibondhuapp.R;
import com.example.krishibondhuapp.Pojoclass.SellingPosoClass;
//import com.karumi.dexter.Dexter;

import java.util.List;


public class SellingAdapter extends RecyclerView.Adapter<SellingViewHolder> {

    private List<SellingPosoClass> MyItem;
    Context context;

    public SellingAdapter(List<SellingPosoClass> myItem, Context context) {
        MyItem = myItem;
        this.context = context;
    }


    @NonNull
    @Override
    public SellingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.selling_product_sample_design, viewGroup, false);
        SellingViewHolder MVH = new SellingViewHolder(view);
        return MVH;


    }

    @Override
    public void onBindViewHolder(@NonNull final SellingViewHolder sellingViewHolder, final int i) {
        final SellingPosoClass itemPosition = MyItem.get(i);

//        commentViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        final String sellerPhoneNumber = itemPosition.getUserPhoneNumber();

        sellingViewHolder.sellerFulname.setText(itemPosition.getUserName());
        sellingViewHolder.date.setText(itemPosition.getDate());
        sellingViewHolder.time.setText(itemPosition.getTime());

        sellingViewHolder.category.setText(itemPosition.getCategory());
        sellingViewHolder.productName.setText(itemPosition.getProductName());
        sellingViewHolder.jat.setText(itemPosition.getJat());
        sellingViewHolder.quantity.setText(itemPosition.getQuantity());
        sellingViewHolder.price.setText(itemPosition.getPrice());
        sellingViewHolder.place.setText(itemPosition.getPlaceName());


        Glide.with(context)
                .load(itemPosition.getUserPic())
                .into(sellingViewHolder.sellerCircleImageView);

        Glide.with(context)
                .load(itemPosition.getImageUrl())
                .into(sellingViewHolder.sellingImageView);

        sellingViewHolder.callSeller.setOnClickListener(new View.OnClickListener() {
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
