package com.i2c.groceryapp.activity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.View;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvAllCategoryDetailsADP;
import com.i2c.groceryapp.databinding.ActivityAllSubcategoryBinding;
import com.i2c.groceryapp.utils.BaseActivity;


public class AllSubCategoryActivity extends BaseActivity {
    ActivityAllSubcategoryBinding binding;
    private RvAllCategoryDetailsADP allCategoryDetailsADP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_subcategory);
        setUpControls();
    }

    @Override
    protected void setContent() {
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