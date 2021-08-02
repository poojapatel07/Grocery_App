package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemFreebiesBinding;
import com.i2c.groceryapp.model.Todayspecial_list;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.SessionManager;

import java.util.ArrayList;

public class RvFreebiesADP extends RecyclerView.Adapter<RvFreebiesADP.MyViewHolder> {
    private final Activity activity;
    private ArrayList<Todayspecial_list> freebies_arraylist;
    private final AddtoFavouriteFree addtoFavourite;
    private final AddToReviewCartList addToReviewCartList;
    private final UpdateReviewCart updateReviewCart;
    private Boolean flag = true;
    int first_moq;
    int add_moq;
    float STR_PRICE;
    float FINAL_PRICE;
    private float Price;
    float STR;
    int free_product;
    int free;
    SessionManager sessionManager;

    public RvFreebiesADP(Activity activity, ArrayList<Todayspecial_list> freebies_arraylist,
                         AddtoFavouriteFree addtoFavourite,  AddToReviewCartList addToReviewCartList,
                         UpdateReviewCart updateReviewCart) {
        this.activity = activity;
        this.freebies_arraylist = freebies_arraylist;
        this.addtoFavourite = addtoFavourite;
        this.addToReviewCartList = addToReviewCartList;
        this.updateReviewCart = updateReviewCart;

        if(activity!=null){
            sessionManager = new SessionManager(activity);
        }
    }

    public void updateIsFavData(int adapterPosition) {
        if (freebies_arraylist.get(adapterPosition).getIs_favorite() == 1) {
            freebies_arraylist.get(adapterPosition).setIs_favorite(0);
        }else {
            freebies_arraylist.get(adapterPosition).setIs_favorite(1);
        }
        notifyDataSetChanged();
    }

    public void updateCart(int adapterPosition, String quantity) {
        Log.e("TAG", "updateIsFavData: position::::"+adapterPosition);
        freebies_arraylist.get(adapterPosition).setIn_cart_qty(Integer.parseInt(quantity));
        notifyDataSetChanged();
    }

    public interface AddtoFavouriteFree {
        void addToFavouriteFree(String product_id, Boolean isclick, int position);
    }

    public interface AddToReviewCartList {
        void addtoReviewCartList(String product_id, String quantity, int position,
                                 RelativeLayout addCart, LinearLayout cartquant);
    }

    public interface UpdateReviewCart {
        void updateReviewCart(String product_id, String update_quantity, int pos);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemFreebiesBinding binding;

