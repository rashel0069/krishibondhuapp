package com.example.krishibondhuapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyingViewHolder extends RecyclerView.ViewHolder {

    CircleImageView sellerCircleImageView;
    TextView sellerFulname,date,time;
    TextView category,productName,jat,quantity,price,place;
    ImageView sellingImageView;
    LinearLayout callSeller;
    CardView cardView;

    public BuyingViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.buysam_cardViewId);

        sellerCircleImageView = itemView.findViewById(R.id.buy_uerImageId);
        sellerFulname = itemView.findViewById(R.id.buy_userFullnameId);
        date = itemView.findViewById(R.id.buyingDateId);
        time = itemView.findViewById(R.id.buyingTimeId);

        category = itemView.findViewById(R.id.buyingCategoryTvId);
        productName = itemView.findViewById(R.id.buyingProductTvId);
        jat = itemView.findViewById(R.id.buyingJatTvId);
        quantity = itemView.findViewById(R.id.buyingQuantityTvId);
        price = itemView.findViewById(R.id.buyingPriceTvId);
        place = itemView.findViewById(R.id.buyingPlaceTvId);

        sellingImageView = itemView.findViewById(R.id.buyingImageId);
        callSeller = itemView.findViewById(R.id.callBuyerId);


    }
}
