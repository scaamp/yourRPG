package com.example.yourrpg.retrofit;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Spellbook;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {
    String AFFIRMATION_URL = "https://www.affirmations.dev";
    String SPELLBOOK_URL = "http://172.23.240.3:8090/api/spellbooks/";
    String CHARACTER_URL = "http://172.23.240.3:8090/api/characters/";
    String QUESTLOG_URL = "http://172.23.240.3:8090/api/quests/";

    @POST("/api/spellbooks")
    Call<Spellbook> addSpell(@Body Spellbook spellbook);

    @POST("/api/characters")
    Call<Character> addCharacter(@Body Character character);

    @DELETE("/api/spellbooks/{id}")
    Call<ResponseBody> deleteSpell(@Path("id") int spellbookId);

    @GET("https://www.affirmations.dev")
    Call<Spellbook> getAffirmation();

    @Headers({"Content-Type: application/json"})
    @PUT("/api/characters/{id}")
    Call<Character> updateCharacter(@Path("id") long id, @Body Character character);

}

