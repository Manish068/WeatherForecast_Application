package com.andoiddevop.weatherapplication.view.fragment.weather.view;

import com.andoiddevop.weatherapplication.model.WeatherBean;

public interface WeatherFragmentView {

    void showProgressBar();
    void hideProgressBar();
    void showFeedBackWeather(String message);
    void onSuccessfullWeather(WeatherBean weatherBean);

}
