package com.i2c.groceryapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i2c.groceryapp.R;
import com.i2c.groceryapp.activity.FreebiesActivity;
import com.i2c.groceryapp.activity.HomeActivity;
import com.i2c.groceryapp.activity.TradeOfferActivity;
import com.i2c.groceryapp.adapter.BaseCategoryADP;
import com.i2c.groceryapp.adapter.RvTodaySpecialListADP;
import com.i2c.groceryapp.databinding.FragmentHomeBinding;
import com.i2c.groceryapp.utils.BaseFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {
    FragmentHomeBinding binding;
    RvTodaySpecialListADP todaySpecialListADP;
    BaseCategoryADP baseCategoryADP;


    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpContorls(view);
    }

    private void setUpContorls(View view) {
        // Today's special
        binding.rvTodaySpecial.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.rvTodaySpecial.setLayoutManager(manager);
        todaySpecialListADP = new RvTodaySpecialListADP(getActivity());
        binding.rvTodaySpecial.setAdapter(todaySpecialListADP);

        // Base Category
        binding.rvBaseCategory.setHasFixedSize(false);
        LinearLayoutManager manager_base = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.rvBaseCategory.setLayoutManager(manager_base);
        baseCategoryADP = new BaseCategoryADP(getActivity());
        binding.rvBaseCategory.setAdapter(baseCategoryADP);

        binding.llCategory.setOnClickListener(this);
        binding.llFreebies.setOnClickListener(this);
        binding.llTradeOffer.setOnClickListener(this);
        binding.llOffer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCategory:
                getRootActivity().binding.viewPager.setCurrentItem(1);
                break;

            case R.id.llFreebies:
                startActivity(new Intent(getActivity(), FreebiesActivity.class));
                break;

            case R.id.llTradeOffer:
                startActivity(new Intent(getActivity(), TradeOfferActivity.class));
                break;

            case R.id.llOffer:
                break;
        }
    }

    private HomeActivity getRootActivity() {
        return (HomeActivity) getActivity();
    }
}