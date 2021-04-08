package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.BrandCategoryItemADP;
import com.i2c.groceryapp.adapter.RvAllCategoryDetailsADP;
import com.i2c.groceryapp.adapter.RvBrandCompanyItemADP;
import com.i2c.groceryapp.adapter.RvDialogProductListADP;
import com.i2c.groceryapp.adapter.RvProductCategoryMOQListADP;
import com.i2c.groceryapp.adapter.RvProduct_VerticleADP;
import com.i2c.groceryapp.adapter.RvSearchMOQListADP;
import com.i2c.groceryapp.adapter.RvProduct_VerticleADP;
import com.i2c.groceryapp.databinding.ActivityProductCategoryBinding;
import com.i2c.groceryapp.databinding.DialogProductlistBinding;
import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.Brand_companies_list;
import com.i2c.groceryapp.model.Brand_list;
import com.i2c.groceryapp.model.FavUnFavModel;
import com.i2c.groceryapp.model.Subcategories_list;
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

public class ProductCategoryActivity extends BaseActivity implements
        BrandCategoryItemADP.CallMainBrandID, 
        RvBrandCompanyItemADP.CallBrandCompanyId,
        RvProduct_VerticleADP.OpenMOQDialog,
        RvProduct_VerticleADP.AddFavouritetoAllProduct,
        RvProduct_VerticleADP.AddToReviewCartList,
        RvProduct_VerticleADP.UpdateReviewCart,
        RvProductCategoryMOQListADP.CheckedMOQ,
        RvProduct_VerticleADP.PassValue_ProductDeatlis, RvDialogProductListADP.CategoryIDFromHometoFrmgt
{

    ActivityProductCategoryBinding binding;
    private BrandCategoryItemADP brand_adp;
    private RvBrandCompanyItemADP brand_company_adp;
    private RvProduct_VerticleADP adp;
    private ArrayList<Todayspecial_list> allProductList = new ArrayList<>();
    private ArrayList<Brand_list> brandlist = new ArrayList<>();
    private ArrayList<Brand_companies_list> brandCompnaylist = new ArrayList<>();

    private int SORT_BY = 2;
    private int POSITION = 0;
    private int Str_Items;

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
    RvProductCategoryMOQListADP adp_moq;
    private String Other_margin_Qnty = "0";

    /*sort*/
    private RadioButton radioLowToHigh;
    private RadioButton radioHighToLow;
    private Boolean IsRadioSelected = true;
    private ArrayList<Subcategories_list> arrayList = new ArrayList<>();
    private Dialog dialog_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_category);
        setUpControls();
    }

    @Override
    protected void setContent() {}

    private void setUpControls() {
         //Brand Category RV
        binding.rvBrandCategory.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.rvBrandCategory.setLayoutManager(manager);

        //Brand Company Category RV
        binding.rvBrandCompanyCategory.setHasFixedSize(false);
        LinearLayoutManager manager_sub_cat = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.rvBrandCompanyCategory.setLayoutManager(manager_sub_cat);

        //Sub Vertical Category Items RV
        binding.rvSubCategoryItem.setHasFixedSize(false);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        binding.rvSubCategoryItem.setLayoutManager(manager1);


        binding.tvProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openDialogProductList();
                callSubCaegoryList();
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        callAllProductAPI(POSITION, SORT_BY);

        binding.rvSubCategoryItem.setOnScrollListener(new EndlessRecyclerOnScrollListenerNewGrid(manager) {
            @Override
            public void onLoadMore(int paramInt) {
                POSITION += 1;
                callAllProductAPI(POSITION, SORT_BY);
            }
        });

        binding.constSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSortByDialog();
            }
        });
    }

    private void callSubCaegoryList() {
        if (!CommonUtils.isInternetOn(this)) {
            CommonUtils.showToast(this, getString(R.string.check_internet));
            return;
        }
        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Subcategories_list>> callAPI = apiInterface.subcategories_list(
                sessionManager.getStringValue(Constant.API_TOKEN),
                sessionManager.getStringValue(Constant.BASE_CAT_ID));

        callAPI.enqueue(new Callback<ListResponse<Subcategories_list>>() {
            @Override
            public void onResponse(Call<ListResponse<Subcategories_list>> call, Response<ListResponse<Subcategories_list>> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        binding.rvSubCategoryItem.setVisibility(View.VISIBLE);
                        binding.tvNotData.setVisibility(View.GONE);

                        arrayList.clear();
                        arrayList = response.body().getData();
//                        allCategoryDetailsADP = new RvAllCategoryDetailsADP(
//                                ProductCategoryActivity.this, arrayList);
//                        binding.rvCategoryDetail.setAdapter(allCategoryDetailsADP);

                        if(arrayList.size()!=0){
                            openDialogProductList();
                        }else {
                            showToast("Data not found");
                        }
                    }else if(response.body().getSuccess().equals("0")){

                    }else {

                    }
                }else if(response.code()==404){
                    binding.rvSubCategoryItem.setVisibility(View.GONE);
                    binding.tvNotData.setVisibility(View.VISIBLE);
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<Subcategories_list>> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }



    private void openSortByDialog() {
        binding.rlSubCatBG.setVisibility(View.VISIBLE);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_sorting_category);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

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
                callAllProductAPI(0, SORT_BY);
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
                callAllProductAPI(0, SORT_BY);
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
                binding.rlSubCatBG.setVisibility(View.GONE);
            }
        });
    }

    private void openDialogProductList() {
        DialogProductlistBinding productlistBinding;
        binding.rlSubCatBG.setVisibility(View.VISIBLE);
        dialog_product = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        productlistBinding = DataBindingUtil.inflate(dialog_product.getLayoutInflater(),
                R.layout.dialog_productlist, null, false);
        dialog_product.setContentView(productlistBinding.getRoot());

        dialog_product.setCancelable(false);
        dialog_product.setCanceledOnTouchOutside(true);
        dialog_product.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog_product.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.85f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = layoutParams.WRAP_CONTENT;

        dialog_product.getWindow().setAttributes(layoutParams);
        dialog_product.show();

        productlistBinding.rvProductList.setHasFixedSize(false);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        productlistBinding.rvProductList.setLayoutManager(manager);

        RvDialogProductListADP adp = new RvDialogProductListADP(this,
                arrayList, this);
        productlistBinding.rvProductList.setAdapter(adp);

        dialog_product.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.rlSubCatBG.setVisibility(View.GONE);
            }
        });
    }

    private void callAllProductAPI(int position, int sortBy) {
        if(!isInternetOn(this)){
            showToast(getResources().getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Todayspecial_list>> callAPI = apiInterface.product_list(
                sessionManager.getStringValue(Constant.API_TOKEN),
                String.valueOf(position),
                sessionManager.getStringValue(Constant.SUB_CATEGORY_ID),
                String.valueOf(sortBy),
                sessionManager.getStringValue(Constant.BRAND_ID),
                sessionManager.getStringValue(Constant.BRAND_COMPANY_ID_PRODUCT));

        callAPI.enqueue(new Callback<ListResponse<Todayspecial_list>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ListResponse<Todayspecial_list>> call, Response<ListResponse<Todayspecial_list>> response) {
                if(response.body()!=null){
                    Log.e("TAG", "onResponse:HELLO:::"+new Gson().toJson(response.body()));

                    if(response.body().getSuccess().equals("1")){
                        binding.rvSubCategoryItem.setVisibility(View.VISIBLE);
                        binding.tvNotData.setVisibility(View.GONE);

                        allProductList.clear();

                        if (allProductList.size() == 0) {
                            allProductList.addAll(response.body().getData());
                            adp = new RvProduct_VerticleADP(ProductCategoryActivity.this,
                                    allProductList, ProductCategoryActivity.this,
                                    ProductCategoryActivity.this, ProductCategoryActivity.this,
                                    ProductCategoryActivity.this, ProductCategoryActivity.this);
                            binding.rvSubCategoryItem.setAdapter(adp);
                        } else {
                            allProductList.addAll(response.body().getData());
                        }
                        adp.notifyDataSetChanged();

                        /*total*/
                        Str_Items = allProductList.size();
                        binding.tvTotleItems.setText(String.valueOf(Str_Items) + " " + "items");

                        //brand list
                        brandlist.clear();
                        brandlist.addAll(response.body().getBrand_list());
                        brand_adp = new BrandCategoryItemADP(ProductCategoryActivity.this,
                                brandlist, ProductCategoryActivity.this);
                        binding.rvBrandCategory.setAdapter(brand_adp);
                        brand_adp.notifyDataSetChanged();


                        //brand company list
                        brandCompnaylist.clear();
                        brandCompnaylist.addAll(response.body().getBrand_companies_list());
                        brand_company_adp = new RvBrandCompanyItemADP(
                                ProductCategoryActivity.this, brandCompnaylist,
                                ProductCategoryActivity.this);
                        binding.rvBrandCompanyCategory.setAdapter(brand_company_adp);
                        brand_company_adp.notifyDataSetChanged();

                    }else if(response.body().getSuccess().equals("0")){

                    }
                }else if(response.code() == 404){
                    binding.rvSubCategoryItem.setVisibility(View.GONE);
                    binding.tvNotData.setVisibility(View.VISIBLE);
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
    public void callMainBrandId(String brand_id, String brand_compny_id) {
        Log.e("TAG", "callMainBrandId: brand::::"+brand_id+" "+brand_compny_id);
        POSITION = 0;
        sessionManager.setStringValue(Constant.BRAND_ID, brand_id);
        sessionManager.setStringValue(Constant.BRAND_COMPANY_ID_PRODUCT, brand_compny_id);
        callAllProductAPI(POSITION, SORT_BY);
    }

    @Override
    public void CallBrandCompanyID(String brand_company_id) {
        Log.e("TAG", "CallBrandCompanyID: brand_company_id:::"+brand_company_id);
        POSITION = 0;
        sessionManager.setStringValue(Constant.BRAND_COMPANY_ID_PRODUCT, brand_company_id);
        callAllProductAPI(POSITION, SORT_BY);
    }

    public void openMoqDialog(String ProductName, String PRODUCT_ID, final int position,
                              TextView textview,
                              final String Quantity, float price, String margin, RelativeLayout addCart, LinearLayout llQnty,
                              TextView tvCartQnty, TextView MARGIN, TextView RETAIL,
                              TextView tvTotalPrice, TextView tvFree) {

        binding.rlSubCatBG.setVisibility(View.VISIBLE);
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

                    adp_moq = new RvProductCategoryMOQListADP(ProductCategoryActivity.this,
                            allProductList.get(POSTION).getOther_margin_list(),
                            map, ProductCategoryActivity.this);
                    rvMOQList.setAdapter(adp_moq);
                }
            }
        });

        if (map.size() == 0) {
            for (int i = 0; i < allProductList.size(); i++) {
                map.put(i, false);
            }
        }

        adp_moq = new RvProductCategoryMOQListADP(this,
                allProductList.get(POSTION).getOther_margin_list(), map,
                ProductCategoryActivity.this);
        rvMOQList.setAdapter(adp_moq);

        TXT_MOQ = textview;
        rlAdd_Cart= addCart;
        llQunty = llQnty;
        tvCARTQunty= tvCartQnty;

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.rlSubCatBG.setVisibility(View.GONE);
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
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(ProductCategoryActivity.this,
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
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(ProductCategoryActivity.this,
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
        if (!CommonUtils.isInternetOn(ProductCategoryActivity.this)) {
            CommonUtils.showToast(ProductCategoryActivity.this, getString(R.string.check_internet));
            return;
        }

        CommonUtils.showCustomLoader(ProductCategoryActivity.this);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.addToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                cart_product_id, quantity, margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    Log.e("TAG", "onResponse: CALLED:::::" + new Gson().toJson(response.body()));
                    if (response.body().getSuccess().equals("1")) {
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                        adp.updateCart(position, quantity);
                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(ProductCategoryActivity.this, "Product is not added in cart");
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(ProductCategoryActivity.this, t.getMessage());
            }
        });
    }

    @Override
    public void updateReviewCart(String product_id, String update_quantity) {
        Log.e("TAG", "updateReviewCart: update:::"+product_id+"   qty:::"+update_quantity);
        callUPdateCart(product_id, update_quantity, MARGIN_ID);
    }

    private void callUPdateCart(String update_pro_id, String product_quantity, String margin_id) {
        if (!CommonUtils.isInternetOn(ProductCategoryActivity.this)) {
            CommonUtils.showToast(ProductCategoryActivity.this, getString(R.string.check_internet));
            return;
        }
        CommonUtils.showCustomLoader(ProductCategoryActivity.this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.updateToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                update_pro_id, product_quantity, margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(ProductCategoryActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(ProductCategoryActivity.this,
                            "Product is not updated!");
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(ProductCategoryActivity.this, t.getMessage());
            }
        });
    }



    @Override
    public void passvalueProductDetail(int pos, String product_image, String product_id,
                                       String product_name, String product_mrp,
                                       float product_retail, String margin, float TotalPrice,
                                       int cartQuanty, int In_Cart_qunty,
                                       String min_order_qunaty, int IS_FAV) {

        Intent intent = new Intent(ProductCategoryActivity.this,
                ProductDetailActivity.class);
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

        adp_moq = new RvProductCategoryMOQListADP(ProductCategoryActivity.this,
                allProductList.get(POSTION).getOther_margin_list(), map,
                ProductCategoryActivity.this);
        rvMOQList.setAdapter(adp_moq);
    }

    @Override
    public void CategoryIdFromHomeToFrgmt(String sub_cate, String brand_id, String
            brand_compny_id, String product_name) {
        if(brand_id.equals("0") || brand_compny_id.equals("0")){
            showToast("Data not found!");
        }else {
            sessionManager.setStringValue(Constant.SUB_CATEGORY_ID, sub_cate);
            sessionManager.setStringValue(Constant.BRAND_ID, brand_id);
            sessionManager.setStringValue(Constant.BRAND_COMPANY_ID_PRODUCT, brand_compny_id);

            Log.e("TAG", "CategoryIdFromHomeToFrgmt: CHANGE::::" +
                    "" + sub_cate + "  " + brand_id + "  " + brand_compny_id);

            /*dialog dismiss*/
            dialog_product.dismiss();
            callAllProductAPI(0, SORT_BY);
        }
    }
}