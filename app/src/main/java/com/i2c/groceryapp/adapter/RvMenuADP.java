package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ItemRvMenuBinding;
import com.i2c.groceryapp.model.DataModel;

import java.util.ArrayList;


public class RvMenuADP extends RecyclerView.Adapter<RvMenuADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<DataModel> arrayList = new ArrayList<>();
    private OpenFragment openFragment;

    public interface OpenFragment{
        void openFragmt(int pos);
    }

    public RvMenuADP(Activity activity, ArrayList<DataModel> arrayList, OpenFragment openFragment) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.openFragment=openFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRvMenuBinding itemRvMenuBinding;

        public MyViewHolder(@NonNull ItemRvMenuBinding itemView) {
            super(itemView.getRoot());
            itemRvMenuBinding = itemView;

            itemRvMenuBinding.constTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFragment.openFragmt(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvMenuBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_rv_menu, parent, false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(arrayList.get(position).getIcon())
                .into(holder.itemRvMenuBinding.ivIcon);

        holder.itemRvMenuBinding.tvName.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
