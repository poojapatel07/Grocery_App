package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvNotificationADP;
import com.i2c.groceryapp.databinding.ActivityNotifictionBinding;
import com.i2c.groceryapp.utils.BaseActivity;

public class NotifictionActivity extends BaseActivity {
    ActivityNotifictionBinding binding;
    private RvNotificationADP adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notifiction);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        binding.rvNotification.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvNotification.setLayoutManager(manager);

        adp = new RvNotificationADP(this);
        binding.rvNotification.setAdapter(adp);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}