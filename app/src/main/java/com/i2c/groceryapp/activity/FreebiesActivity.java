package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvFreebiesADP;
import com.i2c.groceryapp.databinding.ActivityFreebiesBinding;

public class FreebiesActivity extends AppCompatActivity {
    ActivityFreebiesBinding binding;
    private RvFreebiesADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_freebies);
        setUpControls();
    }

    private void setUpControls() {
        binding.rvFreebies.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvFreebies.setLayoutManager(manager);
        adp = new RvFreebiesADP(this);
        binding.rvFreebies.setAdapter(adp);
    }
}