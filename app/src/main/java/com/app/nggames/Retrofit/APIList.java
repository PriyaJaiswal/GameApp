package com.app.nggames.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIList {
    public static Retrofit retrofit = null;
    public static final String BASEURL = "https://atomswebsites.com/nggames/public/api/auth/";


    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getApClient(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50,TimeUnit.SECONDS).build();
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASEURL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
