package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ItemBrandCategoryBinding;
import com.i2c.groceryapp.databinding.ItemTradeOfferBinding;

public class RvProduct_VerticleADP extends RecyclerView.Adapter<RvProduct_VerticleADP.MyViewHolder> {
    private Activity activity;

    public RvProduct_VerticleADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemTradeOfferBinding binding;

        public MyViewHolder(@NonNull ItemTradeOfferBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @NonNull
    @Override
    public RvProduct_VerticleADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTradeOfferBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_trade_offer, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvProduct_VerticleADP.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
