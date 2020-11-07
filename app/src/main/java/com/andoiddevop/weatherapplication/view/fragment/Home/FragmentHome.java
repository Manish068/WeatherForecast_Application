package com.andoiddevop.weatherapplication.view.fragment.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.andoiddevop.weatherapplication.Database.SavedPlaces;
import com.andoiddevop.weatherapplication.Database.SavedPlacesRepository;
import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.MyApp;
import com.andoiddevop.weatherapplication.view.fragment.BaseFragment;
import com.andoiddevop.weatherapplication.view.fragment.Home.adapter.ViewPagerAdapter;
import com.andoiddevop.weatherapplication.view.fragment.weather.FragmentWeather;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class FragmentHome extends BaseFragment implements SavedPlacesRepository.FetchSavedLocations {

    ViewPager viewPager;
    TabLayout tabLayout;

    ViewPagerAdapter mViewPagerAdapter;
    private SavedPlacesRepository savedPlacesRepository;
    private SavedPlacesRepository.FetchSavedLocations fetchSavedLocations;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.ViewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        fetchSavedLocations=this;
        savedPlacesRepository = new SavedPlacesRepository(getActivity());
        savedPlacesRepository.getTasks(fetchSavedLocations);
        return view;

    }

    //Adding child fragment "Weather fragment" into Parent Fragment "Home fragment"
    private void setupViewPager(ViewPager ViewPager) {
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mViewPagerAdapter.addFragment(new FragmentWeather(), MyApp.cityName, String.valueOf(MyApp.latitude), String.valueOf(MyApp.longitude));
        ViewPager.setAdapter(mViewPagerAdapter);
    }


    @Override
    public void savedPlaces(List<SavedPlaces> savedPlaces) { updateViewPager(savedPlaces);
    }

    private void updateViewPager(List<SavedPlaces> savedPlaces) {
        for(int i = 0; i < savedPlaces.size(); i++){
            mViewPagerAdapter.addFragment(new FragmentWeather(),
                    savedPlaces.get(i).title,
                    savedPlaces.get(i).getLatitude(),
                    savedPlaces.get(i).getLongitude());

        }
        mViewPagerAdapter.notifyDataSetChanged();
    }
}
