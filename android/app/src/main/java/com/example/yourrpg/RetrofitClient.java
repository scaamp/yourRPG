package com.example.yourrpg;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private RetrofitAPI myRetrofitAPI;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RetrofitAPI.AFFIRMATION_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myRetrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public RetrofitAPI getMyRetrofitAPI() {
        return myRetrofitAPI;
    }
}
