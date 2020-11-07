package com.andoiddevop.weatherapplication.view.fragment.weather.presenter;

import com.andoiddevop.weatherapplication.model.WeatherBean;
import com.andoiddevop.weatherapplication.view.fragment.weather.view.WeatherFragmentView;
import com.andoiddevop.weatherapplication.web.WebServices;
import com.andoiddevop.weatherapplication.web.handler.WeatherHandler;

public class WeatherFragmentPresenter implements WeatherFragmentPresenterHandler {

    public WeatherFragmentView weatherFragmentView;

    public WeatherFragmentPresenter(WeatherFragmentView weatherFragmentView) {
        this.weatherFragmentView = weatherFragmentView;
    }

    @Override
    public void getCurrentWeather(String lat, String lon, String key) {
        weatherFragmentView.showProgressBar();
        WebServices.getInstance().getWeatherData(new WeatherHandler() {
            @Override
            public void onError(String message) {
                weatherFragmentView.hideProgressBar();
                weatherFragmentView.showFeedBackWeather(message);
            }

            @Override
            public void onSuccess(WeatherBean weatherBean) {
                weatherFragmentView.hideProgressBar();
                weatherFragmentView.onSuccessfullWeather(weatherBean);

            }
        },lat,lon,key);

    }
}
