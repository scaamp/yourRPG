package com.example.yourrpg;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Spellbook;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("/api/spellbooks")
    Call<Spellbook> createPost(@Body Spellbook spellbook);
    @POST("/api/characters")
    Call<Character> createPost(@Body Character character);

}

