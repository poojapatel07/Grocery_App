package com.i2c.groceryapp.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ItemBaseItemBinding;
import com.i2c.groceryapp.databinding.ItemRvMenuBinding;


public class BaseCategoryADP extends RecyclerView.Adapter<BaseCategoryADP.MyViewHolder> {
    private Activity activity;

    public BaseCategoryADP(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemBaseItemBinding baseItemBinding;

        public MyViewHolder(@NonNull ItemBaseItemBinding itemView) {
            super(itemView.getRoot());
            baseItemBinding = itemView;
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

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
