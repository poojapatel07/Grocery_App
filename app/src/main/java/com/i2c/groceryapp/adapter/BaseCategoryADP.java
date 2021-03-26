package com.i2c.groceryapp.adapter;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.AllSubCategoryActivity;
import com.i2c.groceryapp.databinding.ItemBaseItemBinding;
import com.i2c.groceryapp.databinding.ItemRvMenuBinding;
import com.i2c.groceryapp.model.Category;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.SessionManager;

import java.util.ArrayList;


public class BaseCategoryADP extends RecyclerView.Adapter<BaseCategoryADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Category> categorylist = new ArrayList<>();
    SessionManager sessionManager;

    public BaseCategoryADP(Activity activity, ArrayList<Category> categorylist) {
        this.activity = activity;
        this.categorylist = categorylist;
        if(activity!=null){
            sessionManager = new SessionManager(activity);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemBaseItemBinding baseItemBinding;

        public MyViewHolder(@NonNull ItemBaseItemBinding itemView) {
            super(itemView.getRoot());
            baseItemBinding = itemView;

            baseItemBinding.cardMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager.setStringValue(Constant.BASE_CAT_ID, categorylist.get(getAdapterPosition()).getCategorie_id());
                    sessionManager.setStringValue(Constant.BASE_CAT_NAME, categorylist.get(getAdapterPosition()).getName());
                    activity.startActivity(new Intent(activity, AllSubCategoryActivity.class));
                }
            });
        }
    }

    @NonNull
    @Override
    public BaseCategoryADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBaseItemBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_base_item, parent, false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseCategoryADP.MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(categorylist.get(position).getLogo())
                .into(holder.baseItemBinding.ivImage);

        holder.baseItemBinding.tvCategoryName.setText(categorylist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }
}
