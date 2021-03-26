package com.i2c.groceryapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.adapter.ReviewBasketADP;
import com.i2c.groceryapp.databinding.FragmentRiewBasketBinding;
import com.i2c.groceryapp.model.ReviewCartModel;
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


public class RiewBasketFragment extends Fragment {
    FragmentRiewBasketBinding binding;
    private ReviewBasketADP adp;
    private ArrayList<ReviewCartModel>arrayList = new ArrayList<>();
    private SessionManager sessionManager;

    public RiewBasketFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_riew_basket,
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvAllReviewBasket.setLayoutManager(linearLayoutManager);


        callAllCartReviewAPI(true);
    }

    private void callAllCartReviewAPI(Boolean isFirst) {
        if(!CommonUtils.isInternetOn(getActivity())){
            CommonUtils.showToast(getActivity(), getResources().getString(R.string.check_internet));
            return;
        }

        CommonUtils.showCustomLoader(getActivity());
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<ListResponse<ReviewCartModel>> callAPI = apiInterface.cart_list(
                sessionManager.getStringValue(Constant.API_TOKEN));

        callAPI.enqueue(new Callback<ListResponse<ReviewCartModel>>() {
            @Override
            public void onResponse(Call<ListResponse<ReviewCartModel>> call, Response<ListResponse<ReviewCartModel>> response) {
                Log.e("TAG", "onResponse: CALLED::::"+new Gson().toJson(response.body()));
                if(response.body()!=null){
                    if(response.body().getSuccess().equals("1")){

                        arrayList.clear();

                        arrayList.addAll(response.body().getData());
                        adp = new ReviewBasketADP(getActivity(), arrayList);
                        binding.rvAllReviewBasket.setAdapter(adp);

                    }else if(response.body().getSuccess().equals("0")){
                        CommonUtils.showToast(getActivity(), response.body().getMessage());
                    }else {

                    }
                }
                CommonUtils.dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<ListResponse<ReviewCartModel>> call, Throwable t) {
                CommonUtils.dismissCustomLoader();
                CommonUtils.showToast(getActivity(), t.getMessage());
            }
        });
    }
}