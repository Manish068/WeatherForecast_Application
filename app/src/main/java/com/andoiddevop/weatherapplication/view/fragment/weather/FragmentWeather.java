package com.andoiddevop.weatherapplication.view.fragment.weather;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.model.DataItem;
import com.andoiddevop.weatherapplication.model.WeatherBean;
import com.andoiddevop.weatherapplication.utils.Constants;
import com.andoiddevop.weatherapplication.view.fragment.BaseFragment;
import com.andoiddevop.weatherapplication.view.fragment.weather.adapter.FiveDaysWeatherAdapter;
import com.andoiddevop.weatherapplication.view.fragment.weather.presenter.WeatherFragmentPresenter;
import com.andoiddevop.weatherapplication.view.fragment.weather.view.WeatherFragmentView;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentWeather extends BaseFragment implements WeatherFragmentView {


    ImageView imageViewWeather;
    TextView textViewLastUpdate;
    TextView textViewCityAddress;
    TextView textViewTime;
    TextView textViewMaxMin;
    TextView textViewWeather;
    TextView textViewCityFeelsLike;
    TextView textViewWeatherInfo;
    TextView textViewPreciption;
    TextView textViewPressure;
    TextView textViewVisibility;
    TextView textViewCloudCover;
    TextView textViewUVIndex;
    TextView textViewHumidity;
    TextView textViewSunRise;
    TextView textViewSunSet;
    TextView textViewWind;
    RecyclerView recyclerViewFiveDays;

    private String latitude = "";
    private String longitude = "";
    private WeatherFragmentPresenter weatherFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        Bundle bundle = getArguments();
        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");

        init(view);


        weatherFragmentPresenter = new WeatherFragmentPresenter(this);
        weatherFragmentPresenter.getCurrentWeather(latitude, longitude, Constants.WEATHERBIT_API_KEY);
        Log.d("latitude_longitude", " " + latitude + " " + longitude);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewFiveDays.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void init(View view) {
        imageViewWeather = view.findViewById(R.id.imageViewWeather);
        textViewLastUpdate = view.findViewById(R.id.textViewLastUpdate);
        textViewCityAddress = view.findViewById(R.id.textViewCityAddress);
        textViewTime = view.findViewById(R.id.textViewTime);
        textViewMaxMin = view.findViewById(R.id.textViewMaxMin);
        textViewWeather = view.findViewById(R.id.textViewWeather);
        textViewCityFeelsLike = view.findViewById(R.id.textViewCityFeelsLike);
        textViewWeatherInfo = view.findViewById(R.id.textViewWeatherInfo);
        textViewPreciption = view.findViewById(R.id.textViewPreciption);
        textViewPressure = view.findViewById(R.id.textViewPressure);
        textViewVisibility = view.findViewById(R.id.textViewVisibility);
        textViewCloudCover = view.findViewById(R.id.textViewCloudCover);
        textViewUVIndex = view.findViewById(R.id.textViewUVIndex);
        textViewHumidity = view.findViewById(R.id.textViewHumidity);
        textViewSunRise = view.findViewById(R.id.textViewSunRise);
        textViewWind = view.findViewById(R.id.textViewWind);
        textViewSunSet = view.findViewById(R.id.textViewSunSet);
        recyclerViewFiveDays = view.findViewById(R.id.recyclerViewFiveDays);


    }

    @Override
    public void showProgressBar() {
        showProgress();
    }

    @Override
    public void hideProgressBar() {
        hideProgress();
    }

    @Override
    public void showFeedBackWeather(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Log.d("message", "showFeedBackWeather: " + message);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccessfullWeather(WeatherBean weatherBean) {

        if (weatherBean != null) {

            String Date_Time, Time, SunsetTime, SunRiseTime;
            long unix_seconds = weatherBean.getData().get(0).getTs();
            long unix_sunrise_second = weatherBean.getData().get(0).getSunriseTs();
            long unix_sunset_second = weatherBean.getData().get(0).getSunsetTs();
            Date date = new Date(unix_seconds * 1000L);
            Date SunRise = new Date(unix_sunrise_second * 1000L);
            Date Sunset = new Date(unix_sunset_second * 1000L);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM", Locale.US);
            SimpleDateFormat currentTime = new SimpleDateFormat("h:mm a", Locale.US);
            Date_Time = simpleDateFormat.format(date.getTime()) + " " + currentTime.format(date.getTime());

            Time = currentTime.format(date.getTime());
            SunsetTime = currentTime.format(Sunset.getTime());
            SunRiseTime = currentTime.format(SunRise.getTime());

            String imageUrl = "https://www.weatherbit.io/static/img/icons/" + weatherBean.getData().get(0).getWeather().getIcon() + ".png";
            Glide.with(getActivity()).load(imageUrl).into(imageViewWeather);
            textViewWeatherInfo.setText(weatherBean.getData().get(0).getWeather().getDescription());
            textViewCityAddress.setText(weatherBean.getCity_name());
            textViewLastUpdate.setText("Last Update " + Date_Time);
            textViewTime.setText(Time);
            textViewVisibility.setText("Visibility: " + String.valueOf(weatherBean.getData().get(0).getVis()));
            textViewWeather.setText(String.valueOf(weatherBean.getData().get(0).getTemp()));
            textViewMaxMin.setText("MAX/MIN: " + String.valueOf(weatherBean.getData().get(0).getMax_temp()) + "/" + String.valueOf(weatherBean.getData().get(0).getMin_temp()));
            textViewUVIndex.setText("UV INDEX: " + String.valueOf(weatherBean.getData().get(0).getUv()));
            textViewPressure.setText("Pressure: " + String.valueOf(weatherBean.getData().get(0).getPres()));
            textViewPreciption.setText("Preciption: " + String.valueOf(weatherBean.getData().get(0).getPrecip()));
            textViewSunRise.setText("SUN RISE: " + SunRiseTime);
            textViewSunSet.setText("SUNSET: " + SunsetTime);
            textViewHumidity.setText("HUMIDITY: " + String.valueOf(weatherBean.getData().get(0).getRh()) + "%");
            textViewWind.setText("WIND: " + String.valueOf(weatherBean.getData().get(0).getWindSpd()));
            textViewCloudCover.setText("CLOUD COVER: " + weatherBean.getData().get(0).getClouds());

            setFiveDaysAdapter(weatherBean.getData());

        } else {
            Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
        }


    }

    private void setFiveDaysAdapter(List<DataItem> data) {

        FiveDaysWeatherAdapter fiveDaysWeatherAdapter = new FiveDaysWeatherAdapter(data,getActivity());
        recyclerViewFiveDays.setAdapter(fiveDaysWeatherAdapter);

    }
}
