package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvAllCategoryADP;
import com.i2c.groceryapp.adapter.RvFavouruteMOQListADP;
import com.i2c.groceryapp.adapter.RvMyFavouriteADP;
import com.i2c.groceryapp.adapter.RvNotificationADP;
import com.i2c.groceryapp.adapter.RvTodaysSpecialMOQListADP;
import com.i2c.groceryapp.databinding.ActivityMyFavouriteBinding;
import com.i2c.groceryapp.fragment.HomeFragment;
import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.Category;
import com.i2c.groceryapp.model.FavUnFavModel;
import com.i2c.groceryapp.model.FavouriteModel;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.CommonUtils;
import com.i2c.groceryapp.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFavouriteActivity extends BaseActivity implements
        RvMyFavouriteADP.RemoveFavouriteList,
        RvMyFavouriteADP.AddToReviewCartList, RvMyFavouriteADP.UpdateReviewCart,
        RvMyFavouriteADP.OpenMOQDialog, RvFavouruteMOQListADP.CheckedMOQ,
        RvMyFavouriteADP.PassValue_ProductDeatlis {

    ActivityMyFavouriteBinding binding;
    private RvMyFavouriteADP adp;
    private ArrayList<FavouriteModel> favouriteModels = new ArrayList<>();
    private String MARGIN_ID = "";

    /*moq*/
    private Dialog dialog;
    CheckBox check_box;
    private RelativeLayout rlAdd_Cart;
    private LinearLayout llQunty;
    private TextView tvCARTQunty;
    private TextView TVMARGIN;
    private TextView TVRETAIL;
    private TextView TVTOTALPRICE;
    private TextView TVFREE;
    private int IN_CART_QUNTY = 0;
    RecyclerView rvMOQList;
    private int POSTION;
    private HashMap<Integer, Boolean> map = new HashMap<>();
    private TextView TXT_MOQ;
    RvFavouruteMOQListADP adp_moq;
    private String Other_margin_Qnty = "0";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_favourite);
        setUpControls();
    }

    @Override
    protected void setContent() {}

    @Override
    protected void onResume() {
        callFavouriteListAPI(true);
        super.onResume();
    }

    private void setUpControls() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.rvMyFavourite.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvMyFavourite.setLayoutManager(manager);
    }


    private void callFavouriteListAPI(Boolean isFirst) {
        Log.e("TAG", "callFavouriteListAPI: TOKEN:::"
                +sessionManager.getStringValue(Constant.API_TOKEN));
        if (!CommonUtils.isInternetOn(this)) {
            CommonUtils.showToast(this, getString(R.string.check_internet));
            return;
        }
        if(isFirst) {
            showCustomLoader(this);
        }
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<FavouriteModel>> callAPI = apiInterface.favourite_list(
                sessionManager.getStringValue(Constant.API_TOKEN));

        callAPI.enqueue(new Callback<ListResponse<FavouriteModel>>() {
            @Override
            public void onResponse(Call<ListResponse<FavouriteModel>> call, Response<ListResponse<FavouriteModel>> response) {
                Log.e("TAG", "onResponse: FAV::::"+new Gson().toJson(response.body()));
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        favouriteModels.clear();
                        favouriteModels.addAll(response.body().getData());

                        if (favouriteModels.size() != 0) {
                            binding.rvMyFavourite.setVisibility(View.VISIBLE);
                            binding.tvNoData.setVisibility(View.GONE);

                            adp = new RvMyFavouriteADP(MyFavouriteActivity.this,
                                    favouriteModels,
                                    MyFavouriteActivity.this,
                                    MyFavouriteActivity.this,
                                    MyFavouriteActivity.this,
                                    MyFavouriteActivity.this,
                                    MyFavouriteActivity.this);
                            binding.rvMyFavourite.setAdapter(adp);
                            adp.notifyDataSetChanged();
                        }

                    }else if(response.body().getSuccess().equals("0")){
                        binding.rvMyFavourite.setVisibility(View.GONE);
                        binding.tvNoData.setVisibility(View.VISIBLE);
                    }else{

                    }
                }else {
                    binding.rvMyFavourite.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<FavouriteModel>> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }


    @Override
    public void onRemoveFavourite(String position) {
        callRemoveFavouriteAPI(position);
    }

    private void callRemoveFavouriteAPI(String product_id) {
        if (!CommonUtils.isInternetOn(this)) {
            CommonUtils.showToast(this, getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<FavUnFavModel> callApi = apiInterface.remove_favourite(
                sessionManager.getStringValue(Constant.API_TOKEN), product_id);

        callApi.enqueue(new Callback<FavUnFavModel>() {
            @Override
            public void onResponse(Call<FavUnFavModel> call, Response<FavUnFavModel> response) {
                Log.e("TAG", "onResponse: REMOVE:::::"+new Gson().toJson(response.body()));

                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());
                        callFavouriteListAPI(false);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());
                    }
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<FavUnFavModel> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
            }
        });
    }


    @Override
    public void addtoReviewCartList(String product_id, String quantity, int position) {
        callAddToReviewCartAPI(product_id, quantity, MARGIN_ID, position);
    }

    private void callAddToReviewCartAPI(String cart_product_id, String quantity,
                                        String margin_id, int position) {
        if (!CommonUtils.isInternetOn(this)) {
            CommonUtils.showToast(this, getString(R.string.check_internet));
            return;
        }

        CommonUtils.showCustomLoader(this);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.addToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                cart_product_id, quantity, margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    Log.e("TAG", "onResponse: CALLED:::::" + new Gson().toJson(response.body()));
                    if (response.body().getSuccess().equals("1")) {
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());
                        adp.updateCart(position, quantity);

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(MyFavouriteActivity.this, "Product is not added in cart");
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(MyFavouriteActivity.this, t.getMessage());
            }
        });
    }


    @Override
    public void updateReviewCart(String product_id, String product_quantity) {
        callUPdateCart(product_id, product_quantity, MARGIN_ID);
    }
    
    private void callUPdateCart(String update_pro_id, String product_quantity, String margin_id) {
        if (!CommonUtils.isInternetOn(this)) {
            CommonUtils.showToast(this, getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.updateToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                update_pro_id, product_quantity, margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(MyFavouriteActivity.this, response.body().getMessage());
                    }
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(MyFavouriteActivity.this, t.getMessage());
            }
        });
    }


    @Override
    public void openMoqDialog(String ProductName, String PRODUCT_ID, final int position,
                              TextView textview, final String Quantity,
                              float price, String margin,
                              RelativeLayout addCart, LinearLayout llQnty,
                              TextView tvCartQnty, TextView MARGIN, TextView RETAIL,
                              TextView tvTotalPrice, final TextView tvFree) {

        binding.rlMainBG.setVisibility(View.VISIBLE);
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_open_moq);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.85f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = layoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();

        TextView tvProductName;
        TextView tvLLMOQ;
        TextView tvPrice;
        TextView tvMargin;

        tvLLMOQ = dialog.findViewById(R.id.tvLLMOQ);
        tvPrice = dialog.findViewById(R.id.tvPrice);
        tvMargin = dialog.findViewById(R.id.tvMargin);
        check_box = dialog.findViewById(R.id.check_box);

        tvProductName = dialog.findViewById(R.id.tvProductName);
        tvProductName.setText(ProductName);

        tvLLMOQ.setText(String.valueOf(Quantity));
        tvPrice.setText(String.valueOf(price));
        tvMargin.setText(margin);

        rvMOQList = dialog.findViewById(R.id.rvMOQList);
        rvMOQList.setHasFixedSize(false);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvMOQList.setLayoutManager(manager);

        POSTION = position;
        TVMARGIN = MARGIN;
        TVRETAIL = RETAIL;
        TVTOTALPRICE = tvTotalPrice;
        TVFREE =tvFree;

        check_box.setChecked(true);

        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < favouriteModels.size(); i++) {
                        map.put(i, false);
                    }
                    TXT_MOQ.setText(String.valueOf(Quantity));
                    TVMARGIN.setText(String.valueOf(favouriteModels.get(position).getProduct_details().getMargin()));
                    TVMARGIN.setText(String.valueOf(favouriteModels.get(position).getProduct_details().getRetail_price()));

                    MARGIN_ID = " ";

                    if (!favouriteModels.get(position).getIn_cart_qty().equals("0")) {
                        rlAdd_Cart.setVisibility(View.GONE);
                        llQunty.setVisibility(View.VISIBLE);

                        IN_CART_QUNTY = Integer.parseInt(favouriteModels.get(position).getIn_cart_qty());
                        tvCARTQunty.setText(String.valueOf(favouriteModels.get(position).getIn_cart_qty()));

                        TVRETAIL.setText(String.valueOf(favouriteModels.get(position).getProduct_details().getRetail_price()));
                        TVTOTALPRICE.setText(String.valueOf(IN_CART_QUNTY * Float.parseFloat(TVRETAIL.getText().toString())));

                        if (!favouriteModels.get(POSTION).getProduct_details().getIs_free_product().equals("0")) {
                            int buy_one = Integer.parseInt(favouriteModels.get(POSTION).getProduct_details().getMin_qty_for_free());
                            int get_one = Integer.parseInt(favouriteModels.get(POSTION).getProduct_details().getPro_qty_for_free());
                            int free_product = Integer.valueOf(tvCARTQunty.getText().toString()) * get_one;
                            int devide = free_product / buy_one;
                            TVFREE.setText(String.valueOf(devide));
                        }

                    } else {
                        rlAdd_Cart.setVisibility(View.VISIBLE);
                        llQunty.setVisibility(View.GONE);
                        TVTOTALPRICE.setText("0");
                        TVFREE.setText("0");
                    }

                    adp_moq = new RvFavouruteMOQListADP(MyFavouriteActivity.this,
                            favouriteModels.get(POSTION).getProduct_details().getOther_margin_list(),
                            map, MyFavouriteActivity.this);
                    rvMOQList.setAdapter(adp_moq);
                }
            }
        });

        if (map.size() == 0) {
            for (int i = 0; i < favouriteModels.size(); i++) {
                map.put(i, false);
            }
        }

        adp_moq = new RvFavouruteMOQListADP(this,
                favouriteModels.get(POSTION).getProduct_details().getOther_margin_list(), map,
                MyFavouriteActivity.this);
        rvMOQList.setAdapter(adp_moq);

        TXT_MOQ = textview;
        rlAdd_Cart= addCart;
        llQunty = llQnty;
        tvCARTQunty= tvCartQnty;

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.rlMainBG.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void CheckedMOQ(int pos, String MOQ, String margin_id, String other_cart_qnty,
                           String MARGIN, String price) {
        Other_margin_Qnty = other_cart_qnty;

        check_box.setChecked(false);
        TVMARGIN.setText(MARGIN);
        TVRETAIL.setText(String.valueOf(price));

        MARGIN_ID = String.valueOf(margin_id);

        if (!other_cart_qnty.equals("0")) {
            rlAdd_Cart.setVisibility(View.GONE);
            llQunty.setVisibility(View.VISIBLE);

            tvCARTQunty.setText(other_cart_qnty);

            TVTOTALPRICE.setText(String.valueOf(Integer.parseInt(price) *
                    Integer.parseInt(other_cart_qnty)));

            if (!favouriteModels.get(POSTION).getProduct_details().getIs_free_product().equals("0")) {
                int buy_one = Integer.parseInt(favouriteModels.get(POSTION).getProduct_details().getMin_qty_for_free());
                int get_one = Integer.parseInt(favouriteModels.get(POSTION).getProduct_details().getPro_qty_for_free());
                int free_product = Integer.parseInt(other_cart_qnty) * get_one;
                int devide = free_product / buy_one;
                TVFREE.setText(String.valueOf(devide));
            }

        } else {
            rlAdd_Cart.setVisibility(View.VISIBLE);
            llQunty.setVisibility(View.GONE);

            TVTOTALPRICE.setText("0");
            TVFREE.setText("0");
        }

        TXT_MOQ.setText(String.valueOf(MOQ));
        map.clear();

        for (int i = 0; i < favouriteModels.get(POSTION).getProduct_details().getOther_margin_list().size(); i++) {
            if (i == pos) {
                map.put(i, true);
            } else {
                map.put(i, false);
            }
        }

        adp_moq = new RvFavouruteMOQListADP(this,
                favouriteModels.get(POSTION).getProduct_details().getOther_margin_list(), map,
                MyFavouriteActivity.this);
        rvMOQList.setAdapter(adp_moq);

    }

    @Override
    public void passvalueProductDetail(int product_pos, String product_image, String product_id,
                                       String product_name, String product_mrp, float product_retail,
                                       String margin, float TotalPrice,
                                       int cartQuanty, String In_Cart_qunty, String min_order_qunaty) {

        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(Constant.PRODUCT_IMAGE, product_image);
        intent.putExtra(Constant.PRODUCT_ID, product_id);
        intent.putExtra(Constant.PRODUCT_NAME, product_name);
        intent.putExtra(Constant.PRODUCT_MRP, product_mrp);
        intent.putExtra(Constant.PRODUCT_IN_CART_QNTY, cartQuanty);
        intent.putExtra(Constant.IS_CART, In_Cart_qunty);
        intent.putExtra(Constant.PRODUCT_MOQ, min_order_qunaty);
        intent.putExtra(Constant.PRODUCT_OTHER_MARGIN, Other_margin_Qnty);
        intent.putExtra(Constant.PRODUCT_ISFAVOURITE, 1);

        Log.e("TAG", "passvalueProductDetail: Other_margin_Qnty:::::::::" + Other_margin_Qnty);
        if (Other_margin_Qnty.equals("0")) {
            intent.putExtra(Constant.PRODUCT_TOTAL_PRICE, TotalPrice);
            intent.putExtra(Constant.PRODUCT_RETAIL_PRICE, product_retail);
            intent.putExtra(Constant.PRODUCT_MARGIN, margin);
        } else {
            intent.putExtra(Constant.PRODUCT_TOTAL_PRICE, Float.parseFloat(TVTOTALPRICE.getText().toString()));
            intent.putExtra(Constant.PRODUCT_RETAIL_PRICE, Float.parseFloat(TVRETAIL.getText().toString()));
            intent.putExtra(Constant.PRODUCT_MARGIN, TVMARGIN.getText().toString());
        }

        if (favouriteModels.get(product_pos).getProduct_details()
                .getOther_margin_list().size() != 0) {
            intent.putExtra(Constant.PRODUCT_MARGIN_ID, MARGIN_ID);
        } else {
            intent.putExtra(Constant.PRODUCT_MARGIN_ID, " ");
        }
        startActivity(intent);
    }
}