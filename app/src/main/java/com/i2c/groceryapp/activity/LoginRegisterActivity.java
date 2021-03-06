package com.i2c.groceryapp.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityLoginRegisterBinding;
import com.i2c.groceryapp.helper.LocaleHelper;


public class LoginRegisterActivity extends AppCompatActivity {
    ActivityLoginRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_register);

        setUpControls();
    }

    private void setUpControls() {
        int start = 10;
        int end = 18;
        SpannableString spannableString = new SpannableString("Welcome to\nGrocery shopping");
        spannableString.setSpan(null, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(null, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvText.setText(spannableString);

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginRegisterActivity.this, LoginActivity.class));
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginRegisterActivity.this, SelectRoleActivity.class));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}