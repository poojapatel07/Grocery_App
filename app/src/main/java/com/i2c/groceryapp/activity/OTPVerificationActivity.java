package com.i2c.groceryapp.activity;

import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityMainOTPBinding;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.GenericTextWatcher;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationActivity extends BaseActivity implements View.OnClickListener {
    ActivityMainOTPBinding binding;
    private String enterOtp = "";
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_o_t_p);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        countDownTimerDefault();

        binding.tvmobile.setText(sessionManager.getStringValue(Constant.USER_MOBILE));

        binding.btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTPVerificationActivity.this, MainActivity.class));
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.tvChangeNum.setOnClickListener(this);
        binding.tvResendOTP.setOnClickListener(this);

        binding.btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = binding.et1.getText().toString()+binding.et2.getText().toString()+
                        binding.et3.getText().toString()+binding.et4.getText().toString();

                Log.e("TAG", "onClick:enterOtp::::::"+otp+  "length:::"+otp.length());

                if (binding.et1.getText().toString().isEmpty() ||
                        binding.et2.getText().toString().isEmpty() ||
                        binding.et3.getText().toString().isEmpty() ||
                        binding.et4.getText().toString().isEmpty()) {
                    Toast.makeText(OTPVerificationActivity.this, "Enter valid OTP", Toast.LENGTH_SHORT).show();

                } else if (!otp.equals(sessionManager.getStringValue(Constant.OTP))) {
                    Toast.makeText(OTPVerificationActivity.this, "Enter valid OTP", Toast.LENGTH_SHORT).show();

                } else {
                    callOTPVerifyStatusAPI(binding.tvmobile.getText().toString(), otp);
                }
            }
        });

        EditText[] edit = {binding.et1, binding.et2, binding.et3, binding.et4};

        binding.et1.addTextChangedListener(new GenericTextWatcher(binding.et1, edit));
        binding.et2.addTextChangedListener(new GenericTextWatcher(binding.et2, edit));
        binding.et3.addTextChangedListener(new GenericTextWatcher(binding.et3, edit));
        binding.et4.addTextChangedListener(new GenericTextWatcher(binding.et4, edit));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvChangeNum:

            case R.id.tvResendOTP:
                onBackPressed();
                break;
        }
    }

    private void callOTPVerifyStatusAPI(String mobile, String otp) {
        if(!isInternetOn(this)){
            showToast(getString(R.string.check_internet));
            return;
        }
        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RestResponse<Data>> callApi = apiInterface.otp_verify(mobile, otp);

        callApi.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                if(response.body()!=null){
                    Log.e("TAG", "onResponse:CALLED::::"+new Gson().toJson(response.body()));

                    if(response.body().getSuccess().equals("1")){
                        sessionManager.setBooleanValue(Constant.IS_ALREADY_LOGIN, true);
                        showToast(response.body().getMessage());
                        launchActivityWithClearStack(OTPVerificationActivity.this, HomeActivity.class);

                    }else if(response.body().getSuccess().equals("0")){
                        showToast(response.body().getMessage());
                    }else {
                        showToast(response.body().getMessage());
                    }
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


    void countDownTimerDefault() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.start();
        } else {
            countDownTimer = new CountDownTimer(90000, 1000) {
                @SuppressLint({"DefaultLocale", "SetTextI18n"})
                public void onTick(long millisUntilFinished) {
                    binding.tvTimeUP.setText("" + String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                public void onFinish() {
                    showToast( "Time UP");
                    launchActivityWithClearStack(OTPVerificationActivity.this, LoginRegisterActivity.class);
                }
            }.start();
        }
    }
}
