package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityOrderSummaryBinding;
import com.i2c.groceryapp.databinding.ItemOrderSummaryBinding;
import com.i2c.groceryapp.utils.BaseActivity;

public class OrderSummaryActivity extends BaseActivity {
    ActivityOrderSummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_summary);
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

        binding.constContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderSummaryActivity.this, PaymentActivity.class));
            }
        });
    }
}