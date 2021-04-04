package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.model.Other_margin_list;

import java.util.ArrayList;
import java.util.HashMap;


public class RvBrandCompnayProductMOQListADP extends RecyclerView.Adapter<RvBrandCompnayProductMOQListADP.MyViewHolder> {
    private Activity activity;
    ArrayList<Other_margin_list> all_product_moq = new ArrayList<>();
    private HashMap<Integer, Boolean> map=new HashMap<>();
    private CheckedMOQ checkedMOQ;


    public RvBrandCompnayProductMOQListADP(Activity activity, ArrayList<Other_margin_list> all_product_moq,
                                           HashMap<Integer, Boolean> map,
                                           CheckedMOQ checkedMOQ) {
        this.activity = activity;
        this.all_product_moq = all_product_moq;
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
                            all_product_moq.get(getAdapterPosition()).getMin_order_qty(),
                            all_product_moq.get(getAdapterPosition()).getProduct_margin_id(),
                            all_product_moq.get(getAdapterPosition()).getOther_cart_qty(),
                            all_product_moq.get(getAdapterPosition()).getMargin(),
                            all_product_moq.get(getAdapterPosition()).getPrice());
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
        holder.tvMOQ.setText(String.valueOf(all_product_moq.get(position).getMin_order_qty()));
        holder.tvPrice.setText(String.valueOf(all_product_moq.get(position).getPrice()));
        holder.tvMargin.setText(String.valueOf(all_product_moq.get(position).getMargin()));

        if (map.get(position)) {
            Log.e("TAG", "onBindViewHolder: IF:::::::: "+map.get(position));
            holder.checkBOX.setChecked(true);
        } else {
            Log.e("TAG", "onBindViewHolder: ELSE:::::::: "+map.get(position));
            holder.checkBOX.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return all_product_moq.size();
    }
}
