package com.i2c.groceryapp.activity;

import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityLanguageSelectionBinding;
import com.i2c.groceryapp.helper.LocaleHelper;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;

public class LanguageSelectionActivity extends BaseActivity implements View.OnClickListener {
    ActivityLanguageSelectionBinding binding;
    String selectlang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_selection);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        binding.tvEnglish.setOnClickListener(this);
        binding.tvGujarati.setOnClickListener(this);
        binding.tvHindi.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvEnglish:
                selectlang = "english";
                updateViews("en");

                callHanlder();
                break;

            case R.id.tvHindi:
                selectlang = "hindi";
                updateViews("hi");

                callHanlder();
                break;

            case R.id.tvGujarati:
                selectlang = "gujarati";
                updateViews("gu");

                callHanlder();
                break;
        }
    }

    private void callHanlder() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(LanguageSelectionActivity.this,
//                        LoginRegisterActivity.class));
            }
        }, 300);
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        sessionManager.setStringValue(Constant.GETLANGUAGE, languageCode);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}