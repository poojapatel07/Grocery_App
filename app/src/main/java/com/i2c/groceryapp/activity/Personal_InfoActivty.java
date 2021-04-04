package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityPersonalInfoActivtyBinding;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Personal_InfoActivty extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    ActivityPersonalInfoActivtyBinding binding;
    private Calendar mCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal__info_activty);
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

        binding.tvUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        binding.etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDate = Calendar.getInstance();
                int year = mCurrentDate.get(Calendar.YEAR);
                int month = mCurrentDate.get(Calendar.MONTH);
                int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                        Personal_InfoActivty.this,
                        Personal_InfoActivty.this, year, month, day);
                mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                mDatePickerDialog.show();
            }
        });

        binding.etDOB.setFocusable(false);
        binding.etEmail.setEnabled(false);

        binding.etEmail.setText(sessionManager.getLoginData().getEmail());
        binding.etName.setText(sessionManager.getLoginData().getName());

        binding.etDOB.setText(sessionManager.getLoginData().getDate_of_birth());
        binding.etWPNO.setText(sessionManager.getLoginData().getWhatsapp_no());
        binding.etShippingAdd.setText(sessionManager.getLoginData().getShipping_address());
        binding.etBillingAdd.setText(sessionManager.getLoginData().getBilling_address());

        binding.etMobile.setEnabled(false);
        binding.etMobile.setText(sessionManager.getLoginData().getMobile());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        binding.etDOB.setText(dayOfMonth + "-" + month + "-" + year);
    }

    private void checkValidation() {
        if (binding.etName.getText().toString().length() == 0) {
            binding.etName.setError("Enter Name");
            binding.etName.requestFocus();

        } else if (binding.etDOB.getText().toString().length() == 0) {
            binding.etDOB.setError("Enter Date of Birth");
            binding.etDOB.requestFocus();

        } else if (binding.etMobile.getText().toString().length() == 0) {
            binding.etMobile.setError("Enter Mobile Number");
            binding.etMobile.requestFocus();

        } else if (binding.etWPNO.getText().toString().length() == 0) {
            binding.etWPNO.setError("Enter WhatsApp Number");
            binding.etWPNO.requestFocus();

        } else if (binding.etShippingAdd.getText().toString().length() == 0) {
            binding.etShippingAdd.setError("Enter Shipping Address");
            binding.etShippingAdd.requestFocus();

        } else if (binding.etBillingAdd.getText().toString().length() == 0) {
            binding.etBillingAdd.setError("Enter Billing Address");
            binding.etBillingAdd.requestFocus();
        } else {
            callUserProfileAPI();
        }

    }

    private void callUserProfileAPI() {
        if (!isInternetOn(this)) {
            showToast(getResources().getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<RestResponse<Data>> callAPI = apiInterface.update_profile(
                sessionManager.getStringValue(Constant.API_TOKEN),
                binding.etName.getText().toString(), binding.etMobile.getText().toString(),
                binding.etDOB.getText().toString(), binding.etWPNO.getText().toString(),
                "1", binding.etBillingAdd.getText().toString(),
                binding.etShippingAdd.getText().toString());

        callAPI.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        sessionManager.saveLoginData(response.body().getData());
                        showToast(response.body().getMessage());
                        onBackPressed();

                    } else if (response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<RestResponse<Data>> call, Throwable t) {
                dismissCustomLoader();
            }
        });

    }


}