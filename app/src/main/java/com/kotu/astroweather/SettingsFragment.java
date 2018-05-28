package com.kotu.astroweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class SettingsFragment extends PreferenceFragment implements AstroWeatherCallback, Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String ARG_TITLE = "Tittle";
    private static final String ARG_PAGE = "Page number";
    private String title;
    private int page;
    private SharedPreferences preferences;
    private AstroWeatherConfig astroWeatherConfig;

    private EditTextPreference szer;
    private EditTextPreference dlug;
    private ListPreference refresh;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String title, int page) {
        SettingsFragment fragment = new SettingsFragment();
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
            astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
            astroWeatherConfig.registerForUpdates(this);
            initializePreferences();
            setupPreferences();
            checkPreferences();
        }

    }

    void initializePreferences() {
        addPreferencesFromResource(R.xml.preference);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        szer = (EditTextPreference) findPreference(getString(R.string.prefSzer));
        dlug = (EditTextPreference) findPreference(getString(R.string.prefDlug));
        refresh = (ListPreference) findPreference(getString(R.string.prefRef));
    }

    void setupPreferences() {
        refresh.setSummary("15min");
        szer.setText(String.valueOf(astroWeatherConfig.getLocation().getLatitude()));
        dlug.setText(String.valueOf(astroWeatherConfig.getLocation().getLongitude()));
        bindPreferenceSummaryToValue(szer);
        bindPreferenceSummaryToValue(dlug);
        bindPreferenceSummaryToValue(refresh);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefRef)));
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
    }


    void checkPreferences() {
        if (!preferences.getBoolean("needs_setup", false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("needs_setup", false);
            editor.apply();
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        szer.setEnabled(true);
        dlug.setEnabled(true);
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, preferences.getString(preference.getKey(), null));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        if (value != null) {
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(value.toString());

                preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

            } else if (preference instanceof EditTextPreference) {
                try {
                    double doubleValue = Double.parseDouble(value.toString());
                    if (doubleValue < -90 || doubleValue > 90) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Zły zakres danych", Toast.LENGTH_SHORT).show();
                    return false;
                }
                preference.setSummary(value.toString());
            } else if (preference instanceof EditTextPreference) {
                try {
                    double doubleValue = Double.parseDouble(value.toString());
                    if (doubleValue < -180 || doubleValue > 180) throw new Exception();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Zły zakres danych", Toast.LENGTH_SHORT).show();
                    return false;
                }
                preference.setSummary(value.toString());

            }
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    @Override
    public void onSettingsUpdate() {
    }

}





