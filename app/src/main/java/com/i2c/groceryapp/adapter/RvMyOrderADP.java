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
import com.i2c.groceryapp.activity.ViewAllOrderSummaryActivity;
import com.i2c.groceryapp.databinding.ItemMyOrderBinding;
import com.i2c.groceryapp.model.MyOrderList;
import com.i2c.groceryapp.model.OrderList;
import com.i2c.groceryapp.model.Order_status_data;
import com.i2c.groceryapp.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class RvMyOrderADP extends RecyclerView.Adapter<RvMyOrderADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<OrderList> myOrderlist = new ArrayList<>();
    private List<Order_status_data> myorderStatus = new ArrayList<>();


    public RvMyOrderADP(Activity activity, ArrayList<OrderList> myOrderlist) {
        this.activity = activity;
        this.myOrderlist = myOrderlist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMyOrderBinding binding;

        public MyViewHolder(@NonNull ItemMyOrderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.cardMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ViewAllOrderSummaryActivity.class);
                    intent.putExtra(Constant.ORDER_ID, myOrderlist.get(getAdapterPosition()).getOrder_id());
                    intent.putExtra(Constant.ORDER_NO, myOrderlist.get(getAdapterPosition()).getOrder_no());
                    intent.putExtra(Constant.ORDER_STATUS, myOrderlist.get(getAdapterPosition()).getStatus());
                    intent.putExtra(Constant.ORDER_SHIPPING_ADD, myOrderlist.get(getAdapterPosition()).getShipping_address());
                    intent.putExtra(Constant.ORDER_BILLING_ADD, myOrderlist.get(getAdapterPosition()).getBilling_address());
                    intent.putExtra(Constant.TOTAL_ORDER_AMOUNT, myOrderlist.get(getAdapterPosition()).getTotal_amount());
                    intent.putExtra(Constant.ARRAYLIST_POSITION,getAdapterPosition());
                    activity.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public RvMyOrderADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyOrderBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_my_order, parent, false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvMyOrderADP.MyViewHolder holder, int position) {
        holder.binding.tvOrderID.setText(myOrderlist.get(position).getOrder_no());
        holder.binding.tvTotalPrice.setText("\u20B9" + " " + myOrderlist.get(position).getTotal_amount());

        OrderList myorder = myOrderlist.get(position);
        myorderStatus = myorder.getOrder_status_data();

        String status = null;

        for (int i = 0; i < myorderStatus.size(); i++) {
            status = myorderStatus.get(i).getStatus();

            if (status.equals("0")) {
                holder.binding.llPending.setVisibility(View.VISIBLE);

//                String pending_str = myorderStatus.get(i).getCreated_at();
//                String[] separated = pending_str.split(" ");
                holder.binding.tvPendingDate.setText(myorderStatus.get(i).getCreated_at());

//                String[] pending_time = separated[1].split("\\.");
//                holder.binding.tvPendingTime.setText(pending_time[0]);


            } else if (status.equals("1")) {
                holder.binding.llDispatched.setVisibility(View.VISIBLE);

//                String dispatch_str = myorderStatus.get(i).getCreated_at().getDate();
//                String[] separated = dispatch_str.split(" ");
                holder.binding.tvDispatchedDate.setText(myorderStatus.get(i).getCreated_at());

//                String[] pending_time = separated[1].split("\\.");
//                holder.binding.tvDiapatchTime.setText(pending_time[0]);

            } else if (status.equals("2")) {
                holder.binding.llDelivered.setVisibility(View.VISIBLE);

//                String dispatch_str = myorderStatus.get(i).getCreated_at().getDate();
//                String[] separated = dispatch_str.split(" ");
                holder.binding.tvDeliveredDate.setText(myorderStatus.get(i).getCreated_at());

//                String[] pending_time = separated[1].split("\\.");
//                holder.binding.tvDeliveredTime.setText(pending_time[0]);

            } else if (status.equals("3")) {
                holder.binding.llCancel.setVisibility(View.VISIBLE);

//                String dispatch_str = myorderStatus.get(i).getCreated_at().getDate();
//                String[] separated = dispatch_str.split(" ");
                holder.binding.tvCancelDate.setText(myorderStatus.get(i).getCreated_at());

//                String[] pending_time = separated[1].split("\\.");
//                holder.binding.tvCancelTime.setText(pending_time[0]);
            }
        }

    }

    @Override
    public int getItemCount() {
        return myOrderlist.size();
    }
}
