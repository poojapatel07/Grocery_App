package com.i2c.groceryapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.ReviewBasketADP;
import com.i2c.groceryapp.databinding.FragmentRiewBasketBinding;


public class RiewBasketFragment extends Fragment {
    FragmentRiewBasketBinding binding;
    private ReviewBasketADP adp;

    public RiewBasketFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_riew_basket, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpContorls(view);
    }

    private void setUpContorls(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvAllReviewBasket.setLayoutManager(linearLayoutManager);

        adp = new ReviewBasketADP(getActivity());
        binding.rvAllReviewBasket.setAdapter(adp);
    }
}