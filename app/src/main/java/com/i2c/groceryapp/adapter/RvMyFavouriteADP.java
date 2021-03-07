package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemTodaySpecialBinding;
import com.i2c.groceryapp.databinding.ItemTradeOfferBinding;


public class RvMyFavouriteADP extends RecyclerView.Adapter<RvMyFavouriteADP.MyViewHolder> {
    private Activity activity;

    public RvMyFavouriteADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemTradeOfferBinding binding;

        public MyViewHolder(@NonNull ItemTradeOfferBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.cardMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProductDetailActivity.class));
                }
            });
        }
    }

    @NonNull
    @Override
    public RvMyFavouriteADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTradeOfferBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_trade_offer, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvMyFavouriteADP.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
