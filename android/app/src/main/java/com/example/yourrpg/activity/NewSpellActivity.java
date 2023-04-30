package com.example.yourrpg.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourrpg.R;
import com.example.yourrpg.retrofit.RetrofitAPI;
import com.example.yourrpg.retrofit.RetrofitClient;
import com.example.yourrpg.model.Spellbook;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSpellActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText textSpellbook;
    private EditText trainerSpellbook;
    private EditText dateSpellbook;
    private Button addSpellButton;
    private ImageButton addRandomSpellButton;
    private Spinner spinnerSpellbookRank;
    private DateFormat dateFormat;
    public static final String NEW_SPELL = "NEW_SPELL";
    private RetrofitClient retrofitClient;
    private List<Spellbook> spellbooks;

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
        getSpells();
        addSpellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textSpellbook.getText().toString().isEmpty()) {
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
                } else {
                    Spellbook spellbook = new Spellbook(UUID.randomUUID(), textSpellbook.getText().toString(), trainerSpellbook.getText().toString(),
                            spinnerSpellbookRank.getSelectedItem().toString(), getDateEditTextDate());
                    postData(spellbook);
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

    private void postData(Spellbook spellbook) {

        retrofitClient = new RetrofitClient(RetrofitAPI.SPELLBOOK_URL);
        Call<Spellbook> call = retrofitClient.getMyRetrofitAPI().addSpell(spellbook);
        call.enqueue(new Callback<Spellbook>() {

            @Override
            public void onResponse(Call<Spellbook> call, Response<Spellbook> response) {
                Spellbook responseFromAPI = response.body();
                spellbook.setSpellbookId(response.body().getSpellbookId());
                //spellId = responseFromAPI.getSpellbookId();
            }

            @Override
            public void onFailure(Call<Spellbook> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    public List<Spellbook> getSpells() {
        retrofitClient = new RetrofitClient(RetrofitAPI.SPELLBOOK_URL);
        Call<List<Spellbook>> call = retrofitClient.getMyRetrofitAPI().getSpells();

        call.enqueue(new Callback<List<Spellbook>>() {

            @Override
            public void onResponse(Call<List<Spellbook>> call, Response<List<Spellbook>> response) {
                spellbooks = response.body();
            }

            @Override
            public void onFailure(Call<List<Spellbook>> call, Throwable t) {
                t.getMessage();
            }
        });
        return spellbooks;
    }

    private void getAffirmation() {
        Call<Spellbook> call = RetrofitClient.getInstance(RetrofitAPI.AFFIRMATION_URL).getMyRetrofitAPI().getAffirmation();
        call.enqueue(new Callback<Spellbook>() {
            @Override
            public void onResponse(Call<Spellbook> call, Response<Spellbook> response) {
                String affirmation = response.body().getAffirmation();
                textSpellbook.setText(affirmation);
            }

            @Override
            public void onFailure(Call<Spellbook> call, Throwable t) {
                t.getMessage();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getCurrentDate() {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        dateSpellbook.setText(dateFormat.format(calendar.getTime()));
    }

    private Date getDateEditTextDate() {

//        try
//        {
//            return dateFormat.parse(dateSpellbook.getText().toString());
//        } catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
//
//        dateFormat = DateFormat.getDateInstance();
//        return new Date();

        try {
            SimpleDateFormat parser = new SimpleDateFormat("MMM dd, yyyy");
            Date date = parser.parse(dateSpellbook.getText().toString());
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aa", Locale.ENGLISH);

            String formattedDate = formatter.format(date);
            Date date1 = formatter.parse(formattedDate);

            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();


        //        SimpleDateFormat formatter = new SimpleDateFormat("MM dd, yyyy");
        //        SimpleDateFormat formatterOut = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        //        try {
        //
        //            Date date1 = formatter.parse(dateSpellbook.getText().toString());
        //            System.out.println(date1);
        //            System.out.println(formatterOut.format(date1));
        //            String dateString = formatterOut.format(date1);
        //            Date date2 = formatterOut.parse(dateString);
        //            return date2;
        //
        //        } catch (ParseException e) {
        //            e.printStackTrace();
        //        }
        //        dateFormat = DateFormat.getDateInstance();
        //        return new Date();
        //
    }
}


