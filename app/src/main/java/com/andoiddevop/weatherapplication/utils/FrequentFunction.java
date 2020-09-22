package com.andoiddevop.weatherapplication.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
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
}
