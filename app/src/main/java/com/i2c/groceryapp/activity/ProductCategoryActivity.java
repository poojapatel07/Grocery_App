package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.BrandCategoryItemADP;
import com.i2c.groceryapp.adapter.RvBrandCompanyItemADP;
import com.i2c.groceryapp.adapter.RvDialogProductListADP;
import com.i2c.groceryapp.adapter.RvProduct_VerticleADP;
import com.i2c.groceryapp.databinding.ActivityProductCategoryBinding;
import com.i2c.groceryapp.databinding.DialogProductlistBinding;

public class ProductCategoryActivity extends AppCompatActivity {
    ActivityProductCategoryBinding binding;
    private BrandCategoryItemADP brandCategoryItemADP;
    private RvBrandCompanyItemADP rvBrandAdp;
    private RvProduct_VerticleADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_category);
        setUpControls();
    }

    private void setUpControls() {
         //Brand Category RV
        binding.rvBrandCategory.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.rvBrandCategory.setLayoutManager(manager);
        brandCategoryItemADP = new BrandCategoryItemADP(this);
        binding.rvBrandCategory.setAdapter(brandCategoryItemADP);


        //Brand Company Category RV
        binding.rvBrandCompanyCategory.setHasFixedSize(false);
        LinearLayoutManager manager_sub_cat = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.rvBrandCompanyCategory.setLayoutManager(manager_sub_cat);
        rvBrandAdp = new RvBrandCompanyItemADP(this);
        binding.rvBrandCompanyCategory.setAdapter(rvBrandAdp);


        //Sub Vertical Category Items RV
        binding.rvSubCategoryItem.setHasFixedSize(false);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        binding.rvSubCategoryItem.setLayoutManager(manager1);
        adp = new RvProduct_VerticleADP(this);
        binding.rvSubCategoryItem.setAdapter(adp);


        binding.rlProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogProductList();
            }
        });

    }

    private void openDialogProductList() {
        DialogProductlistBinding productlistBinding;
        binding.rlSubCatBG.setVisibility(View.VISIBLE);
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        productlistBinding = DataBindingUtil.inflate(dialog.getLayoutInflater(),
                R.layout.dialog_productlist, null, false);
        dialog.setContentView(productlistBinding.getRoot());

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.85f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = layoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();

        productlistBinding.rvProductList.setHasFixedSize(false);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        productlistBinding.rvProductList.setLayoutManager(manager);

        RvDialogProductListADP adp = new RvDialogProductListADP(this);
        productlistBinding.rvProductList.setAdapter(adp);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.rlSubCatBG.setVisibility(View.GONE);
            }
        });

    }
}