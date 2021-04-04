package com.i2c.groceryapp.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.AllSubCategoryActivity;
import com.i2c.groceryapp.activity.FreebiesActivity;
import com.i2c.groceryapp.activity.HomeActivity;
import com.i2c.groceryapp.activity.ProductDetailActivity;
import com.i2c.groceryapp.activity.TradeOfferActivity;
import com.i2c.groceryapp.adapter.BaseCategoryADP;
import com.i2c.groceryapp.adapter.RvTodaySpecialListADP;
import com.i2c.groceryapp.adapter.RvTodaysSpecialMOQListADP;
import com.i2c.groceryapp.databinding.FragmentHomeBinding;
import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.Category;
import com.i2c.groceryapp.model.FavUnFavModel;
import com.i2c.groceryapp.model.Todayspecial_list;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.CommonUtils;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener,
        RvTodaySpecialListADP.AddFavouritetoAllProduct,
        RvTodaySpecialListADP.AddToReviewCartList, RvTodaySpecialListADP.UpdateReviewCart,
        RvTodaySpecialListADP.OpenMOQDialog, RvTodaysSpecialMOQListADP.CheckedMOQ,
        RvTodaySpecialListADP.PassValue_ProductDeatlis{

    FragmentHomeBinding binding;
    RvTodaySpecialListADP todaySpecialListADP;
    BaseCategoryADP baseCategoryADP;
    SessionManager sessionManager;
    private ArrayList<Todayspecial_list> todayspecialListBeans = new ArrayList<>();
    private ArrayList<Category> categorylist = new ArrayList<>();
    private BaseCategoryADP base_catADP;

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
    RvTodaysSpecialMOQListADP adp_moq;
    private String Other_margin_Qnty = "0";

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpContorls(view);
    }

    private void setUpContorls(View view) {
        sessionManager = new SessionManager(getActivity());

        // Today's special
        binding.rvTodaySpecial.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        binding.rvTodaySpecial.setLayoutManager(manager);

        // Base Category
        binding.rvBaseCategory.setHasFixedSize(false);
        LinearLayoutManager manager_base = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.rvBaseCategory.setLayoutManager(manager_base);
//        baseCategoryADP = new BaseCategoryADP(getActivity());
//        binding.rvBaseCategory.setAdapter(baseCategoryADP);

        binding.llCategory.setOnClickListener(this);
        binding.llFreebies.setOnClickListener(this);
        binding.llTradeOffer.setOnClickListener(this);
        binding.llOffer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCategory:
                getRootActivity().binding.viewPager.setCurrentItem(1);
                break;

            case R.id.llFreebies:
                startActivity(new Intent(getActivity(), FreebiesActivity.class));
                break;

            case R.id.llTradeOffer:
                startActivity(new Intent(getActivity(), TradeOfferActivity.class));
                break;

            case R.id.llOffer:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        callHomeListAPI();
        callGetCategoryAPI();
    }

    private void callGetCategoryAPI() {
        if (!CommonUtils.isInternetOn(getActivity())) {
            CommonUtils.showToast(getActivity(), getString(R.string.check_internet));
            return;
        }
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Category>> callAPI = apiInterface.categories_list(
                sessionManager.getStringValue(Constant.API_TOKEN));

        callAPI.enqueue(new Callback<ListResponse<Category>>() {
            @Override
            public void onResponse(Call<ListResponse<Category>> call, Response<ListResponse<Category>> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        categorylist.clear();
                        categorylist.addAll(response.body().getData());

                        if (categorylist.size() != 0) {
                            base_catADP = new BaseCategoryADP(getActivity(),categorylist);
                            binding.rvBaseCategory.setAdapter(base_catADP);
                            base_catADP.notifyDataSetChanged();
                        }

                    }else if(response.body().getSuccess().equals("0")){

                    }else {

                    }
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<Category>> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
            }
        });

    }

    private void callHomeListAPI() {
        if (!CommonUtils.isInternetOn(getActivity())) {
            CommonUtils.showToast(getActivity(), getString(R.string.check_internet));
            return;
        }

        CommonUtils.showCustomLoader(getActivity());

        Log.e("TAG", "callHomeListAPI:API:::"
                + sessionManager.getStringValue(Constant.API_TOKEN));
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RestResponse<Todayspecial_list>> callAPI = apiInterface.homeList(
                sessionManager.getStringValue(Constant.API_TOKEN));

        callAPI.enqueue(new Callback<RestResponse<Todayspecial_list>>() {
            @Override
            public void onResponse(Call<RestResponse<Todayspecial_list>> call, Response<RestResponse<Todayspecial_list>> response) {
                if (response.body() != null) {
                    Log.e("TAG", "onResponse: BODY:::" + new Gson().toJson(response.body()));
                    if (response.body().getSuccess().equals("1")) {
                        Glide.with(getActivity())
                                .load(response.body().getFirst_banner().getLogo())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        binding.progressBar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        binding.progressBar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(binding.ivFirstBanner);


                        Glide.with(getActivity())
                                .load(response.body().getLast_banner().getLogo())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        binding.progressBottomBar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        binding.progressBottomBar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(binding.ivLastBanner);

                        todayspecialListBeans.clear();
                        todayspecialListBeans.addAll(response.body().getTodayspecial_list());

                        /*only show 2 product*/

                        /*for (int i = 0; i < response.body().getTodayspecial_list().size(); i++) {
                            if (i < 2) {
                                todayspecialListBeans.addAll(response.body().getTodayspecial_list());
                            }
                        }*/

                        /*if(response.body().getTodayspecial_list().size()>2){
                            binding.tvViewAll.setVisibility(View.VISIBLE);
                            sessionManager.save_today_specialList(response.body().getTodayspecial_list());
                        }else {
                            binding.tvViewAll.setVisibility(View.GONE);
                        }*/

                        todaySpecialListADP = new RvTodaySpecialListADP(getActivity(),
                                todayspecialListBeans, HomeFragment.this,
                                HomeFragment.this, HomeFragment.this,
                                HomeFragment.this, HomeFragment.this);

                        binding.rvTodaySpecial.setAdapter(todaySpecialListADP);
                        todaySpecialListADP.notifyDataSetChanged();

                    } else {

                    }
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<RestResponse<Todayspecial_list>> call, Throwable t) {
                Log.e("TAG", "onFailure: onFAIL:::" + t.getMessage());
                CommonUtils.dismissCustomLoader();
            }
        });
    }

    private HomeActivity getRootActivity() {
        return (HomeActivity) getActivity();
    }

    public void openMoqDialog(String ProductName, String PRODUCT_ID, final int position,
                              TextView textview,
                              final String Quantity, float price, String margin, RelativeLayout addCart, LinearLayout llQnty,
                              TextView tvCartQnty, TextView MARGIN, TextView RETAIL,
                              TextView tvTotalPrice, TextView tvFree) {

        binding.rlMainBG.setVisibility(View.VISIBLE);
        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_open_moq);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

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

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
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
                    for (int i = 0; i < todayspecialListBeans.size(); i++) {
                        map.put(i, false);
                    }
                    TXT_MOQ.setText(String.valueOf(Quantity));
                    TVMARGIN.setText(String.valueOf(todayspecialListBeans.get(position).getMargin()));
                    TVMARGIN.setText(String.valueOf(todayspecialListBeans.get(position).getRetail_price()));

                    MARGIN_ID = " ";

                    if (todayspecialListBeans.get(position).getIn_cart_qty()!=0) {
                        rlAdd_Cart.setVisibility(View.GONE);
                        llQunty.setVisibility(View.VISIBLE);

                        IN_CART_QUNTY = todayspecialListBeans.get(position).getIn_cart_qty();
                        tvCARTQunty.setText(String.valueOf(todayspecialListBeans.get(position).getIn_cart_qty()));

                        TVRETAIL.setText(String.valueOf(todayspecialListBeans.get(position).getRetail_price()));
                        TVTOTALPRICE.setText(String.valueOf(IN_CART_QUNTY * Float.parseFloat(TVRETAIL.getText().toString())));

                        if (!todayspecialListBeans.get(POSTION).getIs_free_product().equals("0")) {
                            int buy_one = Integer.parseInt(todayspecialListBeans.get(POSTION).getMin_qty_for_free());
                            int get_one = Integer.parseInt(todayspecialListBeans.get(POSTION).getPro_qty_for_free());
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

                    adp_moq = new RvTodaysSpecialMOQListADP(getActivity(),
                            todayspecialListBeans.get(POSTION).getOther_margin_list(),
                            map, HomeFragment.this);
                    rvMOQList.setAdapter(adp_moq);
                }
            }
        });

        if (map.size() == 0) {
            for (int i = 0; i < todayspecialListBeans.size(); i++) {
                map.put(i, false);
            }
        }

        adp_moq = new RvTodaysSpecialMOQListADP(getActivity(),
                todayspecialListBeans.get(POSTION).getOther_margin_list(), map,
                HomeFragment.this);
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

    @Override
    public void addtoReviewCartList(String product_id, String quantity) {
        Log.d("TAG", "addtoReviewCartList returned: " + product_id);
        callAddToReviewCartAPI(product_id, quantity, MARGIN_ID);
    }

    private void callAddToReviewCartAPI(String cart_product_id, String quantity, String margin_id) {
        if (!CommonUtils.isInternetOn(getActivity())) {
            CommonUtils.showToast(getActivity(), getString(R.string.check_internet));
            return;
        }

        CommonUtils.showCustomLoader(getActivity());

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddUpdateCart> callAPI = apiInterface.addToCart(sessionManager.getStringValue(Constant.API_TOKEN),
                cart_product_id, quantity, margin_id);

        callAPI.enqueue(new Callback<AddUpdateCart>() {
            @Override
            public void onResponse(Call<AddUpdateCart> call, Response<AddUpdateCart> response) {
                if (response.body() != null) {
                    Log.e("TAG", "onResponse: CALLED:::::" + new Gson().toJson(response.body()));
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
                        todaySpecialListADP.updateIsFavData(position);

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
                        todaySpecialListADP.updateIsFavData(position);

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


    @Override
    public void updateReviewCart(String product_id, String update_quantity) {
        Log.e("TAG", "updateReviewCart: update:::"+product_id+"   qty:::"+update_quantity);
        callUPdateCart(product_id, update_quantity, MARGIN_ID);
    }

    @Override
    public void passvalueProductDetail(int pos, String product_image, String product_id,
                                       String product_name, String product_mrp,
                                       float product_retail, String margin, float TotalPrice,
                                       int cartQuanty, int In_Cart_qunty,
                                       String min_order_qunaty, int IS_FAV) {

        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
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

        if (todayspecialListBeans.get(pos).getOther_margin_list().size() != 0) {
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

            if (!todayspecialListBeans.get(POSTION).getIs_free_product().equals("0")) {
                int buy_one = Integer.parseInt(todayspecialListBeans.get(POSTION).getMin_qty_for_free());
                int get_one = Integer.parseInt(todayspecialListBeans.get(POSTION).getPro_qty_for_free());
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

        for (int i = 0; i < todayspecialListBeans.get(POSTION).getOther_margin_list().size(); i++) {
            if (i == pos) {
                map.put(i, true);
            } else {
                map.put(i, false);
            }
        }

        adp_moq = new RvTodaysSpecialMOQListADP(getActivity(),
                todayspecialListBeans.get(POSTION).getOther_margin_list(), map,
                HomeFragment.this);
        rvMOQList.setAdapter(adp_moq);
    }
}