package com.i2c.groceryapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.BrandCategoryItemADP;
import com.i2c.groceryapp.adapter.RvSearchMOQListADP;
import com.i2c.groceryapp.adapter.RvSearchProductListADP;
import com.i2c.groceryapp.adapter.RvTodaysSpecialMOQListADP;
import com.i2c.groceryapp.databinding.ActivitySearchBinding;
import com.i2c.groceryapp.fragment.HomeFragment;
import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.Data;
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
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements
        RvSearchProductListADP.OpenMOQDialog,
        RvSearchProductListADP.AddFavouritetoAllProduct,
        RvSearchProductListADP.AddToReviewCartList,
        RvSearchProductListADP.UpdateReviewCart,
        RvSearchMOQListADP.CheckedMOQ,
        RvSearchProductListADP.PassValue_ProductDeatlis {
    
    ActivitySearchBinding binding;
    private RvSearchProductListADP adp;
    private int MAIN_POS = 0;
    private int PAGE_POS = 0;
    private int SORT_BY = 2;
    boolean flag = true;
    private int NEW_SEARCH = 0;
    private Boolean Search_flag = false;
    private Boolean IS_SEARCH = true;
    private int SEARCH_PAGE_POS = 0;
    private LinearLayoutManager manager;
    private ArrayList<Todayspecial_list> allProductList = new ArrayList<>();

    /*sort dialog*/
    private RadioButton radioLowToHigh;
    private RadioButton radioHighToLow;
    private Boolean IsRadioSelected = true;
    private int SORT_POS_LOW = 0;
    private int SORT_POS_HIGH = 0;

    /*moq*/
    private Dialog dialog;
    CheckBox check_box;
    private String MARGIN_ID = "";
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
    RvSearchMOQListADP adp_moq;
    private String Other_margin_Qnty = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setUpControls();
    }

    @Override
    protected void setContent() {}

    private void setUpControls() {
        binding.rvSearchProduct.setHasFixedSize(false);
         manager = new LinearLayoutManager(this);
        binding.rvSearchProduct.setLayoutManager(manager);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.constSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSortByDialog();
            }
        });

        MAIN_POS = 0;
        callAllSearchProductAPI(true,PAGE_POS, "", SORT_BY);


       binding.rvSearchProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (MAIN_POS == 0) {
                    PAGE_POS += 1;
                    callAllSearchProductAPI(false,PAGE_POS, "", SORT_BY);

                } else if (MAIN_POS == 1) {
                    SORT_POS_LOW += 1;
                    callAllSearchProductAPI(false,SORT_POS_LOW, "", SORT_BY);
                    Log.e("TAG", "onScrolled: LOW::::::::" + SORT_POS_LOW);

                } else if (MAIN_POS == 2) {
                    SORT_POS_HIGH += 1;
                    callAllSearchProductAPI(false,SORT_POS_HIGH, "", SORT_BY);
                    Log.e("TAG", "onScrolled: HIGH::::::::" + SORT_POS_HIGH);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        if (IS_SEARCH) {
            binding.rvSearchProduct.setOnScrollListener(new EndlessRecyclerOnScrollListenerNewGrid(manager) {
                @Override
                public void onLoadMore(int paramInt) {
                    if (MAIN_POS == 3) {
                        SEARCH_PAGE_POS += 1;
                        callAllSearchProductAPI(false,SEARCH_PAGE_POS,
                                binding.etSearchProduct.getText().toString(),
                                SORT_BY);
                    }
                }
            });
        }


        binding.etSearchProduct.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    MAIN_POS = 3;
                    flag = true;

                    Search_flag = true;
                    NEW_SEARCH++;
                    callAllSearchProductAPI(true,SEARCH_PAGE_POS,
                            binding.etSearchProduct.getText().toString(), SORT_BY);
                    return true;
                }
                return false;
            }
        });

    }

    private void openSortByDialog() {
        binding.rlMainBG.setVisibility(View.VISIBLE);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_sorting_category);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.85f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = layoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();

        radioLowToHigh = dialog.findViewById(R.id.radioLowToHigh);
        radioHighToLow = dialog.findViewById(R.id.radioHighToLow);

        radioLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsRadioSelected = true;
                radioLowToHigh.setChecked(true);
                radioHighToLow.setChecked(false);
                dialog.dismiss();
                SORT_BY = 2;

                MAIN_POS = 1;
                flag = true;
                SORT_POS_LOW = 0;
                callAllSearchProductAPI(true,SORT_POS_LOW, "", SORT_BY);

                Log.e("TAG", "onClick: START::::::::" + SORT_POS_LOW);
            }
        });

        radioHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsRadioSelected = false;
                radioHighToLow.setChecked(true);
                radioLowToHigh.setChecked(false);
                dialog.dismiss();
                SORT_BY = 3;

                MAIN_POS = 2;
                flag = true;
                SORT_POS_HIGH = 0;
                callAllSearchProductAPI(true,SORT_POS_HIGH, "", SORT_BY);
                Log.e("TAG", "onClick: CLICK:::::" + SORT_POS_HIGH);

            }
        });


        if (IsRadioSelected) {
            radioLowToHigh.setChecked(true);
            radioHighToLow.setChecked(false);
        } else {
            radioLowToHigh.setChecked(false);
            radioHighToLow.setChecked(true);
        }

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.rlMainBG.setVisibility(View.GONE);
            }
        });
    }

    private void callAllSearchProductAPI(Boolean isLoading, int page_pos, String search_product, int sortBy) {
        if(!isInternetOn(this)){
            showToast(getResources().getString(R.string.check_internet));
            return;
        }

        if(isLoading) {
            showCustomLoader(this);
        }

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Todayspecial_list>> callAPI = apiInterface.all_search_product_list(
                sessionManager.getStringValue(Constant.API_TOKEN),
                String.valueOf(page_pos), String.valueOf(sortBy), search_product);

        callAPI.enqueue(new Callback<ListResponse<Todayspecial_list>>() {
            @Override
            public void onResponse(Call<ListResponse<Todayspecial_list>> call, Response<ListResponse<Todayspecial_list>> response) {
                Log.e("TAG", "onResponse:CALLED::::"+new Gson().toJson(response.body()));

                if(response.body()!=null){
                    binding.rvSearchProduct.setVisibility(View.VISIBLE);
                    binding.tvNoData.setVisibility(View.GONE);

                    if(response.body().getSuccess().equals("1")){
                        if (flag) {
                            allProductList.clear();
                            flag = false;
                        }

                        if (allProductList.size() == 0) {
                            allProductList.addAll(response.body().getData());
                            adp = new RvSearchProductListADP(
                                    SearchActivity.this,
                                    allProductList, SearchActivity.this,
                                    SearchActivity.this,
                                    SearchActivity.this,
                                    SearchActivity.this,
                                    SearchActivity.this);
                            binding.rvSearchProduct.setAdapter(adp);
                        } else {
                            allProductList.addAll(response.body().getData());
                        }
                        adp.notifyDataSetChanged();

                        Log.e("TAG", "onResponse: SIZE:::::::::" + allProductList.size());

                        if (Search_flag) {
                            if (NEW_SEARCH > 0) {
                                binding.rvSearchProduct.setOnScrollListener
                                        (new EndlessRecyclerOnScrollListenerNewGrid(manager) {
                                    @Override
                                    public void onLoadMore(int paramInt) {
                                        IS_SEARCH = false;
                                        SEARCH_PAGE_POS += 1;
                                        /*search*/
                                        callAllSearchProductAPI(false,SEARCH_PAGE_POS,
                                                binding.etSearchProduct.getText().toString(),
                                                SORT_BY);
                                    }
                                });
                            }
                        }

                    }else if(response.body().getSuccess().equals("0")){

                    }
                }else if(response.code()==404){
                    binding.rvSearchProduct.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<Todayspecial_list>> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }

    public void openMoqDialog(String ProductName, String PRODUCT_ID, final int position,
                              TextView textview,
                              final String Quantity, float price, String margin, RelativeLayout addCart, LinearLayout llQnty,
                              TextView tvCartQnty, TextView MARGIN, TextView RETAIL,
                              TextView tvTotalPrice, TextView tvFree) {

        binding.rlMainBG.setVisibility(View.VISIBLE);
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_open_moq);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

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
                    for (int i = 0; i < allProductList.size(); i++) {
                        map.put(i, false);
                    }
                    TXT_MOQ.setText(String.valueOf(Quantity));
                    TVMARGIN.setText(String.valueOf(allProductList.get(position).getMargin()));
                    TVMARGIN.setText(String.valueOf(allProductList.get(position).getRetail_price()));

                    MARGIN_ID = " ";

                    if (allProductList.get(position).getIn_cart_qty()!=0) {
                        rlAdd_Cart.setVisibility(View.GONE);
                        llQunty.setVisibility(View.VISIBLE);

                        IN_CART_QUNTY = allProductList.get(position).getIn_cart_qty();
                        tvCARTQunty.setText(String.valueOf(allProductList.get(position).getIn_cart_qty()));

                        TVRETAIL.setText(String.valueOf(allProductList.get(position).getRetail_price()));
                        TVTOTALPRICE.setText(String.valueOf(IN_CART_QUNTY * Float.parseFloat(TVRETAIL.getText().toString())));

                        if (!allProductList.get(POSTION).getIs_free_product().equals("0")) {
                            int buy_one = Integer.parseInt(allProductList.get(POSTION).getMin_qty_for_free());
                            int get_one = Integer.parseInt(allProductList.get(POSTION).getPro_qty_for_free());
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

                    adp_moq = new RvSearchMOQListADP(SearchActivity.this,
                            allProductList.get(POSTION).getOther_margin_list(),
                            map, SearchActivity.this);
                    rvMOQList.setAdapter(adp_moq);
                }
            }
        });

        if (map.size() == 0) {
            for (int i = 0; i < allProductList.size(); i++) {
                map.put(i, false);
            }
        }

        adp_moq = new RvSearchMOQListADP(this,
                allProductList.get(POSTION).getOther_margin_list(), map,
                SearchActivity.this);
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
    public void addToFavouriteAllProduct(String product_id, Boolean isclick, int position) {
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
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(SearchActivity.this,
                            "Not added in favourite");
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
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(SearchActivity.this,
                            "Not remove from favourite");
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
        Log.d("TAG", "addtoReviewCartList returned: " + product_id);
        callAddToReviewCartAPI(product_id, quantity, MARGIN_ID, position);
    }

    private void callAddToReviewCartAPI(String cart_product_id, String quantity,
                                        String margin_id, int position) {
        if (!CommonUtils.isInternetOn(SearchActivity.this)) {
            CommonUtils.showToast(SearchActivity.this, getString(R.string.check_internet));
            return;
        }

        CommonUtils.showCustomLoader(SearchActivity.this);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.addToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                cart_product_id, quantity, margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    Log.e("TAG", "onResponse: CALLED:::::" + new Gson().toJson(response.body()));
                    if (response.body().getSuccess().equals("1")) {
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                        adp.updateCart(position, quantity);
                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(SearchActivity.this, "Product is not added in cart");
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(SearchActivity.this, t.getMessage());
            }
        });
    }

    @Override
    public void updateReviewCart(String product_id, String update_quantity) {
        Log.e("TAG", "updateReviewCart: update:::"+product_id+"   qty:::"+update_quantity);
        callUPdateCart(product_id, update_quantity, MARGIN_ID);
    }

    private void callUPdateCart(String update_pro_id, String product_quantity, String margin_id) {
        if (!CommonUtils.isInternetOn(SearchActivity.this)) {
            CommonUtils.showToast(SearchActivity.this, getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(SearchActivity.this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.updateToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                update_pro_id, product_quantity, margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(SearchActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(SearchActivity.this,
                            "Product is not updated!");
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(SearchActivity.this, t.getMessage());
            }
        });
    }


    @Override
    public void passvalueProductDetail(int pos, String product_image, String product_id,
                                       String product_name, String product_mrp,
                                       float product_retail, String margin, float TotalPrice,
                                       int cartQuanty, int In_Cart_qunty,
                                       String min_order_qunaty, int IS_FAV) {

        Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
        intent.putExtra(Constant.PRODUCT_IMAGE, product_image);
        intent.putExtra(Constant.PRODUCT_ID, product_id);
        intent.putExtra(Constant.PRODUCT_NAME, product_name);
        intent.putExtra(Constant.PRODUCT_MRP, product_mrp);
        intent.putExtra(Constant.PRODUCT_IN_CART_QNTY, cartQuanty);
        intent.putExtra(Constant.IS_CART, In_Cart_qunty);
        intent.putExtra(Constant.PRODUCT_MOQ, min_order_qunaty);
        intent.putExtra(Constant.PRODUCT_OTHER_MARGIN, Other_margin_Qnty);
        intent.putExtra(Constant.PRODUCT_ISFAVOURITE, IS_FAV);

        if (Other_margin_Qnty.equals("0")){
            intent.putExtra(Constant.PRODUCT_TOTAL_PRICE, TotalPrice);
            intent.putExtra(Constant.PRODUCT_RETAIL_PRICE, product_retail);
            intent.putExtra(Constant.PRODUCT_MARGIN, margin);
        }else {
            intent.putExtra(Constant.PRODUCT_TOTAL_PRICE, Float.parseFloat(TVTOTALPRICE.getText().toString()));
            intent.putExtra(Constant.PRODUCT_RETAIL_PRICE, Float.parseFloat(TVRETAIL.getText().toString()));
            intent.putExtra(Constant.PRODUCT_MARGIN, TVMARGIN.getText().toString());
        }

        if (allProductList.get(pos).getOther_margin_list().size() != 0) {
            intent.putExtra(Constant.PRODUCT_MARGIN_ID, MARGIN_ID);
        } else {
            intent.putExtra(Constant.PRODUCT_MARGIN_ID, " ");
        }

        startActivity(intent);

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

            if (!allProductList.get(POSTION).getIs_free_product().equals("0")) {
                int buy_one = Integer.parseInt(allProductList.get(POSTION).getMin_qty_for_free());
                int get_one = Integer.parseInt(allProductList.get(POSTION).getPro_qty_for_free());
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

        for (int i = 0; i < allProductList.get(POSTION).getOther_margin_list().size(); i++) {
            if (i == pos) {
                map.put(i, true);
            } else {
                map.put(i, false);
            }
        }

        adp_moq = new RvSearchMOQListADP(SearchActivity.this,
                allProductList.get(POSTION).getOther_margin_list(), map,
                SearchActivity.this);
        rvMOQList.setAdapter(adp_moq);
    }
}