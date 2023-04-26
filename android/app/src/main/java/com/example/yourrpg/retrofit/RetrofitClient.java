package com.example.yourrpg.retrofit;

import com.example.yourrpg.model.Spellbook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private RetrofitAPI myRetrofitAPI;
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build();

    public RetrofitClient(String url) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
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
