package com.eme22.applicacioncomida.ui.cart;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme22.applicacioncomida.R;
import com.eme22.applicacioncomida.data.model.Cart;
import com.eme22.applicacioncomida.data.model.CartItem;
import com.eme22.applicacioncomida.data.model.Item;
import com.eme22.applicacioncomida.data.model.Promo;
import com.eme22.applicacioncomida.data.model.User;
import com.eme22.applicacioncomida.databinding.FragmentCartBinding;
import com.eme22.applicacioncomida.ui.login.LoginActivity;
import com.eme22.applicacioncomida.ui.main.MainActivity;
import com.eme22.applicacioncomida.ui.register.RegisterActivity;

import java.io.IOException;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    private CartViewModel mViewModel;

    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;

    private Double precioAcc = 0.00;

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

        User user =  ((MainActivity) getActivity()).user;

        mViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {


                ArrayList<CartItem> items = cart.getCartItems();

                int j = 0;
                for (int i = 0; i < items.size() ; i++) {

                    CartItem item = items.get(i);

                    try {
                        Item item1 = mViewModel.retrieveItem(Math.toIntExact(item.getItemId()));

                        double discount = 0;

                        if (item1.getPromoId() != null) {

                            Promo promo = mViewModel.retrievePromo(Math.toIntExact(item1.getPromoId()));

                            discount = promo.getDiscount();

                        }

                        precioAcc += item1.getPrice() - (item1.getPrice() * discount);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    j++;
                }

                if (j > 0)
                    binding.cartPriceText.setText("S/. "+ precioAcc);

            }
        });

        mViewModel.getCurrentCart(user);


    }

    private void initView() {
        mViewModel =  new ViewModelProvider(this).get(CartViewModel.class);

        Button button = binding.cartBuyButton;
        TextView priceText = binding.cartPriceText;
        LinearLayout buyLayout = binding.buyLayout;
        ImageView empty = binding.emptyCart;

        RecyclerView recycler = binding.cartRecyler;
        cartAdapter = new CartAdapter( cart -> deleteCart(cart) );
        recycler.setAdapter(cartAdapter);
    }

    private void deleteCart(CartItem cart) {

        new AlertDialog.Builder(requireContext())
                .setMessage("¿Estas segudo de borrar este elemento?")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    mViewModel.deleteCartItem(cart);
                })
                .setNegativeButton(android.R.string.cancel,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}