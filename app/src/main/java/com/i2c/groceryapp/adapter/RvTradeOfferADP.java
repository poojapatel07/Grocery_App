package com.i2c.groceryapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
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
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemFreebiesBinding;
import com.i2c.groceryapp.databinding.ItemTradeOfferBinding;
import com.i2c.groceryapp.model.Todayspecial_list;
import com.i2c.groceryapp.utils.Constant;

import java.util.ArrayList;

public class RvTradeOfferADP extends RecyclerView.Adapter<RvTradeOfferADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Todayspecial_list> freebies_arraylist = new ArrayList<>();
    private AddFavouritetoAllProduct addtoFavourite;
    private AddToReviewCartList addToReviewCartList;
    private UpdateReviewCart updateReviewCart;
    private OpenMOQDialog openDialogMOQ;
    private Boolean flag = true;

    int first_moq;
    int add_moq;
    float STR_PRICE;
    float FINAL_PRICE;
    private float Price;
    float STR;
    int moq;
    int free_product;
    int free;


    public RvTradeOfferADP(Activity activity, ArrayList<Todayspecial_list> trade_arraylist,
                           AddFavouritetoAllProduct addtoFavourite, AddToReviewCartList addToReviewCartList,
                           OpenMOQDialog openDialogMOQ,
                           UpdateReviewCart updateReviewCart) {
        this.activity = activity;
        this.freebies_arraylist = trade_arraylist;
        this.addtoFavourite = addtoFavourite;
        this.addToReviewCartList = addToReviewCartList;
        this.openDialogMOQ = openDialogMOQ;
        this.updateReviewCart = updateReviewCart;
    }


    public interface OpenMOQDialog{
        void openMoqDialog(String productName, String Product_ID, int position, TextView text,
                           String quantity, float price, String margin, RelativeLayout addCart,
                           LinearLayout llQnty, TextView tvCart, TextView tvMargin,
                           TextView tvRetail, TextView tvTotal, TextView tvFree);
    }

    public interface AddFavouritetoAllProduct{
        void addToFavouriteAllProduct(String product_id, Boolean isclick, int position);
    }

    public interface AddToReviewCartList {
        void addtoReviewCartList(String product_id, String quantity);
    }

    public interface UpdateReviewCart {
        void updateReviewCart(String product_id, String update_quantity);
    }

    public interface PassValue_ProductDeatlis{
        void passvalueProductDetail(int pos,String product_image, int product_id, String product_name,
                                    int product_mrp, float product_retail, String margin,
                                    float TotalPrice, int cartQuanty, int In_Cart_qunty,
                                    int min_order_qunaty, int is_fav);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemTradeOfferBinding binding;

        public MyViewHolder(@NonNull ItemTradeOfferBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.topMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                    addtoFavourite.addToFavouriteAllProduct(
                            freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                            false, getAdapterPosition());
                    flag = true;
                } else {
                    addtoFavourite.addToFavouriteAllProduct(freebies_arraylist.get(getAdapterPosition()).getProduct_id(), true,getAdapterPosition());
                    flag = false;
                }
            });


            /*add cart*/
            binding.rlAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.rlQuntity.setVisibility(View.VISIBLE);
                    binding.rlAddCart.setVisibility(View.GONE);

                    add_moq=0;
                    FINAL_PRICE=0;

                    if (freebies_arraylist.get(getAdapterPosition()).getOther_margin_list().size()!=0) {
                        first_moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                        binding.tvCartQuny.setText(String.valueOf(binding.tvProductMOQ.getText().toString()));

                        addToReviewCartList.addtoReviewCartList(freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                                binding.tvProductMOQ.getText().toString());

                        if (Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getIs_free_product())!=0) {
                            free = Integer.parseInt(binding.tvProductMOQ.getText().toString()) *
                                    Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getPro_qty_for_free());
                        }

                    }else {
                        first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                        binding.tvCartQuny.setText(String.valueOf(binding.tvMOQ.getText().toString()));

                        addToReviewCartList.addtoReviewCartList(
                                freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                                binding.tvMOQ.getText().toString());

                        if (Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getIs_free_product())!=0) {
                            free = Integer.valueOf(binding.tvMOQ.getText().toString()) *
                                    Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getPro_qty_for_free());
                        }
                    }

                    if (Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getIs_free_product())!=0) {
                        free_product = free / Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_qty_for_free());
                    }

                    binding.tvFree.setText(String.valueOf(free_product));
                    Price = freebies_arraylist.get(getAdapterPosition()).getRetail_price();
                    STR_PRICE = Price*first_moq;
                    binding.tvTotalPrice.setText(String.valueOf(STR_PRICE));
                }
            });


            /*plus cart*/
            binding.rlCartPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (freebies_arraylist.get(getAdapterPosition()).getOther_margin_list().size()!=0) {
                        first_moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                        moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                    }else {
                        first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                        moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                    }

                    if (freebies_arraylist.get(getAdapterPosition()).getIs_free_product().equals("1")){
                        int free = first_moq * Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getPro_qty_for_free());
                        int add_free = free / Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_qty_for_free());

                        binding.tvFree.setText(String.valueOf(Integer.valueOf(binding.tvFree.getText().toString()) + add_free));
                    }

                    int a = Integer.valueOf(binding.tvCartQuny.getText().toString());

                    float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                    float c = Float.parseFloat(binding.tvReatil.getText().toString());

                    binding.tvCartQuny.setText(String.valueOf(first_moq + a));

                    STR = c * moq;
                    FINAL_PRICE = b + STR ;

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    updateReviewCart.updateReviewCart(freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString());

