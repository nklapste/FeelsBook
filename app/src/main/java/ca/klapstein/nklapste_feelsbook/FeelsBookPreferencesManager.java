package ca.klapstein.nklapste_feelsbook;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Helper Class for accessing the  Android's SharedPreferences for use in FeelsBook.
 */
public class FeelsBookPreferencesManager {
    private static final String TAG = "FeelsBookPreferencesManager";

    private static final String FEELS_QUEUE_PREF_NAME = "mFeelsQueue";
    private static final String FEELS_QUEUE_PREF_JSON_KEY = "mFeelQueueJson";

    /**
     * Save a FeelsList using Android's SharedPreferences
     *
     * @param context  {@code Context}
     * @param feelQueue {@code FeelQueue}
     */
    public static void saveSharedPreferencesFeelList(Context context, FeelQueue feelQueue) {
        SharedPreferences mPrefs = context.getSharedPreferences(FEELS_QUEUE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(feelQueue);
        prefsEditor.putString("mFeelListJson", json);
        prefsEditor.apply();
    }

    /**
     * Load the FeelsList using Android's SharedPreferences
     *
     * @param context {@code Context}
     */
    public static FeelQueue loadSharedPreferencesFeelList(Context context) {
        FeelQueue feelQueue;
        SharedPreferences mPrefs = context.getSharedPreferences(FEELS_QUEUE_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(FEELS_QUEUE_PREF_JSON_KEY, "");
        if (json.isEmpty()) {
            feelQueue = new FeelQueue();
        } else {
            Type type = new TypeToken<FeelQueue>() {
            }.getType();
            feelQueue = gson.fromJson(json, type);
        }
        return feelQueue;
    }
}
