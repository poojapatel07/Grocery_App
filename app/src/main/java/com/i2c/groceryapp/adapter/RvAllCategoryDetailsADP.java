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
import com.i2c.groceryapp.activity.ProductCategoryActivity;
import com.i2c.groceryapp.databinding.ItemAllCategoryBinding;
import com.i2c.groceryapp.databinding.ItemAllCategoryDetailsBinding;

public class RvAllCategoryDetailsADP extends RecyclerView.Adapter<RvAllCategoryDetailsADP.MyViewHolder> {
    private Activity activity;

    public RvAllCategoryDetailsADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemAllCategoryDetailsBinding binding;

        public MyViewHolder(@NonNull ItemAllCategoryDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            
            binding.constMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProductCategoryActivity.class));
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllCategoryDetailsBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_all_category_details, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAllCategoryDetailsADP.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
