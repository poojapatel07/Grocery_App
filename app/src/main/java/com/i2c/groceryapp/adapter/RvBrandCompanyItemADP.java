package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ItemBrandCategoryBinding;
import com.i2c.groceryapp.databinding.ItemBrandCompanyCategoryBinding;

public class RvBrandCompanyItemADP extends RecyclerView.Adapter<RvBrandCompanyItemADP.MyViewHolder> {
    private Activity activity;

    public RvBrandCompanyItemADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemBrandCompanyCategoryBinding binding;

        public MyViewHolder(@NonNull ItemBrandCompanyCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @NonNull
    @Override
    public RvBrandCompanyItemADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBrandCompanyCategoryBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                ,R.layout.item_brand_company_category, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvBrandCompanyItemADP.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
