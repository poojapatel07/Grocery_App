package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvBrandCompanyProductADP;
import com.i2c.groceryapp.databinding.ActivityBrandCompanyProductListBinding;

public class BrandCompanyProductListActivity extends AppCompatActivity {
    ActivityBrandCompanyProductListBinding binding;
    private RvBrandCompanyProductADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_brand_company_product_list);
        setUpControls();
    }

    private void setUpControls() {
        binding.rvBrandCompanyProduct.setHasFixedSize(false);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvBrandCompanyProduct.setLayoutManager(manager);
        adp = new RvBrandCompanyProductADP(this);
        binding.rvBrandCompanyProduct.setAdapter(adp);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}