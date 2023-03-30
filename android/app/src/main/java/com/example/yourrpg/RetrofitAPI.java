package com.example.yourrpg;
import com.example.yourrpg.model.Spellbook;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("/spellbooks")
    Call<Spellbook> createPost(@Body Spellbook spellbook);

}

