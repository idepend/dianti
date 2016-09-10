package com.app.dianti.util;

import java.util.LinkedHashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserPreference {

    private static SharedPreferences mUserPreferences;
    private static String USER_PREFERENCE = "user_preference";

    public static void clearData() {
        if (mUserPreferences != null) {
            Editor editor = mUserPreferences.edit();
            if (editor != null)
                editor.clear();
            mUserPreferences = null;
        }
    }

    public static void ensureIntializePreference(Context context) {
        if (mUserPreferences != null) {
            return;
        }
        mUserPreferences = context.getSharedPreferences(USER_PREFERENCE, Activity.MODE_PRIVATE);
    }

    @SuppressLint("NewApi")
    public static void save(String key, Set<String> value) {
        Editor editor = mUserPreferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    public static void save(String key, String value) {
        Editor editor = mUserPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void save(String key, int value) {
        Editor editor = mUserPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void save(String key, boolean value) {
        Editor editor = mUserPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void save(String key, long value) {
        Editor editor = mUserPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void save(String key, Float value) {
        Editor editor = mUserPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void delete(String key) {
        Editor editor = mUserPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    @SuppressLint("NewApi")
    public static Set<String> get(String key) {
        return mUserPreferences.getStringSet(key, new LinkedHashSet<String>());
    }

    public static String get(String key, String defaultvalue) {
        return mUserPreferences.getString(key, defaultvalue);
    }

    public static int get(String key, int defaultvalue) {
        return mUserPreferences.getInt(key, defaultvalue);
    }

    public static long get(String key, long defaultvalue) {
        return mUserPreferences.getLong(key, defaultvalue);
    }

    public static float get(String key, float defaultvalue) {
        return mUserPreferences.getFloat(key, defaultvalue);
    }

    public static boolean get(String key, boolean defaultvalue) {
        return mUserPreferences.getBoolean(key, defaultvalue);
    }

}
