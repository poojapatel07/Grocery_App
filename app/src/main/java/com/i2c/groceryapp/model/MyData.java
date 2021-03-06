package com.i2c.groceryapp.model;

import android.content.Context;

import com.i2c.groceryapp.R;

public class MyData {

    public Context activity;

    public MyData(Context activity) {
        this.activity = activity;
    }

    public static int[] ICON={
            R.drawable.ic_myorder,
            R.drawable.ic_myfavorite,
            R.drawable.ic_become_prime,
            R.drawable.ic_profile,
            R.drawable.ic_change_lang,
            R.drawable.ic_change_pass,
            R.drawable.ic_contcat_us,
            R.drawable.ic_logout,
    };

}
