package com.example.yourrpg.retrofit;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Spellbook;

import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {
    String AFFIRMATION_URL = "https://www.affirmations.dev";
    String URL = "http://172.23.240.3:8090/api/";
    //String URL = "http://192.168.0.11:8090/api/";
    String SPELLBOOK_URL = URL + "/spellbooks/";
    String CHARACTER_URL = URL + "/characters/";
    String QUESTLOG_URL = URL + "/quests/";

    @POST("/api/spellbooks")
    Call<Spellbook> addSpell(@Body Spellbook spellbook);

    @POST("/api/characters")
    Call<Character> addCharacter(@Body Character character);

    @POST("/api/characters/oracle")
    Call<String> getAnswerFromOracle(@Body String string);

    @DELETE("/api/spellbooks/{id}")
    Call<ResponseBody> deleteSpell(@Path("id") UUID spellbookId);

    @GET("/api/spellbooks")
    Call<List<Spellbook>> getSpells();

    @GET("/api/characters/quests/{category}")
    Call<String> getQuestsProposition(@Path("category") String category);

    @GET("https://www.affirmations.dev")
    Call<Spellbook> getAffirmation();

    @Headers({"Content-Type: application/json"})
    @PUT("/api/characters/{id}")
    Call<Character> updateCharacter(@Path("id") UUID id, @Body Character character);
}

