package ca.klapstein.nklapste_feelsbook;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * Helper Class for accessing the  Android's SharedPreferences for use in FeelsBook.
 * <p>
 * Use {@code Gson} to serialize/deserialize FeelTreeSet to/from a JSON string for saving/loading
 * in Android's SharedPreferences.
 */
class FeelsBookPreferencesManager {
    private static final String TAG = "FeelsBookPreferencesManager";

    private static final String FEELS_TREESET_PREF_NAME = "mFeelsTreeSet";
    private static final String FEELS_TREESET_PREF_JSON_KEY = "mFeelTreeSetJson";

    /**
     * Save a {@code FeelTreeSet} using Android's SharedPreferences.
     *
     * @param context     {@code Context}
     * @param feelTreeSet {@code FeelTreeSet}
     */
    public static void saveSharedPreferencesFeelList(Context context, FeelTreeSet feelTreeSet) {
        SharedPreferences mPrefs = context.getSharedPreferences(FEELS_TREESET_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(feelTreeSet);
        prefsEditor.putString(FEELS_TREESET_PREF_JSON_KEY, json);
        prefsEditor.apply();
    }

    /**
     * Load the {@code FeelTreeSet} using Android's SharedPreferences.
     *
     * @param context {@code Context}
     * @return {@code FeelTreeSet}
     */
    public static FeelTreeSet loadSharedPreferencesFeelList(Context context) {
        FeelTreeSet feelTreeSet;
        SharedPreferences mPrefs = context.getSharedPreferences(FEELS_TREESET_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(FEELS_TREESET_PREF_JSON_KEY, "");
        if (json.isEmpty()) {
            feelTreeSet = new FeelTreeSet();
        } else {
            Type type = new TypeToken<FeelTreeSet>() {
            }.getType();
            feelTreeSet = gson.fromJson(json, type);
        }
        return feelTreeSet;
    }
}
