package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.ProductCategoryActivity;
import com.i2c.groceryapp.databinding.ItemAllCategoryBinding;
import com.i2c.groceryapp.databinding.ItemAllCategoryDetailsBinding;
import com.i2c.groceryapp.model.Subcategories_list;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.SessionManager;

import java.util.ArrayList;

public class RvAllCategoryDetailsADP extends RecyclerView.Adapter<RvAllCategoryDetailsADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Subcategories_list> arrayList = new ArrayList<>();
    private SessionManager sessionManager;

    public RvAllCategoryDetailsADP(Activity activity, ArrayList<Subcategories_list> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        if(activity!=null){
            sessionManager = new SessionManager(activity);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemAllCategoryDetailsBinding binding;

        public MyViewHolder(@NonNull ItemAllCategoryDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            
            binding.constMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrayList.get(getAdapterPosition()).getBrand_id().equals("0")||
                            arrayList.get(getAdapterPosition()).getBrand_companie_id().equals("0")){
                        Toast.makeText(activity, "No data found!", Toast.LENGTH_SHORT).show();

                    }else {
                        sessionManager.setStringValue(Constant.SUB_CATEGORY_ID,
                                arrayList.get(getAdapterPosition()).getSubcategorie_id());

                        sessionManager.setStringValue(Constant.BRAND_ID,
                                arrayList.get(getAdapterPosition()).getBrand_id());

                        sessionManager.setStringValue(Constant.SUB_CATEGORY_NAME, arrayList.get(getAdapterPosition()).getName());

                        sessionManager.setStringValue(Constant.BRAND_COMPANY_ID_PRODUCT,
                                arrayList.get(getAdapterPosition()).getBrand_companie_id());

                        activity.startActivity(new Intent(activity, ProductCategoryActivity.class));
                    }
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
        holder.binding.tvSubCategory.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
