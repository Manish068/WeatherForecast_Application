package com.andoiddevop.weatherapplication.web;


import com.andoiddevop.weatherapplication.model.WeatherBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebApi {
    @GET("forecast/daily")
    Call<WeatherBean>getWeather(@Query("lat") String lat,
                                @Query("lon") String lon,
                                @Query("days") String days,
                                @Query("key") String key);
}
