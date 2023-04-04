package com.example.yourrpg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourrpg.activity.NewCharacterActivity;
import com.example.yourrpg.activity.NewSpellbookActivity;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.ui.HttpUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yourrpg.databinding.ActivityMainBinding;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_CHARACTER = 111;
    private ActivityMainBinding binding;
    private ArrayList<Character> characterList;
    private TextView strengthPoints;
    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        initViews();
        initCharacterList();
        //obtainExtras();

        if (characterList.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, NewCharacterActivity.class);
            startActivityForResult(intent, NEW_CHARACTER);
        }
    }

    private void obtainExtras() {
        character = (Character) getIntent().getExtras().getSerializable(NewCharacterActivity.SPECIAL_DATA);
    }

    private void initViews() {
        strengthPoints = (TextView) findViewById(R.id.strengthPointsHome);
    }


    private void initCharacterList() {
        ArrayList<Character> newCharacterList = SharedPreferencesSaver.loadFrom(getPreferences(MODE_PRIVATE));
        if (newCharacterList != null) {
            characterList = newCharacterList;
        } else {
            characterList = new ArrayList<>();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferencesSaver.saveTo(characterList, getPreferences(MODE_PRIVATE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferencesSaver.saveTo(characterList, getPreferences(MODE_PRIVATE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CHARACTER) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Character newCharacter = (Character) data.getExtras().get(NewCharacterActivity.NEW_CHARACTER);
                    strengthPoints.setText(String.valueOf(newCharacter.getStrength())); //TODO
                    characterList.add(newCharacter);
                }
            }
        }
    }
}

