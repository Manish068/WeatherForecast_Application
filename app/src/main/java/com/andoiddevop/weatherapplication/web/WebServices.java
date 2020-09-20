package com.andoiddevop.weatherapplication.web;

import com.andoiddevop.weatherapplication.model.WeatherBean;
import com.andoiddevop.weatherapplication.utils.Constants;
import com.andoiddevop.weatherapplication.web.handler.WeatherHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServices  {

    private static WebServices mInstance;
    // convert the json the response of my api with the help of GSON
    // we didn't need to parse the JSon it will automatically parse the json by using anotation
    private Gson gson;

    private WebApi webApi;
    public WebServices(){
        mInstance=this;
        //giving the logs of request and response
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .callTimeout(5, TimeUnit.MINUTES)              //connection time limit
                .writeTimeout(5,TimeUnit.MINUTES)
                .readTimeout(5,TimeUnit.MINUTES)
                .build();

        gson = new GsonBuilder().setLenient().create();

        webApi = new Retrofit.Builder().baseUrl(Constants.WEATHERBIT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WebApi.class);

    }

    public static WebServices getInstance(){
        return mInstance;
    }

    public void getWeatherData(WeatherHandler weatherHandler, String lat, String lon, String key) {
        Call<WeatherBean> callback =webApi.getWeather(lat,lon,Constants.DAYS,key);

        callback.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                if(response.body() != null)
                    weatherHandler.onSuccess(response.body());
                else
                    weatherHandler.onError(response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                weatherHandler.onError(t.getMessage());

            }
        });
    }
}
