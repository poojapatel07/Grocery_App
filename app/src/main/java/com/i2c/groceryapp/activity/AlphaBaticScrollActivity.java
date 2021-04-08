package com.i2c.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvAlphabaticScrollADP;
import com.i2c.groceryapp.databinding.ActivityAlphaBaticScrollBinding;
import com.i2c.groceryapp.model.All_SubCategoryList;
import com.i2c.groceryapp.model.ReviewCartModel;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlphaBaticScrollActivity extends BaseActivity implements RvAlphabaticScrollADP.OnClickOpenCategory {
    ActivityAlphaBaticScrollBinding binding;
    private RvAlphabaticScrollADP adp;
    private ArrayList<All_SubCategoryList> allSubcategoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alpha_batic_scroll);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvCategoryAlphabetical.setLayoutManager(manager);

        binding.rvCategoryAlphabetical.setIndexTextSize(12);
        binding.rvCategoryAlphabetical.setIndexBarTextColor("#292929");
        binding.rvCategoryAlphabetical.setIndexBarColor("#cdced2");
        binding.rvCategoryAlphabetical.setIndexbarHighLateTextColor("#b0c916");
        binding.rvCategoryAlphabetical.setIndexBarHighLateTextVisibility(true);
        binding.rvCategoryAlphabetical.setIndexBarTransparentValue((float) 1.0);


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        callAllSubCategoryDataAPI();
    }

    private void callAllSubCategoryDataAPI() {
        if (!isInternetOn(this)) {
            showToast(getResources().getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<ListResponse<All_SubCategoryList>> callAPI = apiInterface.all_brand_companie_list(
                sessionManager.getStringValue(Constant.API_TOKEN));

        callAPI.enqueue(new Callback<ListResponse<All_SubCategoryList>>() {
            @Override
            public void onResponse(Call<ListResponse<All_SubCategoryList>> call, Response<ListResponse<All_SubCategoryList>> response) {
                Log.e("TAG", "onResponse:LIST::::" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1")) {
                        Comparator<All_SubCategoryList> comparator = new
                                Comparator<All_SubCategoryList>() {
                                    @Override
                                    public int compare(All_SubCategoryList lhs,
                                                       All_SubCategoryList rhs) {
                                        String left = lhs.getName().toUpperCase();
                                        String right = rhs.getName().toUpperCase();

                                        return left.compareTo(right);
                                    }
                                };

                        allSubcategoryList.clear();
                        allSubcategoryList.addAll(response.body().getData());
                        Collections.sort(allSubcategoryList, comparator);

                        if (allSubcategoryList.size() != 0) {
                            binding.rvCategoryAlphabetical.setVisibility(View.VISIBLE);
                            binding.tvNoData.setVisibility(View.GONE);
                            adp = new RvAlphabaticScrollADP(AlphaBaticScrollActivity.this,
                                    allSubcategoryList, AlphaBaticScrollActivity.this);
                            binding.rvCategoryAlphabetical.setAdapter(adp);

                            adp.notifyDataSetChanged();

                        } else {
                            binding.rvCategoryAlphabetical.setVisibility(View.GONE);
                            binding.tvNoData.setVisibility(View.VISIBLE);
                        }

                    } else if (response.body().getSuccess().equals("0")) {
                        binding.rvCategoryAlphabetical.setVisibility(View.GONE);
                        binding.tvNoData.setVisibility(View.VISIBLE);
                    } else {

                    }
                }else if(response.code()==404){
                    binding.rvCategoryAlphabetical.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<All_SubCategoryList>> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }

    @Override
    public void openCategory(String brand_company_id) {
        Log.e("TAG", "openCategory:brand_company_id:::::"+brand_company_id);

        sessionManager.setStringValue(Constant.BRAND_COMPANY_ID, brand_company_id);
        startActivity(new Intent(this, BrandCompanyProductListActivity.class));
    }
}