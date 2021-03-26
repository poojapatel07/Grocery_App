package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityProductDetailBinding;
import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.model.FavUnFavModel;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.CommonUtils;
import com.i2c.groceryapp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {
    ActivityProductDetailBinding binding;
    private String ProductName;
    private String ProductImage;
    private String ProductMRP;
    private float ProductRetailPrice;
    private float ProductTotlePrice;
    private String ProductMOQ;
    private String ProductMargin;
    private String Product_ID;
    private int IS_Favourite;
    private int IS_cart_Qnty;
    private int IS_CART;
    float FINAL_PRICE;
    float Quantity;
    private String product_margin_id;
    private String product_other_margin;
    private Boolean product_fav_add;
    private Boolean PRODUCT_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        setUpContols();
    }

    @Override
    protected void setContent() {}

    private void setUpContols() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        product_fav_add = getIntent().getExtras().getBoolean(Constant.PRODUCT_FAVOURITE_ADD);

        product_margin_id = getIntent().getStringExtra(Constant.PRODUCT_MARGIN_ID);
        product_other_margin = getIntent().getStringExtra(Constant.PRODUCT_OTHER_MARGIN);

        ProductName = getIntent().getStringExtra(Constant.PRODUCT_NAME);
        ProductImage = getIntent().getStringExtra(Constant.PRODUCT_IMAGE);
        ProductMRP = getIntent().getStringExtra(Constant.PRODUCT_MRP);
        IS_CART = getIntent().getIntExtra(Constant.IS_CART, 0);
        ProductRetailPrice = getIntent().getFloatExtra(Constant.PRODUCT_RETAIL_PRICE, 0);
        ProductTotlePrice = getIntent().getFloatExtra(Constant.PRODUCT_TOTAL_PRICE, 0);
        ProductMOQ = getIntent().getStringExtra(Constant.PRODUCT_MOQ);
        Product_ID = getIntent().getStringExtra(Constant.PRODUCT_ID);
        IS_Favourite = getIntent().getIntExtra(Constant.PRODUCT_ISFAVOURITE, 0);
        IS_cart_Qnty = getIntent().getIntExtra(Constant.PRODUCT_IN_CART_QNTY, 0);
        ProductMargin = getIntent().getStringExtra(Constant.PRODUCT_MARGIN);

        PRODUCT_FLAG = getIntent().getExtras().getBoolean(Constant.PRODUCT_FLAG);

        Glide.with(this)
                .load(ProductImage)
                .into(binding.ivProductImage);

        binding.tvProductName.setText(ProductName);
        binding.tvMRP.setText(ProductMRP);
        binding.tvRetailPrice.setText(String.valueOf(ProductRetailPrice));
        binding.tvTotlaPrice.setText(String.valueOf(ProductTotlePrice));
        binding.tvMOQ.setText(String.valueOf(ProductMOQ));
        binding.tvMargin.setText(ProductMargin);

        binding.tvCartQuny.setText(String.valueOf(IS_cart_Qnty));

        binding.ivFav.setOnClickListener(this);
        binding.tvAddToCart.setOnClickListener(this);

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        if (IS_Favourite == 0) {
//            binding.tvFavourite.setVisibility(View.VISIBLE);
//            binding.tvUnFavourite.setVisibility(View.GONE);
//        } else {
//            binding.tvFavourite.setVisibility(View.GONE);
//            binding.tvUnFavourite.setVisibility(View.VISIBLE);
//        }


        if (IS_CART==0) {
            binding.tvAddToCart.setVisibility(View.VISIBLE);
            binding.rlQuntity.setVisibility(View.GONE);

        } else {
            binding.tvAddToCart.setVisibility(View.GONE);
            binding.rlQuntity.setVisibility(View.VISIBLE);
        }

        Quantity = ProductRetailPrice * Float.parseFloat(ProductMOQ);

        binding.ivFav.setOnClickListener(this);
        binding.ivUnFav.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivFav:
                break;

            case R.id.ivUnFav:
                callRemoveFavouriteAPI(Product_ID);
                break;

            case R.id.tvAddToCart:
                float STR_PRICE = Float.parseFloat(ProductMOQ) * ProductRetailPrice;

//                Utility.BADGE_PRICE = Utility.BADGE_PRICE + STR_PRICE;
//                HomeActivity.showBadge(this, HomeActivity.bottomNavigationView, R.id.nav_Checkout, Utility.BADGE_PRICE);

                binding.tvAddToCart.setVisibility(View.GONE);
                binding.rlQuntity.setVisibility(View.VISIBLE);

                binding.tvTotlaPrice.setText(String.valueOf(Quantity));
                binding.tvCartQuny.setText(String.valueOf(binding.tvMOQ.getText().toString()));

                callAddToReviewCartAPI();
                break;

            case R.id.rlCartPlus:
                binding.tvCartQuny.setText(String.valueOf(ProductMOQ +
                        Integer.valueOf(binding.tvCartQuny.getText().toString())));

                FINAL_PRICE = Float.parseFloat(binding.tvTotlaPrice.getText().toString()) + Quantity;
                binding.tvTotlaPrice.setText(String.valueOf(FINAL_PRICE));

//                Utility.BADGE_PRICE = Utility.BADGE_PRICE + FINAL_PRICE;
//                HomeActivity.showBadge(this, HomeActivity.bottomNavigationView, R.id.nav_Checkout, Utility.BADGE_PRICE);

                callUPdateCart(Product_ID, binding.tvCartQuny.getText().toString());
                break;

            case R.id.rlCartMinus:
                if (Integer.valueOf(binding.tvCartQuny.getText().toString()) == 0) {
                    binding.tvAddToCart.setVisibility(View.VISIBLE);
                    binding.rlQuntity.setVisibility(View.GONE);

                } else {
                    binding.tvAddToCart.setVisibility(View.GONE);
                    binding.rlQuntity.setVisibility(View.VISIBLE);

                    binding.tvCartQuny.setText(String.valueOf(Integer.valueOf(binding.tvCartQuny.getText().toString()) - Integer.parseInt(ProductMOQ)));
                    FINAL_PRICE = Float.parseFloat(binding.tvTotlaPrice.getText().toString()) - Quantity;
                    binding.tvTotlaPrice.setText(String.valueOf(FINAL_PRICE));

//                    Utility.BADGE_PRICE = Utility.BADGE_PRICE + FINAL_PRICE;
//                    HomeActivity.showBadge(this, HomeActivity.bottomNavigationView, R.id.nav_Checkout, Utility.BADGE_PRICE);
                }

                callUPdateCart(Product_ID, binding.tvCartQuny.getText().toString());
        }
    }

    private void callRemoveFavouriteAPI(String product_id) {
        if (!isInternetOn(this)) {
            showToast(getString(R.string.check_internet));
            return;
        }
        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<FavUnFavModel> callApi = apiInterface.remove_favourite(
                sessionManager.getStringValue(Constant.API_TOKEN), product_id);

        callApi.enqueue(new Callback<FavUnFavModel>() {
            @Override
            public void onResponse(Call<FavUnFavModel> call, Response<FavUnFavModel> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        showToast(response.body().getMessage());

                        binding.ivFav.setVisibility(View.VISIBLE);
                        binding.ivUnFav.setVisibility(View.GONE);

                    }else if(response.body().getSuccess().equals("0")){
                        showToast( response.body().getMessage());
                    }else {
                        showToast(response.body().getMessage());
                    }
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<FavUnFavModel> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }


    private void callAddToReviewCartAPI() {
        if (!isInternetOn(this)) {
            showToast(getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.addToCart(
                sessionManager.getStringValue(Constant.API_TOKEN),
                Product_ID, ProductMOQ, product_margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    Log.e("TAG", "onResponse: CALLED:::::" + new Gson().toJson(response.body()));
                    if (response.body().getSuccess().equals("1")) {
                        showToast(response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                dismissCustomLoader();
                showToast(t.getMessage());
            }
        });
    }


    private void callUPdateCart(String update_pro_id, String product_quantity) {
        if (!isInternetOn(this)) {
            showToast(getString(R.string.check_internet));
            return;
        }
        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.updateToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                update_pro_id, product_quantity, product_margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        showToast(response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                dismissCustomLoader();
                showToast(t.getMessage());
            }
        });
    }



}