package com.CA326MyBubble.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.CA326MyBubble.controllers.AppController;

import com.CA326MyBubble.interfaces.OnFragmentInteractionListener;
import com.CA326MyBubble.model.CountryLocal;
import com.CA326MyBubble.model.CountryServer;
import com.CA326MyBubble.R;
import com.CA326MyBubble.utils.ThemeController;
import com.google.firebase.auth.FirebaseAuth;

import static com.CA326MyBubble.utils.AppUtils.LIST_REQUEST;
import static com.CA326MyBubble.utils.AppUtils.LIST_TYPE;
import static com.CA326MyBubble.utils.AppUtils.LIST_TYPE_LOCAL;
import static com.CA326MyBubble.utils.AppUtils.LOCATION_COUNTRY;


public class SettingsActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private static final String TITLE_TAG = "settingsActivityTitle";
    String state, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        //linearLayout = findViewById(R.id.linearLayout);
        SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstStart = getSharedPreferences.getBoolean("firstStart", true);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        } else {
            setTitle(savedInstanceState.getCharSequence(TITLE_TAG));
        }
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            setTitle(R.string.title_activity_settings);
                            // linearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (AppController.getInstance().getAppType().equals("covidNg")) {
            SharedPreferences pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
            state = pref.getString("state", null);
            country = pref.getString("country", null);
        }


    /*    CardView cardViewCountry = findViewById(R.id.cardCountry);
        CardView cardViewState = findViewById(R.id.cardState);
        tvCountry = findViewById(R.id.tvCountry);
        tvState = findViewById(R.id.tvState);
        tvCountry.setText(country);
        tvState.setText(state);

        cardViewState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayout.setVisibility(View.GONE);
                location = LOCATION_STATE;
                beginListTransaction(location);

            }
        });
        cardViewCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                location = LOCATION_COUNTRY;
                beginListTransaction(location);
            }
        });
        */


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save current activity title so we can set it again after a configuration change
        outState.putCharSequence(TITLE_TAG, getTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }
        return super.onSupportNavigateUp();
    }



    @Override
    public void listItemClickServer(CountryServer countryServer) {
    }

    @Override
    public void listItemClickSetting(CountryLocal countryLocal, String location, String listType) {

        SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = getSharedPreferences.edit();
        String countryName;
        String continent;


        countryName = countryLocal.getName();
        continent = countryLocal.getContinent();
        e.putString("countryLocal", countryName);
        e.putString("continent", continent);
        AppController.getInstance().setCountry(countryName);
        AppController.getInstance().setContinent(continent);

        e.apply();



        onBackPressed();

    }


    public static class SettingsFragment extends PreferenceFragmentCompat {


        private FirebaseAuth mAuth;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

            setPreferencesFromResource(R.xml.root_preferences_global, rootKey);
            mAuth = FirebaseAuth.getInstance();

            Preference prefCountry = findPreference("country");

            if (prefCountry != null) {
                prefCountry.setSummary(AppController.getInstance().getCountry());
                prefCountry.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        Intent it = new Intent(getActivity(), ListActivity.class);
                        it.putExtra(LIST_REQUEST, LOCATION_COUNTRY);
                        it.putExtra(LIST_TYPE, LIST_TYPE_LOCAL);
                        startActivity(it);
                        getActivity().finish();
                        return true;
                    }
                });
            }

            Preference prefProfile = findPreference("profile");
            if (prefProfile != null) {
                prefProfile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        Intent profileIntent = new Intent(getActivity(), SetupActivity.class);
                        startActivity(profileIntent);
                        getActivity().finish();
                        return true;
                    }
                });
            }

            Preference prefLogout = findPreference("logout");
            if (prefLogout != null) {
                prefLogout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        mAuth.signOut();
                        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(loginIntent);
                        getActivity().finish();
                        return true;
                    }
                });
            }

            ListPreference themeListPreference = findPreference("theme");

            if (themeListPreference != null) {
            // Uses AS's built in library for working between Night and Day modes for an application
                int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        themeListPreference.setValueIndex(1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        themeListPreference.setValueIndex(0);
                        break;
                }
                themeListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {

                        new ThemeController(newValue.toString());

                        return true;
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
            //body = "\n\n-----------------------------\nPlease don't remove this information\nCountry: "+ AppController.getInstance().getCountry()+"\nContinent: "+ AppController.getInstance().getContinent()+" \n Device OS: Android \n Device OS version: " +
                    //Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    //"\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
}
