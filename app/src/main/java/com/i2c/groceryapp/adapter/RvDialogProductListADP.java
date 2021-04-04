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
import com.i2c.groceryapp.databinding.ItemDialogProductListBinding;
import com.i2c.groceryapp.databinding.ItemNotificationBinding;
import com.i2c.groceryapp.model.Subcategories_list;

import java.util.ArrayList;

public class RvDialogProductListADP extends RecyclerView.Adapter<RvDialogProductListADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Subcategories_list> basecategory_list = new ArrayList<>();
    private CategoryIDFromHometoFrmgt categoryIDFromHometoFrmgt;

    public RvDialogProductListADP(Activity activity, ArrayList<Subcategories_list> arrayList,
                                  CategoryIDFromHometoFrmgt categoryIDFromHometoFrmgt) {
        this.activity = activity;
        this.basecategory_list = arrayList;
        this.categoryIDFromHometoFrmgt = categoryIDFromHometoFrmgt;
    }

    public interface CategoryIDFromHometoFrmgt{
        void CategoryIdFromHomeToFrgmt(String sub_cate, String brand_id,
                                       String brand_compny_id, String product_name);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemDialogProductListBinding binding;

        public MyViewHolder(@NonNull ItemDialogProductListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.llProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryIDFromHometoFrmgt.CategoryIdFromHomeToFrgmt(
                            basecategory_list.get(getAdapterPosition()).getSubcategorie_id(),
                            basecategory_list.get(getAdapterPosition()).getBrand_id(),
                            basecategory_list.get(getAdapterPosition()).getBrand_companie_id(),
                            basecategory_list.get(getAdapterPosition()).getName());
                }
            });
        }
    }

    @NonNull
    @Override
    public RvDialogProductListADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDialogProductListBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_dialog_product_list, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvDialogProductListADP.MyViewHolder holder, int position) {
        holder.binding.tvProductName.setText(basecategory_list.get(position).getName());

        Glide.with(activity)
                .load(basecategory_list.get(position).getLogo())
                .into(holder.binding.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return basecategory_list.size();
    }
}
