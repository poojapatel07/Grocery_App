package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ItemBrandCategoryBinding;
import com.i2c.groceryapp.databinding.ItemNotificationBinding;

public class BrandCategoryItemADP extends RecyclerView.Adapter<BrandCategoryItemADP.MyViewHolder> {
    private Activity activity;

    public BrandCategoryItemADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemBrandCategoryBinding binding;

        public MyViewHolder(@NonNull ItemBrandCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @NonNull
    @Override
    public BrandCategoryItemADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBrandCategoryBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_brand_category, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandCategoryItemADP.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
