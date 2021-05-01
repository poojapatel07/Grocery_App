package com.i2c.groceryapp.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvMyOrderADP;
import com.i2c.groceryapp.databinding.ActivityMyOrderBinding;
import com.i2c.groceryapp.model.MyOrderList;
import com.i2c.groceryapp.model.OrderList;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.EndlessRecyclerOnScrollListenerNewGrid;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends BaseActivity {
    ActivityMyOrderBinding binding;
    private RvMyOrderADP adp;
    private int PAGE_NO = 0;
    public static ArrayList<OrderList> myOrderlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_my_order);
        setUpconrols();
    }

    @Override
    protected void setContent() { }

    private void setUpconrols() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.rvMyOrder.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvMyOrder.setLayoutManager(manager);

        callAllMyOrderAPI(PAGE_NO, true);

        binding.rvMyOrder.setOnScrollListener(new EndlessRecyclerOnScrollListenerNewGrid(manager) {
            @Override
            public void onLoadMore(int paramInt) {
                PAGE_NO += 1;
                Log.e("TAG", "onLoadMore: POS:::: " + PAGE_NO);
                callAllMyOrderAPI(PAGE_NO, false);
            }
        });
    }

    @Override
    protected void onResume() {
        
        super.onResume();
    }

    private void callAllMyOrderAPI(int page_no, boolean isFirst) {
        Log.e("TAG", "callAllMyOrderAPI: PASS:::"
                +page_no+"  "+sessionManager.getStringValue(Constant.API_TOKEN));

        if(!isInternetOn(this)){
            showToast(getResources().getString(R.string.check_internet));
            return;
        }
        if(isFirst){
            showCustomLoader(this);
        }

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<ListResponse<OrderList>> callAPI = apiInterface.order_list(
                sessionManager.getStringValue(Constant.API_TOKEN), String.valueOf(page_no));

        callAPI.enqueue(new Callback<ListResponse<OrderList>>() {
            @Override
            public void onResponse(Call<ListResponse<OrderList>> call, Response<ListResponse<OrderList>> response) {
                Log.e("TAG", "onResponse:CALLED:::::s"+new Gson().toJson(response.body()));
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        binding.rvMyOrder.setVisibility(View.VISIBLE);
                        binding.tvNoData.setVisibility(View.GONE);

                        if (response.body().getData()!=null) {
                            if (myOrderlist.size() == 0) {
                                myOrderlist.addAll(response.body().getData());

                                adp = new RvMyOrderADP(MyOrderActivity.this, myOrderlist);
                                binding.rvMyOrder.setAdapter(adp);

                            } else {
                                myOrderlist.addAll(response.body().getData());
                            }
                            if(adp!=null) {
                                adp.notifyDataSetChanged();
                            }
                        }

                    }else if(response.body().getSuccess().equals("0")){

                    }else {

                    }
                }else if(response.code()==404){
                    if(myOrderlist.size()==0) {
                        binding.rvMyOrder.setVisibility(View.GONE);
                        binding.tvNoData.setVisibility(View.VISIBLE);
                    }
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<OrderList>> call, Throwable t) {
                dismissCustomLoader();
                Log.e("TAG", "onFailure: FF:::"+t.getMessage());
            }
        });
    }


}