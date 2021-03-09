package com.CA326MyBubble.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


import com.CA326MyBubble.controllers.AppController;
import com.CA326MyBubble.R;

import static com.CA326MyBubble.utils.Utilities.LOCATION;
import static com.CA326MyBubble.utils.Utilities.TYPE;
import static com.CA326MyBubble.utils.Utilities.SETUP;
import static com.CA326MyBubble.utils.Utilities.COUNTRY;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            // At runtime check if this is the users first time opening the app, if so prompt them to select a country they would like to view case data and news on, this can be
            // further changed in the settings activity, if they would like to view a different country.
            public void run() {
                SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean isFirstStart = getSharedPreferences.getBoolean("firstStart", true);
                if(isFirstStart) {
                    // Grabs country selected if none, return null and prompt country selection, once it returns back without null, goes to MainActivity Aka Dashboard
                    String country = AppController.getInstance().getCountry();
                    if(country==null||country.equals(""))
                    {
                        Intent it = new Intent(SplashActivity.this, CountriesActivity.class);
                        it.putExtra(LOCATION, COUNTRY);
                        it.putExtra(TYPE, SETUP);
                        startActivity(it);
                    } else {
                        Intent it = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(it);
                    }
                } else {
                    Intent it = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(it);
                }
                SplashActivity.this.finish();



            }
            // How long the splash screen will display
        }, 2000);
    }
}
