package com.example.yourrpg.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourrpg.R;
import com.example.yourrpg.RetrofitAPI;
import com.example.yourrpg.RetrofitClient;
import com.example.yourrpg.model.Spellbook;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewSpellActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText textSpellbook;
    private EditText trainerSpellbook;
    private EditText dateSpellbook;
    private Button addSpellButton;
    private ImageButton addRandomSpellButton;
    private Spinner spinnerSpellbookRank;
    private DateFormat dateFormat;
    public static final String NEW_SPELL = "NEW_SPELL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_spellbook);
        setTitle("New spell");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textSpellbook = (EditText) findViewById(R.id.spellEditText);
        trainerSpellbook = (EditText) findViewById(R.id.spellTrainerEditText);
        dateSpellbook = (EditText) findViewById(R.id.spellEditTextDate);
        spinnerSpellbookRank = (Spinner) findViewById(R.id.spellStatSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.ranks_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpellbookRank.setAdapter(adapter);
        addSpellButton = (Button) findViewById(R.id.addSpellButton);
        addRandomSpellButton = (ImageButton) findViewById(R.id.getRandomSpellButton);
        addSpellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textSpellbook.getText().toString().isEmpty())
                {
                    new AlertDialog.Builder(NewSpellActivity.this)
                            .setTitle("No spell added")
                            .setMessage("Your spell is empty!\nDo you want to add random generated spell?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getAffirmation();
                                }
                            }).setNegativeButton("No", null)
                            .show();
                }
                else {
                    Spellbook spellbook = new Spellbook(1, textSpellbook.getText().toString(), trainerSpellbook.getText().toString(), spinnerSpellbookRank.getSelectedItem().toString(), getDateEditTextDate());
                    //postData(1, textSpellbook.getText().toString(), trainerSpellbook.getText().toString(), spinnerSpellbookRank.getSelectedItem().toString());
                    Intent intent = new Intent();
                    intent.putExtra(NEW_SPELL, spellbook);
                    setResult(Activity.RESULT_OK, intent);
                    Toast.makeText(NewSpellActivity.this, "Spell added!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        addRandomSpellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAffirmation();
                trainerSpellbook.setText("Random generated");
            }
        });
        dateSpellbook.setText(getCurrentDate());
        dateSpellbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewSpellActivity.this, NewSpellActivity.this, year, month, day);

                datePickerDialog.show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void postData(long id, String text, String trainer, String rank) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://172.23.240.3:8090/api/spellbooks/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
//        Spellbook modal = new Spellbook(id, text, trainer, rank);
//        Call<Spellbook> call = retrofitAPI.createPost(modal);
//        call.enqueue(new Callback<Spellbook>() {
//
//            @Override
//            public void onResponse(Call<Spellbook> call, Response<Spellbook> response) {
//                Spellbook responseFromAPI = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Spellbook> call, Throwable t) {
//                t.getMessage();
//            }
//        });
//    }

    private void getAffirmation() {
        Call<Spellbook> call = RetrofitClient.getInstance().getMyRetrofitAPI().getAffirmation();
        call.enqueue(new Callback<Spellbook>() {
            @Override
            public void onResponse(Call<Spellbook> call, Response<Spellbook> response) {
                String affirmation = response.body().getText();
                textSpellbook.setText(affirmation);
            }

            @Override
            public void onFailure(Call<Spellbook> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getCurrentDate()
    {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        dateSpellbook.setText(dateFormat.format(calendar.getTime()));
    }

    private Date getDateEditTextDate()
    {
        try
        {
            return dateFormat.parse(dateSpellbook.getText().toString());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        dateFormat = DateFormat.getDateInstance();
        return new Date();
    }
}


