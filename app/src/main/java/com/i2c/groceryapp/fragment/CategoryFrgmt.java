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
import com.i2c.groceryapp.adapter.RvAllCategoryADP;
import com.i2c.groceryapp.databinding.FragmentCategoryBinding;


public class CategoryFrgmt extends Fragment {
    FragmentCategoryBinding binding;
    private RvAllCategoryADP allCategoryADP;


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
        binding.rvAllCategory.setHasFixedSize(false);
        LinearLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        binding.rvAllCategory.setLayoutManager(manager);

        allCategoryADP = new RvAllCategoryADP(getActivity());
        binding.rvAllCategory.setAdapter(allCategoryADP);
    }

}