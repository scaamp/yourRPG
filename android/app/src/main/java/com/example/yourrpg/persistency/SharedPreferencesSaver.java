package com.example.yourrpg.persistency;

import android.content.SharedPreferences;

import com.example.yourrpg.model.Character;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


/**
 * Klasa zapisująca stan aplikacji w pamięci wewnętrznej urządzenia
 */

public class SharedPreferencesSaver
{
    private static final String AUTO_PREF = "AUTO_PREF";

    public static void saveTo(ArrayList<Character> autoList, SharedPreferences preferences)
    {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString(AUTO_PREF, gson.toJson(autoList));
        editor.apply();
    }

    public static ArrayList<Character> loadFrom(SharedPreferences preferences)
    {
        String string = preferences.getString(AUTO_PREF, null);
        Gson gson = new Gson();
        return gson.fromJson(string, new TypeToken<ArrayList<Character>>(){}.getType());
    }
}
