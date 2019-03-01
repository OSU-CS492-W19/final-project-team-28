package com.example.swapisearcher;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ListPreference userUnits = (ListPreference)findPreference(getString(R.string.pref_key));
        ListPreference userLang = (ListPreference)findPreference(getString(R.string.pref_Lang_key));
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    ListPreference lp = (ListPreference)findPreference(getString(R.string.pref_key));
    CharSequence entry = lp.getEntry(); //delete
    String val = lp.getValue();


    ListPreference lp2 = (ListPreference)findPreference(getString(R.string.pref_Lang_key));
    CharSequence entry2 = lp2.getEntry(); //delete
    String val2 = lp2.getValue();
    //WeatherPreferences.setUnits(val);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.prefs);
    }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
