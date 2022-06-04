package com.eme22.applicacioncomida.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eme22.applicacioncomida.data.api.WebApiAdapter;
import com.eme22.applicacioncomida.data.api.WebApiService;
import com.eme22.applicacioncomida.data.model.Cart;
import com.eme22.applicacioncomida.data.model.CartItem;
import com.eme22.applicacioncomida.data.model.Category;
import com.eme22.applicacioncomida.data.model.Item;
import com.eme22.applicacioncomida.data.model.Promo;
import com.eme22.applicacioncomida.data.model.Result;
import com.eme22.applicacioncomida.data.model.User;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {

    private final WebApiService service = WebApiAdapter.getApiService();

    private final MutableLiveData<Cart> cart = new MutableLiveData<>();

    LiveData<Cart> getCart() {
        return cart;
    }

    private boolean hasCar = false;

    private boolean hasItems = false;

    public void getCurrentCart(User user) {

        Call<Cart> call = service.getCurrentCart(Math.toIntExact(user.getId()));

        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {

                    Cart cart1 = response.body();
                    cart.setValue(cart1);
                }
                else {
                    cart.setValue(new Cart(0, user, user.getId(), new ArrayList<>(), false, ""));
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });

    }

    public Item retrieveItem(Integer id) throws IOException {
        Response<Item> itemdata = service.getItem(id).execute();

        if (itemdata.isSuccessful()) {
            return itemdata.body();
        }

        return null;

    }

    public void addItem(CartItem cartItem) {
        Cart cart1 = cart.getValue();

        ArrayList<CartItem> items = null;
        if (cart1 != null) {
            items = cart1.getCartItems();
            items.add(cartItem);
            cart1.setCartItems(items);
            cart.setValue(cart1);
        }
    }


    public void deleteCartItem(CartItem cartItem) {
        Cart cart1 = cart.getValue();

        ArrayList<CartItem> items = null;
        if (cart1 != null) {
            items = cart1.getCartItems();
            items.remove(cartItem);
            cart1.setCartItems(items);
            cart.setValue(cart1);
        }
    }

    public Promo retrievePromo(int id) throws IOException {

        Response<Promo> itemdata = service.getPromo(id).execute();

        if (itemdata.isSuccessful()) {
            return itemdata.body();
        }

        return null;

    }
}