package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.model.FavouriteModel;
import com.i2c.groceryapp.model.Other_margin_list;

import java.util.ArrayList;
import java.util.HashMap;


public class RvFavouruteMOQListADP extends RecyclerView.Adapter<RvFavouruteMOQListADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Other_margin_list> trade_arraylist_moq = new ArrayList<>();
    private HashMap<Integer, Boolean> map=new HashMap<>();
    private CheckedMOQ checkedMOQ;


    public RvFavouruteMOQListADP(Activity activity,
                                 ArrayList<Other_margin_list> trade_arraylist_moq,
                                 HashMap<Integer, Boolean> map,
                                 CheckedMOQ checkedMOQ) {
        this.activity = activity;
        this.trade_arraylist_moq = trade_arraylist_moq;
        this.map=map;
        this.checkedMOQ = checkedMOQ;
    }

    public interface CheckedMOQ{
        void CheckedMOQ(int pos, String MOQ, String margin_id, String other_cart_qunt,
                        String margin, String price);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMOQ;
        private TextView tvPrice;
        private TextView tvMargin;
        private CheckBox checkBOX;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMOQ=itemView.findViewById(R.id.tvMOQ);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvMargin=itemView.findViewById(R.id.tvMargin);

            checkBOX=itemView.findViewById(R.id.checkBOX);
                checkBOX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checkedMOQ.CheckedMOQ(getAdapterPosition(),
                                trade_arraylist_moq.get(getAdapterPosition()).getMin_order_qty(),
                                trade_arraylist_moq.get(getAdapterPosition()).getProduct_margin_id(),
                                trade_arraylist_moq.get(getAdapterPosition()).getOther_cart_qty(),
                                trade_arraylist_moq.get(getAdapterPosition()).getMargin(),
                                trade_arraylist_moq.get(getAdapterPosition()).getPrice());
                    }
                });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_moq_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvMOQ.setText(String.valueOf(trade_arraylist_moq.get(position).getMin_order_qty()));
        holder.tvPrice.setText(String.valueOf(trade_arraylist_moq.get(position).getPrice()));
        holder.tvMargin.setText(String.valueOf(trade_arraylist_moq.get(position).getMargin()));

        if (map.get(position)) {
            holder.checkBOX.setChecked(true);
        } else {
            holder.checkBOX.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return trade_arraylist_moq.size();
    }
}
