package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityOrderSummaryBinding;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;


public class OrderSummaryActivity extends BaseActivity {
    ActivityOrderSummaryBinding binding;
    private float Grand_amount;
    private int TOTAL_ITMES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_summary);
        setUpControls();
    }

    @Override
    protected void setContent() {}

    @SuppressLint("SetTextI18n")
    private void setUpControls() {
        
        
        Grand_amount=getIntent().getFloatExtra(Constant.GRAND_TOTAL_AMOUNT,0);
        TOTAL_ITMES=getIntent().getIntExtra(Constant.TOTAL_ITEM,0);

        binding.tvTotalPrice.setText("\u20B9" + " " +Grand_amount);
        binding.tvAMountPayable.setText("\u20B9" + " " +Grand_amount);
        binding.tvTotalAmount.setText("\u20B9" + " " +Grand_amount);
        binding.tvTotalItem.setText("Total Item :"+" "+String.valueOf(TOTAL_ITMES));
        binding.tvPrice.setText("Price"+" "+"("+String.valueOf(TOTAL_ITMES)+" "+"items"+")");

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.constContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(OrderSummaryActivity.this,PaymentActivity.class);
                intent.putExtra(Constant.GRAND_TOTAL_AMOUNT, Grand_amount);
                intent.putExtra(Constant.TOTAL_ITEM, TOTAL_ITMES);
                startActivity(intent);
            }
        });
    }
}