//                    Utility.BADGE_PRICE = Utility.BADGE_PRICE + STR;
//                    HomeActivity.showBadge(activity, HomeActivity.bottomNavigationView, R.id.nav_Checkout,
//                            Utility.BADGE_PRICE);
                }
            });

            /*minus cart*/
            binding.rlCartMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (freebies_arraylist.get(getAdapterPosition()).getOther_margin_list().size()!=0) {
                        first_moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                        moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                    }else {
                        first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                        moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                    }

                    int a = Integer.valueOf(binding.tvCartQuny.getText().toString());

                    float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                    float c = Float.parseFloat(binding.tvReatil.getText().toString());
                    int moq = Integer.valueOf(binding.tvMOQ.getText().toString());

                    if (a != 0) {
                        binding.tvCartQuny.setText(String.valueOf(a - first_moq));
                        STR = c * moq;
                        FINAL_PRICE = b - STR ;
//                        Utility.BADGE_PRICE = Utility.BADGE_PRICE - STR;

                        if (freebies_arraylist.get(getAdapterPosition()).getIs_free_product().equals("1")){
                            int free = first_moq * Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getPro_qty_for_free());
                            int add_free = free / Integer.parseInt(freebies_arraylist.get(getAdapterPosition()).getMin_qty_for_free());

                            binding.tvFree.setText(String.valueOf(Integer.valueOf(binding.tvFree.getText().toString()) - add_free));
                        }

                    } else {
                        binding.rlAddCart.setVisibility(View.VISIBLE);
                        binding.rlQuntity.setVisibility(View.GONE);
                    }

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    updateReviewCart.updateReviewCart(freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString());

