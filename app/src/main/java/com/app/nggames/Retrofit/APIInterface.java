package com.app.nggames.Retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {


    @Headers("Content-Type: application/json")
    @POST("login")
    Call<JsonObject> loginUser(@Body JsonObject body);




    @Headers("Content-Type: application/json")
    @POST("register")
    retrofit2.Call<JsonObject> registerUSer(@Body JsonObject body);



    @FormUrlEncoded
    @POST("auth/User")
    retrofit2.Call<JSONObject> UserDetails(@Field("email") String email,
                                         @Field("password") String password);


    @FormUrlEncoded
    @POST("auth/logout")
    retrofit2.Call<JSONObject> logoutUser(@Field("email") String email,
                                         @Field("password") String password);








}
