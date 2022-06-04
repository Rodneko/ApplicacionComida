package com.eme22.applicacioncomida.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eme22.applicacioncomida.R;
import com.eme22.applicacioncomida.data.model.Category;
import com.eme22.applicacioncomida.data.model.Promo;
import com.eme22.applicacioncomida.data.model.User;
import com.eme22.applicacioncomida.databinding.FragmentHomeBinding;
import com.eme22.applicacioncomida.ui.cart.CartViewModel;
import com.eme22.applicacioncomida.ui.category.CategoryAdapter;
import com.eme22.applicacioncomida.ui.main.MainActivity;
import com.eme22.applicacioncomida.ui.user.UserFragment;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    private FragmentHomeBinding binding;

    private int currentItem = 0;

    private CategoryAdapter categoryAdapter;

    private PromoAdapter promoAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initView();
        initData();

        return view;
    }


    private void initView() {

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        User user =  ((MainActivity) getActivity()).user;

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bienvenido "+ user.getFirstName());

        binding.categoryRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2)); 
        categoryAdapter = new CategoryAdapter (category -> loadCategory(category) );
        binding.categoryRecycler.setAdapter(categoryAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.promosRecycler.setLayoutManager(linearLayoutManager);
        binding.promosRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                currentItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            }
        });
        promoAdapter = new PromoAdapter (promo -> loadPromo(promo) );
        binding.promosRecycler.setAdapter(promoAdapter);
        binding.homeSearch.onActionViewExpanded();
        binding.homeSearch.clearFocus();

    }

    private void loadCategory(Category category) {
    }

    private void loadPromo(Promo promo) {

        FragmentTransaction fTransaction = getParentFragmentManager().beginTransaction();
        fTransaction.addToBackStack(null);
        fTransaction.add(R.id.main_fragment, UserFragment.newInstance(), "PromoItem");
        fTransaction.commit();

    }

    private void initData() {

        mViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                Collections.shuffle(categories);
                int size = Math.min(categories.size(), 3);
                categoryAdapter.addAll(categories.subList(0, size));
            }
        });

        mViewModel.getPromos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Promo>>() {
            @Override
            public void onChanged(ArrayList<Promo> promos) {

                int size = Math.min(promos.size(), 4);

                promos = new ArrayList<>(promos.subList(0, size));
                promoAdapter.addAll(promos);
                autoScroll();
            }
        });

        mViewModel.retrieveCategories();
        mViewModel.retrievePromos();

    }

    private void autoScroll() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentItem++;
                binding.promosRecycler.smoothScrollToPosition(currentItem);
                handler.postDelayed(this, 20000);
            }
        };
        handler.postDelayed(runnable, 20000);

    }


}