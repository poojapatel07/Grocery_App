package com.i2c.groceryapp.activity;

import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityLoginBinding;
import com.i2c.groceryapp.databinding.DialogForgotPasswordBinding;
import com.i2c.groceryapp.databinding.DialogUpdatePasswordBinding;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    ActivityLoginBinding binding;
    private DialogForgotPasswordBinding forgotPasswordBinding;
    private DialogUpdatePasswordBinding updatePasswordBinding;
    private Dialog dialog;
    private Dialog dialog_newPass;
    private boolean isShowPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        binding.btnLogin.setOnClickListener(this);
        binding.tvForgotPassword.setOnClickListener(this);
        binding.btnOtpLogin.setOnClickListener(this);
        binding.ivPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                checkValidation();
                break;


            case R.id.tvForgotPassword:
                openForgotPasswordDialog();
                break;

            case R.id.btnOtpLogin:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LoginActivity.this,
                                LoginByOTPActivity.class));
                    }
                }, 200);
                break;

            case R.id.ivPassword:
                if (isShowPassword) {
                    binding.edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    binding.ivPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide_eye));
                    isShowPassword = false;
                } else {
                    binding.edtPassword.setTransformationMethod(null);
                    binding.ivPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye));
                    isShowPassword = true;
                }
                break;
        }
    }

    private void checkValidation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (binding.edtEmail.getText().toString().length() == 0) {
            binding.edtEmail.setError("Enter Email");
            binding.edtEmail.requestFocus();

        } else if (!binding.edtEmail.getText().toString().matches(emailPattern)) {
            binding.edtEmail.setError("Enter valid Email");
            binding.edtEmail.requestFocus();

        } else if (binding.edtPassword.getText().toString().length() == 0) {
            binding.edtPassword.setError("Enter Password");
            binding.edtPassword.requestFocus();

//        } else if (binding.edtPassword.getText().toString().length() < 8) {
//            binding.edtPassword.setError("Password must be 8 char long!");
//            binding.edtPassword.requestFocus();
        } else {
            callLoginAPI();
        }
    }

    private void callLoginAPI() {
        if (!isInternetOn(this)) {
            showToast(getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<RestResponse<Data>> callApi = apiInterface.Login(binding.edtEmail.getText().toString(),
                binding.edtPassword.getText().toString(), "Android", "abc");

        callApi.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                Log.e("TAG", "onResponse:CALLED:::" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        sessionManager.saveLoginData(response.body().getData());
                        sessionManager.setStringValue(Constant.API_TOKEN, response.body().getData().getApi_token());
                        sessionManager.setStringValue(Constant.USER_TYPE, response.body().getData().getUser_type());
                        sessionManager.setBooleanValue(Constant.IS_ALREADY_LOGIN, true);
                        showToast(response.body().getMessage());
                        launchActivityWithClearStack(LoginActivity.this, HomeActivity.class);

                    } else {
                        showToast(response.body().getMessage());
                    }
                }else if(response.code()==404){
                    showToast("User not found");
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<RestResponse<Data>> call, Throwable t) {
                showToast(t.getMessage());
                dismissCustomLoader();
            }
        });
    }

    private void openForgotPasswordDialog() {
        binding.rlLoginBG.setVisibility(View.VISIBLE);
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        forgotPasswordBinding = DataBindingUtil.inflate(dialog.getLayoutInflater(), R.layout.dialog_forgot_password,
                null, false);
        dialog.setContentView(forgotPasswordBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.88f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();

        forgotPasswordBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                binding.rlLoginBG.setVisibility(View.GONE);
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        forgotPasswordBinding.btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMobile();
            }
        });
    }

    private void checkMobile() {
        if (forgotPasswordBinding.edtMobile.getText().toString().length() == 0) {
            forgotPasswordBinding.edtMobile.setError("Enter Mobile number");
            forgotPasswordBinding.edtMobile.requestFocus();
        } else if (forgotPasswordBinding.edtMobile.getText().toString().length() < 10) {
            forgotPasswordBinding.edtMobile.setError("Mobile number must be 10 char long!");
            forgotPasswordBinding.edtMobile.requestFocus();

        } else {
            callOTPVerificationAPI(forgotPasswordBinding.edtMobile.getText().toString());
        }
    }

    private void callOTPVerificationAPI(String mobile) {
        if (!isInternetOn(this)) {
            showToast(getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RestResponse<Data>> callApi = apiInterface.otp_login(mobile,
                "Android", "abc");

        callApi.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                if (response.body() != null) {
                    Log.e("TAG", "onResponse:CALLED::::" + new Gson().toJson(response.body()));
                    if (response.body().getSuccess().equals("1")) {
                        dialog.dismiss();
                        openDialogForNewPassword(response.body().getData().getMobile(), response.body().getData().getOtp());

                    } else if (response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }

                }else if(response.code()==404){
                    showToast("Account not activated.");
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

    private void openDialogForNewPassword(String mobile, String otp) {
        dialog_newPass = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        updatePasswordBinding = DataBindingUtil.inflate(dialog_newPass.getLayoutInflater(),
                R.layout.dialog_update_password, null,false);
        dialog_newPass.setContentView(updatePasswordBinding.getRoot());
        dialog_newPass.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_newPass.setCancelable(false);
        dialog_newPass.setCanceledOnTouchOutside(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog_newPass.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.88f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog_newPass.getWindow().setAttributes(layoutParams);
        dialog_newPass.show();

        updatePasswordBinding.ivPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowPassword) {
                    updatePasswordBinding.edtNewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    updatePasswordBinding.ivPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide_eye));
                    isShowPassword = false;
                } else {
                    updatePasswordBinding.edtNewPassword.setTransformationMethod(null);
                    updatePasswordBinding.ivPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye));
                    isShowPassword = true;
                }
            }
        });

        updatePasswordBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_newPass.dismiss();
                binding.rlLoginBG.setVisibility(View.GONE);
            }
        });

        dialog_newPass.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
                binding.rlLoginBG.setVisibility(View.GONE);
            }
        });

        updatePasswordBinding.btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updatePasswordBinding.edtOp.getText().toString().equals(otp)) {
                    callGetNewPasswordAPI(mobile,
                            updatePasswordBinding.edtOp.getText().toString(),
                            updatePasswordBinding.edtNewPassword.getText().toString());
                } else {
                    showToast("Enter Correct OTP!");
                }
            }
        });
    }

    private void callGetNewPasswordAPI(String mobile, String otp, String newPass) {
        if (!isInternetOn(this)) {
            showToast(getString(R.string.check_internet));
            return;
        }
        Log.e("TAG", "callGetNewPasswordAPI: CALLED:::"+mobile+"   otp:::"+otp+    "pass:::"+newPass);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RestResponse<Data>> calAPi = apiInterface.change_new_password(
                mobile, otp, newPass);

        showCustomLoader(this);
        calAPi.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        dialog_newPass.dismiss();
                        sessionManager.saveLoginData(response.body().getData());
                        sessionManager.setStringValue(Constant.API_TOKEN, response.body().getData().getApi_token());
                        sessionManager.setStringValue(Constant.USER_TYPE, response.body().getData().getUser_type());
                        sessionManager.setBooleanValue(Constant.IS_ALREADY_LOGIN, true);
                        showToast(response.body().getMessage());
                        launchActivityWithClearStack(LoginActivity.this, HomeActivity.class);

                    } else if (response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }

                }else if(response.code()==404){
                    showToast("Something went wrong!");
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