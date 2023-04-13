package com.example.yourrpg.persistency;

import android.content.SharedPreferences;

import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Questlog;
import com.example.yourrpg.model.Spellbook;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


/**
 * Klasa zapisująca stan aplikacji w pamięci wewnętrznej urządzenia
 */

public class SharedPreferencesSaver
{
    public static final String CHARACTER_PREF = "CHARACTER_PREF";
    public static final String SPELLBOOK_PREF = "SPELLBOOK_PREF";
    public static final String QUESTLOG_PREF = "QUESTLOG_PREF";

    public static ArrayList loadFrom(SharedPreferences preferences, String pref)
    {
        String string = preferences.getString(pref, null);
        Gson gson = new Gson();
        if (pref.equals(SPELLBOOK_PREF)) return gson.fromJson(string, new TypeToken<ArrayList<Spellbook>>(){}.getType());
        if (pref.equals(CHARACTER_PREF)) return gson.fromJson(string, new TypeToken<ArrayList<Character>>(){}.getType());
        if (pref.equals(QUESTLOG_PREF)) return gson.fromJson(string, new TypeToken<ArrayList<Questlog>>(){}.getType());
        return new ArrayList();
    }

    public static void saveTo(ArrayList list, SharedPreferences preferences, String pref)
    {

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        if (pref.equals(SPELLBOOK_PREF)) editor.putString(pref, gson.toJson(list));
        if (pref.equals(CHARACTER_PREF)) editor.putString(pref, gson.toJson(list));
        if (pref.equals(QUESTLOG_PREF)) editor.putString(pref, gson.toJson(list));
        editor.apply();
    }

//    public static ArrayList<Spellbook> loadSpellbookFrom(SharedPreferences preferences)
//    {
//        String string = preferences.getString(SPELLBOOK_PREF, null);
//        Gson gson = new Gson();
//        return gson.fromJson(string, new TypeToken<ArrayList<Spellbook>>(){}.getType());
//    }
//
//    public static ArrayList<Questlog> loadQuestlogFrom(SharedPreferences preferences)
//    {
//        String string = preferences.getString(QUESTLOG_PREF, null);
//        Gson gson = new Gson();
//        return gson.fromJson(string, new TypeToken<ArrayList<Questlog>>(){}.getType());
//    }
}
