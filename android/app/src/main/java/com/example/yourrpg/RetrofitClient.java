package com.example.yourrpg;

import com.example.yourrpg.model.Spellbook;

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

    public RetrofitAPI createRetrofitClient(String url)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.23.240.3:8090/api/spellbooks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        return retrofitAPI;
    }

}
