package com.eme22.applicacioncomida.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("email")
    @Expose
    @Nullable
    private String email;
    @SerializedName("passwordHash")
    @Expose
    @Nullable
    private String passwordHash;
    @SerializedName("firstName")
    @Expose
    @Nullable
    private String firstName;
    @SerializedName("lastName")
    @Expose
    @Nullable
    private String lastName;
    @SerializedName("address")
    @Expose
    @Nullable
    private String address;
    @SerializedName("phone")
    @Expose
    @Nullable
    private Long phone;
    @SerializedName("admin")
    @Expose
    private boolean admin;
    @SerializedName("image")
    @Expose
    @Nullable
    private String image;
    @SerializedName("createdAt")
    @Expose
    @Nullable
    private String createdAt;

    public User(long id, @Nullable String email, @Nullable String passwordHash, @Nullable String firstName, @Nullable String lastName, @Nullable String address, @Nullable Long phone, boolean admin, @Nullable String image, @Nullable String createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.admin = admin;
        this.image = image;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getPasswordHash() {
        return passwordHash;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    @Nullable
    public Long getPhone() {
        return phone;
    }

    public boolean isAdmin() {
        return admin;
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
