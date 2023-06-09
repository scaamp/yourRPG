package com.example.yourrpg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourrpg.activity.NewCharacterActivity;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Spellbook;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.retrofit.RetrofitAPI;
import com.example.yourrpg.retrofit.RetrofitClient;
import com.example.yourrpg.ui.character.CharacterFragment;
import com.example.yourrpg.ui.spellbook.SpellbookFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yourrpg.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    public static final int NEW_CHARACTER = 111;
    public static final int QUEST_DONE = 444;
    private ActivityMainBinding binding;
    private ArrayList<Character> characterList;
    private TextView strengthPoints;
    private RetrofitClient retrofitClient;
    private static final String SPELLBOOK_PREF = "SPELLBOOK_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initCharacterList();

        if (characterList.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, NewCharacterActivity.class);
            startActivityForResult(intent, NEW_CHARACTER);
        }
    }

    private void initViews() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        strengthPoints = (TextView) findViewById(R.id.strengthPointsHome);
    }

    public ArrayList<Character> getCharacterList() {
        return characterList;
    }


    public Character getCurrentCharacter()
    {
        if (characterList.size() !=0 ) {
            return (Character) characterList.get(0);
        }
        return new Character (UUID.randomUUID(), "", 0,0);
    }

    private void initCharacterList() {

        ArrayList<Character> newCharacterList = SharedPreferencesSaver.loadFrom(getPreferences(MODE_PRIVATE), SharedPreferencesSaver.CHARACTER_PREF);
        if (newCharacterList  != null) {
            characterList = newCharacterList;
            //characterList.clear();
            //strengthPoints.setText(String.valueOf(getCurrentCharacter().getStrength()));
        } else {
            characterList = new ArrayList<>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CHARACTER) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Character newCharacter = (Character) data.getExtras().get(NewCharacterActivity.NEW_CHARACTER);
                    strengthPoints.setText(String.valueOf(newCharacter.getStrength()));
                    characterList.add(newCharacter);
                }
            }

        }

        if (requestCode == QUEST_DONE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
//                    Character newCharacter = (Character) data.getExtras().get(CharacterFragment.NEW_CHARACTER);
//                    strengthPoints.setText(String.valueOf(newCharacter.getStrength()));
//                    characterList.add(newCharacter);
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferencesSaver.saveTo(characterList, getPreferences(MODE_PRIVATE), SharedPreferencesSaver.CHARACTER_PREF);
        SharedPreferencesSaver.saveTo(SpellbookFragment.getSpellList(), getPreferences(MODE_PRIVATE), SPELLBOOK_PREF);
        syncData();
    }


    protected void syncData()
    {
        retrofitClient = new RetrofitClient(RetrofitAPI.CHARACTER_URL);
        Call<Character> call = retrofitClient.getMyRetrofitAPI().updateCharacter(getCurrentCharacter().getUserId(), getCurrentCharacter());

        // on below line we are executing our method.
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                response.body();
                //long id = response.body().getUserId();
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}

