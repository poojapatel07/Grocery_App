package com.i2c.groceryapp.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityRegisterBinding;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends BaseActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (binding.edtName.getText().toString().length() == 0) {
            binding.edtName.setError("Enter Name");
            binding.edtName.requestFocus();
        } else if (binding.edtEmail.getText().toString().length() == 0) {
            binding.edtEmail.setError("Enter Email");
            binding.edtEmail.requestFocus();
        } else if (!binding.edtEmail.getText().toString().matches(emailPattern)) {
            binding.edtEmail.setError("Enter valid Email");
            binding.edtEmail.requestFocus();

        } else if (binding.edtMobile.getText().toString().length() == 0) {
            binding.edtMobile.setError("Enter Mobile Number");
            binding.edtMobile.requestFocus();

        } else if (binding.edtMobile.getText().toString().length() < 10) {
            binding.edtMobile.setError("Mobile number must be 10 char long!");
            binding.edtMobile.requestFocus();

        } else if (binding.edtPassword.getText().toString().length() == 0) {
            binding.edtPassword.setError("Enter Password");
            binding.edtPassword.requestFocus();

        } else if (binding.edtPassword.getText().toString().length() < 8) {
            binding.edtPassword.setError("Password must be 8 char long!");
            binding.edtPassword.requestFocus();

        } else {
            callRegisterAPI();
        }
    }

    private void callRegisterAPI() {
        if(!isInternetOn(this)){
            showToast(getString(R.string.check_internet));
            return;
        }
        showCustomLoader(this);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<RestResponse<Data>> callApi = apiInterface.Register(binding.edtName.getText().toString(),
                binding.edtEmail.getText().toString(), binding.edtMobile.getText().toString(),
                binding.edtPassword.getText().toString(),
                "91", sessionManager.getStringValue(Constant.USER_TYPE),
                binding.edtClientId.getText().toString(), "Android", "abc");

        callApi.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                Log.e("TAG", "onResponse: CALLED:::"+response.code());
                Log.e("TAG", "onResponse: "+new Gson().toJson(response.body()));
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        sessionManager.saveLoginData(response.body().getData());
                        sessionManager.setStringValue(Constant.API_TOKEN, response.body().getData().getApi_token());
                        sessionManager.setStringValue(Constant.USER_TYPE, response.body().getData().getUser_type());
                        sessionManager.setStringValue(Constant.USER_MOBILE, response.body().getData().getMobile());
                        sessionManager.setStringValue(Constant.OTP, response.body().getData().getOtp());
                        sessionManager.setBooleanValue(Constant.OTP_FLAG, false);

                        showToast(response.body().getMessage());
                        launchActivityWithClearStack(RegisterActivity.this, OTPVerificationActivity.class);

                    }else if(response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());
                    }else {
                        showToast(response.body().getMessage());
                    }
                }else if(response.code()==404){
                    showToast("The email has already been taken.,The mobile has already been taken.");
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<RestResponse<Data>> call, Throwable t) {
                showToast(t.getMessage());
                dismissCustomLoader();
                Log.e("TAG", "onFailure: ERROR:::"+t.getMessage());
            }
        });
    }
}