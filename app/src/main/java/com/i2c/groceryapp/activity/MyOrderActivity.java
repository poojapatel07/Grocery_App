package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvMyFavouriteADP;
import com.i2c.groceryapp.adapter.RvMyOrderADP;
import com.i2c.groceryapp.databinding.ActivityMyOrderBinding;
import com.i2c.groceryapp.utils.BaseActivity;

public class MyOrderActivity extends BaseActivity {
    ActivityMyOrderBinding binding;
    private RvMyOrderADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_my_order);
        setUpconrols();
    }

    @Override
    protected void setContent() { }

    private void setUpconrols() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.rvMyOrder.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvMyOrder.setLayoutManager(manager);

        adp = new RvMyOrderADP(this);
        binding.rvMyOrder.setAdapter(adp);
    }


}