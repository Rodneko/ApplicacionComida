package com.eme22.applicacioncomida.ui.category;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eme22.applicacioncomida.R;
import com.eme22.applicacioncomida.data.model.Category;
import com.eme22.applicacioncomida.databinding.FragmentCategoryBinding;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private CategoryViewModel mViewModel;
    private FragmentCategoryBinding binding;
    private CategoryAdapter adapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initView();
        initData();

        return view;
    }


    private void initView() {

        mViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        RecyclerView recycler = binding.categoryRecycler;

        //recycler.setNestedScrollingEnabled(false);

        //recycler.addItemDecoration(MarginDecoration(this))
        //recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CategoryAdapter(
                this::loadCategory
        );
        recycler.setAdapter(adapter);

    }

    private void loadCategory(Category category) {

    }

    private void initData() {
        mViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories.size() == 0)
                binding.categoryNoItems.setVisibility(View.VISIBLE);
            else
                binding.categoryNoItems.setVisibility(View.GONE);


            adapter.addAll(categories);
        });
        mViewModel.retrieveCategories();
    }

}