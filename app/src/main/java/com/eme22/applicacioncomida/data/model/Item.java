package com.eme22.applicacioncomida.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

public final class Item {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("categoryId")
    @Expose
    private long categoryId;
    @SerializedName("name")
    @Expose
    @Nullable
    private String name;
    @SerializedName("description")
    @Expose
    @Nullable
    private String description;
    @SerializedName("price")
    @Expose
    @Nullable
    private Double price;
    @SerializedName("promoId")
    @Expose
    @Nullable
    private Long promoId;
    @SerializedName("image")
    @Expose
    @Nullable
    private String image;
    @SerializedName("createdAt")
    @Expose
    @Nullable
    private String createdAt;

    public Item(long id, long categoryId, @Nullable String name, @Nullable String description, @Nullable Double price, @Nullable Long promoId, @Nullable String image, @Nullable String createdAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.promoId = promoId;
        this.image = image;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public Double getPrice() {
        return price;
    }

    @Nullable
    public Long getPromoId() {
        return promoId;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    @Nullable
    public String getCreatedAt() {
        return createdAt;
    }
}
