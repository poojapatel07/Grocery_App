package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.BrandCompanyProductListActivity;
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemRvAlphabeticalCateBinding;
import com.i2c.groceryapp.databinding.ItemRvAlphabeticalCateBinding;
import com.i2c.groceryapp.model.All_SubCategoryList;

import java.util.ArrayList;
import java.util.List;

public class RvAlphabaticScrollADP extends
        RecyclerView.Adapter<RvAlphabaticScrollADP.MyViewHolder>implements SectionIndexer, Comparable {
    private Activity activity;
    private ArrayList<All_SubCategoryList> allSubcategoryList = new ArrayList<>();
    private ArrayList<Integer> mSectionPositions;
    private OnClickOpenCategory onClickOpenCategory;

    public interface OnClickOpenCategory{
        void openCategory(String brand_company_id);
    }

    public RvAlphabaticScrollADP(Activity activity,
                                 ArrayList<All_SubCategoryList> allSubcategoryList,
                                 OnClickOpenCategory onClickOpenCategory) {
        this.activity = activity;
        this.allSubcategoryList = allSubcategoryList;
        this.onClickOpenCategory = onClickOpenCategory;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemRvAlphabeticalCateBinding binding;

        public MyViewHolder(@NonNull ItemRvAlphabeticalCateBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickOpenCategory.openCategory(allSubcategoryList.get(getAdapterPosition()).getBrand_companie_id());
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
        holder.binding.tvCategoryName.setText(allSubcategoryList.get(position).getName());

        Glide.with(activity)
                .load(allSubcategoryList.get(position).getLogo())
                .into(holder.binding.ivCategoryImage);

    }

    @Override
    public int getItemCount() {
        return allSubcategoryList.size();
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>();
        mSectionPositions = new ArrayList<>();
        for (int i = 0, size = allSubcategoryList.size(); i < size; i++) {
            String section = String.valueOf(allSubcategoryList.get(i).getName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int i) {
        return mSectionPositions.get(i);    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
