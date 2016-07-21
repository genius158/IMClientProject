package com.yan.imclientproject.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Administrator on 2016/7/21.
 */
public class PreferencesManager {

    public static final String IM_SHARED_PREFERENCE = "IM_SHARED_PREFERENCE";
    private SharedPreferences sharedPreferences;
    private Context context;

    public PreferencesManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(IM_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void setSharedPreferences(String sharedPreference) {
        this.sharedPreferences = null;
        this.sharedPreferences = context.getSharedPreferences(sharedPreference, Context.MODE_PRIVATE);
    }

    public PreferencesManager(Context context, String sharedPreference) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(sharedPreference, Context.MODE_PRIVATE);
    }

    public void addValue(String key, String value) {
        sharedPreferences.edit().putString(key, value);
    }

    public void addValues(ContentValues contentValues) {
        Set<String> keys = contentValues.keySet();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : keys) {
            editor.putString(key, contentValues.getAsString(key));
        }
        editor.commit();
    }

    public void clearValue(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, "");
        editor.commit();
    }

    public void clearValues() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
