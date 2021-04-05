package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityPaymentBinding;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends BaseActivity {
    ActivityPaymentBinding binding;
    private float TOTAL_AMOUNT;
    private int TOTAL_ITMES;
    private String Payment_type;
    private String Shipping_add;
    private String Billing_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        setUpControls();
    }

    @Override
    protected void setContent() {}

    @SuppressLint("SetTextI18n")
    private void setUpControls() {
        TOTAL_AMOUNT = getIntent().getFloatExtra(Constant.GRAND_TOTAL_AMOUNT,0);
        TOTAL_ITMES = getIntent().getIntExtra(Constant.TOTAL_ITEM, 0);

        binding.tvTotalAmount.setText("\u20B9" + " " + TOTAL_AMOUNT);
        binding.tvPayableAmount.setText("\u20B9" + " " + TOTAL_AMOUNT);
        binding.tvTotalFinalPrice.setText("\u20B9" + " " + TOTAL_AMOUNT);
        binding.tvTotalItem.setText("Total Item :" + " " + String.valueOf(TOTAL_ITMES));

        binding.constContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(sessionManager.getLoginData().getShipping_address()!=null){
            Shipping_add = sessionManager.getLoginData().getShipping_address();
        }

        if(sessionManager.getLoginData().getBilling_address()!=null){
            Billing_add = sessionManager.getLoginData().getBilling_address();
        }

        if (Shipping_add==null||Billing_add==null){
            binding.llNoAddress.setVisibility(View.VISIBLE);
        }else {
            binding.llNoAddress.setVisibility(View.GONE);
        }

        binding.llNoAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this,
                        Personal_InfoActivty.class));
            }
        });

        binding.radioCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.radioCash.setChecked(true);
                Payment_type = "0";
            }
        });
    }

    private void checkValidation() {
        if (Payment_type==null) {
            showToast( "Please Select Payment Type!");

        }else if(Shipping_add==null) {
            showToast("Please Enter Shipping Address in Profile!");

        }   else if(Billing_add==null){
            showToast("Please Enter Billing Address in Profile!");

        }else  {
            callAddOrderAPI();
        }
    }

    private void callAddOrderAPI() {
        if(!isInternetOn(this)){
            showToast(getResources().getString(R.string.check_internet));
            return;
        }
        showCustomLoader(this);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<String>> callAPI = apiInterface.add_order(
                sessionManager.getStringValue(Constant.API_TOKEN),
                String.valueOf(TOTAL_AMOUNT),
                "","0",
                String.valueOf(TOTAL_AMOUNT),
                Payment_type,
                "0",Shipping_add, Billing_add);

        callAPI.enqueue(new Callback<ListResponse<String>>() {
            @Override
            public void onResponse(Call<ListResponse<String>> call, Response<ListResponse<String>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        showToast(response.body().getMessage());
                        openDialog();

                    } else if (response.body().getSuccess().equals("0")) {

                    } else {

                    }
                } else if (response.code() == 404) {
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


    @SuppressLint("SetTextI18n")
    private void openDialog() {
        binding.rlBG.setVisibility(View.VISIBLE);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_cotinue_payment);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.75f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();

        TextView tvOrderID;
        TextView tvOrderAmount;
        TextView llContinueShopping;

        tvOrderID = dialog.findViewById(R.id.tvOrderID);
        tvOrderAmount = dialog.findViewById(R.id.tvOrderAmount);
        llContinueShopping = dialog.findViewById(R.id.btnContinue);

        tvOrderID.setVisibility(View.GONE);
        tvOrderAmount.setText("Order Amount :"+" "+TOTAL_AMOUNT);

        llContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                launchActivityWithClearStack(PaymentActivity.this,HomeActivity.class);
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.rlBG.setVisibility(View.GONE);
            }
        });
    }


}