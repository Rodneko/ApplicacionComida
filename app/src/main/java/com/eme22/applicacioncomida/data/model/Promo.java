package com.eme22.applicacioncomida.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

public final class Promo {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    @Nullable
    private String name;
    @SerializedName("description")
    @Expose
    @Nullable
    private String description;
    @SerializedName("image")
    @Expose
    @Nullable
    private String image;
    @SerializedName("discount")
    @Expose
    private double discount;

    public Promo(long id, @Nullable String name, @Nullable String description, @Nullable String image, double discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.discount = discount;
    }

    public long getId() {
        return id;
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
    public String getImage() {
        return image;
    }

    public double getDiscount() {
        return discount;
    }
}
