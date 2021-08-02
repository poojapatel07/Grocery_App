
package com.i2c.groceryapp.activity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
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
import com.i2c.groceryapp.adapter.RvFreebiesADP;
import com.i2c.groceryapp.adapter.RvTodaysSpecialMOQListADP;
import com.i2c.groceryapp.adapter.RvTradeOfferADP;
import com.i2c.groceryapp.adapter.RvTradeOfferMOQListADP;
import com.i2c.groceryapp.databinding.ActivityTradeOfferBinding;
import com.i2c.groceryapp.fragment.HomeFragment;
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
import java.util.HashMap;

import io.reactivex.internal.util.BlockingHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TradeOfferActivity extends BaseActivity implements
        RvTradeOfferADP.AddFavouritetoAllProduct,
RvTradeOfferADP.AddToReviewCartList, RvTradeOfferADP.OpenMOQDialog,
        RvTradeOfferADP.UpdateReviewCart, RvTradeOfferMOQListADP.CheckedMOQ{

    ActivityTradeOfferBinding binding;
    private RvTradeOfferADP adp;
    private int Trade_position = 0;
    private ArrayList<Todayspecial_list> arrayList = new ArrayList<>();

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
    RvTradeOfferMOQListADP adp_moq;
    LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trade_offer);
        setUpControls();
    }

    @Override
    protected void setContent() { }

    private void setUpControls() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.rvTredeOffers.setHasFixedSize(false);
        manager = new LinearLayoutManager(this);
        binding.rvTredeOffers.setLayoutManager(manager);


        callTreadOfferAPI(Trade_position, true);

        binding.rvTredeOffers.setOnScrollListener(new EndlessRecyclerOnScrollListenerNewGrid
                (manager) {
            @Override
            public void onLoadMore(int paramInt) {
                Trade_position += 1;
                Log.e("TAG", "onLoadMore: TRADE_POS:::: " + Trade_position);
                callTreadOfferAPI(Trade_position, false);
            }
        });
    }


    @Override
    protected void onResume() {
        if(arrayList.size()!=0){
            arrayList.clear();
        }

        if(adp!=null){
            adp = null;
        }

        POSTION = 0;
        callTreadOfferAPI(POSTION, true);

        binding.rvTredeOffers.setOnScrollListener(new EndlessRecyclerOnScrollListenerNewGrid(manager) {
            @Override
            public void onLoadMore(int paramInt) {
                POSTION += 1;
                callTreadOfferAPI(POSTION, false);
            }
        });
        super.onResume();
    }

    private void callTreadOfferAPI(int trade_position, Boolean isFirst) {
        if(!isInternetOn(this)){
            showToast(getResources().getString(R.string.check_internet));
            return;
        }

        if(isFirst){
            showCustomLoader(this);
        }

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Todayspecial_list>> callAPI = apiInterface.trade_product_list(
                    sessionManager.getStringValue(Constant.API_TOKEN), String.valueOf(trade_position));

        callAPI.enqueue(new Callback<ListResponse<Todayspecial_list>>() {
            @Override
            public void onResponse(Call<ListResponse<Todayspecial_list>> call, Response<ListResponse<Todayspecial_list>> response) {
                Log.e("TAG", "GET:CALLED::::"+new Gson().toJson(response.body()));
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        binding.rvTredeOffers.setVisibility(View.VISIBLE);
                        binding.tvNoData.setVisibility(View.GONE);

                        if(trade_position==0){
                            arrayList.clear();
                        }
                        if (arrayList.size() == 0) {
                            Log.e("TAG", "onResponse: IF:::"+arrayList.size());
                            arrayList.addAll(response.body().getData());

                            adp = new RvTradeOfferADP(TradeOfferActivity.this,
                                    arrayList, TradeOfferActivity.this,
                                    TradeOfferActivity.this,
                                    TradeOfferActivity.this,
                                    TradeOfferActivity.this);
                            binding.rvTredeOffers.setAdapter(adp);

                        } else {
                            Log.e("TAG", "onResponse: BODY:::"+arrayList.size());
                            arrayList.addAll(response.body().getData());
                        }
                        adp.notifyDataSetChanged();

                    }else if(response.body().getSuccess().equals("0")){

                    }else{

                    }

                }else if(response.code()==404){
                    if(arrayList.size()==0) {
                        binding.rvTredeOffers.setVisibility(View.GONE);
                        binding.tvNoData.setVisibility(View.VISIBLE);
                    }

                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<Todayspecial_list>> call, Throwable t) {
                dismissCustomLoader();
                Log.e("TAG", "onFailure: ERROR:::"+t.getMessage());
            }
        });
    }


    public void openMoqDialog(String ProductName, String PRODUCT_ID, final int position,
                              TextView textview, final String Quantity, float price, String margin,
                              RelativeLayout addCart, LinearLayout llQnty,
                              TextView tvCartQnty, TextView MARGIN, TextView RETAIL,
                              TextView tvTotalPrice, TextView tvFree) {

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
                    for (int i = 0; i < arrayList.size(); i++) {
                        map.put(i, false);
                    }
                    TXT_MOQ.setText(String.valueOf(Quantity));
                    TVMARGIN.setText(String.valueOf(arrayList.get(position).getMargin()));
                    TVMARGIN.setText(String.valueOf(arrayList.get(position).getRetail_price()));

                    MARGIN_ID = " ";

                    if (arrayList.get(position).getIn_cart_qty()!=0) {
                        rlAdd_Cart.setVisibility(View.GONE);
                        llQunty.setVisibility(View.VISIBLE);

                        IN_CART_QUNTY = arrayList.get(position).getIn_cart_qty();
                        tvCARTQunty.setText(String.valueOf(arrayList.get(position).getIn_cart_qty()));

                        TVRETAIL.setText(String.valueOf(arrayList.get(position).getRetail_price()));
                        TVTOTALPRICE.setText(String.valueOf(IN_CART_QUNTY * Float.parseFloat(TVRETAIL.getText().toString())));

                        if (!arrayList.get(POSTION).getIs_free_product().equals("0")) {
                            int buy_one = Integer.parseInt(arrayList.get(POSTION).getMin_qty_for_free());
                            int get_one = Integer.parseInt(arrayList.get(POSTION).getPro_qty_for_free());
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

                    adp_moq = new RvTradeOfferMOQListADP(TradeOfferActivity.this,
                            arrayList.get(POSTION).getOther_margin_list(),
                            map, TradeOfferActivity.this);
                    rvMOQList.setAdapter(adp_moq);
                }
            }
        });

        if (map.size() == 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                map.put(i, false);
            }
        }

        adp_moq = new RvTradeOfferMOQListADP(this,
                arrayList.get(POSTION).getOther_margin_list(), map, this);
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
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(TradeOfferActivity.this,
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
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                        adp.updateIsFavData(position);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                    }else {
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(TradeOfferActivity.this,
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
                        adp.updateCart(position, quantity);
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(TradeOfferActivity.this, "Product is not added in cart");
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(TradeOfferActivity.this, t.getMessage());
            }
        });
    }


    @Override
    public void updateReviewCart(String product_id, String update_quantity) {
        callUPdateCart(product_id, update_quantity, MARGIN_ID);
    }

    private void callUPdateCart(String update_pro_id, String product_quantity,
                                String margin_id) {
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
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());

                    } else if (response.body().getSuccess().equals("0")) {
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                    } else {
                        CommonUtils.showToast(TradeOfferActivity.this, response.body().getMessage());
                    }
                }else if(response.code()==404){
                    CommonUtils.showToast(TradeOfferActivity.this,
                            "Product is not updated!");
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<AddUpdateCart> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(TradeOfferActivity.this, t.getMessage());
            }
        });
    }

    @Override
    public void CheckedMOQ(int pos, String MOQ, String margin_id, String other_cart_qnty,
                           String MARGIN, String price) {
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

            if (!arrayList.get(POSTION).getIs_free_product().equals("0")) {
                int buy_one = Integer.parseInt(arrayList.get(POSTION).getMin_qty_for_free());
                int get_one = Integer.parseInt(arrayList.get(POSTION).getPro_qty_for_free());
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

        for (int i = 0; i < arrayList.get(POSTION).getOther_margin_list().size(); i++) {
            if (i == pos) {
                map.put(i, true);
            } else {
                map.put(i, false);
            }
        }

        adp_moq = new RvTradeOfferMOQListADP(this,
                arrayList.get(POSTION).getOther_margin_list(), map, this);
        rvMOQList.setAdapter(adp_moq);
    }
}