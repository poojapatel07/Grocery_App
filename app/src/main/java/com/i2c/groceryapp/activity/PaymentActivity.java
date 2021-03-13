package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {
    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        setUpControls();
    }

    private void setUpControls() {

        binding.constContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        binding.rlBG.setVisibility(View.VISIBLE);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_cotinue_payment);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.75f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();

        TextView tvOrderID;
        TextView tvOrderAmount;
        TextView llContinueShopping;

        tvOrderID = dialog.findViewById(R.id.tvOrderID);
        tvOrderAmount = dialog.findViewById(R.id.tvOrderAmount);
        llContinueShopping = dialog.findViewById(R.id.btnContinue);

        tvOrderID.setVisibility(View.GONE);
//        tvOrderAmount.setText("Order Amount :"+" "+TOTAL_AMOUNT);

        llContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.rlBG.setVisibility(View.GONE);
            }
        });
    }


}