package de.mi.ur.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import de.mi.ur.SettingsFragment;

/**
 * Created by Anna-Marie on 10.08.2016.
 */
public class SettingsActivity extends PreferenceActivity {

    public static final String KEY_PREF_USER_NAME = "pref_user_name";
    public static final String KEY_PREF_PUSH_NOTIFICATIONS = "pref_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }


}
