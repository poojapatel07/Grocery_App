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
import com.i2c.groceryapp.databinding.ItemBrandCategoryBinding;
import com.i2c.groceryapp.databinding.ItemTodaySpecialBinding;
import com.i2c.groceryapp.databinding.ItemTradeOfferBinding;
import com.i2c.groceryapp.model.Todayspecial_list;

import java.util.ArrayList;

public class RvProduct_VerticleADP extends RecyclerView.Adapter<RvProduct_VerticleADP.MyViewHolder> {
    private Activity activity;
    boolean flag = false;
    private OpenMOQDialog openDialogMOQ;
    private AddFavouritetoAllProduct addtoFavourite;
    private AddToReviewCartList addToReviewCartList;
    private UpdateReviewCart updateReviewCart;
    private PassValue_ProductDeatlis passValue_productDeatlis;
    private ArrayList<Todayspecial_list> allProductList = new ArrayList<>();
    int first_moq;
    int add_moq;
    float STR_PRICE;
    float FINAL_PRICE;
    private float Price;
    float STR;
    int moq;
    int free_product;
    int free;

    public RvProduct_VerticleADP(Activity activity,
                                  ArrayList<Todayspecial_list> allProductList,
                                  OpenMOQDialog openDialogMOQ,
                                  AddFavouritetoAllProduct addtoFavourite,
                                  AddToReviewCartList addToReviewCartList,
                                  UpdateReviewCart updateReviewCart,
                                  PassValue_ProductDeatlis passValue_productDeatlis) {
        this.activity = activity;
        this.openDialogMOQ = openDialogMOQ;
        this.allProductList = allProductList;
        this.addtoFavourite = addtoFavourite;
        this.addToReviewCartList = addToReviewCartList;
        this.updateReviewCart = updateReviewCart;
        this.passValue_productDeatlis = passValue_productDeatlis;
    }

    public interface OpenMOQDialog {
        void openMoqDialog(String productName, String Product_ID, int position, TextView text,
                           String quantity, float price, String margin, RelativeLayout addCart,
                           LinearLayout llQnty, TextView tvCart, TextView tvMargin,
                           TextView tvRetail, TextView tvTotal, TextView tvFree);
    }

    public interface AddFavouritetoAllProduct {
        void addToFavouriteAllProduct(String product_id, Boolean isclick, int position);
    }

    public interface AddToReviewCartList {
        void addtoReviewCartList(String product_id, String quantity, int position);
    }

    public interface UpdateReviewCart {
        void updateReviewCart(String product_id, String update_quantity);
    }

    public interface PassValue_ProductDeatlis {
        void passvalueProductDetail(int pos, String product_image, String product_id,
                                    String product_name, String product_mrp, float product_retail,
                                    String margin, float TotalPrice, int cartQuanty,
                                    int In_Cart_qunty, String min_order_qunaty, int IS_FAV);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemTodaySpecialBinding binding;

