package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvMyFavouriteADP;
import com.i2c.groceryapp.adapter.RvNotificationADP;
import com.i2c.groceryapp.databinding.ActivityMyFavouriteBinding;

public class MyFavouriteActivity extends AppCompatActivity {
    ActivityMyFavouriteBinding binding;
    private RvMyFavouriteADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_favourite);
        setUpControls();
    }

    private void setUpControls() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.rvMyFavourite.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvMyFavourite.setLayoutManager(manager);

        adp = new RvMyFavouriteADP(this);
        binding.rvMyFavourite.setAdapter(adp);
    }
}