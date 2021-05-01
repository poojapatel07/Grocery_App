package com.i2c.groceryapp.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityLoginByOtpBinding;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginByOTPActivity extends BaseActivity {
    ActivityLoginByOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_by_otp);
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

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        if (binding.edtMobile.getText().toString().length() == 0) {
            binding.edtMobile.setError("Enter Mobile Number");
            binding.edtMobile.requestFocus();
        } else if (binding.edtMobile.getText().toString().length() < 10) {
            binding.edtMobile.setError("Mobile Number must be 10 char long");
            binding.edtMobile.requestFocus();
        } else {
            callOTPVerificationAPI();
        }
    }

    private void callOTPVerificationAPI() {
        if(!isInternetOn(this)){
            showToast(getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RestResponse<Data>> callApi = apiInterface.otp_login(binding.edtMobile.getText().toString(),
                "Android", "abc");

        callApi.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                if(response.body()!=null){
                    Log.e("TAG", "onResponse:CALLED::::"+new Gson().toJson(response.body()));
                    if(response.body().getSuccess().equals("1")){
                        sessionManager.saveLoginData(response.body().getData());
                        sessionManager.setStringValue(Constant.API_TOKEN, response.body().getData().getApi_token());
                        sessionManager.setStringValue(Constant.USER_TYPE, response.body().getData().getUser_type());
                        sessionManager.setStringValue(Constant.USER_MOBILE, response.body().getData().getMobile());
                        sessionManager.setStringValue(Constant.OTP, response.body().getData().getOtp());
                        sessionManager.setBooleanValue(Constant.OTP_FLAG, true);
                        showToast(response.body().getMessage());
                        launchActivity(LoginByOTPActivity.this, OTPVerificationActivity.class);

                    }else if(response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());
                    }else {
                        showToast(response.body().getMessage());
                    }
                }else if(response.code()==404){
                    showToast("Account not activated");
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<RestResponse<Data>> call, Throwable t) {
                dismissCustomLoader();
                showToast(t.getMessage());
            }
        });
    }
}