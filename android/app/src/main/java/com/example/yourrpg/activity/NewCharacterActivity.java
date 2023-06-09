package com.example.yourrpg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.retrofit.RetrofitAPI;
import com.example.yourrpg.retrofit.RetrofitClient;
import com.example.yourrpg.model.Character;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewCharacterActivity extends AppCompatActivity {
    private Button doneCharacterButton;
    private Button addStrengthButton;
    private TextView strengthPoints;
    private TextView agilityPoints;
    public static final String NEW_CHARACTER = "NEW_CHARACTER";
    public RetrofitClient retrofitClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_character);
        setTitle("Create your character");
        init();
    }

    public void init()
    {
        strengthPoints = (TextView) findViewById(R.id.strengthPoints);
        agilityPoints = (TextView) findViewById(R.id.agilityPoints);
        addStrengthButton = (Button) findViewById(R.id.addStrengthButton);
        addStrengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int strengthPointsInt = Integer.parseInt(strengthPoints.getText().toString());
                if (strengthPointsInt < 5 ) {
                    strengthPointsInt++;
                    strengthPoints.setText(String.valueOf(strengthPointsInt));
                }
            }
        });
        doneCharacterButton = (Button) findViewById(R.id.doneCreationCharacterButton);
        doneCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Character character = new Character(1, "XD", Integer.parseInt(strengthPoints.getText().toString()), Integer.parseInt(agilityPoints.getText().toString()));
                Character character = new Character(UUID.randomUUID(), "scamp", 1, 0, Integer.parseInt(strengthPoints.getText().toString()), Integer.parseInt(agilityPoints.getText().toString()));
                //postData(1, "scamp", 1, 0, Integer.parseInt(strengthPoints.getText().toString()), Integer.parseInt(agilityPoints.getText().toString()));

                postData(character);
                Intent intent = new Intent();
                intent.putExtra(NEW_CHARACTER, character);
                setResult(Activity.RESULT_OK, intent);
                //Toast.makeText(NewCharacterActivity.this,"Character created!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


    private void postData(UUID characterId, String name, int strength, int agility) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.23.240.3:8090/characters/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Character modal = new Character(characterId, name, strength, agility);
        Call<Character> call = retrofitAPI.addCharacter(modal);
        call.enqueue(new Callback<Character>() {

            @Override
            public void onResponse(Call<Character> call, Response<
                    Character> response) {
                Character responseFromAPI = response.body();
            }
            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                t.getMessage();
            }
        });
    }

//    private void postData(Character c) {
//        retrofitClient = new RetrofitClient(RetrofitAPI.CHARACTER_URL);
//        Call<Character> call = retrofitClient.getMyRetrofitAPI().addCharacter(c.getCharacterId(), c.getName(), c.getLevel(), c.getExp(), c.getStrength(), c.getAgility());
//        call.enqueue(new Callback<Character>() {
//
//            @Override
//            public void onResponse(Call<Character> call, Response<Character> response) {
//                Character responseFromAPI = response.body();
////                Toast.makeText(NewCharacterActivity.this,responseFromAPI.getName(), Toast.LENGTH_LONG).show();
//            }
//            @Override
//            public void onFailure(Call<Character> call, Throwable t) {
//                t.getMessage();
//            }
//        });
//    }


    private void postData(Character character) {
        retrofitClient = new RetrofitClient(RetrofitAPI.CHARACTER_URL);
        Call<Character> call = retrofitClient.getMyRetrofitAPI().addCharacter(character);
        call.enqueue(new Callback<Character>() {

            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Character responseFromAPI = response.body();
            }
            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
