package com.andoiddevop.weatherapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.andoiddevop.weatherapplication.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    //run time permission check here
    public int checkPermissions(String[] permissions){
        int permissionNeeded = 0;
        if(Build.VERSION.SDK_INT >= 23){
            for(int i = 0; i < permissions.length; i++){
                int result = ContextCompat.checkSelfPermission(this,permissions[i]);
                if(result != PackageManager.PERMISSION_GRANTED){
                    permissionNeeded++;
                }
            }
        }
        return permissionNeeded;
    }
}