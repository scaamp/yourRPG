package com.example.yourrpg;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yourrpg.character.NewCharacterActivity;
import com.example.yourrpg.databinding.ActivityMainBinding;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Questlog;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.retrofit.RetrofitAPI;
import com.example.yourrpg.retrofit.RetrofitClient;
import com.example.yourrpg.spellbook.SpellbookFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

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
        onPermission();

        if (characterList.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, NewCharacterActivity.class);
            startActivityForResult(intent, NEW_CHARACTER);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.my_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case R.id.statistic:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<androidx.fragment.app.Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (androidx.fragment.app.Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    private void initViews() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_oracle)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        strengthPoints = (TextView) findViewById(R.id.strengthPointsHome);
    }

    public ArrayList<Character> getCharacterList() {
        return characterList;
    }

    public Character getCurrentCharacter() {
        if (characterList.size() != 0) {
            return (Character) characterList.get(0);
        }
        return new Character(UUID.randomUUID(), "", 0, 0, 1);
    }

    private void initCharacterList() {

        ArrayList<Character> newCharacterList = SharedPreferencesSaver.loadFrom(getPreferences(MODE_PRIVATE), SharedPreferencesSaver.CHARACTER_PREF);
        if (newCharacterList != null) {
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

    protected void syncData() {
        retrofitClient = new RetrofitClient(RetrofitAPI.CHARACTER_URL);
        Call<Character> call = retrofitClient.getMyRetrofitAPI().updateCharacter(getCurrentCharacter().getUserId(), getCurrentCharacter());
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

    public void onPermission()
    {
// Permision can add more at your convinient
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]
                            {
                                    Manifest.permission.READ_CONTACTS,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.BLUETOOTH,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.CALL_PHONE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                            },
                    0
            );
        }
    }
}

