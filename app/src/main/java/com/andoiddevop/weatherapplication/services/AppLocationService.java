package com.andoiddevop.weatherapplication.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.andoiddevop.weatherapplication.MyApp;
import com.andoiddevop.weatherapplication.utils.FrequentFunction;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class AppLocationService implements LocationListener {

    private Context mContext;
    private LocationManager locationManager;         //getting the gps location
    private Criteria criteria = new Criteria();
    private String provider;

    public AppLocationService(Context mContext) {
        this.mContext = mContext;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //if network is enabled we fetch the location from network
        //else if gps is enabled we fetch the location from gps
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE); // accuracy fine used for gps
        }
        if (isNetworkEnabled) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE); // accuracy fine used for gps
        }

        provider = locationManager.getBestProvider(criteria, true);
        getLastLocation();

    }

    private void getLastLocation() {
        //the last known location
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    MyApp.latitude = location.getLatitude();
                    MyApp.longitude = location.getLongitude();
                    MyApp.cityName = FrequentFunction.getCityName(mContext,location.getLatitude(),location.getLongitude());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            MyApp.latitude = location.getLatitude();
            MyApp.longitude = location.getLongitude();
            MyApp.cityName = FrequentFunction.getCityName(mContext,location.getLatitude(),location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void stopGPSServices(){
        if(locationManager != null){
            locationManager.removeUpdates(AppLocationService.this);
        }
    }
}
