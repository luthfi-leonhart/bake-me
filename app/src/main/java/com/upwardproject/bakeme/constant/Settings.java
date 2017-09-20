package com.upwardproject.bakeme.constant;

import android.content.SharedPreferences;

import com.upwardproject.bakeme.ui.MyApplication;

/**
 * Created by Dark on 15/12/2015.
 */

public class Settings {

    private static Settings instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean bulkUpdating = false;

    private Settings() {
        sp = MyApplication.getInstance().getPreference();
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }

        return instance;
    }

    public void removeAll() {
        remove(Key.SELECTED_RECIPE_ID);
    }

    public void put(String key, String val) {
        doEdit();
        editor.putString(key, val);
        doApply();
    }

    public void put(String key, int val) {
        doEdit();
        editor.putInt(key, val);
        doApply();
    }

    public void put(String key, boolean val) {
        doEdit();
        editor.putBoolean(key, val);
        doApply();
    }

    public void put(String key, float val) {
        doEdit();
        editor.putFloat(key, val);
        doApply();
    }

    /**
     * Convenience method for storing doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to store.
     * @param val The new value for the preference.
     */
    public void put(String key, double val) {
        doEdit();
        editor.putString(key, String.valueOf(val));
        doApply();
    }

    public void put(String key, long val) {
        doEdit();
        editor.putLong(key, val);
        doApply();
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public String getString(String key) {
        return sp.getString(key, null);
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return sp.getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
        return sp.getFloat(key, 0);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * Convenience method for retrieving doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to fetch.
     */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    /**
     * Convenience method for retrieving doubles.
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to fetch.
     */
    public double getDouble(String key, double defaultValue) {
        try {
            return Double.valueOf(sp.getString(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The enum of the key(s) to be removed.
     */
    public void remove(String... keys) {
        doEdit();
        for (String key : keys) {
            editor.remove(key);
        }
        doApply();
    }

    public void edit() {
        editor = sp.edit();
        bulkUpdating = true;
    }

    public void apply() {
        editor.apply();
        editor = null;
        bulkUpdating = false;
    }

    private void doEdit() {
        if (!bulkUpdating && editor == null) {
            editor = sp.edit();
        }
    }

    private void doApply() {
        if (!bulkUpdating && editor != null) {
            editor.apply();
            editor = null;
        }
    }

    public static class Key {
        public static final String SELECTED_RECIPE_ID = "selected_recipe_key";
    }

}
