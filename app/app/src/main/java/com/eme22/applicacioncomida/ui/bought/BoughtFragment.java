package com.eme22.applicacioncomida.ui.bought;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.eme22.applicacioncomida.databinding.FragmentBoughtBinding;

public class BoughtFragment extends Fragment {

    public static BoughtFragment newInstance() {
        return new BoughtFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentBoughtBinding binding = FragmentBoughtBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.imageButton.setOnClickListener(v -> requireActivity().finish());

        return view;
    }
}