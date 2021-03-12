package com.CA326MyBubble.App_Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.CA326MyBubble.App_Controllers.AppController;
import com.CA326MyBubble.R;
import com.CA326MyBubble.Layout_Fragments.CountriesFragment;
import com.CA326MyBubble.Layout_Fragments.CountryDetailFrag;
import com.CA326MyBubble.App_Interfaces.InteractionListener;
import com.CA326MyBubble.Layout_Models.CountrySelection;
import com.CA326MyBubble.Layout_Models.GlobalCountries;

import static com.CA326MyBubble.App_Utilities.Utilities.LOCATION;
import static com.CA326MyBubble.App_Utilities.Utilities.TYPE;
import static com.CA326MyBubble.App_Utilities.Utilities.COUNTRIES;
import static com.CA326MyBubble.App_Utilities.Utilities.SETUP;


public class CountriesActivity extends AppCompatActivity implements InteractionListener {

    String location;
    // Sets up and initialises the fragment
    private void initialiseFragment(String data, String listType)
    {
        CountriesFragment frag = CountriesFragment.newInstance(data, listType);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag).addToBackStack(frag.getTag())
                .commit();
    }
    private void startDetails(GlobalCountries globalCountries){
        CountryDetailFrag frag = CountryDetailFrag.newInstance(globalCountries);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag).addToBackStack(frag.getTag()).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        // Setup Action bar
        Toolbar bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);

        String listType = getIntent().getStringExtra(TYPE);
        String data = getIntent().getStringExtra(LOCATION);

        location = listType;
        initialiseFragment(data, listType);
        // Allows a user to return to previous page via actionbar
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
    // When first opening the app displays all available details
    @Override
    public void listItemClickCountry(GlobalCountries globalCountries) {
        startDetails(globalCountries);

    }
    // Grabs the country select option from settings, and essentially gathers the selected data and sets it.
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

        // Sets the data via app controller
        AppController.getInstance().setCountry(countryName);
        AppController.getInstance().setContinent(continent);
        AppController.getInstance().setCode(code);

        // If first time opening the app, user will be prompted to select a country
        if(listType.equals(SETUP))
        {
            e.putBoolean("firstStart",false);
            e.apply();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        // Otherwise its not first start and can be selected via the settings activity
        else if(listType.equals(COUNTRIES)) {
            e.apply();
            startActivity(new Intent(getApplicationContext(), com.CA326MyBubble.App_Activities.SettingsActivity.class));
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
