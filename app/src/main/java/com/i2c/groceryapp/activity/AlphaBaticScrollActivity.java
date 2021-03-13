package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvAlphabaticScrollADP;
import com.i2c.groceryapp.databinding.ActivityAlphaBaticScrollBinding;

public class AlphaBaticScrollActivity extends AppCompatActivity {
    ActivityAlphaBaticScrollBinding binding;
    private RvAlphabaticScrollADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alpha_batic_scroll);
        setUpControls();
    }

    private void setUpControls() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvCategoryAlphabetical.setLayoutManager(manager);

        binding.rvCategoryAlphabetical.setIndexTextSize(12);
        binding.rvCategoryAlphabetical.setIndexBarTextColor("#292929");
        binding.rvCategoryAlphabetical.setIndexBarColor("#cdced2");
        binding.rvCategoryAlphabetical.setIndexbarHighLateTextColor("#b0c916");
        binding.rvCategoryAlphabetical.setIndexBarHighLateTextVisibility(true);
        binding.rvCategoryAlphabetical.setIndexBarTransparentValue((float) 1.0);

        adp = new RvAlphabaticScrollADP(this);
        binding.rvCategoryAlphabetical.setAdapter(adp);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}