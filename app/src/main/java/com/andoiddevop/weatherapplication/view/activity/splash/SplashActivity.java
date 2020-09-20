package com.andoiddevop.weatherapplication.view.activity.splash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.view.activity.BaseActivity;
import com.andoiddevop.weatherapplication.view.activity.home.HomeActivity;
import com.andoiddevop.weatherapplication.services.AppLocationService;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity {

    @BindView(R.id.imageViewSplash)
    ImageView imageViewSplash;
    @BindView(R.id.imageViewSplash2)
    ImageView imageViewSplash2;

    private int LOCATION_CODE=123;
    private String[] permission= {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    private LocationSettingsRequest mlocationSettingsRequest;
    private SettingsClient msettingClient;
    private int REQUEST_GPS=123;

    public static AppLocationService appLocationService;  //don't want to initialize again and again

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.weather).into(imageViewSplash);
        imageViewSplash.setAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce_left));
        Glide.with(this).load(R.drawable.weather2).into(imageViewSplash2);
        imageViewSplash2.setAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce_right));


        if(checkPermissions(permission) > 0){
            ActivityCompat.requestPermissions(this,permission,LOCATION_CODE);

        }

        if(!isGPSOn()){
            displayLocationEnableDialog();
        }else{
            navigateToHomeScreen();
        }
    }

    public void displayLocationEnableDialog(){
        LocationRequest request;
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        mlocationSettingsRequest = builder.build();

        //checking if status of GPS
        //if it is disabled i can show the dialog
        msettingClient = LocationServices.getSettingsClient(this);
        msettingClient.checkLocationSettings(mlocationSettingsRequest).addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("GPS","SUCCESS");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException)e).getStatusCode();
                switch (statusCode){
                    case LocationSettingsStatusCodes
                            .RESOLUTION_REQUIRED:
                        ResolvableApiException apiException = (ResolvableApiException) e;
                        try {
                            apiException.startResolutionForResult(SplashActivity.this,REQUEST_GPS);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d("GPS","Location Settings are unavailable");
                        break;
                }
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Log.d("GPS","CANCELLED");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_GPS) {
            switch (requestCode) {
                case RESULT_OK:
                    navigateToHomeScreen();
                    break;
                case RESULT_CANCELED:
                    //Open the setting page for user
                    openGPSEnabledSettings();
                    break;
            }
        }
    }

    private boolean isGPSOn(){
        Context context;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    private void navigateToHomeScreen() {
        appLocationService = new AppLocationService(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }
        },3000);

    }


    private void openGPSEnabledSettings(){
        //user can manually enable the gps
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}
