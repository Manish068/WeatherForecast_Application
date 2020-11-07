package com.andoiddevop.weatherapplication.view.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.view.activity.BaseActivity;
import com.andoiddevop.weatherapplication.view.fragment.Home.FragmentHome;
import com.andoiddevop.weatherapplication.view.fragment.ManageLocation.FragmentManageLocation;
import com.andoiddevop.weatherapplication.view.fragment.Settings.FragmentSettings;
import com.andoiddevop.weatherapplication.utils.Constants;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private int navigationIndex = 0;
    private String[] drawerTitles;
    private String CURRENT_TAG = Constants.TAG_HOME;

    private Handler handler;
    ActionBarDrawerToggle toggle;

    boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        handler = new Handler();
        drawerTitles = getResources().getStringArray(R.array.navigation_titles_array);

        setSupportActionBar(toolbar);

        settingUpNavigationView();



        if (savedInstanceState == null) {
            navigationIndex = 0;
            CURRENT_TAG = Constants.TAG_HOME;
            loadFragment();
        }



    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */

    /*
     * Returns respected fragment that user
     * selected from navigation menu
     */


    private void loadFragment() {
     // selecting appropriate nav menu item
        settingUpNavigationMenu();

        settingToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawerLayout.closeDrawers();

            // show or hide the fab button
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getSelectedFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();

            }
        };

        handler.post(runnable);
        drawerLayout.closeDrawers();
        invalidateOptionsMenu();

    }

    private void settingToolbarTitle() {
        getSupportActionBar().setTitle(drawerTitles[navigationIndex]);
    }

    private void settingUpNavigationMenu() {
        navView.getMenu().getItem(navigationIndex).setChecked(true);
    }

    private Fragment getSelectedFragment() {
        switch (navigationIndex) {
            case 0:
                //Home
                FragmentHome fragmentHome = new FragmentHome();
                return fragmentHome;

            case 1:
                //ManageLocation
                FragmentManageLocation fragmentManageLocation = new FragmentManageLocation();
                return fragmentManageLocation;

            case 2:
                FragmentSettings fragmentSettings = new FragmentSettings();
                return fragmentSettings;

            default:
                return new FragmentHome();

        }
    }

    private void settingUpNavigationView() {
        navView.setNavigationItemSelectedListener(item ->
        {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    navigationIndex = 0;
                    CURRENT_TAG = Constants.TAG_HOME;
                    break;
                case R.id.nav_manage_location:
                    navigationIndex = 1;
                    CURRENT_TAG = Constants.TAG_MANAGE_LOCATION;
                    break;
                case R.id.nav_Setting:
                    navigationIndex = 2;
                    CURRENT_TAG = Constants.TAG_SETTINGS;
                    break;
                default:
                    navigationIndex = 0;

            }

            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }
            item.setChecked(true);

            loadFragment();
            return true;
        });

        toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar,
                R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (navigationIndex == 0)
        {
            getMenuInflater().inflate(R.menu.menu_items,menu);
        }

        return true;
    }
}
