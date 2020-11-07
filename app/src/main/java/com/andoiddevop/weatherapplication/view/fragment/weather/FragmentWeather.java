package com.andoiddevop.weatherapplication.view.fragment.weather;


import android.annotation.SuppressLint;
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
import com.andoiddevop.weatherapplication.utils.FrequentFunction;
import com.andoiddevop.weatherapplication.utils.SharePref;
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

            textViewWeatherInfo.setText(weatherBean.getData().get(0).getWeather().getDescription());
            textViewCityAddress.setText(weatherBean.getCity_name());

            String check = weatherBean.getData().get(0).getWeather().getIcon();

            switch (check) {
                case "c01d":
                    Glide.with(getActivity()).load(R.drawable.ic_sun).into(imageViewWeather);
                    break;
                case "c03d":
                    Glide.with(getActivity()).load(R.drawable.ic_brokencloud).into(imageViewWeather);
                    break;
                case "c04d":
                    Glide.with(getActivity()).load(R.drawable.ic_overcast).into(imageViewWeather);
                    break;
                case "c02d":
                    Glide.with(getActivity()).load(R.drawable.ic_fewclouds).into(imageViewWeather);
                    break;
                case "r01d":
                    Glide.with(getActivity()).load(R.drawable.ic_light_rain).into(imageViewWeather);
                    break;
                case "r02d":
                    Glide.with(getActivity()).load(R.drawable.ic_moderate_rain).into(imageViewWeather);
                    break;
                case "r03d":
                    Glide.with(getActivity()).load(R.drawable.ic_heavy_rain).into(imageViewWeather);
                    break;
                case "r04d":
                case "r06d":
                    Glide.with(getActivity()).load(R.drawable.ic_shower_rain).into(imageViewWeather);
                    break;
                case "r05d":
                    Glide.with(getActivity()).load(R.drawable.ic_showerraining).into(imageViewWeather);
                    break;

                case "d01d":
                case "d02d":
                case "d03d":
                    Glide.with(getActivity()).load(R.drawable.ic_drizzle).into(imageViewWeather);
                    break;
                case "t01d":
                case "t02d":
                case "t03d":
                    Glide.with(getActivity()).load(R.drawable.ic_t01d).into(imageViewWeather);
                    break;
                case "t04d":
                case "t05d":
                    Glide.with(getActivity()).load(R.drawable.ic_t05d).into(imageViewWeather);
                    break;
                case "a01d":
                    Glide.with(getActivity()).load(R.drawable.ic_mist).into(imageViewWeather);
                    break;
                case "a03d":
                    Glide.with(getActivity()).load(R.drawable.ic_haze).into(imageViewWeather);
                    break;
                case "a04d":
                    Glide.with(getActivity()).load(R.drawable.ic_weather_dust).into(imageViewWeather);
                    break;
                case "a05d":
                case "a06d":
                    Glide.with(getActivity()).load(R.drawable.ic_fog).into(imageViewWeather);
                    break;

                case "s05d":
                    Glide.with(getActivity()).load(R.drawable.ic_sleet).into(imageViewWeather);
                    break;

                case "s01d":
                    Glide.with(getActivity()).load(R.drawable.ic_snow_shower).into(imageViewWeather);
                    break;

                case "s02d":
                    Glide.with(getActivity()).load(R.drawable.ic_snow_heavy).into(imageViewWeather);
                    break;

                case "s04d":
                    Glide.with(getActivity()).load(R.drawable.ic_moderate_snow).into(imageViewWeather);
                    break;
            }


            if (SharePref.getDataFromPref(Constants.TIME_FORMAT).equalsIgnoreCase(Constants.TIME_FORMAT_24)){
                textViewSunRise.setText("SUN RISE: " + FrequentFunction.get24HrsTimeStamp(weatherBean.getData().get(0).getSunriseTs()));
                textViewSunSet.setText("SUNSET: " + FrequentFunction.get24HrsTimeStamp(weatherBean.getData().get(0).getSunsetTs()));
                textViewTime.setText(FrequentFunction.get24HrsByDate(weatherBean.getData().get(0).getDatetime()));
            }else {
                textViewSunRise.setText("SUN RISE: " + FrequentFunction.get12HrsTimeStamp(weatherBean.getData().get(0).getSunriseTs()));
                textViewSunSet.setText("SUNSET: " + FrequentFunction.get12HrsTimeStamp(weatherBean.getData().get(0).getSunsetTs()));
                textViewTime.setText(FrequentFunction.get12HrsByDate(weatherBean.getData().get(0).getDatetime()));
            }


            if (SharePref.getDataFromPref(Constants.DATE_FORMAT).equalsIgnoreCase(Constants.DATE_FORMAT_API))
                textViewLastUpdate.setText("Last Update: " +
                        weatherBean.getData().get(0).getDatetime());
            else {
                textViewLastUpdate.setText("Last Update: " +
                        FrequentFunction.getCurrentDate());
            }

            if (SharePref.getDataFromPref(Constants.TEMPERATURE).equalsIgnoreCase(Constants.TEMPERATURE_F)) {

                textViewMaxMin.setText("MAX/MIN: " + FrequentFunction.celsiusToFahrenheit(weatherBean.getData().get(0).getMax_temp())+
                        "/" + FrequentFunction.celsiusToFahrenheit(weatherBean.getData().get(0).getMin_temp())+"°F");

                textViewWeather.setText(FrequentFunction.celsiusToFahrenheit(weatherBean.getData().get(0).getTemp())+"°F");


            } else {
                textViewMaxMin.setText("MAX/MIN: " + String.valueOf(weatherBean.getData().get(0).getMax_temp())+
                        "/" + String.valueOf(weatherBean.getData().get(0).getMin_temp())+"°C");

                textViewWeather.setText(String.valueOf(weatherBean.getData().get(0).getTemp())+"°C");

            }


            if (SharePref.getDataFromPref(Constants.PRESSURE).
                    equalsIgnoreCase(Constants.PRESSURE_MILLIBAR))
                textViewPressure.setText("Pressure: " +
                        (FrequentFunction.pascalToMillibar(weatherBean.getData().get(0).getPres())+Constants.PRESSURE_MILLIBAR));
            else
                textViewPressure.setText("Pressure: " +
                        (String.valueOf(weatherBean.getData().get(0).getPres())+Constants.PRESSURE_INCHES));

            if (SharePref.getDataFromPref(Constants.PRECIPTION).
                    equalsIgnoreCase(Constants.PRECIPTION_INCHES))
                textViewPreciption.setText("Precip: " +
                        (String.valueOf(weatherBean.getData().get(0).getPrecip())+" "+Constants.PRECIPTION_INCHES));

            else
                textViewPreciption.setText("Precip: " +
                        (String.valueOf(weatherBean.getData().get(0).getPrecip())+" "+Constants.PRECIPTION_MM));

            if (SharePref.getDataFromPref(Constants.WIND_SPEED).equalsIgnoreCase
                    (Constants.WIND_METERS))
                textViewWind.setText("Wind: " + String.valueOf(weatherBean.getData().get(0).getWindSpd()) + " " +
                        Constants.WIND_METERS);

            else
                textViewWind.setText("Wind: " + String.valueOf(weatherBean.getData().get(0).getWindSpd()) + " "
                        + Constants.WIND_KILOMETERS);




            textViewVisibility.setText("Visibility: " + String.valueOf(weatherBean.getData().get(0).getVis()));
            textViewUVIndex.setText("UV INDEX: " + String.valueOf(weatherBean.getData().get(0).getUv()));
            textViewHumidity.setText("HUMIDITY: " + String.valueOf(weatherBean.getData().get(0).getRh()) + "%");
            textViewCloudCover.setText("CLOUD COVER: " + weatherBean.getData().get(0).getClouds());

            setFiveDaysAdapter(weatherBean.getData());

        } else {
            Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
        }


    }

    private void setFiveDaysAdapter(List<DataItem> data) {

        FiveDaysWeatherAdapter fiveDaysWeatherAdapter = new FiveDaysWeatherAdapter(data,getActivity());
        recyclerViewFiveDays.setAdapter(fiveDaysWeatherAdapter);
        FrequentFunction.recyclerViewAnimation(getActivity(),recyclerViewFiveDays);
    }
}
