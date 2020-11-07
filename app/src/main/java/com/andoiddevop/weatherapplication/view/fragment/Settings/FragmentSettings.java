package com.andoiddevop.weatherapplication.view.fragment.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.utils.Constants;
import com.andoiddevop.weatherapplication.utils.SharePref;
import com.andoiddevop.weatherapplication.view.fragment.BaseFragment;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

public class FragmentSettings extends BaseFragment {

    ToggleSwitch toggleTemperature, toggleTimeFormat;
    TextView textViewPerception, textViewPressure, textViewWindSpeed, textViewDateFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        init(view);

        setToggleData();
        setTextData();
        toggleListner();


        textViewDateFormat.setOnClickListener(this::showDateFormatPopup);
        
        textViewWindSpeed.setOnClickListener(this::showWindSpeedPopup);

        textViewPressure.setOnClickListener(this::showPressurePopup);

        textViewPerception.setOnClickListener(this::showPreciptionPopup);

        
        return view;
    }

    private void setToggleData() {
        if(SharePref.getDataFromPref(Constants.TEMPERATURE).equalsIgnoreCase(Constants.TEMPERATURE_F)){
            toggleTemperature.setCheckedPosition(1);
        }else {
            toggleTemperature.setCheckedPosition(0);
        }

        if(SharePref.getDataFromPref(Constants.TIME_FORMAT).equalsIgnoreCase(Constants.TIME_FORMAT_24)){
            toggleTimeFormat.setCheckedPosition(1);
        }else {
            toggleTimeFormat.setCheckedPosition(0);
        }
    }

    private void setTextData() {
        if(!SharePref.getDataFromPref(Constants.DATE_FORMAT).isEmpty())
            textViewDateFormat.setText(SharePref.getDataFromPref(Constants.DATE_FORMAT));
        if(!SharePref.getDataFromPref(Constants.WIND_SPEED).isEmpty())
            textViewDateFormat.setText(SharePref.getDataFromPref(Constants.WIND_SPEED));
        if(!SharePref.getDataFromPref(Constants.PRESSURE).isEmpty())
            textViewDateFormat.setText(SharePref.getDataFromPref(Constants.PRESSURE));
        if(!SharePref.getDataFromPref(Constants.PRECIPTION).isEmpty())
            textViewDateFormat.setText(SharePref.getDataFromPref(Constants.PRECIPTION));
    }

    private void toggleListner() {
        toggleTemperature.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if(i==0)
                    SharePref.setDataPref(Constants.TEMPERATURE,Constants.TEMPERATURE_C);
                else
                    SharePref.setDataPref(Constants.TEMPERATURE,Constants.TEMPERATURE_F);


            }
        });

        toggleTimeFormat.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if(i==0)
                    SharePref.setDataPref(Constants.TIME_FORMAT,Constants.TIME_FORMAT_12);
                else
                    SharePref.setDataPref(Constants.TIME_FORMAT,Constants.TIME_FORMAT_24);

            }
        });
    }

    private void showPreciptionPopup(View v) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), v);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.item_mm:
                        SharePref.setDataPref(Constants.PRECIPTION,Constants.PRECIPTION_MM);
                        textViewPerception.setText(Constants.PRECIPTION_MM);
                        popupMenu.dismiss();
                        break;
                    case R.id.item_inches:
                        SharePref.setDataPref(Constants.PRECIPTION,Constants.PRECIPTION_INCHES);
                        textViewPerception.setText(Constants.PRECIPTION_INCHES);
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });

        popupMenu.inflate(R.menu.menu_preciption_popup);
        popupMenu.show();
    }

    private void showPressurePopup(View v) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), v);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.item_inches:
                        SharePref.setDataPref(Constants.PRESSURE,Constants.PRESSURE_INCHES);
                        textViewPressure.setText(Constants.PRESSURE_INCHES);
                        popupMenu.dismiss();
                        break;
                    case R.id.item_millibar:
                        SharePref.setDataPref(Constants.PRESSURE,Constants.PRESSURE_MILLIBAR);
                        textViewPressure.setText(Constants.PRECIPTION_MM);
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });

        popupMenu.inflate(R.menu.menu_pressure_popup);
        popupMenu.show();
    }

    private void showWindSpeedPopup(View v) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), v);

        popupMenu.setOnMenuItemClickListener(item -> {

            int id = item.getItemId();

            switch (id) {
                case R.id.item_km:
                    SharePref.setDataPref(Constants.WIND_SPEED,Constants.WIND_KILOMETERS);
                    textViewWindSpeed.setText(Constants.WIND_KILOMETERS);
                    popupMenu.dismiss();
                    break;
                case R.id.item_mh:
                    SharePref.setDataPref(Constants.WIND_SPEED,Constants.WIND_METERS);
                    textViewWindSpeed.setText(Constants.WIND_METERS);
                    popupMenu.dismiss();
                    break;
            }
            return true;
        });

        popupMenu.inflate(R.menu.menu_wind_popup);
        popupMenu.show();
    }

    private void showDateFormatPopup(View v) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), v);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.system:
                        SharePref.setDataPref(Constants.DATE_FORMAT,Constants.DATE_FORMAT_SYSTEM);
                        textViewDateFormat.setText(Constants.DATE_FORMAT_SYSTEM);
                        popupMenu.dismiss();
                        break;
                    case R.id.item_api:
                        SharePref.setDataPref(Constants.DATE_FORMAT,Constants.DATE_FORMAT_API);
                        textViewDateFormat.setText(Constants.DATE_FORMAT_API);
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });

        popupMenu.inflate(R.menu.menu_date_popup);
        popupMenu.show();
    }

    private void init(View view) {
        toggleTemperature = view.findViewById(R.id.toggleTemperature);
        toggleTimeFormat = view.findViewById(R.id.toggleTimeFormat);
        textViewPerception = view.findViewById(R.id.textViewPerception);
        textViewPressure = view.findViewById(R.id.textViewPressure);
        textViewWindSpeed = view.findViewById(R.id.textViewWindSpeed);
        textViewDateFormat = view.findViewById(R.id.textViewDateFormat);
    }
}
