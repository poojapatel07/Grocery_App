package com.i2c.groceryapp.activity;
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
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityPaymentBinding;
import com.i2c.groceryapp.model.Checksum;
import com.i2c.groceryapp.model.PaytmCheckSum;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.ws.VolleyService;
import com.i2c.groceryapp.ws.interfaces.VolleyResponseListener;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends BaseActivity implements VolleyResponseListener, PaytmPaymentTransactionCallback {
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
            callAddOrderAPI(binding.tvPayableAmount.getText().toString());

//            callAPIforCheckSum();

//            getChaeckSum();
        }
    }

    private PaytmCheckSum paytm;
    String CHECSUM_URL;

    private void getChaeckSum(){
        if(!isInternetOn(this)){
            showToast(getResources().getString(R.string.check_internet));
            return;
        }

        APIInterface apiInterface = APIClient.getClientPaytm().create(APIInterface.class);
        Call<RestResponse<Checksum>> callApi = apiInterface.getCheckSumUrl("a56462f8c45947d0b450ee8db27b51b2");
//                ,"aZIVDa99442085737603",
//                "f602a35f92ae49f881b44cfddca5e123","WAP","1.0",
//                "WEBSTAGING","https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp",
//                "Retail");

        callApi.enqueue(new Callback<RestResponse<Checksum>>() {
            @Override
            public void onResponse(Call<RestResponse<Checksum>> call, Response<RestResponse<Checksum>> response) {
                Log.e("TAG", "onResponse:called:::"+new Gson().toJson(response.body()));
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        Log.e("TAG", "onResponse: succ::::"+response.body().getData().getChecksumHash());
                    }
                }
            }

            @Override
            public void onFailure(Call<RestResponse<Checksum>> call, Throwable t) {
                Log.e("TAG", "onFailure: ERROR:::"+t.getMessage());
            }
        });

    }

    private void callAPIforCheckSum() {
        paytm = new PaytmCheckSum(Constant.TEST_MERCHANT_ID, Constant.CHANNEL_ID,
                binding.tvPayableAmount.getText().toString(), Constant.WEBSITE,
                Constant.CALLBACK_URL, Constant.INDUSTRY_TYPE_ID);

        CHECSUM_URL = "https://idea2codeinfotech.com/fresh-fine-paytm-cs/generateChecksum.php";

        HashMap<String, String> map = new HashMap<>();
        map.put("ORDER_ID", paytm.getOrderId());
        map.put("MID", paytm.getmId());
        map.put("CUST_ID", paytm.getCustId());
        map.put("CHANNEL_ID", paytm.getChannelId());
            map.put("TXN_AMOUNT", paytm.getTxnAmount());
        map.put("WEBSITE", paytm.getWebsite());
        map.put("CALLBACK_URL", paytm.getCallBackUrl());
        map.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());

        Log.e("TAG", "callAPIforCheckSum: MAP:::" + map.toString());

        if (isInternetOn(PaymentActivity.this)) {
            showCustomLoader(this);
            VolleyService.PostMethod(CHECSUM_URL, com.i2c.groceryapp.model.Checksum.class,
                    map, this);
        } else {
            showToast(getString(R.string.check_internet));
        }
    }

    @Override
    public void onResponse(Object response, String url, boolean isSuccess, VolleyError error) {
        if (url.equals(CHECSUM_URL)) {
            if (isSuccess) {
                if (response instanceof com.i2c.groceryapp.model.Checksum) {
                    Log.e("TAG", "onResponse: checksum ::"
                            + ((com.i2c.groceryapp.model.Checksum) response).getChecksumHash());
                    initializePaytmPayment(((com.i2c.groceryapp.model.Checksum) response)
                            .getChecksumHash(), paytm);
                } else {
                   dismissCustomLoader();
                }
            } else {
                dismissCustomLoader();
            }
        }
    }

    private void initializePaytmPayment(String checksumHash, PaytmCheckSum paytm) {
        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();
//        PaytmPGService Service = PaytmPGService.getProductionService();

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", paytm.getmId());
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());

        PaytmOrder order = new PaytmOrder(paramMap);

        Log.i("paramMap", "initializePaytmPayment: " + paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true,
                true, this);
    }


    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Log.e("TAG", "onTransactionResponse: " + inResponse.toString());
        String status = "";
        String orderID = "";
        if (inResponse.containsKey("STATUS")) {
            status = inResponse.getString("STATUS");
        }
        if (inResponse.containsKey("ORDERID")) {
            orderID = inResponse.getString("ORDERID");
        }
        if (status.equals("TXN_SUCCESS")) {
            showToast("Transaction Successful!");

            callAddOrderAPI(binding.tvPayableAmount.getText().toString());
        } else {
        }
    }

    @Override
    public void networkNotAvailable() {
        showToast("No Internet Available!");
    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        showToast(inErrorMessage);
        Log.e("TAG", "clientAuthenticationFailed: ERROR::::"+inErrorMessage);
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        showToast(inErrorMessage);
        Log.e("TAG", "someUIErrorOccurred:::: "+inErrorMessage);
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        showToast(inErrorMessage);
        Log.e("TAG", "onErrorLoadingWebPage::::: "+inErrorMessage);
    }

    @Override
    public void onBackPressedCancelTransaction() {
        showToast("cancel Transaction!");
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        showToast("Transaction cancelled by user!");
    }


//    private void callAddOrderAPI(String totalAmount) {
    private void callAddOrderAPI(String totalAmount) {
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

        Log.e("TAG", "callAddOrderAPI:fgfgfg "+sessionManager.getStringValue(Constant.API_TOKEN)+"  "
        +totalAmount+"   "+Payment_type+"  "+Shipping_add+"  "+Billing_add);


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
        final Dialog dialog = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
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