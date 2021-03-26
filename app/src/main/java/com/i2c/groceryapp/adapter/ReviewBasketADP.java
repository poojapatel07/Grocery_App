package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.OrderSummaryActivity;
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemFreebiesBinding;
import com.i2c.groceryapp.model.ReviewCartModel;

import java.util.ArrayList;

public class ReviewBasketADP extends RecyclerView.Adapter<ReviewBasketADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<ReviewCartModel> arrayList = new ArrayList<>();


    public ReviewBasketADP(Activity activity, ArrayList<ReviewCartModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemFreebiesBinding binding;

        public MyViewHolder(@NonNull ItemFreebiesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.cardMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, OrderSummaryActivity.class));
                }
            });
        }
    }

    @NonNull
    @Override
    public ReviewBasketADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFreebiesBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_freebies, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewBasketADP.MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(arrayList.get(position).getProduct_details().getThumb_image())
                .into(holder.binding.ivImage);

        holder.binding.tvCateName.setText(arrayList.get(position).getProduct_details().getName());
        holder.binding.tvMRP.setText(String.valueOf(arrayList.get(position).getProduct_details().getMrp_price()));
        holder.binding.tvReatil.setText(String.valueOf(arrayList.get(position).getProduct_details().getRetail_price()));
        holder.binding.tvMOQ.setText(String.valueOf(arrayList.get(position).getProduct_details().getMin_order_qty()));
        holder.binding.tvProductMargin.setText(arrayList.get(position).getProduct_details().getMargin());

        Log.e("TAG", "onBindViewHolder: getFreeProduct::::"
                +arrayList.get(position).getProduct_details().getIs_free_product());

        if (arrayList.get(position).getProduct_details().getIs_free_product().equals("1")) {
            holder.binding.bottomMain.setVisibility(View.VISIBLE);
        } else {
            holder.binding.bottomMain.setVisibility(View.GONE);
        }

        Glide.with(activity)
                .load(arrayList.get(position).getProduct_details().getFree_product_details().getThumb_image())
                .into(holder.binding.ivFreeProduct);

        String freeproduct_main = arrayList.get(position).getProduct_details().getName() + " " +
                arrayList.get(position).getProduct_details().getMrp_price() + " " + "MRP";

        String freeproduct_sec = arrayList.get(position).getProduct_details().getFree_product_details().getName() + " " +
                arrayList.get(position).getProduct_details().getFree_product_details().getMrp_price() + " " + "MRP";

        String buy_one = "Buy" + " " + String.valueOf(arrayList.get(position).getProduct_details().getMin_qty_for_free()) +
                " " + "pcs";

        String get_one = "Get" + " " + String.valueOf(arrayList.get(position).getProduct_details().getPro_qty_for_free()) +
                " " + "Free";

        holder.binding.tvFreeProductName.setText(freeproduct_main);
        holder.binding.tvfreeproductGet.setText(freeproduct_sec);

        holder.binding.tvBuyOne.setText(buy_one);
        holder.binding.tvGetOne.setText(get_one);

        if (arrayList.get(position).getIs_favorite().equals("1")) {
            holder.binding.ivLike.setImageResource(R.drawable.ic_fav_green);
        } else {
            holder.binding.ivLike.setImageResource(R.drawable.ic_fav);
        }

        holder.binding.tvProductMOQ.setText(String.valueOf(
                arrayList.get(position).getProduct_details().getMin_order_qty()));

        holder.binding.rlAddCart.setVisibility(View.GONE);
            holder.binding.rlQuntity.setVisibility(View.VISIBLE);

            holder.binding.tvCartQuny.setText(arrayList.get(position).getQty());

            float total_price = Float.parseFloat(holder.binding.tvReatil.getText().toString()) *
                    Integer.valueOf(holder.binding.tvCartQuny.getText().toString());
            holder.binding.tvTotalPrice.setText(String.valueOf(total_price));

            if (arrayList.get(position).getProduct_details().getIs_free_product().equals("1")) {
                int free = Integer.valueOf(holder.binding.tvCartQuny.getText().toString()) *
                        Integer.parseInt(arrayList.get(position).getProduct_details().getPro_qty_for_free());
                int free_product = free / Integer.parseInt(arrayList.get(position).getProduct_details().getMin_qty_for_free());

                holder.binding.tvFree.setText(String.valueOf(free_product));

            } else {
                holder.binding.tvFree.setText("0");
            }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
