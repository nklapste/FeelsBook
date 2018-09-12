package ca.klapstein.nklapste_feelsbook;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Helper Class for accessing the  Android's SharedPreferences for use in FeelsBook.
 */
public class PreferencesManager {
    private static final String TAG = "PreferencesManager";
    public static final String FEELS_LIST_PREF_NAME = "mFeelsList";
    public static final String FEELS_LIST_PREF_JSON_KEY = "mFeelListJson";

    /**
     * Save a FeelsList using Android's SharedPreferences
     *
     * @param context {@code Context}
     * @param feelList
     */
    public static void saveSharedPreferencesFeelList(Context context, ArrayList<Feel> feelList) {
        SharedPreferences mPrefs = context.getSharedPreferences(FEELS_LIST_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(feelList);
        prefsEditor.putString("mFeelListJson", json);
        prefsEditor.apply();
    }

    /**
     * Load the FeelsList using Android's SharedPreferences
     *
     * @param context {@code Context}
     */
    public static ArrayList<Feel> loadSharedPreferencesFeelList(Context context) {
        ArrayList<Feel> feelList;
        SharedPreferences mPrefs = context.getSharedPreferences(FEELS_LIST_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(FEELS_LIST_PREF_JSON_KEY, "");
        if (json.isEmpty()) {
            feelList = new ArrayList<Feel>();
        } else {
            Type type = new TypeToken<ArrayList<Feel>>() {
            }.getType();
            feelList = gson.fromJson(json, type);
        }
        return feelList;
    }
}
