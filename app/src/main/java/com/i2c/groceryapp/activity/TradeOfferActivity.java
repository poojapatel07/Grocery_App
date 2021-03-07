package com.i2c.groceryapp.activity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvTradeOfferADP;
import com.i2c.groceryapp.databinding.ActivityTradeOfferBinding;
import com.i2c.groceryapp.utils.BaseActivity;

public class TradeOfferActivity extends BaseActivity {
    ActivityTradeOfferBinding binding;
    private RvTradeOfferADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trade_offer);
        setUpControls();
    }

    @Override
    protected void setContent() { }

    private void setUpControls() {
        binding.rvTredeOffers.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvTredeOffers.setLayoutManager(manager);
        adp = new RvTradeOfferADP(this);
        binding.rvTredeOffers.setAdapter(adp);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}