package com.eme22.applicacioncomida.data.api;

import com.eme22.applicacioncomida.data.model.Cart;
import com.eme22.applicacioncomida.data.model.Category;
import com.eme22.applicacioncomida.data.model.Item;
import com.eme22.applicacioncomida.data.model.Login;
import com.eme22.applicacioncomida.data.model.Promo;
import com.eme22.applicacioncomida.data.model.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface WebApiService {

    //USERS

    @GET("users/all")
    Call<ArrayList<User>> users();

    @GET("users/id/{id}")
    Call<User> getUser(
            @Path("id")Integer id
    );

    @DELETE("users/id/{id}")
    Call<ResponseBody> deleteUser(
            @Path("id") Integer id
    );

    @Multipart
    @POST("users")
    Call<User> uploadUser(
            @Part("firstName")String firstName,
            @Part("lastName")String lastName,
            @Part("email")String email,
            @Part("passwordHash")String passwordHash,
            @Part("address")String address,
            @Part("phone")Integer phone,
            @Part("admin")Boolean admin,
            @Part MultipartBody.Part data
    );

    @FormUrlEncoded
    @POST("users/login")
    Call<User> getLogin(
            @Field("email")String username,
            @Field("passwordHash")String password
    );

    @GET("users/count")
    Call<Login> count();

    @GET("users/exist/{email}")
    Call<Login> exist(
            @Path("email") String username
    );

    //CATEGORIES

    @GET("categories")
    Call<ArrayList<Category>> categories();

    @GET("categories/{id}")
    Call<ResponseBody> getCategory(
            @Path("id") Integer id
    );

    @DELETE("categories/{id}")
    Call<ResponseBody> deleteCategory(
            @Path("id") Integer id
    );

    @Multipart
    @POST("categories")
    Call<ResponseBody> uploadCategories(
            @Part("name")String name,
            @Part("description") String description,
            @Part MultipartBody.Part data
    );

    //CART

    @GET("carts/all")
    Call<ArrayList<Cart>> carts();

    @GET("carts/all/{userId}")
    Call<ArrayList<Cart>> userCarts(
            @Path("userId")Integer userId
    );

    @GET("carts/id/{id}")
    Call<Cart> getCart(
            @Path("id")Integer id
    );

    @DELETE("carts/id/{id}")
    Call<ResponseBody> deleteCart(
            @Path("id")Integer id
    );

    @GET("carts/current/{userId}")
    Call<Cart> getCurrentCart(
            @Path("userId")Integer userId
    );

    @POST("carts")
    Call<Cart> uploadCart(
            @Part("name")String name ,
            @Part("description")String description,
            @Part  MultipartBody.Part data
            );

    //PROMO

    @GET("promos")
    Call<ArrayList<Promo>> promos();

    @GET("promos/{id}")
    Call<Promo> getPromo(
            @Path("id")Integer id
    );

    //Items

    @GET("items/{id}")
    Call<Item> getItem(
            @Path("id")Integer id
    );


}
