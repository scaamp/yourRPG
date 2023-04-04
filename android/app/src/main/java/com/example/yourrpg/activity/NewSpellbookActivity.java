package com.example.yourrpg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourrpg.MainActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.RetrofitAPI;
import com.example.yourrpg.model.Spellbook;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewSpellbookActivity extends AppCompatActivity {
    private EditText textSpellbook;
    private EditText trainerSpellbook;
    private Button sendSpellbookButton;
    private Spinner spinnerSpellbookRank;
    private ArrayAdapter<String> spinnerAdapterRank;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_spellbook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textSpellbook = (EditText) findViewById(R.id.editTextSpellbookText);
        trainerSpellbook = (EditText) findViewById(R.id.editTextSpellbookTrainer);
        spinnerSpellbookRank = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.ranks_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpellbookRank.setAdapter(adapter);
        sendSpellbookButton = (Button) findViewById(R.id.addSpellbookButton);

        sendSpellbookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spellbook spellbook = new Spellbook(1, textSpellbook.getText().toString(), trainerSpellbook.getText().toString(), spinnerSpellbookRank.getSelectedItem().toString());
                postData(1, textSpellbook.getText().toString(), trainerSpellbook.getText().toString(), spinnerSpellbookRank.getSelectedItem().toString());
                Toast.makeText(NewSpellbookActivity.this,"Spell added!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){

            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void postData(long id, String text, String trainer, String rank) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.23.240.3:8090/api/spellbooks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Spellbook modal = new Spellbook(id, text, trainer, rank);
        Call<Spellbook> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Spellbook>() {

            @Override
            public void onResponse(Call<Spellbook> call, Response<Spellbook> response) {
                Spellbook responseFromAPI = response.body();
            }
            @Override
            public void onFailure(Call<Spellbook> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
