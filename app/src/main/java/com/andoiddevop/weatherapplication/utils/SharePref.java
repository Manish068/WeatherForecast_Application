package com.andoiddevop.weatherapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.core.content.ContextCompat;

public class SharePref {
    Context context;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public SharePref(Context context){
        this.context =context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataPref(String key, String value){
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getDataFromPref(String key){
        String s= sharedPreferences.getString(key,"");
        return s;
    }
}
