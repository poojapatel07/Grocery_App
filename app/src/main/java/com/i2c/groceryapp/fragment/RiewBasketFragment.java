package com.i2c.groceryapp.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.OrderSummaryActivity;
import com.i2c.groceryapp.adapter.ReviewBasketADP;
import com.i2c.groceryapp.databinding.FragmentRiewBasketBinding;
import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.ClearCart;
import com.i2c.groceryapp.model.FavUnFavModel;
import com.i2c.groceryapp.model.ReviewCartModel;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.CommonUtils;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RiewBasketFragment extends Fragment implements  
        ReviewBasketADP.AddFavouritetoAllProduct,
        ReviewBasketADP.UpdateReviewCart{
    FragmentRiewBasketBinding binding;
    private ReviewBasketADP adp;
    private ArrayList<ReviewCartModel>arrayList = new ArrayList<>();
    private SessionManager sessionManager;
    int SUM_CART_ITEMS;
    int CART_QUANTY;
    float FINAL_PRICE;
    float Multiple_Price;

    public RiewBasketFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_riew_basket,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpContorls(view);
    }

    private void setUpContorls(View view) {
        sessionManager = new SessionManager(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvAllReviewBasket.setLayoutManager(linearLayoutManager);

        callAllCartReviewAPI(true);

        binding.tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog();
            }
        });

        binding.constContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderSummaryActivity.class);
                intent.putExtra(Constant.GRAND_TOTAL_AMOUNT, FINAL_PRICE);
                intent.putExtra(Constant.TOTAL_ITEM, SUM_CART_ITEMS);
                startActivity(intent);
            }
        });
    }

    private void callDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to clear the Cart?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        callAllClearCartAPI();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(getResources().getColor(R.color.green));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getResources().getColor(R.color.green));
    }

    private void callAllClearCartAPI() {
        if(!CommonUtils.isInternetOn(getActivity())){
            CommonUtils.showToast(getActivity(),getResources().getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(getActivity());
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ClearCart> callAPI =
                apiInterface.clear_cart(sessionManager.getStringValue(Constant.API_TOKEN));
        callAPI.enqueue(new Callback<ClearCart>() {
            @Override
            public void onResponse(Call<ClearCart> call, Response<ClearCart> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        arrayList.clear();
                        adp.notifyDataSetChanged();
                        CommonUtils.showToast(getActivity(),response.body().getMessage());
                        callAllCartReviewAPI(true);

                    }else if(response.body().getSuccess().equals("0")){

                    }else {

                    }
                }else {

                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ClearCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
            }
        });

    }

    private void callAllCartReviewAPI(Boolean isFirst) {
        if(!CommonUtils.isInternetOn(getActivity())){
            CommonUtils.showToast(getActivity(), getResources().getString(R.string.check_internet));
            return;
        }

        CommonUtils.showCustomLoader(getActivity());
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<ListResponse<ReviewCartModel>> callAPI = apiInterface.cart_list(
                sessionManager.getStringValue(Constant.API_TOKEN));

        callAPI.enqueue(new Callback<ListResponse<ReviewCartModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ListResponse<ReviewCartModel>> call, Response<ListResponse<ReviewCartModel>> response) {
                Log.e("TAG", "onResponse: CALLED::::"+new Gson().toJson(response.body()));
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){

                        arrayList.clear();
                        arrayList.addAll(response.body().getData());

                        if(arrayList.size()!=0){
                            binding.tvNoData.setVisibility(View.GONE);
                            binding.rvAllReviewBasket.setVisibility(View.VISIBLE);
                            binding.tvClear.setVisibility(View.VISIBLE);
                            binding.constBottom.setVisibility(View.VISIBLE);

                            adp = new ReviewBasketADP(getActivity(), arrayList, 
                                    RiewBasketFragment.this,
                                    RiewBasketFragment.this);
                            binding.rvAllReviewBasket.setAdapter(adp);

                        }else {
                            binding.rvAllReviewBasket.setVisibility(View.GONE);
                            binding.tvNoData.setVisibility(View.VISIBLE);
                            binding.tvClear.setVisibility(View.GONE);
                            binding.constBottom.setVisibility(View.GONE);
                        }

                        for (int i=0; i<arrayList.size(); i++){
                            SUM_CART_ITEMS = SUM_CART_ITEMS + Integer.parseInt(arrayList.get(i).getQty());
                            binding.tvTotalItem.setText("Total Item : "+String.valueOf(SUM_CART_ITEMS));

                            float retail_price = arrayList.get(i).getProduct_details().getRetail_price();
                            int cart_qnt = Integer.parseInt(arrayList.get(i).getQty());

                            Multiple_Price = retail_price * cart_qnt;
                            FINAL_PRICE = FINAL_PRICE + Multiple_Price;

                            binding.tvFinalPrice.setText("\u20B9 "+String.valueOf(FINAL_PRICE));
                        }


                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
                    }else {

                    }

                }else if(response.code()==404){
                    binding.rvAllReviewBasket.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                    binding.tvClear.setVisibility(View.GONE);
                    binding.constBottom.setVisibility(View.GONE);
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<ReviewCartModel>> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(getActivity(), t.getMessage());
            }
        });
    }


    @Override
    public void addToFavouriteAllProduct(String product_id, Boolean isclick, int position) {
        if (isclick) {
            callAddToFavouriteAPI(product_id, position);
        } else {
            callRemoveFavouriteAPI(product_id, position);
        }
    }

    private void callAddToFavouriteAPI(String product_id, int position) {
        if (!CommonUtils.isInternetOn(getActivity())) {
            CommonUtils.showToast(getActivity(), getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(getActivity());
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<FavUnFavModel> callApi = apiInterface.add_favourite(
                sessionManager.getStringValue(Constant.API_TOKEN), product_id);

        callApi.enqueue(new Callback<FavUnFavModel>() {
            @Override
            public void onResponse(Call<FavUnFavModel> call, Response<FavUnFavModel> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
                    }else {
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
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
        if (!CommonUtils.isInternetOn(getActivity())) {
            CommonUtils.showToast(getActivity(), getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(getActivity());
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<FavUnFavModel> callApi = apiInterface.remove_favourite(
                sessionManager.getStringValue(Constant.API_TOKEN), product_id);

        callApi.enqueue(new Callback<FavUnFavModel>() {
            @Override
            public void onResponse(Call<FavUnFavModel> call, Response<FavUnFavModel> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
                    }else {
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
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


    @SuppressLint("SetTextI18n")
    @Override
    public void updateReviewCart(String product_id, String update_quantity, int cart_quanty,
                                 float add_price,
                                 boolean cart_bool, String margin_ID) {
        if(cart_bool){
            SUM_CART_ITEMS = SUM_CART_ITEMS + CART_QUANTY;
            FINAL_PRICE = FINAL_PRICE +add_price;

        }else {
            SUM_CART_ITEMS = SUM_CART_ITEMS - CART_QUANTY;
            FINAL_PRICE = FINAL_PRICE - add_price;
        }

        binding.tvTotalItem.setText("Total Item : "+String.valueOf(SUM_CART_ITEMS));
        binding.tvFinalPrice.setText("\u20B9 "+String.valueOf(FINAL_PRICE));

        callUPdateCart(product_id, update_quantity, margin_ID);
    }

    private void callUPdateCart(String update_pro_id, String product_quantity, String margin_id) {
        if (!CommonUtils.isInternetOn(getActivity())) {
            CommonUtils.showToast(getActivity(), getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(getActivity());
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.updateToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                update_pro_id, product_quantity, margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        CommonUtils.showToast(getActivity(), response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
                    } else {
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
                    }
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(getActivity(), t.getMessage());
            }
        });
    }

}