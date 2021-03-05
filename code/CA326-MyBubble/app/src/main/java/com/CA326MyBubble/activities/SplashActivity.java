package com.CA326MyBubble.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


import com.CA326MyBubble.controllers.AppController;
import com.CA326MyBubble.R;

import static com.CA326MyBubble.utils.AppUtils.LIST_REQUEST;
import static com.CA326MyBubble.utils.AppUtils.LIST_TYPE;
import static com.CA326MyBubble.utils.AppUtils.LIST_TYPE_SETUP;
import static com.CA326MyBubble.utils.AppUtils.LOCATION_COUNTRY;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean isFirstStart = getSharedPreferences.getBoolean("firstStart", true);
                if(isFirstStart) {
                    String country = AppController.getInstance().getCountry();
                    if(country==null||country.equals(""))
                    {
                        Intent it = new Intent(SplashActivity.this, ListActivity.class);
                        it.putExtra(LIST_REQUEST, LOCATION_COUNTRY);
                        it.putExtra(LIST_TYPE, LIST_TYPE_SETUP);
                        startActivity(it);
                    }
                    else {
                        Intent it = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(it);
                        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                    }
                }
                else {
                    Intent it = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(0,0);
                }



                SplashActivity.this.finish();



            }
        }, 1000);
    }
}
