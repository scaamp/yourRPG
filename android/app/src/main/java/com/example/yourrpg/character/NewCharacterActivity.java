package com.example.yourrpg.character;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
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
    private ImageButton selectAvatarImageButton;
    private Button doneCharacterButton;
    private Button addStrengthButton;
    private TextView strengthPoints;
    private TextView agilityPoints;
    private EditText nicknameEditText;
    public static final String NEW_CHARACTER = "NEW_CHARACTER";
    public RetrofitClient retrofitClient;
    public int iconPosition;

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
        nicknameEditText = (EditText) findViewById(R.id.nicknameEditText);
        selectAvatarImageButton = (ImageButton) findViewById(R.id.selectAvatarImageButton);
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
                if (!nicknameEditText.getText().toString().isEmpty()) {
                    //Character character = new Character(1, "XD", Integer.parseInt(strengthPoints.getText().toString()), Integer.parseInt(agilityPoints.getText().toString()));
                    Character character = new Character(UUID.randomUUID(), nicknameEditText.getText().toString(),1,0,  Integer.parseInt(strengthPoints.getText().toString()), Integer.parseInt(agilityPoints.getText().toString()), iconPosition);
                    //postData(1, "scamp", 1, 0, Integer.parseInt(strengthPoints.getText().toString()), Integer.parseInt(agilityPoints.getText().toString()));

                    postData(character);
                    Intent intent = new Intent();
                    intent.putExtra(NEW_CHARACTER, character);
                    setResult(Activity.RESULT_OK, intent);
                    Toast.makeText(NewCharacterActivity.this,"Character created!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(NewCharacterActivity.this,"You have to enter you nickname!", Toast.LENGTH_LONG).show();
            }
        });

        selectAvatarImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String [] items = new String[] {"Ninja", "Guru", "XD"};
                final Integer[] icons = new Integer[] {R.drawable.character, R.drawable.guru, R.drawable.oracle};
                ListAdapter adapter = new ArrayAdapterWithIcon(NewCharacterActivity.this, items, icons);

                new AlertDialog.Builder(NewCharacterActivity.this).setTitle("Select Icon")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item ) {
                                Toast.makeText(NewCharacterActivity.this, "Item Selected: " + item, Toast.LENGTH_SHORT).show();
                                iconPosition = item;
                                selectAvatarImageButton.setImageResource(icons[item]);
                            }
                        }).show();
            }
        });
    }

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
