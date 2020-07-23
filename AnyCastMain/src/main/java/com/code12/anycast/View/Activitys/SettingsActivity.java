package com.code12.anycast.View.Activitys;

import android.os.Bundle;

import com.code12.anycast.R;
import com.code12.playerframework.config.PlayerChooser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Preference pref = findPreference("PlayerChooser");
            pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String v = newValue.toString();
                    if (v == null) return false;
                    PlayerChooser.setDefaultPlanId(Integer.valueOf(v));
                    return true;
                }
            });
        }

        //TODO: dynamic fill the player modules.
//        private void buildSettings() {
//            ListPreference listPreferenceCategory = (ListPreference) this.findPreference("PlayerChooser");
//            if (listPreferenceCategory != null) {
//                listPreferenceCategory.setEntries(entries);
//                listPreferenceCategory.setEntryValues(entryValues);
//            }
//        }
    }

}