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
import com.i2c.groceryapp.activity.ViewAllOrderSummaryActivity;
import com.i2c.groceryapp.databinding.ItemMyOrderBinding;


public class RvMyOrderADP extends RecyclerView.Adapter<RvMyOrderADP.MyViewHolder> {
    private Activity activity;

    public RvMyOrderADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMyOrderBinding binding;

        public MyViewHolder(@NonNull ItemMyOrderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.cardMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ViewAllOrderSummaryActivity.class));
                }
            });
        }
    }

    @NonNull
    @Override
    public RvMyOrderADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyOrderBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_my_order, parent, false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvMyOrderADP.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
