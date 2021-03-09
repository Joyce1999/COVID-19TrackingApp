package com.CA326MyBubble.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.CA326MyBubble.controllers.AppController;
import com.CA326MyBubble.R;
import com.CA326MyBubble.fragments.CountriesFragment;
import com.CA326MyBubble.fragments.CountryDetailFrag;
import com.CA326MyBubble.interfaces.InteractionListener;
import com.CA326MyBubble.model.CountrySelection;
import com.CA326MyBubble.model.GlobalCountries;

import static com.CA326MyBubble.utils.Utilities.LOCATION;
import static com.CA326MyBubble.utils.Utilities.TYPE;
import static com.CA326MyBubble.utils.Utilities.LOCAL;
import static com.CA326MyBubble.utils.Utilities.SETUP;


public class CountriesActivity extends AppCompatActivity implements InteractionListener {

    String location;
    private void startTransaction(String data, String listType)
    {
        CountriesFragment fragment = CountriesFragment.newInstance(data, listType);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(fragment.getTag())
                .commit();
    }
    private void startDetails(GlobalCountries globalCountries){
        CountryDetailFrag fragment = CountryDetailFrag.newInstance(globalCountries);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(fragment.getTag()).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String data = getIntent().getStringExtra(LOCATION);
        String listType = getIntent().getStringExtra(TYPE);
        location = listType;
        startTransaction(data, listType);

        getSupportFragmentManager().addOnBackStackChangedListener(
                () -> {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {

                        onBackPressed();

                    }
                });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }



    }

    @Override
    public void listItemClickServer(GlobalCountries globalCountries) {
        startDetails(globalCountries);

    }

    @Override
    public void listItemClickSetting(CountrySelection countrySelection, String location, String listType) {

        SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = getSharedPreferences.edit();

        String countryName;
        String continent;
        String code;

        countryName =  countrySelection.getName();
        continent = countrySelection.getContinent();
        code = countrySelection.getCode();

        e.putString("country", countryName);
        e.putString("continent",continent);
        e.putString("code",code);

        AppController.getInstance().setCountry(countryName);
        AppController.getInstance().setContinent(continent);
        AppController.getInstance().setCode(code);


        if(listType.equals(SETUP))
        {
            e.putBoolean("firstStart",false);
            e.apply();
            startActivity(new Intent(getApplicationContext(), com.CA326MyBubble.activities.MainActivity.class));
            finish();
        }
        else if(listType.equals(LOCAL)) {
            e.apply();
            startActivity(new Intent(getApplicationContext(), com.CA326MyBubble.activities.SettingsActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }
        return super.onSupportNavigateUp();
    }
}
