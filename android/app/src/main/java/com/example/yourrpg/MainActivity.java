package com.example.yourrpg;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yourrpg.character.CharacterFragment;
import com.example.yourrpg.character.NewCharacterActivity;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Questlog;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.retrofit.RetrofitAPI;
import com.example.yourrpg.retrofit.RetrofitClient;
import com.example.yourrpg.spellbook.SpellbookFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yourrpg.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_CHARACTER = 111;
    public static final int QUEST_DONE = 444;
    private ActivityMainBinding binding;
    private ArrayList<Character> characterList;
    private ArrayList<Questlog> questList;
    private TextView strengthPoints;
    private RetrofitClient retrofitClient;
    private static final String SPELLBOOK_PREF = "SPELLBOOK_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initCharacterList();
        //createNotificationChannel();
        //checkTaskDate();

        if (characterList.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, NewCharacterActivity.class);
            startActivityForResult(intent, NEW_CHARACTER);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.statistic:
//                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//                Notification notify=new Notification.Builder
//                        (getApplicationContext()).setContentTitle("cze").setContentText("czeeee").
//                        setContentTitle("czesc").setSmallIcon(R.drawable.ic_heart).build();
//
//                notify.flags |= Notification.FLAG_AUTO_CANCEL;
//                notif.notify(0, notify);


//                getSupportFragmentManager().beginTransaction().detach(getVisibleFragment());
//                ((AppCompatActivity)getApplicationContext()).getSupportFragmentManager().popBackStack("findThisFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                FragmentManager fm = getSupportFragmentManager();
//                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//                    fm.popBackStack();
//                }
//                androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
//                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//                CharacterFragment nextFrag = new CharacterFragment();
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.scrollingLayout, nextFrag, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_blank, new BlankFragment()).commit();
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.add(R.id.fragment_blank,BlankFragment,YOUR_FRAGMENT_STRING_TAG);
//                transaction.addToBackStack(null);
//                transaction.commit();
                //super.onPostResume();
//                getSupportFragmentManager().beginTransaction().remove(getVisibleFragment()).commit();
//                FragmentManager fm = getSupportFragmentManager();
//
//                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
//                    fm.popBackStack();
//                }
//                getSupportFragmentManager().beginTransaction()
//                        .replace(android.R.id.content, new BlankFragment()).commit();

                //().popBackStackImmediate();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "XD";
            String desc = "XDDD";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<androidx.fragment.app.Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(androidx.fragment.app.Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
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


    public Character getCurrentCharacter()
    {
        if (characterList.size() !=0 ) {
            return (Character) characterList.get(0);
        }
        return new Character (UUID.randomUUID(), "", 0,0, 1);
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

