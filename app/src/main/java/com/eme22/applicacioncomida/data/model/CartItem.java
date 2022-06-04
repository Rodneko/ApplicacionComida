package com.eme22.applicacioncomida.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

public final class CartItem {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("cart")
    @Expose
    @Nullable
    private String cart;
    @SerializedName("cartId")
    @Expose
    private long cartId;
    @SerializedName("itemId")
    @Expose
    private long itemId;
    @SerializedName("count")
    @Expose
    private long count;

    public CartItem(long id, @Nullable String cart, long cartId, long itemId, long count) {
        this.id = id;
        this.cart = cart;
        this.cartId = cartId;
        this.itemId = itemId;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    @Nullable
    public String getCart() {
        return cart;
    }

    public long getCartId() {
        return cartId;
    }

    public long getItemId() {
        return itemId;
    }

    public long getCount() {
        return count;
    }
}

