package com.i2c.groceryapp.fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.BaseCategoryADP;
import com.i2c.groceryapp.adapter.RvAllCategoryADP;
import com.i2c.groceryapp.databinding.FragmentCategoryBinding;
import com.i2c.groceryapp.model.Category;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.utils.CommonUtils;
import com.i2c.groceryapp.utils.Constant;
import com.i2c.groceryapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFrgmt extends Fragment {
    FragmentCategoryBinding binding;
    private RvAllCategoryADP allCategoryADP;
    private ArrayList<Category> categorylist = new ArrayList<>();
    SessionManager sessionManager;

    public CategoryFrgmt() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpContorls(view);
    }

    private void setUpContorls(View view) {
        sessionManager = new SessionManager(getActivity());

        binding.rvAllCategory.setHasFixedSize(false);
        LinearLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        binding.rvAllCategory.setLayoutManager(manager);
    }


    @Override
    public void onResume() {
        callGetCategoryAPI();
        super.onResume();
    }

    private void callGetCategoryAPI() {
        if (!CommonUtils.isInternetOn(getActivity())) {
            CommonUtils.showToast(getActivity(), getString(R.string.check_internet));
            return;
        }
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Category>> callAPI = apiInterface.categories_list(
                sessionManager.getStringValue(Constant.API_TOKEN));

        callAPI.enqueue(new Callback<ListResponse<Category>>() {
            @Override
            public void onResponse(Call<ListResponse<Category>> call, Response<ListResponse<Category>> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){
                        binding.rvAllCategory.setVisibility(View.VISIBLE);
                        binding.tvNotData.setVisibility(View.GONE);

                        categorylist.clear();
                        categorylist.addAll(response.body().getData());

                        if (categorylist.size() != 0) {
                            allCategoryADP = new RvAllCategoryADP(getActivity(), categorylist);
                            binding.rvAllCategory.setAdapter(allCategoryADP);
                            allCategoryADP.notifyDataSetChanged();
                        }

                    }else if(response.body().getSuccess().equals("0")){

                    }else {

                    }
                }else if(response.code()==404){
                    binding.rvAllCategory.setVisibility(View.GONE);
                    binding.tvNotData.setVisibility(View.VISIBLE);
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<Category>> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
            }
        });
    }
}