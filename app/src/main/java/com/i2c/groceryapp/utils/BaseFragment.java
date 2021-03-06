package com.i2c.groceryapp.utils;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.i2c.groceryapp.R;

import static android.view.View.GONE;


public abstract class BaseFragment extends Fragment {
    private View rootView;
    public int deviceHeight, deviceWidth;
    private ProgressDialog mProgressDialog;

    protected abstract int setFragmentLayout();

    Dialog dialog;
    public static Dialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(setFragmentLayout(), container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setContent(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public void showCustomLoader(Activity activity) {
        if (alertDialog != null && alertDialog.isShowing()) {
            return;
        }
        alertDialog = new Dialog(activity);
        alertDialog.setCancelable(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.layout_custom_loader);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void dismissCustomLoader() {
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
    }

    public void hideDialog() {
        dialog.dismiss();
    }

    protected abstract void setContent(View rootView);

    protected boolean hasAds() {
        return true;
    }

    protected void setVisibleGone(int tv_emptyData) {
        rootView.findViewById(tv_emptyData).setVisibility(GONE);
    }

    protected void setVisible(int tv_emptyData) {
        rootView.findViewById(tv_emptyData).setVisibility(View.VISIBLE);
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    protected View getRootView() {
        return rootView;
    }

    public static boolean isInternetOn(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
