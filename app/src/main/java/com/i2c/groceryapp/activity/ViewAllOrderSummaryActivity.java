package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.OrderSummaryADP;
import com.i2c.groceryapp.databinding.ActivityMyOrderBinding;
import com.i2c.groceryapp.databinding.ActivityViewAllOrderSummaryBinding;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewAllOrderSummaryActivity extends BaseActivity {
    ActivityViewAllOrderSummaryBinding binding;
    private OrderSummaryADP adp;
    private String Order_id;
    private String Order_no;
    private String Status;
    private String Shipping_address;
    private String Billing_address;
    private String Order_total_amount;
    private int POSTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_all_order_summary);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Order_no = getIntent().getStringExtra(Constant.ORDER_NO);
        Order_id = getIntent().getStringExtra(Constant.ORDER_ID);
        POSTION = getIntent().getIntExtra(Constant.ARRAYLIST_POSITION, 0);
        Status = getIntent().getStringExtra(Constant.ORDER_STATUS);
        Shipping_address = getIntent().getStringExtra(Constant.ORDER_SHIPPING_ADD);
        Billing_address = getIntent().getStringExtra(Constant.ORDER_BILLING_ADD);
        Order_total_amount = getIntent().getStringExtra(Constant.TOTAL_ORDER_AMOUNT);


        binding.tvOrderNum.setText("Order ID :" + " " + Order_no);

        binding.tvShpAdd.setText(Shipping_address);
        binding.tvBillingAddress.setText(Billing_address);
        binding.tvTotalPrice.setText(Order_total_amount);

        if (Status.equals("0")) {
            binding.tvStatus.setText("Pending");
            binding.ivDelete.setVisibility(View.VISIBLE);

        } else if (Status.equals("1")) {
            binding.tvStatus.setText("Dispatched");
            binding.ivDelete.setVisibility(View.GONE);

        } else if (Status.equals("2")) {
            binding.tvStatus.setText("Delivered");
            binding.ivDelete.setVisibility(View.GONE);

        } else if (Status.equals("3")) {
            binding.tvStatus.setText("Cancel");
            binding.ivDelete.setVisibility(View.GONE);
        }


        binding.rvOrderSummary.setHasFixedSize(false);
        binding.rvOrderSummary.setFocusable(false);

        binding.rvOrderSummary.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvOrderSummary.setLayoutManager(manager);

        adp = new OrderSummaryADP(this, MyOrderActivity.myOrderlist.get(POSTION).getOrder_product_data());
        binding.rvOrderSummary.setAdapter(adp);
        adp.notifyDataSetChanged();

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCancelOrder();
            }
        });
    }

    private void openCancelOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Cancel this Order?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callOrderCancelAPI();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.parseColor("#b0c916"));
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.parseColor("#b0c916"));
    }

    private void callOrderCancelAPI() {
        if(!isInternetOn(this)){
            showToast(getResources().getString(R.string.check_internet));
            return;
        }
        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<String>> callApi = apiInterface.cancel_order(
                sessionManager.getStringValue(Constant.API_TOKEN),
                String.valueOf(Order_id));

        callApi.enqueue(new Callback<ListResponse<String>>() {
            @Override
            public void onResponse(Call<ListResponse<String>> call, Response<ListResponse<String>> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        showToast(response.body().getMessage());
                        onBackPressed();

                    }else if(response.body().getSuccess().equals("0")){

                    }
                }else if(response.code() == 404){
                    showToast("Something went wrong");
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<String>> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }
}