package com.eme22.applicacioncomida.ui.home;

import static com.eme22.applicacioncomida.data.api.WebApiAdapter.API_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eme22.applicacioncomida.R;
import com.eme22.applicacioncomida.data.model.Category;
import com.eme22.applicacioncomida.data.model.Item;
import com.eme22.applicacioncomida.data.model.Promo;
import com.eme22.applicacioncomida.data.model.SearchResult;
import com.eme22.applicacioncomida.data.model.User;
import com.eme22.applicacioncomida.databinding.FragmentHomeBinding;
import com.eme22.applicacioncomida.ui.buy.BuyFragment;
import com.eme22.applicacioncomida.ui.cart.CartViewModel;
import com.eme22.applicacioncomida.ui.category.CategoryAdapter;
import com.eme22.applicacioncomida.ui.category.CategoryViewModel;
import com.eme22.applicacioncomida.ui.category_item.CategoryItem;
import com.eme22.applicacioncomida.ui.main.MainActivity;
import com.eme22.applicacioncomida.ui.user.UserFragment;
import com.eme22.floatingsearchview.FloatingSearchView;
import com.eme22.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.eme22.floatingsearchview.suggestions.model.SearchSuggestion;
import com.eme22.floatingsearchview.util.Util;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    private HomeViewModel mViewModel;

    private FragmentHomeBinding binding;

    private int currentItem = 0;

    private CategoryAdapter categoryAdapter;

    private PromoAdapter promoAdapter;


    private boolean recycler1Ready = false;

    private boolean recycler2Ready = false;

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

        User user =  ((MainActivity) requireActivity()).getUser() ;

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Bienvenido "+ user.getFirstName());

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

        binding.floatingSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            if (!oldQuery.equals("") && newQuery.equals("")) {
                binding.floatingSearchView.clearSuggestions();
            } else {
                binding.floatingSearchView.showProgress();
                mViewModel.retrieveSearch(newQuery);
            }
        });

        binding.floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                SearchResult result = (SearchResult) searchSuggestion;

                switch (result.getCategory()) {
                    default: return;
                    case "item": mViewModel.retrieveItem(result.getId()); return;
                    case "category": mViewModel.retrieveCategory(result.getId());
                }

            }

            @Override
            public void onSearchAction(String query) {

            }
        });

        binding.floatingSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {

                Picasso.get().load(API_URL +((SearchResult) item).getImage()).into(leftIcon);
                //textView.setTextColor(Color.parseColor(textColor));
            }
        });


        mViewModel.getSearch().observe( requireActivity(), searchResults -> {
            binding.floatingSearchView.swapSuggestions(searchResults);
            binding.floatingSearchView.hideProgress();
        });

    }

    private void loadCategory(Category category) {
        new ViewModelProvider(requireActivity()).get(CategoryViewModel.class).setSelected(category);
        FragmentTransaction fTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fTransaction.addToBackStack(null);
        fTransaction.add(R.id.main_fragment, CategoryItem.newInstance(category.getName()), "CategoryItem");
        fTransaction.commit();
    }

    private void loadPromo(Promo promo) {

        /*
        FragmentTransaction fTransaction = getParentFragmentManager().beginTransaction();
        fTransaction.addToBackStack(null);
        fTransaction.add(R.id.main_fragment, UserFragment.newInstance(), "PromoItem");
        fTransaction.commit();
        */
    }

    private void initData() {

        mViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                Collections.shuffle(categories);
                int size = Math.min(categories.size(), 3);
                categoryAdapter.addAll(categories.subList(0, size));
                recycler1Ready = true;
                checkReady();
            }
        });

        mViewModel.getPromos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Promo>>() {
            @Override
            public void onChanged(ArrayList<Promo> promos) {

                int size = Math.min(promos.size(), 4);

                promos = new ArrayList<>(promos.subList(0, size));
                promoAdapter.addAll(promos);
                autoScroll();
                recycler2Ready = true;
                checkReady();
            }
        });

        mViewModel.getSelectedItem().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                loadItem(item);
            }
        });

        mViewModel.getSelectedCategory().observe(getViewLifecycleOwner(), new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                loadCategory(category);
            }
        });

        mViewModel.retrieveCategories();
        mViewModel.retrievePromos();

    }

    private void loadItem(Item item) {
        FragmentTransaction fTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fTransaction.addToBackStack(null);
        fTransaction.add(R.id.main_fragment, BuyFragment.newInstance(item), "BuyItem");
        fTransaction.commit();
    }

    private void checkReady() {
        if (recycler1Ready && recycler2Ready){
            binding.fragmentHomeContent.setVisibility(View.VISIBLE);
            binding.floatingSearchView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
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


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        binding.floatingSearchView.setTranslationY(verticalOffset);
    }
}