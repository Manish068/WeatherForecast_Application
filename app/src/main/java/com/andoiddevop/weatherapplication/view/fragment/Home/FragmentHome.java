package com.andoiddevop.weatherapplication.view.fragment.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.MyApp;
import com.andoiddevop.weatherapplication.view.fragment.BaseFragment;
import com.andoiddevop.weatherapplication.view.fragment.Home.adapter.ViewPagerAdapter;
import com.andoiddevop.weatherapplication.view.fragment.weather.FragmentWeather;
import com.google.android.material.tabs.TabLayout;

public class FragmentHome extends BaseFragment {

    ViewPager viewPager;
    TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout =view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.ViewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;

    }

    private void setupViewPager(ViewPager ViewPager) {
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mViewPagerAdapter.addFragment(new FragmentWeather(), MyApp.cityName, String.valueOf(MyApp.latitude),String.valueOf(MyApp.longitude));
        ViewPager.setAdapter(mViewPagerAdapter);
    }


}
