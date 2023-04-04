package com.example.yourrpg.persistency;

import android.content.SharedPreferences;

import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Spellbook;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


/**
 * Klasa zapisująca stan aplikacji w pamięci wewnętrznej urządzenia
 */

public class SharedPreferencesSaver
{
    private static final String CHARACTER_PREF = "CHARACTER_PREF";
    private static final String SPELLBOOK_PREF = "SPELLBOOK_PREF";

    public static void saveTo(ArrayList<Character> characterList, SharedPreferences preferences)
    {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString(CHARACTER_PREF, gson.toJson(characterList));
        editor.apply();
    }

    public static ArrayList<Character> loadFrom(SharedPreferences preferences)
    {
        String string = preferences.getString(CHARACTER_PREF, null);
        Gson gson = new Gson();
        return gson.fromJson(string, new TypeToken<ArrayList<Character>>(){}.getType());
    }

    public static void saveSpellbookTo(ArrayList<Spellbook> spellList, SharedPreferences preferences)
    {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString(SPELLBOOK_PREF, gson.toJson(spellList));
        editor.apply();
    }

    public static ArrayList<Spellbook> loadSpellbookFrom(SharedPreferences preferences)
    {
        String string = preferences.getString(SPELLBOOK_PREF, null);
        Gson gson = new Gson();
        return gson.fromJson(string, new TypeToken<ArrayList<Spellbook>>(){}.getType());
    }
}
