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
import com.i2c.groceryapp.databinding.ItemFreebiesBinding;
import com.i2c.groceryapp.model.FavouriteModel;

import java.util.ArrayList;


public class RvMyFavouriteADP extends RecyclerView.Adapter<RvMyFavouriteADP.MyViewHolder> {
    private Activity activity;
    private ArrayList<FavouriteModel> favourite_list = new ArrayList<>();
    private RemoveFavouriteList removeFavouriteList;
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


    public RvMyFavouriteADP(Activity activity, ArrayList<FavouriteModel> favourite_list,
                          RemoveFavouriteList removeFavouriteList, AddToReviewCartList addToReviewCartList,
                          UpdateReviewCart updateReviewCart,OpenMOQDialog openDialogMOQ,
                          PassValue_ProductDeatlis passValue_productDeatlis) {
        this.activity = activity;
        this.favourite_list = favourite_list;
        this.removeFavouriteList=removeFavouriteList;
        this.addToReviewCartList = addToReviewCartList;
        this.updateReviewCart = updateReviewCart;
        this.openDialogMOQ = openDialogMOQ;
        this.passValue_productDeatlis = passValue_productDeatlis;
    }


    public interface PassValue_ProductDeatlis{
        void passvalueProductDetail(int pos,String product_image, String product_id,
                                    String product_name, String product_mrp, float product_retail,
                                    String margin, float TotalPrice,
                                    int cartQuanty, String In_Cart_qunty, String min_order_qunaty);
    }

    public interface OpenMOQDialog{
        void openMoqDialog(String productName, String Product_ID, int position, TextView text,
                           String quantity, float price, String margin, RelativeLayout addCart, LinearLayout llQnty,
                           TextView tvCart, TextView tvMargin, TextView tvRetail, TextView tvTotal, TextView tvFree);
    }

    public interface RemoveFavouriteList{
        void onRemoveFavourite(String position);
    }

    public interface AddToReviewCartList {
        void addtoReviewCartList(String product_id, String quantity, int pos);
    }

    public interface UpdateReviewCart {
        void updateReviewCart(String product_id, String update_quantity);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
         ItemFreebiesBinding binding;

        public MyViewHolder(@NonNull ItemFreebiesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.ivLike.setImageResource(R.drawable.ic_fav_green);
            binding.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFavouriteList.onRemoveFavourite(favourite_list.get(getAdapterPosition()).getProduct_id());
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

                    if (favourite_list.get(getAdapterPosition()).getProduct_details().getOther_margin_list().size()!=0) {
                        first_moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                        binding.tvCartQuny.setText(String.valueOf(binding.tvProductMOQ.getText().toString()));

                        addToReviewCartList.addtoReviewCartList(
                                favourite_list.get(getAdapterPosition()).getProduct_id(),
                                binding.tvProductMOQ.getText().toString(),getAdapterPosition());

                        if (Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getIs_free_product())!=0) {
                            free = Integer.parseInt(binding.tvProductMOQ.getText().toString()) *
                                    Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getPro_qty_for_free());
                        }

                    }else {
                        first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                        binding.tvCartQuny.setText(String.valueOf(binding.tvMOQ.getText().toString()));

                        addToReviewCartList.addtoReviewCartList(
                                favourite_list.get(getAdapterPosition()).getProduct_id(),
                                binding.tvMOQ.getText().toString(), getAdapterPosition());

                        if (Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getIs_free_product())!=0) {
                            free = Integer.valueOf(binding.tvMOQ.getText().toString()) *
                                    Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getPro_qty_for_free());
                        }
                    }

                    if (Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getIs_free_product())!=0) {
                        free_product = free / Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getMin_qty_for_free());
                    }

                    binding.tvFree.setText(String.valueOf(free_product));
                    Price = favourite_list.get(getAdapterPosition()).getProduct_details().getRetail_price();
                    STR_PRICE = Price*first_moq;
                    binding.tvTotalPrice.setText(String.valueOf(STR_PRICE));
                }
            });


            /*plus cart*/
            binding.rlCartPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favourite_list.get(getAdapterPosition()).getProduct_details().getOther_margin_list().size()!=0) {
                        first_moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                        moq = Integer.valueOf(binding.tvProductMOQ.getText().toString());
                    }else {
                        first_moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                        moq = Integer.valueOf(binding.tvMOQ.getText().toString());
                    }

                    if (favourite_list.get(getAdapterPosition()).getProduct_details().getIs_free_product().equals("1")){
                        int free = first_moq * Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getPro_qty_for_free());
                        int add_free = free / Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getMin_qty_for_free());

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

//                    Utility.BADGE_PRICE = Utility.BADGE_PRICE + STR;
//                    HomeActivity.showBadge(activity, HomeActivity.bottomNavigationView, R.id.nav_Checkout,
//                            Utility.BADGE_PRICE);
                }
            });

            /*minus cart*/
            binding.rlCartMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favourite_list.get(getAdapterPosition()).getProduct_details().getOther_margin_list().size()!=0) {
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

                        if (favourite_list.get(getAdapterPosition()).getProduct_details().getIs_free_product().equals("1")){
                            int free = first_moq * Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getPro_qty_for_free());
                            int add_free = free / Integer.parseInt(favourite_list.get(getAdapterPosition()).getProduct_details().getMin_qty_for_free());

                            binding.tvFree.setText(String.valueOf(Integer.valueOf(binding.tvFree.getText().toString()) - add_free));
                        }

                    } else {
                        binding.rlAddCart.setVisibility(View.VISIBLE);
                        binding.rlQuntity.setVisibility(View.GONE);
                    }

                    binding.tvTotalPrice.setText(String.valueOf(FINAL_PRICE));

                    updateReviewCart.updateReviewCart(favourite_list.get(getAdapterPosition()).getProduct_id(),
                            binding.tvCartQuny.getText().toString());

