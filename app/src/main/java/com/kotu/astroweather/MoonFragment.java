package com.kotu.astroweather;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MoonFragment extends Fragment implements AstroWeatherCallback {
    private static final String ARG_TITLE = "Tittle";
    private static final String ARG_PAGE = "Page number";
    private String title;
    private int page;
    private AstroWeatherConfig astroWeatherConfig;
    private TextView moonWschodCzas;
    private TextView moonZachodCzas;
    private TextView moonNow;
    private TextView moonPelnia;
    private TextView moonFaza;
    private TextView moonDMS;


    public MoonFragment() {
    }

    public static Fragment newInstance(String title, int page) {
        MoonFragment fragment = new MoonFragment();
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
        View RootView = inflater.inflate(R.layout.fragment_moon, container, false);
        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
        astroWeatherConfig.registerForUpdates(this);
        initializeTextViews(RootView);
        initializeView();
        return RootView;
    }

    void initializeView() {
        moonWschodCzas.setText(astroWeatherConfig.getAstroCalculator().getMoonInfo().getMoonrise().toString());
        moonZachodCzas.setText(astroWeatherConfig.getAstroCalculator().getMoonInfo().getMoonset().toString());
        moonNow.setText(astroWeatherConfig.getAstroCalculator().getMoonInfo().getNextNewMoon().toString());
        moonPelnia.setText(astroWeatherConfig.getAstroCalculator().getMoonInfo().getNextFullMoon().toString());
        moonFaza.setText(String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getIllumination()));
        moonDMS.setText(String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getAge()));
    }

    void initializeTextViews(View RootView) {
        moonWschodCzas = (TextView) RootView.findViewById(R.id.moonWschod);
        moonZachodCzas = (TextView) RootView.findViewById(R.id.moonZachod);
        moonNow = (TextView) RootView.findViewById(R.id.moonNow);
        moonPelnia = (TextView) RootView.findViewById(R.id.moonPelnia);
        moonFaza = (TextView) RootView.findViewById(R.id.moonFaza);
        moonDMS = (TextView) RootView.findViewById(R.id.moonDMS);

    }


    @Override
    public void onSettingsUpdate() {
        initializeView();
    }


}