//                    HomeActivity.showBadge(activity, HomeActivity.bottomNavigationView, R.id.nav_Checkout,
//                            Utility.BADGE_PRICE);
                }
            });


            /*moq*/
            binding.rlOtherMargin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogMOQ.openMoqDialog(freebies_arraylist.get(getAdapterPosition()).getName(),
                            freebies_arraylist.get(getAdapterPosition()).getProduct_id(),
                            getAdapterPosition(), binding.tvProductMOQ,
                            freebies_arraylist.get(getAdapterPosition()).getMin_order_qty(),
                            freebies_arraylist.get(getAdapterPosition()).getRetail_price(),
                            freebies_arraylist.get(getAdapterPosition()).getMargin(),
                            binding.rlAddCart, binding.rlQuntity,
                            binding.tvCartQuny, binding.tvProductMargin,
                            binding.tvReatil, binding.tvTotalPrice,
                            binding.tvFree);
                }
            });
        }
    }

    public void updateIsFavData(int adapterPosition) {
        Log.e("TAG", "updateIsFavData: position::::"+adapterPosition);
        if (freebies_arraylist.get(adapterPosition).getIs_favorite() == 1) {
            freebies_arraylist.get(adapterPosition).setIs_favorite(0);
        }else {
            freebies_arraylist.get(adapterPosition).setIs_favorite(1);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RvTradeOfferADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTradeOfferBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_trade_offer, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvTradeOfferADP.MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(freebies_arraylist.get(position).getThumb_image())
                .into(holder.binding.ivImage);

        holder.binding.tvCateName.setText(freebies_arraylist.get(position).getName());
        holder.binding.tvMRP.setText(String.valueOf(freebies_arraylist.get(position).getMrp_price()));
        holder.binding.tvReatil.setText(String.valueOf(freebies_arraylist.get(position).getRetail_price()));
        holder.binding.tvMOQ.setText(String.valueOf(freebies_arraylist.get(position).getMin_order_qty()));
        holder.binding.tvProductMargin.setText(freebies_arraylist.get(position).getMargin());


        Glide.with(activity)
                .load(freebies_arraylist.get(position).getFree_product_details().getThumb_image())
                .into(holder.binding.ivFreeProduct);

        String freeproduct_main = freebies_arraylist.get(position).getName() + " " +
                freebies_arraylist.get(position).getMrp_price() + " " + "MRP";

        String freeproduct_sec = freebies_arraylist.get(position).getFree_product_details().getName() + " " +
                freebies_arraylist.get(position).getFree_product_details().getMrp_price() + " " + "MRP";

        String buy_one = "Buy" + " " + String.valueOf(freebies_arraylist.get(position).getMin_qty_for_free()) +
                " " + "pcs";

        String get_one = "Get" + " " + String.valueOf(freebies_arraylist.get(position).getPro_qty_for_free()) +
                " " + "Free";

        holder.binding.tvFreeProductName.setText(freeproduct_main);
        holder.binding.tvfreeproductGet.setText(freeproduct_sec);

        holder.binding.tvBuyOne.setText(buy_one);
        holder.binding.tvGetOne.setText(get_one);

        if (freebies_arraylist.get(position).getIs_favorite()==1) {
            holder.binding.ivLike.setImageResource(R.drawable.ic_fav_green);
        } else {
            holder.binding.ivLike.setImageResource(R.drawable.ic_fav);
        }

        holder.binding.tvProductMOQ.setText(String.valueOf(freebies_arraylist.get(position).getMin_order_qty()));

        if (freebies_arraylist.get(position).getOther_margin_list().size()==0){
            holder.binding.llNoOtherMargin.setVisibility(View.VISIBLE);
            holder.binding.rlOtherMargin.setVisibility(View.GONE);

        }else {
            holder.binding.llNoOtherMargin.setVisibility(View.GONE);
            holder.binding.rlOtherMargin.setVisibility(View.VISIBLE);
        }


        if (freebies_arraylist.get(position).getIn_cart_qty() == 0) {
            holder.binding.rlAddCart.setVisibility(View.VISIBLE);
            holder.binding.rlQuntity.setVisibility(View.GONE);
            holder.binding.tvTotalPrice.setText("0");

        } else {
            holder.binding.rlAddCart.setVisibility(View.GONE);
            holder.binding.rlQuntity.setVisibility(View.VISIBLE);

            holder.binding.tvCartQuny.setText(String.valueOf(freebies_arraylist.get(position).getIn_cart_qty()));

            float total_price = Float.parseFloat(holder.binding.tvReatil.getText().toString()) *
                    Integer.valueOf(holder.binding.tvCartQuny.getText().toString());
            holder.binding.tvTotalPrice.setText(String.valueOf(total_price));

            if (freebies_arraylist.get(position).getIs_free_product().equals("1")) {
                int free = Integer.valueOf(holder.binding.tvCartQuny.getText().toString()) *
                        Integer.parseInt(freebies_arraylist.get(position).getPro_qty_for_free());
                int free_product = free / Integer.parseInt(freebies_arraylist.get(position).getMin_qty_for_free());

                holder.binding.tvFree.setText(String.valueOf(free_product));

            } else {
                holder.binding.tvFree.setText("0");
            }
        }

        holder.binding.tvMRP.setPaintFlags(holder.binding.tvMRP.getPaintFlags()
                | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public int getItemCount() {
        return freebies_arraylist.size();
    }
}