        public MyViewHolder(@NonNull ItemFreebiesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.consRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager.setIntegerValue(Constant.freebies_position, getAdapterPosition());

                    Intent intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra(Constant.PRODUCT_ID, freebies_arraylist.get(getAdapterPosition()).getProduct_id());
                    intent.putExtra(Constant.PRODUCT_NAME, freebies_arraylist.get(getAdapterPosition()).getName());
                    intent.putExtra(Constant.PRODUCT_IMAGE, freebies_arraylist.get(getAdapterPosition()).getImage());
                    intent.putExtra(Constant.PRODUCT_MRP, freebies_arraylist.get(getAdapterPosition()).getMrp_price());
                    intent.putExtra(Constant.PRODUCT_RETAIL_PRICE, freebies_arraylist.get(getAdapterPosition()).getRetail_price());
                    intent.putExtra(Constant.PRODUCT_MOQ, freebies_arraylist.get(getAdapterPosition()).getMin_order_qty());
                    intent.putExtra(Constant.PRODUCT_MARGIN, freebies_arraylist.get(getAdapterPosition()).getMargin());
                    intent.putExtra(Constant.PRODUCT_DP, freebies_arraylist.get(getAdapterPosition()).getProduct_dp());
                    intent.putExtra(Constant.PRODUCT_TOTAL_PRICE, Float.parseFloat(binding.tvTotalPrice.getText().toString()));
                    intent.putExtra(Constant.PRODUCT_ISFAVOURITE, freebies_arraylist.get(getAdapterPosition()).getIs_favorite());
                    intent.putExtra(Constant.PRODUCT_IN_CART_QNTY, Integer.valueOf(binding.tvCartQuny.getText().toString()));
                    intent.putExtra(Constant.IS_CART, freebies_arraylist.get(getAdapterPosition()).getIn_cart_qty());
                    intent.putExtra(Constant.PRODUCT_FLAG, flag);
                    activity.startActivity(intent);
                }
            });

            binding.ivLike.setOnClickListener(v -> {
                if (!flag) {
                    addtoFavourite.addToFavouriteFree(
                            freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                            false, getAdapterPosition());
                    flag = true;
                } else {
                    addtoFavourite.addToFavouriteFree(freebies_arraylist.get(getAdapterPosition()).getProduct_id(), true,getAdapterPosition());
                    flag = false;
                }
            });


            binding.rlAddCart.setOnClickListener(v -> {
                if (!freebies_arraylist.get(getAdapterPosition()).getMin_order_qty().equals("0") ||
                        !binding.tvMOQ.getText().toString().equals("0") ||
                        !binding.tvTotalPrice.getText().toString().equals("0")
                ) {
                    binding.rlAddCart.setVisibility(View.GONE);
                    binding.rlQuntity.setVisibility(View.VISIBLE);

                    add_moq = 0;
                    FINAL_PRICE = 0;

                    free = Integer.parseInt(binding.tvMOQ.getText().toString()) *
                            Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getPro_qty_for_free());

                    free_product = free / Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_qty_for_free());
                    binding.tvFree.setText(String.valueOf(free_product));

                    first_moq = Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_order_qty());
                    Price = freebies_arraylist.get(getAdapterPosition()).getRetail_price();

                    STR_PRICE = Price * first_moq;
                    binding.tvTotalPrice.setText(String.valueOf(STR_PRICE));
                    binding.tvCartQuny.setText(binding.tvMOQ.getText().toString());

                    addToReviewCartList.addtoReviewCartList(
                            freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                            freebies_arraylist.get(getAdapterPosition()).getMin_order_qty(),
                            getAdapterPosition(), binding.rlAddCart, binding.rlQuntity);
                }else{
                    Toast.makeText(activity, "Not added in cart", Toast.LENGTH_SHORT).show();
                }

            });

            binding.rlCartPlus.setOnClickListener(v -> {
                first_moq = Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_order_qty());

                int free = first_moq * Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getPro_qty_for_free());
                int add_free = free / Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_qty_for_free());

                binding.tvFree.setText(String.valueOf(Integer.parseInt(binding.tvFree.getText().toString()) + add_free));

                int a = Integer.parseInt(binding.tvCartQuny.getText().toString());

                float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                float c = Float.parseFloat(binding.tvReatil.getText().toString());
                int moq = Integer.parseInt(binding.tvMOQ.getText().toString());

                binding.tvCartQuny.setText(String.valueOf(first_moq + a));

                STR = c * moq;
                FINAL_PRICE = b + STR;

                binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                updateReviewCart.updateReviewCart(freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                    binding.tvCartQuny.getText().toString(),getAdapterPosition());
            });

            binding.rlCartMinus.setOnClickListener(v -> {
                first_moq = Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_order_qty());
                int a = Integer.parseInt(binding.tvCartQuny.getText().toString());

                float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                float c = Float.parseFloat(binding.tvReatil.getText().toString());
                int moq = Integer.parseInt(binding.tvMOQ.getText().toString());

                if (a != 0) {
                    binding.tvCartQuny.setText(String.valueOf(a - first_moq));
                    STR = c * moq;
                    FINAL_PRICE = b - STR;

                    int free = first_moq * Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getPro_qty_for_free());
                    int add_free = free / Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_qty_for_free());

                    binding.tvFree.setText(String.valueOf(Integer.parseInt(binding.tvFree.getText().toString()) - add_free));

                } else {
                    binding.rlAddCart.setVisibility(View.VISIBLE);
                    binding.rlQuntity.setVisibility(View.GONE);
                }

                binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                updateReviewCart.updateReviewCart(freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                        binding.tvCartQuny.getText().toString(),getAdapterPosition());
            });

        }
    }

    @NonNull
    @Override
    public RvFreebiesADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFreebiesBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_freebies, parent, false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvFreebiesADP.MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(freebies_arraylist.get(position).getThumb_image())
                .into(holder.binding.ivImage);

        holder.binding.tvCateName.setText(freebies_arraylist.get(position).getName());
        holder.binding.tvMRP.setText(String.valueOf(freebies_arraylist.get(position).getMrp_price()));
        holder.binding.tvReatil.setText(String.valueOf(freebies_arraylist.get(position).getRetail_price()));
        holder.binding.tvMOQ.setText(String.valueOf(freebies_arraylist.get(position).getMin_order_qty()));
        holder.binding.tvMargin.setText(freebies_arraylist.get(position).getMargin());

        if (freebies_arraylist.get(position).getIs_free_product().equals("1")) {
            holder.binding.bottomMain.setVisibility(View.VISIBLE);
        } else {
            holder.binding.bottomMain.setVisibility(View.GONE);
        }

        Glide.with(activity)
                .load(freebies_arraylist.get(position).getFree_product_details().getThumb_image())
                .into(holder.binding.ivFreeProduct);

        String freeproduct_main = freebies_arraylist.get(position).getName() + " " +
                freebies_arraylist.get(position).getMrp_price() + " " + "MRP";

        String freeproduct_sec = freebies_arraylist.get(position).getFree_product_details().getName() + " " +
                freebies_arraylist.get(position).getFree_product_details().getMrp_price() + " " + "MRP";

        String buy_one = "Buy" + " " + freebies_arraylist.get(position).getMin_qty_for_free() +
                " " + "pcs";

        String get_one = "Get" + " " + freebies_arraylist.get(position).getPro_qty_for_free() +
                " " + "Free";

        holder.binding.tvFreeProductName.setText(freeproduct_main);
        holder.binding.tvfreeproductGet.setText(freeproduct_sec);

        holder.binding.tvBuyOne.setText(buy_one);
        holder.binding.tvGetOne.setText(get_one);

        Log.e("TAG", "onBindViewHolder: IS_FAV:::::"+freebies_arraylist.get(position).getIs_favorite());
        if (freebies_arraylist.get(position).getIs_favorite() == 1) {
            holder.binding.ivLike.setImageResource(R.drawable.ic_fav_green);
        } else {
            holder.binding.ivLike.setImageResource(R.drawable.ic_fav);
        }

        if (freebies_arraylist.get(position).getIn_cart_qty() == 0) {
            holder.binding.rlAddCart.setVisibility(View.VISIBLE);
            holder.binding.rlQuntity.setVisibility(View.GONE);
            holder.binding.tvTotalPrice.setText("0");

        } else {
            holder.binding.rlAddCart.setVisibility(View.GONE);
            holder.binding.rlQuntity.setVisibility(View.VISIBLE);

            holder.binding.tvCartQuny.setText(
                    String.valueOf(freebies_arraylist.get(position).getIn_cart_qty()));

            float total_price = Float.parseFloat(holder.binding.tvReatil.getText().toString()) *
                    Integer.parseInt(holder.binding.tvCartQuny.getText().toString());
            holder.binding.tvTotalPrice.setText(String.valueOf(total_price));

            if (freebies_arraylist.get(position).getIs_free_product().equals("1")) {
                int free = Integer.parseInt(holder.binding.tvCartQuny.getText().toString()) *
                        Integer.parseInt(freebies_arraylist.get(position).getPro_qty_for_free());
                int free_product = free / Integer.parseInt(freebies_arraylist.get(position).getMin_qty_for_free());

                holder.binding.tvFree.setText(String.valueOf(free_product));

            } else {
                holder.binding.tvFree.setText("0");
            }
        }
    }

    @Override
    public int getItemCount() {
        return freebies_arraylist.size();
    }
}
