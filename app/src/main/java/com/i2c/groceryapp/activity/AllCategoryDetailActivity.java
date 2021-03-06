package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvAllCategoryDetailsADP;
import com.i2c.groceryapp.databinding.ActivityAllCategoryDetailBinding;
import com.i2c.groceryapp.databinding.ItemAllCategoryBinding;

public class AllCategoryDetailActivity extends AppCompatActivity {
    ActivityAllCategoryDetailBinding binding;
    private RvAllCategoryDetailsADP allCategoryDetailsADP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_category_detail);
        setUpControls();
    }

    private void setUpControls() {
        binding.rvCategoryDetail.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvCategoryDetail.setLayoutManager(manager);
        allCategoryDetailsADP = new RvAllCategoryDetailsADP(this);
        binding.rvCategoryDetail.setAdapter(allCategoryDetailsADP);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}