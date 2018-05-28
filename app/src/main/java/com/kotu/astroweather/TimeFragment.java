package com.kotu.astroweather;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeFragment extends Fragment {

    private TextView timer;
    private TextView szer;
    private TextView dlug;
    private Handler mHandler;
    private AstroWeatherConfig astroWeatherConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        initTime(view);
        return view;
    }

    void initTime(View view) {
        timer = (TextView) view.findViewById(R.id.data);
        szer = (TextView) view.findViewById(R.id.mainSzerokosc);
        dlug = (TextView) view.findViewById(R.id.mainDludosc);
        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable, 2000);
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            timer.setText(dateFormat.format(Calendar.getInstance(TimeZone.getDefault()).getTime()));
            szer.setText(String.valueOf(astroWeatherConfig.getLocation().getLatitude()));
            dlug.setText(String.valueOf(astroWeatherConfig.getLocation().getLongitude()));
            TimeFragment.this.mHandler.postDelayed(m_Runnable, 1000);
        }
    };
}