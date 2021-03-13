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
import com.i2c.groceryapp.activity.BrandCompanyProductListActivity;
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemRvAlphabeticalCateBinding;
import com.i2c.groceryapp.databinding.ItemRvAlphabeticalCateBinding;

public class RvAlphabaticScrollADP extends RecyclerView.Adapter<RvAlphabaticScrollADP.MyViewHolder> {
    private Activity activity;

    public RvAlphabaticScrollADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemRvAlphabeticalCateBinding binding;

        public MyViewHolder(@NonNull ItemRvAlphabeticalCateBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, BrandCompanyProductListActivity.class));
                }
            });
        }
    }

    @NonNull
    @Override
    public RvAlphabaticScrollADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvAlphabeticalCateBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_rv_alphabetical_cate, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAlphabaticScrollADP.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
