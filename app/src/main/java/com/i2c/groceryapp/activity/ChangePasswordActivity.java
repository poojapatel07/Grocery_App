package com.i2c.groceryapp.activity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityChangePasswordBinding;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    ActivityChangePasswordBinding binding;
    private boolean isShowPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        setUpControls();
    }

    @Override
    protected void setContent() {}

    private void setUpControls() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.ivOldPass.setOnClickListener(this);
        binding.ivNewPassword.setOnClickListener(this);
        binding.ivConfirm.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivOldPass:
                if (isShowPassword) {
                    binding.etOldPassword.setTransformationMethod(new PasswordTransformationMethod());
                    binding.ivOldPass.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide_eye));
                    isShowPassword = false;
                } else {
                    binding.etOldPassword.setTransformationMethod(null);
                    binding.ivOldPass.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye));
                    isShowPassword = true;
                }
                break;


            case R.id.ivNewPassword:
                if (isShowPassword) {
                    binding.etNewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    binding.ivNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide_eye));
                    isShowPassword = false;
                } else {
                    binding.etNewPassword.setTransformationMethod(null);
                    binding.ivNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye));
                    isShowPassword = true;
                }
                break;


            case R.id.ivConfirm:
                if (isShowPassword) {
                    binding.etConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    binding.ivConfirm.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide_eye));
                    isShowPassword = false;
                } else {
                    binding.etConfirmPassword.setTransformationMethod(null);
                    binding.ivConfirm.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye));
                    isShowPassword = true;
                }
                break;

            case R.id.btnSave:
                checkValidation();
                break;
        }
    }

    private void checkValidation() {
        if (binding.etOldPassword.getText().toString().length() == 0) {
            binding.etOldPassword.setError("Enter Old Password");
            binding.etOldPassword.requestFocus();

        } else if (binding.etNewPassword.getText().toString().length() == 0) {
            binding.etNewPassword.setError("Enter New Password");
            binding.etNewPassword.requestFocus();

        } else if (binding.etConfirmPassword.getText().toString().length() == 0) {
            binding.etConfirmPassword.setError("Enter Confirm Password");
            binding.etConfirmPassword.requestFocus();

        } else if (!binding.etNewPassword.getText().toString()
                .equals(binding.etConfirmPassword.getText().toString())) {
            binding.etConfirmPassword.setError("Password does not match!");
            binding.etConfirmPassword.requestFocus();
        } else {
            callChangePasswordAPI();
        }
    }

    private void callChangePasswordAPI() {
        if(!isInternetOn(this)){
            showToast(getResources().getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RestResponse<Data>> callAPI = apiInterface.update_password(
                sessionManager.getStringValue(Constant.API_TOKEN),
                binding.etOldPassword.getText().toString(),
                binding.etNewPassword.getText().toString(),
                binding.etConfirmPassword.getText().toString());

        callAPI.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                Log.e("TAG", "onResponse: changed::::"+new Gson().toJson(response.body()));
                Log.e("TAG", "onResponse: status::::"+response.code());
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        showToast(response.body().getMessage());
                        onBackPressed();

                    }else if(response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());
                    }else {
                        showToast(response.body().getMessage());
                    }

                }else if(response.code()==404) {
                    showToast("Invalid Password");
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<RestResponse<Data>> call, Throwable t) {
                dismissCustomLoader();
                Log.e("TAG", "onFailure: FFF:::"+t.getMessage());
            }
        });
    }
}