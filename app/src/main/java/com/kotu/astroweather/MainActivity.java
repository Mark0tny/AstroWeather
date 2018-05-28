package com.kotu.astroweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;

public class MainActivity extends FragmentActivity {

    private FragmentPagerAdapter adapterViewPager;
    private SharedPreferences preferences;
    private AstroWeatherConfig astroWeatherConfig;
    private Runnable updateInfo;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateInfo = new Runnable() {
            @Override
            public void run() {
                synchAstroWeather();
                handler.postDelayed(this, astroWeatherConfig.getTimeInterval());
                //Toast.makeText(getApplicationContext(), "data update"+" " + astroWeatherConfig.getTimeInterval(), Toast.LENGTH_SHORT).show();
            }
        };
        handler.post(updateInfo);

        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();

        if (!getResources().getBoolean(R.bool.isTablet)) {
            initializePageAdapter();
        }else{
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new SettingsFragment()).commit();
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().remove(getString(R.string.szerokosc)).apply();
        preferences.edit().remove(getString(R.string.dlugosc)).apply();
        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();



    }

    void synchAstroWeather() {
        astroWeatherConfig.setTimeInterval(preferences.getString(getString(R.string.prefRef), "5000"));
        double szer = Double.parseDouble(preferences.getString(getString(R.string.prefSzer), "50"));
        double dlug = Double.parseDouble(preferences.getString(getString(R.string.prefDlug), "20"));
        astroWeatherConfig.setLocation(new AstroCalculator.Location(szer, dlug));
        //Toast.makeText(getApplicationContext(), "data update", Toast.LENGTH_SHORT).show();
    }
    @Override

    protected void onResume() {
        super.onResume();
        synchAstroWeather();
    }

    void initializePageAdapter(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapterViewPager = new PagerAdapter(getFragmentManager());
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();
        viewPager.setAdapter(adapterViewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                synchAstroWeather();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
