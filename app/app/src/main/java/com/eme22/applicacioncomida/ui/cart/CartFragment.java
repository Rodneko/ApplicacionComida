package com.eme22.applicacioncomida.ui.cart;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eme22.applicacioncomida.R;
import com.eme22.applicacioncomida.data.model.CartItem;
import com.eme22.applicacioncomida.data.model.Item;
import com.eme22.applicacioncomida.data.model.Promo;
import com.eme22.applicacioncomida.data.model.User;
import com.eme22.applicacioncomida.databinding.FragmentCartBinding;
import com.eme22.applicacioncomida.ui.bought.BoughtFragment;
import com.eme22.applicacioncomida.ui.main.MainActivity;
import com.eme22.applicacioncomida.ui.pago.PagoActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    private CartViewModel mViewModel;

    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;


    private static final DecimalFormat df = new DecimalFormat("0.00");


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 666 || result.getResultCode() == 777) {
                        mViewModel.submitCart();

                    }
                }
            });

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView();
        initData();

        return view;
    }

    private void initData() {

        User user =  ((MainActivity) requireActivity()).getUser();

        mViewModel.getCart().observe(getViewLifecycleOwner(), cart -> {

            ArrayList<CartItem> items = cart.getCartItems();

            for (int i = 0; i < items.size(); i++) {
                CartItem cartItem = items.get(i);
                Item item = cartItem.getItem();
                Promo itemPromo = item.getPromo();
            }
            cartAdapter.clear();
            cartAdapter.addAll(items);
        });

        mViewModel.isHasItems().observe(getViewLifecycleOwner(), cart -> {
            if (cart) {
                binding.buyLayout.setVisibility(View.VISIBLE);
                binding.emptyCart.setVisibility(View.GONE);
            }
            else {
                binding.emptyCart.setVisibility(View.VISIBLE);
                binding.buyLayout.setVisibility(View.GONE);
            }
        });

        mViewModel.getCartPrice().observe(getViewLifecycleOwner(), new Observer<BigDecimal>() {
            @Override
            public void onChanged(BigDecimal bigDecimal) {
                binding.cartPriceText.setText(MessageFormat.format("{0} {1}", getString(R.string.currency), df.format(bigDecimal)));
            }
        });

        mViewModel.getCurrentCart(user);


    }

    private void initView() {
        mViewModel =  new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        RecyclerView recycler = binding.cartRecyler;
        cartAdapter = new CartAdapter(this::deleteCart);
        recycler.setAdapter(cartAdapter);
        binding.cartBuyButton.setOnClickListener(v -> buyCurrentCart());
    }

    private void buyCurrentCart() {

        Intent intent = new Intent(requireActivity(), PagoActivity.class);
        someActivityResultLauncher.launch(intent);

    }

    private void deleteCart(CartItem cart) {

        new AlertDialog.Builder(requireContext())
                .setMessage("??Estas segudo de borrar este elemento?")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    mViewModel.deleteCartItem(cart);
                })
                .setNegativeButton(android.R.string.cancel,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}