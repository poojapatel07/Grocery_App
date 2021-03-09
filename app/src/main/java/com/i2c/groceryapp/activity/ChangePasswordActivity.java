package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityChangePasswordBinding;
import com.i2c.groceryapp.utils.BaseActivity;

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
        }
    }
}