//                    HomeActivity.showBadge(activity, HomeActivity.bottomNavigationView, R.id.nav_Checkout,
//                            Utility.BADGE_PRICE);
                }
            });


            /*moq*/
            binding.rlOtherMargin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogMOQ.openMoqDialog(
                            favourite_list.get(getAdapterPosition()).getProduct_details().getName(),
                            favourite_list.get(getAdapterPosition()).getProduct_id(),
                            getAdapterPosition(), binding.tvProductMOQ,
                            favourite_list.get(getAdapterPosition()).getProduct_details().getMin_order_qty(),
                            favourite_list.get(getAdapterPosition()).getProduct_details().getRetail_price(),
                            favourite_list.get(getAdapterPosition()).getProduct_details().getMargin(),
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

            binding.topMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favourite_list.get(getAdapterPosition()).getProduct_details().getOther_margin_list().size()!=0){

                        passValue_productDeatlis.passvalueProductDetail(
                                getAdapterPosition(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getImage(),
                                favourite_list.get(getAdapterPosition()).getProduct_id(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getName(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getMrp_price(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getRetail_price(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getMargin(),
                                Float.parseFloat(binding.tvTotalPrice.getText().toString()),
                                Integer.valueOf(binding.tvCartQuny.getText().toString()),
                                favourite_list.get(getAdapterPosition()).getIn_cart_qty(),
                                binding.tvProductMOQ.getText().toString());

                    }else {

                        passValue_productDeatlis.passvalueProductDetail(
                                getAdapterPosition(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getImage(),
                                favourite_list.get(getAdapterPosition()).getProduct_id(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getName(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getMrp_price(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getRetail_price(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getMargin(),
                                Float.parseFloat(binding.tvTotalPrice.getText().toString()),
                                Integer.valueOf(binding.tvCartQuny.getText().toString()),
                                favourite_list.get(getAdapterPosition()).getIn_cart_qty(),
                                favourite_list.get(getAdapterPosition()).getProduct_details().getMin_order_qty());
                    }
                }
            });
        }
    }

    public void updateCart(int adapterPosition, String quantity) {
        Log.e("TAG", "updateIsFavData: position::::"+adapterPosition);
        favourite_list.get(adapterPosition).setIn_cart_qty(quantity);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RvMyFavouriteADP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFreebiesBinding rvMyReferralItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_freebies, parent,false);
        return new MyViewHolder(rvMyReferralItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvMyFavouriteADP.MyViewHolder holder, int position) {
        Glide.with(activity)
                .load(favourite_list.get(position).getProduct_details().getThumb_image())
                .into(holder.binding.ivImage);

        holder.binding.tvCateName.setText(favourite_list.get(position).getProduct_details().getName());
        holder.binding.tvMRP.setText(String.valueOf(favourite_list.get(position).getProduct_details().getMrp_price()));
        holder.binding.tvReatil.setText(String.valueOf(favourite_list.get(position).getProduct_details().getRetail_price()));
        holder.binding.tvMOQ.setText(String.valueOf(favourite_list.get(position).getProduct_details().getMin_order_qty()));
        holder.binding.tvMargin.setText(favourite_list.get(position).getProduct_details().getMargin());


        if (favourite_list.get(position).getProduct_details().getOther_margin_list().size()==0){
            holder.binding.llNoOtherMargin.setVisibility(View.VISIBLE);
            holder.binding.rlOtherMargin.setVisibility(View.GONE);

        }else {
            holder.binding.llNoOtherMargin.setVisibility(View.GONE);
            holder.binding.rlOtherMargin.setVisibility(View.VISIBLE);
        }

        if (favourite_list.get(position).getProduct_details().getIs_free_product().equals("1")) {
            holder.binding.bottomMain.setVisibility(View.VISIBLE);
        } else {
            holder.binding.bottomMain.setVisibility(View.GONE);
        }

        holder.binding.tvProductMOQ.setText(String.valueOf(favourite_list.get(position).getProduct_details().getMin_order_qty()));

        Glide.with(activity)
                .load(favourite_list.get(position).getProduct_details()
                        .getFree_product_details().getThumb_image())
                .into(holder.binding.ivFreeProduct);

        String freeproduct_main= favourite_list.get(position).getProduct_details().getName()+" "+
                favourite_list.get(position).getProduct_details().getMrp_price()+" "+"MRP";

        String freeproduct_sec= favourite_list.get(position).getProduct_details().getFree_product_details().getName()+" "+
                favourite_list.get(position).getProduct_details()
                        .getFree_product_details().getMrp_price()+" "+"MRP";

        String buy_one="Buy"+" "+String.valueOf(favourite_list.get(position).getProduct_details().getMin_qty_for_free())+
                " "+"pcs";

        String get_one="Get"+" "+String.valueOf(favourite_list.get(position).getProduct_details().getPro_qty_for_free())+
                " "+"Free";

        holder.binding.tvFreeProductName.setText(freeproduct_main);
        holder.binding.tvfreeproductGet.setText(freeproduct_sec);

        holder.binding.tvBuyOne.setText(buy_one);
        holder.binding.tvGetOne.setText(get_one);

        if (favourite_list.get(position).getIn_cart_qty() == "0") {
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

            if (favourite_list.get(position).getProduct_details().getIs_free_product().equals("1")) {
                int free = Integer.valueOf(holder.binding.tvCartQuny.getText().toString()) *
                        Integer.parseInt(favourite_list.get(position).getProduct_details().getPro_qty_for_free());
                int free_product = free / Integer.parseInt(favourite_list.get(position).getProduct_details().getMin_qty_for_free());

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
