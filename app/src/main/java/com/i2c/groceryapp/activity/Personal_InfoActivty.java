package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityPersonalInfoActivtyBinding;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.utils.BaseActivity;

public class Personal_InfoActivty extends BaseActivity {
    ActivityPersonalInfoActivtyBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_personal__info_activty);
        setUpControls();
    }

    @Override
    protected void setContent() { }

    private void setUpControls() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}