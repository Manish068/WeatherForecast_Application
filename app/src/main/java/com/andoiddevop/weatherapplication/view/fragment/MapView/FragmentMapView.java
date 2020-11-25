package com.andoiddevop.weatherapplication.view.fragment.MapView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.model.WeatherBean;
import com.andoiddevop.weatherapplication.utils.Constants;
import com.andoiddevop.weatherapplication.utils.FrequentFunction;
import com.andoiddevop.weatherapplication.utils.SharePref;
import com.andoiddevop.weatherapplication.view.fragment.BaseFragment;
import com.andoiddevop.weatherapplication.view.fragment.weather.presenter.WeatherFragmentPresenter;
import com.andoiddevop.weatherapplication.view.fragment.weather.view.WeatherFragmentView;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class FragmentMapView extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapLongClickListener, WeatherFragmentView {

    private static final int REQUEST_LOCATION = 123;
    private static final String TAG = "FragmentMapView";
    private final float DEFAULT_ZOOM = 15;
    //Initialize variable
    ArrayList<Marker> tripMarker = new ArrayList<>();
    MarkerOptions markerOptions = new MarkerOptions();
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker globalmarker = null;
    private TextView textViewCityName, textViewAddress, textViewLatitude, textViewLongitude;
    private TextView textViewWeather;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapview, container, false);
        init(view);

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        return view;
    }

    private void init(View view) {

        textViewCityName = view.findViewById(R.id.textViewCityName);
        textViewAddress = view.findViewById(R.id.textViewAddress);
        textViewLatitude = view.findViewById(R.id.textViewLatitude);
        textViewLongitude = view.findViewById(R.id.textViewLongitude);
        textViewWeather = view.findViewById(R.id.textViewWeather);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapLongClickListener(this);

    }


    @SuppressLint("MissingPermission")
    @Override
    public boolean onMyLocationButtonClick() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(getActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onLocationResult(final LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getActivity())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {

                            mapFragment.getMapAsync(googleMap -> {

                                int latestLocationIndex = locationResult.getLocations().size() - 1;
                                double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                                if (!tripMarker.isEmpty()) {
                                    resetSelectedMarker();
                                    globalmarker = googleMap.addMarker(markerOptions.position(new LatLng(latitude, longitude)).title(FrequentFunction.getAddress(getActivity(), latitude, longitude)));
                                } else {
                                    globalmarker = googleMap.addMarker(markerOptions.position(new LatLng(latitude, longitude)));
                                }
                                tripMarker.add(globalmarker);
                                getWeatherInformation(latitude, longitude);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17));
                                Location location = new Location("providerNA");
                                location.setLatitude(latitude);
                                location.setLongitude(longitude);
                            });


                        }

                    }
                }, Looper.getMainLooper());
        return false;

    }

    @SuppressLint("SetTextI18n")
    private void getWeatherInformation(double latitude, double longitude) {

        WeatherFragmentPresenter weatherFragmentPresenter = new WeatherFragmentPresenter(this);
        weatherFragmentPresenter.getCurrentWeather(Double.toString(latitude), Double.toString(longitude), Constants.WEATHERBIT_API_KEY);
        textViewAddress.setText(FrequentFunction.getAddress(getActivity(), latitude, longitude));
        textViewCityName.setText(FrequentFunction.getCityName(getActivity(), latitude, longitude));
        textViewLatitude.setText(getString(R.string.latitude) + " " + String.valueOf(latitude));
        textViewLongitude.setText(getString(R.string.longitude) + " " + String.valueOf(longitude));
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        Location location = new Location("providerNA");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);


        if (!tripMarker.isEmpty()) {
            resetSelectedMarker();
            globalmarker = mMap.addMarker(new MarkerOptions().position(latLng).title(FrequentFunction.getAddress(getActivity(), latLng.latitude, latLng.longitude)).draggable(false));
        } else {
            globalmarker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(false));
        }
        tripMarker.add(globalmarker);
        getWeatherInformation(latLng.latitude, latLng.longitude);


    }

    private void removeMarker() {
        for (Marker marker : tripMarker) {
            marker.remove();
        }
    }


    private void resetSelectedMarker() {
        if (globalmarker != null) {
            globalmarker.setVisible(true);
            globalmarker = null;
            removeMarker();
            tripMarker.clear();
        }
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

            if (SharePref.getDataFromPref(Constants.TEMPERATURE).equalsIgnoreCase(Constants.TEMPERATURE_F)) {
                textViewWeather.setText(FrequentFunction.celsiusToFahrenheit(weatherBean.getData().get(0).getTemp()) + "°F");
            }
        } else {
            textViewWeather.setText(String.valueOf(weatherBean.getData().get(0).getTemp()) + "°C");

        }
    }
}
