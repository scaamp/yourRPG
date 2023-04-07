package com.example.yourrpg.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourrpg.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewQuestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String NEW_QUEST = "NEW_QUEST";
    private EditText questDeadlineEditText;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_questlog);
        setTitle("New quest");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        questDeadlineEditText = (EditText) findViewById(R.id.questDeadlineEditText);
        questDeadlineEditText.setText(getCurrentDate());
        questDeadlineEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewQuestActivity.this, NewQuestActivity.this, year, month, day);

                datePickerDialog.show();
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

    private String getCurrentDate()
    {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        questDeadlineEditText.setText(dateFormat.format(calendar.getTime()));
    }
}
