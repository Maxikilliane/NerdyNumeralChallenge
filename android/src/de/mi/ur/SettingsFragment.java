package de.mi.ur;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Anna-Marie on 10.08.2016.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
