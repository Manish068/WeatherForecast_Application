package com.andoiddevop.weatherapplication;

import android.app.Application;

import com.andoiddevop.weatherapplication.utils.SharePref;
import com.andoiddevop.weatherapplication.web.WebServices;

public class MyApp extends Application {

    public static double latitude,longitude;
    public static String cityName="";

    @Override
    public void onCreate() {
        super.onCreate();
        new WebServices();
        new SharePref(getApplicationContext());
    }
}
