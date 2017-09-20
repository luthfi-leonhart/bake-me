package com.upwardproject.bakeme.ui;

import android.app.Application;
import android.content.Context;

import com.upwardproject.bakeme.util.ObscuredSharedPreferences;

public class MyApplication extends Application {

    private static MyApplication instance;
    private final String SETTINGS_NAME = "default_settings";
    private ObscuredSharedPreferences sp;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    public synchronized ObscuredSharedPreferences getPreference() {
        return sp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        sp = new ObscuredSharedPreferences(getApplicationContext(), getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE));
    }
}