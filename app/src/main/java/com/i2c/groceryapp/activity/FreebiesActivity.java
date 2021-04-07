package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvFreebiesADP;
import com.i2c.groceryapp.databinding.ActivityFreebiesBinding;
import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.FavUnFavModel;
import com.i2c.groceryapp.model.Todayspecial_list;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.CommonUtils;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.EndlessRecyclerOnScrollListenerNewGrid;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FreebiesActivity extends BaseActivity implements
        RvFreebiesADP.AddtoFavouriteFree, RvFreebiesADP.AddToReviewCartList,
        RvFreebiesADP.UpdateReviewCart {
    ActivityFreebiesBinding binding;
    private RvFreebiesADP adp;
    private int POSITION = 0;
    private ArrayList<Todayspecial_list> freebies_arraylist = new ArrayList<>();
    private String MARGIN_ID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_freebies);
        setUpControls();
    }

    @Override
    protected void setContent() {

    }

    private void setUpControls() {
        binding.rvFreebies.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvFreebies.setLayoutManager(manager);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        callGetCategoryAPI(POSITION, true);

        binding.rvFreebies.setOnScrollListener(new EndlessRecyclerOnScrollListenerNewGrid(manager) {
            @Override
            public void onLoadMore(int paramInt) {
                POSITION += 1;
                callGetCategoryAPI(POSITION, false);
            }
        });
    }


    public void callGetCategoryAPI(int position, Boolean isFirst) {
        Log.e("TAG", "callGetCategoryAPI:POSITION::::"+position);
        if(!isInternetOn(this)){
            showToast(getString(R.string.check_internet));
            return;
        }

        if(isFirst){
            showCustomLoader(this);
        }

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Todayspecial_list>> callAPI = apiInterface
                .freebies_product_list(sessionManager.getStringValue(Constant.API_TOKEN), String.valueOf(position));

        callAPI.enqueue(new Callback<ListResponse<Todayspecial_list>>() {
            @Override
            public void onResponse(Call<ListResponse<Todayspecial_list>> call, Response<ListResponse<Todayspecial_list>> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        if (freebies_arraylist.size() == 0) {
                            freebies_arraylist.clear();
                            freebies_arraylist.addAll(response.body().getData());

                            adp = new RvFreebiesADP(FreebiesActivity.this,
                                    freebies_arraylist, FreebiesActivity.this,
                                    FreebiesActivity.this, FreebiesActivity.this);
                            binding.rvFreebies.setAdapter(adp);

                        } else {
                            freebies_arraylist.addAll(response.body().getData());
                        }
                        adp.notifyDataSetChanged();

                    }else if(response.body().getSuccess().equals("0")){

                    }else {

                    }
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<Todayspecial_list>> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }

    @Override
    public void addToFavouriteFree(String product_id, Boolean isclick, int position) {
        if (isclick) {
            callAddToFavouriteAPI(product_id, position);
        } else {
            callRemoveFavouriteAPI(product_id, position);
        }
    }

    private void callAddToFavouriteAPI(String product_id, int position) {
        if (!CommonUtils.isInternetOn(this)) {
            CommonUtils.showToast(this, getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<FavUnFavModel> callApi = apiInterface.add_favourite(
                sessionManager.getStringValue(Constant.API_TOKEN), product_id);

        callApi.enqueue(new Callback<FavUnFavModel>() {
            @Override
            public void onResponse(Call<FavUnFavModel> call, Response<FavUnFavModel> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
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


    private void callRemoveFavouriteAPI(String product_id, int position) {
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
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
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
    public void addtoReviewCartList(String product_id, String quantity,
                                    int position, RelativeLayout addCart, LinearLayout cartquant) {
        callAddToReviewCartAPI(product_id, quantity, MARGIN_ID);
    }

    @Override
    public void updateReviewCart(String product_id, String update_quantity,int position) {
        callUPdateCart(product_id, update_quantity, MARGIN_ID, position);
    }

    private void callAddToReviewCartAPI(String cart_product_id, String quantity,
                                        String margin_id) {
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
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                        adp.updateCart(POSITION, quantity);

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(FreebiesActivity.this, "Product is not added in cart");
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(FreebiesActivity.this, t.getMessage());
            }
        });
    }

    private void callUPdateCart(String update_pro_id, String product_quantity,
                                String margin_id, int position) {
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
                        CommonUtils.showToast(FreebiesActivity.this,
                                response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(FreebiesActivity.this, response.body().getMessage());
                    }
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(FreebiesActivity.this, t.getMessage());
            }
        });
    }

}