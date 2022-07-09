package com.eme22.applicacioncomida.ui.user_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eme22.applicacioncomida.data.model.User;
import com.eme22.applicacioncomida.databinding.FragmentUserHistoryBinding;

public class UserHistoryFragment extends Fragment {

    private UserHistoryViewModel mViewModel;
    private FragmentUserHistoryBinding binding;
    private UserHistoryAdapter adapter;

    private final User user;

    private UserHistoryFragment(User user) {
        this.user = user;
    }

    public static UserHistoryFragment newInstance(User user) {
        return new UserHistoryFragment(user);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initView();
        initData();

        return view;
    }


    private void initView() {

        adapter = new UserHistoryAdapter();
        binding.userHistoryFragmentRecyler.setAdapter(adapter);
        binding.userHistoryFragmentToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void initData() {
        mViewModel = new ViewModelProvider(this).get(UserHistoryViewModel.class);

        mViewModel.getCarts().observe(getViewLifecycleOwner(), carts -> {
            if (carts.size() == 0)
                binding.userHistoryFragmentNoItems.setVisibility(View.VISIBLE);
            else
                binding.userHistoryFragmentNoItems.setVisibility(View.GONE);


            adapter.addAll(carts);
        });

        mViewModel.retrieveCarts(Math.toIntExact(user.getId()));

    }

}