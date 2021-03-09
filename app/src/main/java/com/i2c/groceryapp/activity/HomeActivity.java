package com.i2c.groceryapp.activity;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.FragmentAdapter;
import com.i2c.groceryapp.adapter.RvMenuADP;
import com.i2c.groceryapp.databinding.ActivityHomeBinding;
import com.i2c.groceryapp.fragment.CategoryFrgmt;
import com.i2c.groceryapp.fragment.HomeFragment;
import com.i2c.groceryapp.fragment.OfferFragment;
import com.i2c.groceryapp.fragment.RiewBasketFragment;
import com.i2c.groceryapp.model.DataModel;
import com.i2c.groceryapp.model.MyData;
import com.i2c.groceryapp.utils.BaseActivity;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements RvMenuADP.OpenFragment {
    public ActivityHomeBinding binding;
    private ArrayList<Fragment> fragments;
    private ArrayList<DataModel> arrayList = new ArrayList<>();
    private ArrayList<String> ALL_MENU = new ArrayList<>();
    private RvMenuADP adp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        serDrawerLayout();
        fragments = new ArrayList<>();

        fragments.add(new HomeFragment());
        fragments.add(new CategoryFrgmt());
        fragments.add(new OfferFragment());
        fragments.add(new RiewBasketFragment());

        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext(), fragments);
        binding.viewPager.setAdapter(pagerAdapter);

        binding.tabs.setupWithViewPager(binding.viewPager);

        binding.tabs.getTabAt(0).setIcon(R.drawable.ic_home);
        binding.tabs.getTabAt(1).setIcon(R.drawable.ic_category);
        binding.tabs.getTabAt(2).setIcon(R.drawable.ic_offer);
        binding.tabs.getTabAt(3).setIcon(R.drawable.ic_cart);

        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#229F68"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        binding.ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, NotifictionActivity.class));
            }
        });
    }

    private void serDrawerLayout() {
        binding.rvMenuList.setHasFixedSize(false);

        LinearLayoutManager manager_menu = new LinearLayoutManager(this);
        binding.rvMenuList.setLayoutManager(manager_menu);

        ALL_MENU.clear();
        ALL_MENU.add(getString(R.string.My_Order));
        ALL_MENU.add(getString(R.string.My_Favourite));
        ALL_MENU.add(getString(R.string.Become_a_Prime_member));
        ALL_MENU.add(getString(R.string.Profile));
        ALL_MENU.add(getString(R.string.Change_Language));
        ALL_MENU.add(getString(R.string.Change_Password));
        ALL_MENU.add(getString(R.string.Contact_Us));
        ALL_MENU.add(getString(R.string.Logout));

        for (int i = 0; i < ALL_MENU.size(); i++) {
            arrayList.add(new DataModel(ALL_MENU.get(i), MyData.ICON[i]));
        }

        adp = new RvMenuADP(this, arrayList, this);
        binding.rvMenuList.setAdapter(adp);
    }


    @Override
    public void openFragmt(int pos) {
        Log.e("TAG", "openFragmt:open_frg::::" + pos);
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers();
        }

        switch (pos) {
            case 0:
                launchActivity(HomeActivity.this, MyOrderActivity.class);
                break;

            case 1:
                launchActivity(HomeActivity.this, MyFavouriteActivity.class);
                break;

            case 2:
                launchActivity(HomeActivity.this, Become_prime_memberActivity.class);
                break;

            case 3:
                launchActivity(HomeActivity.this, ProfileActivity.class);
                break;

            case 4:
                launchActivity(HomeActivity.this, ChangeLanguageActivity.class);
                break;

            case 5:
                launchActivity(HomeActivity.this, ChangePasswordActivity.class);
                break;

            case 6:
                launchActivity(HomeActivity.this, ContactUsActivity.class);
                break;

            case 7:
                launchActivity(HomeActivity.this, MyFavouriteActivity.class);
                break;
        }
    }
}