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
import com.i2c.groceryapp.databinding.ItemBrandCompanyCategoryBinding;
import com.i2c.groceryapp.model.Brand_companies_list;

import java.util.ArrayList;

public class RvBrandCompanyItemADP extends RecyclerView.Adapter<RvBrandCompanyItemADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Brand_companies_list> brandCompnaylist=new ArrayList<>();
    private CallBrandCompanyId callBrandCompanyId;

    public RvBrandCompanyItemADP(Activity activity, ArrayList<Brand_companies_list> brandCompnaylist,
                                 CallBrandCompanyId callBrandCompanyId) {
        this.activity = activity;
        this.brandCompnaylist = brandCompnaylist;
        this.callBrandCompanyId = callBrandCompanyId;
    }

    public interface CallBrandCompanyId{
        void CallBrandCompanyID(String brand_company_id);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemBrandCompanyCategoryBinding binding;

        public MyViewHolder(@NonNull ItemBrandCompanyCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.rlBrandCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBrandCompanyId.CallBrandCompanyID(brandCompnaylist.get(getAdapterPosition()).getBrand_companie_id());
                }
            });

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
        Glide.with(activity)
                .load(brandCompnaylist.get(position).getLogo())
                .into(holder.binding.ivBrandCompnay);

        if (brandCompnaylist.get(position).getIs_selected().equals("1")){
            holder.binding.rlBrandCompany.setBackgroundResource(R.drawable.green_dark_border_bg);
        }else {
            holder.binding.rlBrandCompany.setBackgroundResource(R.drawable.white_fill_bg);
        }

    }

    @Override
    public int getItemCount() {
        return brandCompnaylist.size();
    }
}
