package com.kotu.astroweather;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class SunFragment extends Fragment implements AstroWeatherCallback {
    private static final String ARG_TITLE = "Tittle";
    private static final String ARG_PAGE = "Page number";
    private String title;
    private int page;
    private AstroWeatherConfig astroWeatherConfig;


    private TextView sunWschodCzas;
    private TextView sunWschodAzymut;
    private TextView sunZachodCzas;
    private TextView sunZachodAzymut;
    private TextView zmierzch;
    private TextView swit;


    public SunFragment() {
    }

    public static SunFragment newInstance(String title, int page) {
        SunFragment fragment = new SunFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            page = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_sun, container, false);
        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
        astroWeatherConfig.registerForUpdates(this);
        initializeTextViews(RootView);
        initializeView();

        return RootView;

    }


    void initializeView() {
        sunWschodCzas.setText(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunrise().toString());
        sunWschodAzymut.setText(String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getAzimuthRise()));
        sunZachodCzas.setText(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunset().toString());
        sunZachodAzymut.setText(String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getAzimuthSet()));
        zmierzch.setText(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightEvening().toString());
        swit.setText(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightMorning().toString());
    }

    void initializeTextViews(View RootView) {
        sunWschodCzas = (TextView) RootView.findViewById(R.id.sunWschodCzas);
        sunWschodAzymut = RootView.findViewById(R.id.sunWschodAzymut);
        sunZachodCzas = RootView.findViewById(R.id.sunZachodCzas);
        sunZachodAzymut = RootView.findViewById(R.id.sunZachódAzymut);
        zmierzch = RootView.findViewById(R.id.sunZmierzch);
        swit = RootView.findViewById(R.id.sunSwitC);
    }

    @Override
    public void onSettingsUpdate() {
        initializeView();
    }


}
