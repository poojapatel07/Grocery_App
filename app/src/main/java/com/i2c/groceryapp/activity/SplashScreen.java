package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivitySplashScreenBinding;
import com.i2c.groceryapp.helper.LocaleHelper;
import com.i2c.groceryapp.utils.BaseActivity;

public class SplashScreen extends BaseActivity {
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        int SPLASH_DISPLAY_LENGTH = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sessionManager.checkLogin();
//                Intent mainIntent = new Intent(SplashScreen.this, LoginRegisterActivity.class);
//                Intent mainIntent = new Intent(SplashScreen.this, HomeActivity.class);
//                startActivity(mainIntent);
//                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}