package com.example.yourrpg.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private RetrofitAPI myRetrofitAPI;

    public RetrofitClient(String url) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myRetrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static synchronized RetrofitClient getInstance(String url) {
        if (instance == null) {
            instance = new RetrofitClient(url);
        }
        return instance;
    }

    public RetrofitAPI getMyRetrofitAPI() {
        return myRetrofitAPI;
    }

}
