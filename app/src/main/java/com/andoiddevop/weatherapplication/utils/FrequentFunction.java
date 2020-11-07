package com.andoiddevop.weatherapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.DisplayMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FrequentFunction {

    public static String getCityName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addressList == null) {
            return "";
        }
        return addressList.get(0).getLocality();

    }

    public static String getWeekDays(long unixtimestamp){
        long timeStamp=unixtimestamp * 1000L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date date = new Date(timeStamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String dayOfTheWeek = simpleDateFormat.format(date);
        return dayOfTheWeek;
    }

    public static String getDay(long timeStamp) {
        long timestampLong = timeStamp * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date(timestampLong);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }

    public static String getCurrentDate(){
        String formattedDate= "";
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd hh:mm a");
        return formattedDate = dateFormat.format(c);
    }





    public static String get24HrsTimeStamp(long timeStamp) {
        long timestampLong = timeStamp * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d = new Date(timestampLong);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }

    public static String get12HrsTimeStamp(long timeStamp) {
        long timestampLong = timeStamp * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date d = new Date(timestampLong);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }


    public static String get12HrsByDate(String time) {
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final Date date = simpleDateFormat.parse(time);
            time = new SimpleDateFormat("hh:mm a").format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return time;
    }

    public static String get24HrsByDate(String time) {
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final Date date = simpleDateFormat.parse(time);
            time = new SimpleDateFormat("HH:mm").format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return time;
    }



    public static String celsiusToFahrenheit(double celsius) {
        double fahrenheit;
        DecimalFormat formatter = new DecimalFormat("##.00");
        fahrenheit=(9.0/5.0)*celsius + 32;
        return formatter.format(fahrenheit);
    }

    public static double pascalToMillibar(double pascal) {
        return (pascal/1000);
    }

    public static void recyclerViewAnimation(Activity activity, final RecyclerView recyclerView) {

        AnimationSet set = new AnimationSet(true);

        // Fade in animation
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(400);
        fadeIn.setFillAfter(true);
        set.addAnimation(fadeIn);

        // Slide up animation from bottom of screen
        Animation slideUp = new TranslateAnimation(0, 0, getScreenHeight(activity), 0);
        slideUp.setInterpolator(new DecelerateInterpolator(4.f));
        slideUp.setDuration(400);
        set.addAnimation(slideUp);

        // Set up the animation controller              (second parameter is the delay)
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f);
        recyclerView.setLayoutAnimation(controller);

        // Set the adapter

    }

    public static float getScreenHeight(Activity activity) {
        DisplayMetrics dimension = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dimension);
        int width = dimension.widthPixels;
        int height = dimension.heightPixels;
        return height;
    }



}
