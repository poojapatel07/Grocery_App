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
import com.i2c.groceryapp.activity.AllSubCategoryActivity;
import com.i2c.groceryapp.databinding.ItemAllCategoryBinding;
import com.i2c.groceryapp.model.Category;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.SessionManager;

import java.util.ArrayList;

public class RvAllCategoryADP extends RecyclerView.Adapter<RvAllCategoryADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Category> categorylist = new ArrayList<>();
    SessionManager sessionManager;

    public RvAllCategoryADP(Activity activity, ArrayList<Category> categorylist) {
        this.activity = activity;
        this.categorylist = categorylist;
        if(activity!=null){
            sessionManager = new SessionManager(activity);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemAllCategoryBinding binding;

        public MyViewHolder(@NonNull ItemAllCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.rltop.setOnClickListener(new View.OnClickListener() {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllCategoryBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_all_category, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAllCategoryADP.MyViewHolder holder, int position) {
        holder.binding.tvCategory.setText(categorylist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }
}
