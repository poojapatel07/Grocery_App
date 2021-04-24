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
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemOrderSummaryBinding;
import com.i2c.groceryapp.model.OrderList;
import com.i2c.groceryapp.model.Order_product_data;

import java.util.ArrayList;
import java.util.List;


public class OrderSummaryADP extends RecyclerView.Adapter<OrderSummaryADP.MyViewHolder> {
    private Activity activity;
    private List<Order_product_data> myorder_statusList=new ArrayList<>();

    public OrderSummaryADP(Activity activity, List<Order_product_data> myorder_statusList) {
        this.activity = activity;
        this.myorder_statusList = myorder_statusList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemOrderSummaryBinding binding;

        public MyViewHolder(@NonNull ItemOrderSummaryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @NonNull
    @Override
    public OrderSummaryADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderSummaryBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_order_summary, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryADP.MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(myorder_statusList.get(position).getProduct_image().getThumb_image())
                .into(holder.binding.ivImage);

        holder.binding.tvCateName.setText(myorder_statusList.get(position).getName());
        holder.binding.tvAmount.setText(String.valueOf(myorder_statusList.get(position).getAmount()));
        holder.binding.tvMOQ.setText(String.valueOf(myorder_statusList.get(position).getQty()));
        holder.binding.tvMargin.setText(myorder_statusList.get(position).getMargin());

        if (myorder_statusList.get(position).getIs_free_product().equals("1")){
            holder.binding.bottomMain.setVisibility(View.VISIBLE);
        }else {
            holder.binding.bottomMain.setVisibility(View.GONE);
        }

        Glide.with(activity)
                .load(myorder_statusList.get(position).getFree_product_image().getThumb_image())
                .into(holder.binding.ivFreeProduct);

        String freeproduct_main= myorder_statusList.get(position).getName()+" "+
                myorder_statusList.get(position).getMrp_price()+" "+"MRP";


        holder.binding.tvFreeProductName.setText(freeproduct_main);

        Glide.with(activity)
                .load(myorder_statusList.get(position).getFree_product_image().getThumb_image())
                .into(holder.binding.ivFreeProduct);

        holder.binding.tvFree.setText(String.valueOf(myorder_statusList.get(position).getTotal_free_product_qty())+" \n"+"Free");

        if (myorder_statusList.get(position).getIs_free_product().equals("1")){
            holder.binding.bottomMain.setVisibility(View.VISIBLE);
        }else {
            holder.binding.bottomMain.setVisibility(View.GONE);
        }

        float sum_amount = Float.parseFloat(holder.binding.tvAmount.getText().toString()) *
                Integer.valueOf(holder.binding.tvMOQ.getText().toString());
    }

    @Override
    public int getItemCount() {
        return myorder_statusList.size();
    }
}
