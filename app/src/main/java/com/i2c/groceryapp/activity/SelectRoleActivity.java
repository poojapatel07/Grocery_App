package com.i2c.groceryapp.activity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivitySelectRoleBinding;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;


public class SelectRoleActivity extends BaseActivity {
    ActivitySelectRoleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_role);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        binding.retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setStringValue(Constant.USER_TYPE, "1");
                startActivity(new Intent(SelectRoleActivity.this, RegisterActivity.class));
            }
        });

        binding.customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setStringValue(Constant.USER_TYPE, "0");
                startActivity(new Intent(SelectRoleActivity.this, RegisterActivity.class));
            }
        });
    }
}