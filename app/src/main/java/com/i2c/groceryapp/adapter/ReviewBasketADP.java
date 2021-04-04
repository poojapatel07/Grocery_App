package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.OrderSummaryActivity;
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemFreebiesBinding;
import com.i2c.groceryapp.model.ReviewCartModel;
import com.i2c.groceryapp.utils.Constant;

import java.util.ArrayList;

public class ReviewBasketADP extends RecyclerView.Adapter<ReviewBasketADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<ReviewCartModel> arrayList = new ArrayList<>();
    private AddFavouritetoAllProduct addtoFavourite;
    private UpdateReviewCart updateReviewCart;
    boolean flag = false;
    int first_moq;
    float FINAL_PRICE;
    float STR;
    int free;
    int TOTAL_CART_ITEMS;


    public ReviewBasketADP(Activity activity, ArrayList<ReviewCartModel> arrayList,
                           AddFavouritetoAllProduct addtoFavourite,
                           UpdateReviewCart updateReviewCart) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.updateReviewCart = updateReviewCart;
        this.addtoFavourite = addtoFavourite;
    }

    public interface AddFavouritetoAllProduct {
        void addToFavouriteAllProduct(String product_id, Boolean isclick, int position);
    }

    public interface UpdateReviewCart {
        void updateReviewCart(String product_id, String update_quantity,
                              int cart_quanty,float add_price, boolean cart_bool,
                              String margin_id);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemFreebiesBinding binding;

        public MyViewHolder(@NonNull ItemFreebiesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!flag) {
                        addtoFavourite.addToFavouriteAllProduct(
                                arrayList.get(getAdapterPosition()).getProduct_id(),
                                false, getAdapterPosition());
                        flag = true;
                    } else {
                        addtoFavourite.addToFavouriteAllProduct(
                                arrayList.get(getAdapterPosition()).getProduct_id(),
                                true, getAdapterPosition());
                        flag = false;
                    }
                }
            });
            
            
            binding.rlCartPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrayList.get(getAdapterPosition()).getProduct_details().getIs_free_product().equals("1")){
                        int add_free = free /
                                Integer.parseInt(arrayList.get(getAdapterPosition()).getProduct_details().getMin_qty_for_free());

                        binding.tvFree.setText(String.valueOf(Integer.valueOf(binding.tvFree.getText().toString()) + add_free)+"\nFree");
                    }

                    first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());

                    int a = Integer.valueOf(binding.tvCartQuny.getText().toString());

                    float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                    float c = Float.parseFloat(binding.tvReatil.getText().toString());
                    int moq = Integer.valueOf(binding.tvMOQ.getText().toString());

                    binding.tvCartQuny.setText(String.valueOf(first_moq + a));

                    STR = c * moq;
                    FINAL_PRICE = b + STR ;

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    Log.e("TAG", "onClick: PRICE::::::"+Float.parseFloat(
                            binding.tvTotalPrice.getText().toString()) );
                    TOTAL_CART_ITEMS = Integer.valueOf(binding.tvMOQ.getText().toString());

                    updateReviewCart.updateReviewCart(arrayList.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString(),
                            TOTAL_CART_ITEMS, STR,true, arrayList.get(getAdapterPosition()).getProduct_margin_id());

//                    Utility.BADGE_PRICE = Utility.BADGE_PRICE + STR;
//                    HomeActivity.showBadge(activity, HomeActivity.bottomNavigationView, R.id.nav_Checkout,
//                            Utility.BADGE_PRICE);

                }
            });

            binding.rlCartMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());

                    int a = Integer.valueOf(binding.tvCartQuny.getText().toString());

                    float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                    float c = Float.parseFloat(binding.tvReatil.getText().toString());
                    int moq = Integer.valueOf(binding.tvMOQ.getText().toString());

                    if (a != 0) {
                        binding.tvCartQuny.setText(String.valueOf(a - first_moq));
                        STR = c * moq;
                        FINAL_PRICE = b - STR ;
//                        Utility.BADGE_PRICE = Utility.BADGE_PRICE - STR;

                        if (arrayList.get(getAdapterPosition()).getProduct_details().getIs_free_product().equals("1")){
                            int add_free = free /
                                    Integer.parseInt(arrayList.get(getAdapterPosition()).getProduct_details().getMin_qty_for_free());

                            binding.tvFree.setText(String.valueOf(Integer.valueOf(binding.tvFree.getText().toString()) - add_free)+"\nFree");
                        }

                    } else {
                        binding.tvCartQuny.setText(String.valueOf(0));
                        binding.rlAddCart.setVisibility(View.VISIBLE);
                        binding.rlQuntity.setVisibility(View.GONE);
                    }

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    TOTAL_CART_ITEMS = Integer.valueOf(binding.tvMOQ.getText().toString());

                    updateReviewCart.updateReviewCart(
                            arrayList.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString(),
                            TOTAL_CART_ITEMS, STR, false,
                            arrayList.get(getAdapterPosition()).getProduct_margin_id());

//                    HomeActivity.showBadge(activity, HomeActivity.bottomNavigationView, R.id.nav_Checkout,
//                            Utility.BADGE_PRICE);

                }
            });


            binding.cardMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra(Constant.PRODUCT_ID, arrayList.get(getAdapterPosition()).getProduct_id());
                    intent.putExtra(Constant.PRODUCT_NAME, arrayList.get(getAdapterPosition()).getProduct_details().getName());
                    intent.putExtra(Constant.PRODUCT_IMAGE, arrayList.get(getAdapterPosition()).getProduct_details().getImage());
                    intent.putExtra(Constant.PRODUCT_MRP, arrayList.get(getAdapterPosition()).getProduct_details().getMrp_price());
                    intent.putExtra(Constant.PRODUCT_RETAIL_PRICE, arrayList.get(getAdapterPosition()).getProduct_details().getRetail_price());
                    intent.putExtra(Constant.PRODUCT_MOQ, arrayList.get(getAdapterPosition()).getProduct_details().getMin_order_qty());
                    intent.putExtra(Constant.PRODUCT_MARGIN, arrayList.get(getAdapterPosition()).getProduct_details().getMargin());
                    intent.putExtra(Constant.PRODUCT_DP, arrayList.get(getAdapterPosition()).getProduct_details().getProduct_dp());
                    intent.putExtra(Constant.PRODUCT_TOTAL_PRICE, Float.parseFloat(binding.tvTotalPrice.getText().toString()));
                    intent.putExtra(Constant.PRODUCT_ISFAVOURITE, arrayList.get(getAdapterPosition()).getIs_favorite());
                    intent.putExtra(Constant.PRODUCT_IN_CART_QNTY, Integer.valueOf(binding.tvCartQuny.getText().toString()));
                    intent.putExtra(Constant.IS_CART, "1");
                    intent.putExtra(Constant.PRODUCT_FLAG, flag);
                    activity.startActivity(intent);
                }
            });


            /*call to order*/
            binding.llCallOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:8849192202"));
                    activity.startActivity(intent);
                }
            });
        }
    }

    public void updateIsFavData(int adapterPosition) {
        if (arrayList.get(adapterPosition).getIs_favorite().equals("1")) {
            arrayList.get(adapterPosition).setIs_favorite("0");
        }else {
            arrayList.get(adapterPosition).setIs_favorite("1");
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ReviewBasketADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFreebiesBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_freebies, parent, false);
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
                + arrayList.get(position).getProduct_details().getIs_free_product());

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

            holder.binding.tvFree.setText(String.valueOf(free_product)+"\nFree");

        } else {
            holder.binding.tvFree.setText("0"+"\nFree");
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
