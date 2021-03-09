package com.i2c.groceryapp.activity;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityChangeLanguageBinding;
import com.i2c.groceryapp.helper.LocaleHelper;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.utils.BaseActivity;

public class ChangeLanguageActivity extends BaseActivity {
    ActivityChangeLanguageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_change_language);
        setUpControls();
    }

    @Override
    protected void setContent() {


    }


    private void setUpControls() {

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
