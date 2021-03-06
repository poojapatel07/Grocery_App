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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.databinding.ItemFreebiesBinding;
import com.i2c.groceryapp.databinding.ItemTodaySpecialBinding;
import com.i2c.groceryapp.databinding.ItemTradeOfferBinding;
import com.i2c.groceryapp.model.FavouriteModel;
import com.i2c.groceryapp.model.Todayspecial_list;

import java.util.ArrayList;


public class RvBrandCompanyProductADP extends RecyclerView.Adapter<RvBrandCompanyProductADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<Todayspecial_list> favourite_list = new ArrayList<>();
    private AddFavouritetoAllProduct addtoFavourite;
    private AddToReviewCartList addToReviewCartList;
    private UpdateReviewCart updateReviewCart;
    private PassValue_ProductDeatlis passValue_productDeatlis;
    private OpenMOQDialog openDialogMOQ;

    int first_moq;
    int add_moq;
    float STR_PRICE;
    float FINAL_PRICE;
    private float Price;
    float STR;
    int moq;
    int free_product;
    int free;
    private Boolean flag = true;


    public RvBrandCompanyProductADP(Activity activity, ArrayList<Todayspecial_list> favourite_list,
                                    AddToReviewCartList addToReviewCartList,
                                    UpdateReviewCart updateReviewCart, OpenMOQDialog openDialogMOQ,
                                    PassValue_ProductDeatlis passValue_productDeatlis,
                                    AddFavouritetoAllProduct addtoFavourite) {
        this.activity = activity;
        this.favourite_list = favourite_list;
        this.addToReviewCartList = addToReviewCartList;
        this.updateReviewCart = updateReviewCart;
        this.openDialogMOQ = openDialogMOQ;
        this.passValue_productDeatlis = passValue_productDeatlis;
        this.addtoFavourite = addtoFavourite;
    }


    public void updateIsFavData(int adapterPosition) {
        Log.e("TAG", "updateIsFavData: position::::"+adapterPosition);
        if (favourite_list.get(adapterPosition).getIs_favorite() == 1) {
            favourite_list.get(adapterPosition).setIs_favorite(0);
        }else {
            favourite_list.get(adapterPosition).setIs_favorite(1);
        }
        notifyDataSetChanged();
    }


    public void updateCart(int adapterPosition, String quantity) {
        Log.e("TAG", "updateIsFavData: position::::"+adapterPosition);
        favourite_list.get(adapterPosition).setIn_cart_qty(Integer.parseInt(quantity));
        notifyDataSetChanged();
    }


    public interface AddFavouritetoAllProduct{
        void addToFavouriteAllProduct(String product_id, Boolean isclick, int position);
    }

    public interface PassValue_ProductDeatlis{
        void passvalueProductDetail(int pos, int is_fav, String product_image, String product_id,
                                    String product_name, String product_mrp, float product_retail,
                                    String margin, float TotalPrice,
                                    int cartQuanty, int In_Cart_qunty, String min_order_qunaty);
    }

    public interface OpenMOQDialog{
        void openMoqDialog(String productName, String Product_ID, int position, TextView text,
                           String quantity, float price, String margin, RelativeLayout addCart, LinearLayout llQnty,
                           TextView tvCart, TextView tvMargin, TextView tvRetail, TextView tvTotal, TextView tvFree);
    }

    public interface AddToReviewCartList {
        void addtoReviewCartList(String product_id, String quantity, int position);
    }

    public interface UpdateReviewCart {
        void updateReviewCart(String product_id, String update_quantity);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemFreebiesBinding binding;

        public MyViewHolder(@NonNull ItemFreebiesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.ivLike.setOnClickListener(v -> {
                if (!flag) {
                    addtoFavourite.addToFavouriteAllProduct(
                            favourite_list.get(getAdapterPosition()).getProduct_id(),
                            false, getAdapterPosition());
                    flag = true;
                } else {
                    addtoFavourite.addToFavouriteAllProduct(favourite_list.get(getAdapterPosition()).getProduct_id(), true,getAdapterPosition());
                    flag = false;
                }
            });


            /*add cart*/
            binding.rlAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!favourite_list.get(getAdapterPosition()).getMin_order_qty().equals("0") ||
                            !binding.tvMOQ.getText().toString().equals("0") ||
                            !binding.tvTotalPrice.getText().toString().equals("0")
                    ) {
                        binding.rlQuntity.setVisibility(View.VISIBLE);
                        binding.rlAddCart.setVisibility(View.GONE);

                        add_moq = 0;
                        FINAL_PRICE = 0;

                        if (favourite_list.get(getAdapterPosition()).getOther_margin_list().size() != 0) {
                            first_moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                            binding.tvCartQuny.setText(String.valueOf(binding.tvProductMOQ.getText().toString()));

                            addToReviewCartList.addtoReviewCartList(
                                    favourite_list.get(getAdapterPosition()).getProduct_id(),
                                    binding.tvProductMOQ.getText().toString(), getAdapterPosition());

                            if (Integer.parseInt(favourite_list.get(getAdapterPosition()).getIs_free_product()) != 0) {
                                free = Integer.parseInt(binding.tvProductMOQ.getText().toString()) *
                                        Integer.parseInt(favourite_list.get(getAdapterPosition()).getPro_qty_for_free());
                            }

                        } else {
                            first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                            binding.tvCartQuny.setText(String.valueOf(binding.tvMOQ.getText().toString()));

                            addToReviewCartList.addtoReviewCartList(
                                    favourite_list.get(getAdapterPosition()).getProduct_id(),
                                    binding.tvMOQ.getText().toString(), getAdapterPosition());

                            if (Integer.parseInt(favourite_list.get(getAdapterPosition()).getIs_free_product()) != 0) {
                                free = Integer.valueOf(binding.tvMOQ.getText().toString()) *
                                        Integer.parseInt(favourite_list.get(getAdapterPosition()).getPro_qty_for_free());
                            }
                        }

                        if (Integer.parseInt(favourite_list.get(getAdapterPosition()).getIs_free_product()) != 0) {
                            free_product = free / Integer.parseInt(favourite_list.get(getAdapterPosition()).getMin_qty_for_free());
                        }

                        binding.tvFree.setText(String.valueOf(free_product));
                        Price = favourite_list.get(getAdapterPosition()).getRetail_price();
                        STR_PRICE = Price * first_moq;
                        binding.tvTotalPrice.setText(String.valueOf(STR_PRICE));

                }else {
                        Toast.makeText(activity, "Not added in cart", Toast.LENGTH_SHORT).show();
                }
                }
            });


            /*plus cart*/
            binding.rlCartPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favourite_list.get(getAdapterPosition()).getOther_margin_list().size()!=0) {
                        first_moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                        moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                    }else {
                        first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                        moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                    }

                    if (favourite_list.get(getAdapterPosition()).getIs_free_product().equals("1")){
                        int free = first_moq * Integer.parseInt(favourite_list.get(getAdapterPosition()).getPro_qty_for_free());
                        int add_free = free / Integer.parseInt(favourite_list.get(getAdapterPosition()).getMin_qty_for_free());

                        binding.tvFree.setText(String.valueOf(Integer.valueOf(binding.tvFree.getText().toString()) + add_free));
                    }

                    int a = Integer.valueOf(binding.tvCartQuny.getText().toString());

                    float b = Float.parseFloat(binding.tvTotalPrice.getText().toString());
                    float c = Float.parseFloat(binding.tvReatil.getText().toString());

                    binding.tvCartQuny.setText(String.valueOf(first_moq + a));

                    STR = c * moq;
                    FINAL_PRICE = b + STR ;

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    updateReviewCart.updateReviewCart(
                            favourite_list.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString());
                }
            });

            /*minus cart*/
            binding.rlCartMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favourite_list.get(getAdapterPosition()).getOther_margin_list().size()!=0) {
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
                        if (favourite_list.get(getAdapterPosition()).getIs_free_product().equals("1")){
                            int free = first_moq * Integer.parseInt(favourite_list.get(getAdapterPosition()).getPro_qty_for_free());
                            int add_free = free / Integer.parseInt(favourite_list.get(getAdapterPosition()).getMin_qty_for_free());

                            binding.tvFree.setText(String.valueOf(Integer.valueOf(binding.tvFree.getText().toString()) - add_free));
                        }

                    } else {
                        binding.rlAddCart.setVisibility(View.VISIBLE);
                        binding.rlQuntity.setVisibility(View.GONE);
                    }

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    updateReviewCart.updateReviewCart(favourite_list.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString());
                }
            });


            /*moq*/
            binding.rlOtherMargin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogMOQ.openMoqDialog(
                            favourite_list.get(getAdapterPosition()).getName(),
                            favourite_list.get(getAdapterPosition()).getProduct_id(),
                            getAdapterPosition(), binding.tvProductMOQ,
                            favourite_list.get(getAdapterPosition()).getMin_order_qty(),
                            favourite_list.get(getAdapterPosition()).getRetail_price(),
                            favourite_list.get(getAdapterPosition()).getMargin(),
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

            binding.consRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favourite_list.get(getAdapterPosition()).getOther_margin_list().size()!=0){

                        passValue_productDeatlis.passvalueProductDetail(
                                getAdapterPosition(),
                                favourite_list.get(getAdapterPosition()).getIs_favorite(),
                                favourite_list.get(getAdapterPosition()).getImage(),
                                favourite_list.get(getAdapterPosition()).getProduct_id(),
                                favourite_list.get(getAdapterPosition()).getName(),
                                favourite_list.get(getAdapterPosition()).getMrp_price(),
                                favourite_list.get(getAdapterPosition()).getRetail_price(),
                                favourite_list.get(getAdapterPosition()).getMargin(),
                                Float.parseFloat(binding.tvTotalPrice.getText().toString()),
                                Integer.valueOf(binding.tvCartQuny.getText().toString()),
                                favourite_list.get(getAdapterPosition()).getIn_cart_qty(),
                                binding.tvProductMOQ.getText().toString());

                    }else {

                        passValue_productDeatlis.passvalueProductDetail(
                                getAdapterPosition(),
                                favourite_list.get(getAdapterPosition()).getIs_favorite(),
                                favourite_list.get(getAdapterPosition()).getImage(),
                                favourite_list.get(getAdapterPosition()).getProduct_id(),
                                favourite_list.get(getAdapterPosition()).getName(),
                                favourite_list.get(getAdapterPosition()).getMrp_price(),
                                favourite_list.get(getAdapterPosition()).getRetail_price(),
                                favourite_list.get(getAdapterPosition()).getMargin(),
                                Float.parseFloat(binding.tvTotalPrice.getText().toString()),
                                Integer.valueOf(binding.tvCartQuny.getText().toString()),
                                favourite_list.get(getAdapterPosition()).getIn_cart_qty(),
                                favourite_list.get(getAdapterPosition()).getMin_order_qty());
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public RvBrandCompanyProductADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFreebiesBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_freebies, parent, false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvBrandCompanyProductADP.MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(favourite_list.get(position).getThumb_image())
                .into(holder.binding.ivImage);

        holder.binding.tvCateName.setText(favourite_list.get(position).getName());
        holder.binding.tvMRP.setText(String.valueOf(favourite_list.get(position).getMrp_price()));
        holder.binding.tvReatil.setText(String.valueOf(favourite_list.get(position).getRetail_price()));
        holder.binding.tvMOQ.setText(String.valueOf(favourite_list.get(position).getMin_order_qty()));
        holder.binding.tvMargin.setText(favourite_list.get(position).getMargin());

        if (favourite_list.get(position).getIs_favorite() == 1) {
            holder.binding.ivLike.setImageResource(R.drawable.ic_fav_green);
        } else {
            holder.binding.ivLike.setImageResource(R.drawable.ic_fav);
        }

        if (favourite_list.get(position).getOther_margin_list().size()==0){
            holder.binding.llNoOtherMargin.setVisibility(View.VISIBLE);
            holder.binding.rlOtherMargin.setVisibility(View.GONE);

        }else {
            holder.binding.llNoOtherMargin.setVisibility(View.GONE);
            holder.binding.rlOtherMargin.setVisibility(View.VISIBLE);
        }

        if (favourite_list.get(position).getIs_free_product().equals("1")) {
            holder.binding.bottomMain.setVisibility(View.VISIBLE);
        } else {
            holder.binding.bottomMain.setVisibility(View.GONE);
        }

        holder.binding.tvProductMOQ.setText(String.valueOf(favourite_list.get(position).getMin_order_qty()));

        Glide.with(activity)
                .load(favourite_list.get(position)
                        .getFree_product_details().getThumb_image())
                .into(holder.binding.ivFreeProduct);

        String freeproduct_main= favourite_list.get(position).getName()+" "+
                favourite_list.get(position).getMrp_price()+" "+"MRP";

        String freeproduct_sec= favourite_list.get(position).getFree_product_details().getName()+" "+
                favourite_list.get(position)
                        .getFree_product_details().getMrp_price()+" "+"MRP";

        String buy_one="Buy"+" "+String.valueOf(favourite_list.get(position).getMin_qty_for_free())+
                " "+"pcs";

        String get_one="Get"+" "+String.valueOf(favourite_list.get(position).getPro_qty_for_free())+
                " "+"Free";

        holder.binding.tvFreeProductName.setText(freeproduct_main);
        holder.binding.tvfreeproductGet.setText(freeproduct_sec);

        holder.binding.tvBuyOne.setText(buy_one);
        holder.binding.tvGetOne.setText(get_one);

        if (favourite_list.get(position).getIn_cart_qty() == 0) {
            holder.binding.rlAddCart.setVisibility(View.VISIBLE);
            holder.binding.rlQuntity.setVisibility(View.GONE);
            holder.binding.tvTotalPrice.setText("0");

        } else {
            holder.binding.rlAddCart.setVisibility(View.GONE);
            holder.binding.rlQuntity.setVisibility(View.VISIBLE);

            holder.binding.tvCartQuny.setText(String.valueOf(favourite_list.get(position).getIn_cart_qty()));

            float total_price = Float.parseFloat(holder.binding.tvReatil.getText().toString()) *
                    Integer.valueOf(holder.binding.tvCartQuny.getText().toString());
            holder.binding.tvTotalPrice.setText(String.valueOf(total_price));

            if (favourite_list.get(position).getIs_free_product().equals("1")) {
                int free = Integer.valueOf(holder.binding.tvCartQuny.getText().toString()) *
                        Integer.parseInt(favourite_list.get(position).getPro_qty_for_free());
                int free_product = free / Integer.parseInt(favourite_list.get(position).getMin_qty_for_free());

                holder.binding.tvFree.setText(String.valueOf(free_product));

            } else {
                holder.binding.tvFree.setText("0");
            }
        }
    }

    @Override
    public int getItemCount() {
        return favourite_list.size();
    }
}
