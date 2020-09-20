package com.andoiddevop.weatherapplication.web.handler;

import com.andoiddevop.weatherapplication.model.WeatherBean;

public interface WeatherHandler extends BaseHandler{
    void onSuccess(WeatherBean weatherBean);
}
