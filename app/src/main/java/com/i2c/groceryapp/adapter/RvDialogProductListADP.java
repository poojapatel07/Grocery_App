package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ItemDialogProductListBinding;
import com.i2c.groceryapp.databinding.ItemNotificationBinding;

public class RvDialogProductListADP extends RecyclerView.Adapter<RvDialogProductListADP.MyViewHolder> {
    private Activity activity;

    public RvDialogProductListADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemDialogProductListBinding binding;

        public MyViewHolder(@NonNull ItemDialogProductListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
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

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
