package com.example.yourrpg.questlog;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourrpg.R;
import com.example.yourrpg.model.Questlog;
import com.example.yourrpg.retrofit.RetrofitAPI;
import com.example.yourrpg.retrofit.RetrofitClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewQuestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String NEW_QUEST = "NEW_QUEST";
    private EditText questDeadlineEditText;
    private EditText questDeadlineHourEditText;
    private EditText questDescEditText;
    private Spinner questStatSpinner;
    private Spinner questStatPointsSpinner;
    private Button addQuestButton;
    private ImageButton questPropositionButton;
    private DateFormat dateFormat;
    private RetrofitClient retrofitClient;
    private TimePickerDialog picker;
    private String questProposition;
    private TextView questPropositionTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_questlog);
        setTitle("New quest");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createNotificationChannel();

        questDescEditText = (EditText) findViewById(R.id.questDescEditText);
        questStatSpinner = (Spinner) findViewById(R.id.questStatSpinner);
        addQuestButton = (Button) findViewById(R.id.addQuestButton);
        questPropositionButton = (ImageButton) findViewById(R.id.questPropositionButton);
        questPropositionTextView = (TextView) findViewById(R.id.questPropositionTextView);
        questPropositionTextView.setMovementMethod(new ScrollingMovementMethod());
        ArrayAdapter<CharSequence> questStatAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.stats_array, android.R.layout.simple_spinner_item);
        questStatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questStatSpinner.setAdapter(questStatAdapter);

        questStatPointsSpinner = (Spinner) findViewById(R.id.questStatPointsSpinner);
        ArrayAdapter<CharSequence> questStatPointsAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.statsPoints_array, android.R.layout.simple_spinner_item);
        questStatPointsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questStatPointsSpinner.setAdapter(questStatPointsAdapter);

        questDeadlineEditText = (EditText) findViewById(R.id.questDeadlineEditText);
        questDeadlineHourEditText = (EditText) findViewById(R.id.questDeadlineHourEditText);
        questDeadlineHourEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                picker = new TimePickerDialog(NewQuestActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                questDeadlineHourEditText.setText("" + checkDigit(sHour) + ":" + checkDigit(sMinute));
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
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
        questPropositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questProposition = getQuestProposition(questStatSpinner.getSelectedItem().toString());
            }
        });

        addQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date todayDate = new Date();
                if (todayDate.after(getDateEditTextDate())) {
                    Toast.makeText(NewQuestActivity.this, "You can't add a quest from the past", Toast.LENGTH_LONG).show();
                } else {
                    //Questlog questlog = new Questlog(1, "XD", true);
                    Questlog questlog = new Questlog(1, questDescEditText.getText().toString(),
                            questStatSpinner.getSelectedItem().toString(), Integer.valueOf(questStatPointsSpinner.getSelectedItem().toString()), false, getDateEditTextDate());
                    //postData(1, textSpellbook.getText().toString(), trainerSpellbook.getText().toString(), spinnerSpellbookRank.getSelectedItem().toString());

                    if (getDateDiff(todayDate, questlog.getDate(), TimeUnit.HOURS) > 24) {
                        Date dateTask;
                        Calendar c = Calendar.getInstance();
                        c.setTime(questlog.getDate());
                        c.add(Calendar.DATE, -1); // day before deadline
                        dateTask = c.getTime();
                        showAlertNotification(questlog.getDesc(), getDateDiff(todayDate, dateTask, TimeUnit.MILLISECONDS));
                    }

                    Intent intent = new Intent();
                    intent.putExtra(NEW_QUEST, questlog);
                    setResult(Activity.RESULT_OK, intent);
                    Toast.makeText(NewQuestActivity.this, "Quest added!", Toast.LENGTH_LONG).show();
                    finish();
                }
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

    private String getCurrentDate() {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        questDeadlineEditText.setText(dateFormat.format(calendar.getTime()));
    }

    private Date getDateEditTextDate() {
        String stringDate = questDeadlineEditText.getText().toString() + " " + questDeadlineHourEditText.getText().toString();
        if (questDeadlineHourEditText.getText().toString().isEmpty())
            stringDate = questDeadlineEditText.getText().toString() + " 23:59"; //setting hours to end of the day
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        Date date;
        try {
            return date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    /**
    * quest proposal from chatGPT
    */
    public String getQuestProposition(String category) { //
        retrofitClient = new RetrofitClient(RetrofitAPI.CHARACTER_URL + "quests/" + category + "/");
        Call<String> call = retrofitClient.getMyRetrofitAPI().getQuestsProposition(category);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                questProposition = response.body();
                questPropositionTextView.setText(questProposition);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.getMessage();
            }
        });
        return questProposition;
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "XD";
            String desc = "XDDD";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * method to show notification - alarm
    */
    void showAlertNotification(String text, long time) {
        Intent intent = new Intent(this, ReminderQuestBroadcast.class);
        Bundle bundle = new Bundle();
        bundle.putString("XD", text);
        intent.putExtra("bundle", bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time, pendingIntent);
    }
}
