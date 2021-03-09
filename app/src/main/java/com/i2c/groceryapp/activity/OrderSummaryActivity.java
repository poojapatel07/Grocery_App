package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.OrderSummaryADP;
import com.i2c.groceryapp.databinding.ActivityOrderSummaryBinding;

public class OrderSummaryActivity extends AppCompatActivity {
    ActivityOrderSummaryBinding binding;
    private OrderSummaryADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_summary);
        setUpControls();
    }

    private void setUpControls() {
        binding.rvOrderSummary.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvOrderSummary.setLayoutManager(manager);
        
        adp = new OrderSummaryADP(this);
        binding.rvOrderSummary.setAdapter(adp);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}