        public MyViewHolder(@NonNull ItemTodaySpecialBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            /*fav or unFav*/
            binding.ivFavFreebies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!flag) {
                        addtoFavourite.addToFavouriteAllProduct(
                                allProductList.get(getAdapterPosition()).getProduct_id(),
                                false, getAdapterPosition());
                        flag = true;
                    } else {
                        addtoFavourite.addToFavouriteAllProduct(
                                allProductList.get(getAdapterPosition()).getProduct_id(),
                                true, getAdapterPosition());
                        flag = false;
                    }
                }
            });


            binding.cardMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (allProductList.get(getAdapterPosition()).getOther_margin_list().size() != 0) {

                        passValue_productDeatlis.passvalueProductDetail(
                                getAdapterPosition(),
                                allProductList.get(getAdapterPosition()).getImage(),
                                allProductList.get(getAdapterPosition()).getProduct_id(),
                                allProductList.get(getAdapterPosition()).getName(),
                                allProductList.get(getAdapterPosition()).getMrp_price(),
                                allProductList.get(getAdapterPosition()).getRetail_price(),
                                allProductList.get(getAdapterPosition()).getMargin(),
                                Float.parseFloat(binding.tvTotalPrice.getText().toString()),
                                Integer.parseInt(binding.tvCartQuny.getText().toString()),
                                allProductList.get(getAdapterPosition()).getIn_cart_qty(),
                                binding.tvProductMOQ.getText().toString(),
                                allProductList.get(getAdapterPosition()).getIs_favorite());

                    } else {

                        passValue_productDeatlis.passvalueProductDetail(
                                getAdapterPosition(),
                                allProductList.get(getAdapterPosition()).getImage(),
                                allProductList.get(getAdapterPosition()).getProduct_id(),
                                allProductList.get(getAdapterPosition()).getName(),
                                allProductList.get(getAdapterPosition()).getMrp_price(),
                                allProductList.get(getAdapterPosition()).getRetail_price(),
                                allProductList.get(getAdapterPosition()).getMargin(),
                                Float.parseFloat(binding.tvTotalPrice.getText().toString()),
                                Integer.parseInt(binding.tvCartQuny.getText().toString()),
                                allProductList.get(getAdapterPosition()).getIn_cart_qty(),
                                allProductList.get(getAdapterPosition()).getMin_order_qty(),
                                allProductList.get(getAdapterPosition()).getIs_favorite());
                    }
                }
            });


            /*add cart*/
            binding.rlAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.rlQuntity.setVisibility(View.VISIBLE);
                    binding.rlAddCart.setVisibility(View.GONE);

                    add_moq = 0;
                    FINAL_PRICE = 0;

                    if (allProductList.get(getAdapterPosition()).getOther_margin_list().size() != 0) {
                        first_moq = Integer.parseInt(binding.tvProductMOQ.getText().toString());
                        binding.tvCartQuny.setText(String.valueOf(binding.tvProductMOQ.getText().toString()));

                        addToReviewCartList.addtoReviewCartList(allProductList.get(getAdapterPosition()).getProduct_id(),
                                binding.tvProductMOQ.getText().toString(), getAdapterPosition());

                        if (Integer.parseInt(allProductList.get(getAdapterPosition()).getIs_free_product()) != 0) {
                            free = Integer.parseInt(binding.tvProductMOQ.getText().toString()) *
                                    Integer.parseInt(allProductList.get(getAdapterPosition()).getPro_qty_for_free());
                        }

                    } else {
                        first_moq = Integer.parseInt(binding.tvMOQ.getText().toString());
                        binding.tvCartQuny.setText(String.valueOf(binding.tvMOQ.getText().toString()));

                        addToReviewCartList.addtoReviewCartList(
                                allProductList.get(getAdapterPosition()).getProduct_id(),
                                binding.tvMOQ.getText().toString(), getAdapterPosition());

                        if (Integer.parseInt(allProductList.get(getAdapterPosition()).getIs_free_product()) != 0) {
                            free = Integer.parseInt(binding.tvMOQ.getText().toString()) *
                                    Integer.parseInt(allProductList.get(getAdapterPosition()).getPro_qty_for_free());
                        }
                    }

                    if (Integer.parseInt(allProductList.get(getAdapterPosition()).getIs_free_product()) != 0) {
                        free_product = free / Integer.parseInt(allProductList.get(getAdapterPosition()).getMin_qty_for_free());
                    }

                    binding.tvFree.setText(String.valueOf(free_product));
                    Price = allProductList.get(getAdapterPosition()).getRetail_price();
                    STR_PRICE = Price * first_moq;
                    binding.tvTotalPrice.setText(String.valueOf(STR_PRICE));
                }
            });


            /*plus cart*/
            binding.rlCartPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (allProductList.get(getAdapterPosition()).getOther_margin_list().size() != 0) {
                        first_moq = Integer.parseInt(binding.tvProductMOQ.getText().toString());
                        moq = Integer.parseInt(binding.tvProductMOQ.getText().toString());
                    } else {
                        first_moq = Integer.parseInt(binding.tvMOQ.getText().toString());
                        moq = Integer.parseInt(binding.tvMOQ.getText().toString());
                    }

                    if (allProductList.get(getAdapterPosition()).getIs_free_product().equals("1")) {
                        int free = first_moq * Integer.parseInt(allProductList.get(getAdapterPosition()).getPro_qty_for_free());
                        int add_free = free / Integer.parseInt(allProductList.get(getAdapterPosition()).getMin_qty_for_free());

                        binding.tvFree.setText(String.valueOf(Integer.parseInt(binding.tvFree.getText().toString()) + add_free));
                    }

                    int a = Integer.parseInt(binding.tvCartQuny.getText().toString());

                    float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                    float c = Float.parseFloat(binding.tvReatil.getText().toString());

                    binding.tvCartQuny.setText(String.valueOf(first_moq + a));

                    STR = c * moq;
                    FINAL_PRICE = b + STR;

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    updateReviewCart.updateReviewCart(allProductList.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString());
                }
            });

            /*minus cart*/
            binding.rlCartMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (allProductList.get(getAdapterPosition()).getOther_margin_list().size() != 0) {
                        first_moq = Integer.parseInt(binding.tvProductMOQ.getText().toString());
                        moq = Integer.parseInt(binding.tvProductMOQ.getText().toString());
                    } else {
                        first_moq = Integer.parseInt(binding.tvMOQ.getText().toString());
                        moq = Integer.parseInt(binding.tvMOQ.getText().toString());
                    }

                    int a = Integer.parseInt(binding.tvCartQuny.getText().toString());

                    float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                    float c = Float.parseFloat(binding.tvReatil.getText().toString());
                    int moq = Integer.parseInt(binding.tvMOQ.getText().toString());

                    if (a != 0) {
                        binding.tvCartQuny.setText(String.valueOf(a - first_moq));
                        STR = c * moq;
                        FINAL_PRICE = b - STR;

                        if (allProductList.get(getAdapterPosition()).getIs_free_product().equals("1")) {
                            int free = first_moq * Integer.parseInt(allProductList.get(getAdapterPosition()).getPro_qty_for_free());
                            int add_free = free / Integer.parseInt(allProductList.get(getAdapterPosition()).getMin_qty_for_free());

                            binding.tvFree.setText(String.valueOf(Integer.parseInt(binding.tvFree.getText().toString()) - add_free));
                        }

                    } else {
                        binding.rlAddCart.setVisibility(View.VISIBLE);
                        binding.rlQuntity.setVisibility(View.GONE);
                    }

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    updateReviewCart.updateReviewCart(allProductList.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString());
                }
            });


            /*moq*/
            binding.rlOtherMargin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogMOQ.openMoqDialog(allProductList.get(getAdapterPosition()).getName(),
                            allProductList.get(getAdapterPosition()).getProduct_id(),
                            getAdapterPosition(), binding.tvProductMOQ,
                            allProductList.get(getAdapterPosition()).getMin_order_qty(),
                            allProductList.get(getAdapterPosition()).getRetail_price(),
                            allProductList.get(getAdapterPosition()).getMargin(),
                            binding.rlAddCart, binding.rlQuntity,
                            binding.tvCartQuny, binding.tvProductMargin,
                            binding.tvReatil, binding.tvTotalPrice,
                            binding.tvFree);
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
        if (allProductList.get(adapterPosition).getIs_favorite() == 1) {
            allProductList.get(adapterPosition).setIs_favorite(0);
        } else {
            allProductList.get(adapterPosition).setIs_favorite(1);
        }
        notifyDataSetChanged();
    }

    public void updateCart(int adapterPosition, String quantity) {
        Log.e("TAG", "updateIsFavData: position::::"+adapterPosition);
        allProductList.get(adapterPosition).setIn_cart_qty(Integer.parseInt(quantity));
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public RvProduct_VerticleADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTodaySpecialBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_today_special, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvProduct_VerticleADP.MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(allProductList.get(position).getThumb_image())
                .into(holder.binding.ivImage);

        holder.binding.tvCateName.setText(allProductList.get(position).getName());
        holder.binding.tvMRP.setText(String.valueOf(allProductList.get(position).getMrp_price()));
        holder.binding.tvReatil.setText(String.valueOf(allProductList.get(position).getRetail_price()));
        holder.binding.tvMOQ.setText(String.valueOf(allProductList.get(position).getMin_order_qty()));
        holder.binding.tvProductMargin.setText(allProductList.get(position).getMargin());

        if (allProductList.get(position).getIs_free_product().equals("1")) {
            holder.binding.bottomMain.setVisibility(View.VISIBLE);
        } else {
            holder.binding.bottomMain.setVisibility(View.GONE);
        }

        Glide.with(activity)
                .load(allProductList.get(position).getFree_product_details().getThumb_image())
                .into(holder.binding.ivFreeProduct);

        String freeproduct_main = allProductList.get(position).getName() + " " +
                allProductList.get(position).getMrp_price() + " " + "MRP";

        String freeproduct_sec = allProductList.get(position).getFree_product_details().getName() + " " +
                allProductList.get(position).getFree_product_details().getMrp_price() + " " + "MRP";

        String buy_one = "Buy" + " " + String.valueOf(allProductList.get(position).getMin_qty_for_free()) +
                " " + "pcs";

        String get_one = "Get" + " " + String.valueOf(allProductList.get(position).getPro_qty_for_free()) +
                " " + "Free";

        holder.binding.tvFreeProductName.setText(freeproduct_main);
        holder.binding.tvfreeproductGet.setText(freeproduct_sec);

        holder.binding.tvBuyOne.setText(buy_one);
        holder.binding.tvGetOne.setText(get_one);

        if (allProductList.get(position).getIs_favorite() == 1) {
            holder.binding.ivFavFreebies.setImageResource(R.drawable.ic_fav_green);
        } else {
            holder.binding.ivFavFreebies.setImageResource(R.drawable.ic_fav);
        }

        holder.binding.tvProductMOQ.setText(String.valueOf(allProductList.get(position).getMin_order_qty()));

        if (allProductList.get(position).getOther_margin_list().size() == 0) {
            holder.binding.llNoOtherMargin.setVisibility(View.VISIBLE);
            holder.binding.rlOtherMargin.setVisibility(View.GONE);

        } else {
            holder.binding.llNoOtherMargin.setVisibility(View.GONE);
            holder.binding.rlOtherMargin.setVisibility(View.VISIBLE);
        }


        if (allProductList.get(position).getIn_cart_qty() == 0) {
            holder.binding.rlAddCart.setVisibility(View.VISIBLE);
            holder.binding.rlQuntity.setVisibility(View.GONE);
            holder.binding.tvTotalPrice.setText("0");

        } else {
            holder.binding.rlAddCart.setVisibility(View.GONE);
            holder.binding.rlQuntity.setVisibility(View.VISIBLE);

            holder.binding.tvCartQuny.setText(String.valueOf(allProductList.get(position).getIn_cart_qty()));

            float total_price = Float.parseFloat(holder.binding.tvReatil.getText().toString()) *
                    Integer.parseInt(holder.binding.tvCartQuny.getText().toString());
            holder.binding.tvTotalPrice.setText(String.valueOf(total_price));

            if (allProductList.get(position).getIs_free_product().equals("1")) {
                int free = Integer.parseInt(holder.binding.tvCartQuny.getText().toString()) *
                        Integer.parseInt(allProductList.get(position).getPro_qty_for_free());
                int free_product = free / Integer.parseInt(allProductList.get(position).getMin_qty_for_free());

                holder.binding.tvFree.setText(String.valueOf(free_product));

            } else {
                holder.binding.tvFree.setText("0");
            }
        }
    }

    @Override
    public int getItemCount() {
        return allProductList.size();
    }
}
