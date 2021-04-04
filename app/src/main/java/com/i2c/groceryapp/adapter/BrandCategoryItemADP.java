package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ItemBrandCategoryBinding;
import com.i2c.groceryapp.databinding.ItemNotificationBinding;
import com.i2c.groceryapp.model.Brand_list;

import java.util.ArrayList;

public class BrandCategoryItemADP extends RecyclerView.Adapter<BrandCategoryItemADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Brand_list> brandlist=new ArrayList<>();
    private CallMainBrandID callMainBrandID;

    public BrandCategoryItemADP(Activity activity, ArrayList<Brand_list> brandlist,
                                CallMainBrandID callMainBrandID) {
        this.activity = activity;
        this.brandlist = brandlist;
        this.callMainBrandID=callMainBrandID;
    }

    public interface CallMainBrandID{
        void callMainBrandId(String brand_id, String brand_compny_id);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemBrandCategoryBinding binding;

        public MyViewHolder(@NonNull ItemBrandCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.rlMainBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callMainBrandID.callMainBrandId(brandlist.get(getAdapterPosition()).getBrand_id(),
                            brandlist.get(getAdapterPosition()).getBrand_companie_id());
                }
            });
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
        Glide.with(activity)
                .load(brandlist.get(position).getLogo())
                .into(holder.binding.ivBrandImage);

        if (brandlist.get(position).getIs_selected().equals("1")){
            holder.binding.rlMainBrand.setBackgroundResource(R.drawable.green_dark_border_bg);
        }else {
            holder.binding.rlMainBrand.setBackgroundResource(R.drawable.white_fill_bg);
        }
    }

    @Override
    public int getItemCount() {
        return brandlist.size();
    }
}
