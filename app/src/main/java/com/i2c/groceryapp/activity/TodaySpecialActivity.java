package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityTodaySpecialBinding;
import com.i2c.groceryapp.databinding.ItemTodaySpecialBinding;
import com.i2c.groceryapp.utils.BaseActivity;

public class TodaySpecialActivity extends BaseActivity {
    ActivityTodaySpecialBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_today_special);
        setUpControls();
    }

    @Override
    protected void setContent() {}

    private void setUpControls() {
        // Today's special
        binding.rvTodaySpecial.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvTodaySpecial.setLayoutManager(manager);




    }
}