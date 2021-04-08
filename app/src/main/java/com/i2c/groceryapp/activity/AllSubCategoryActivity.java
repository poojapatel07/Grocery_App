package com.i2c.groceryapp.activity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.RvAllCategoryDetailsADP;
import com.i2c.groceryapp.databinding.ActivityAllSubcategoryBinding;
import com.i2c.groceryapp.model.Category;
import com.i2c.groceryapp.model.Subcategories_list;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.CommonUtils;
import com.i2c.groceryapp.utils.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllSubCategoryActivity extends BaseActivity {
    ActivityAllSubcategoryBinding binding;
    private RvAllCategoryDetailsADP allCategoryDetailsADP;
    private ArrayList<Subcategories_list> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_subcategory);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
        Log.e("TAG", "setUpControls: ID::::"+sessionManager.getStringValue(Constant.BASE_CAT_ID));

        binding.tvCategory.setText(sessionManager.getStringValue(Constant.BASE_CAT_NAME));

        binding.rvCategoryDetail.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvCategoryDetail.setLayoutManager(manager);


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        callSubCaegoryList();
        super.onResume();
    }

    private void callSubCaegoryList() {
        if (!CommonUtils.isInternetOn(this)) {
            CommonUtils.showToast(this, getString(R.string.check_internet));
            return;
        }
        showCustomLoader(this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Subcategories_list>> callAPI = apiInterface.subcategories_list(
                sessionManager.getStringValue(Constant.API_TOKEN),
                sessionManager.getStringValue(Constant.BASE_CAT_ID));

        callAPI.enqueue(new Callback<ListResponse<Subcategories_list>>() {
            @Override
            public void onResponse(Call<ListResponse<Subcategories_list>> call, Response<ListResponse<Subcategories_list>> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        binding.rvCategoryDetail.setVisibility(View.VISIBLE);
                        binding.tvNotData.setVisibility(View.GONE);

                        arrayList.clear();
                        arrayList = response.body().getData();
                        allCategoryDetailsADP = new RvAllCategoryDetailsADP(
                                AllSubCategoryActivity.this, arrayList);
                        binding.rvCategoryDetail.setAdapter(allCategoryDetailsADP);

                    }else if(response.body().getSuccess().equals("0")){

                    }else {

                    }
                }else if(response.code()==404){
                    binding.rvCategoryDetail.setVisibility(View.GONE);
                    binding.tvNotData.setVisibility(View.VISIBLE);
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<Subcategories_list>> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }
}