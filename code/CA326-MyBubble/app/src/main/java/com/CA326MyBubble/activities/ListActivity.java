package com.CA326MyBubble.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.CA326MyBubble.controllers.AppController;
import com.CA326MyBubble.R;
import com.CA326MyBubble.fragments.ListActivityFragment;
import com.CA326MyBubble.fragments.ListDetailFragment;
import com.CA326MyBubble.interfaces.OnFragmentInteractionListener;
import com.CA326MyBubble.model.CountryLocal;
import com.CA326MyBubble.model.CountryServer;

import static com.CA326MyBubble.utils.AppUtils.LIST_REQUEST;
import static com.CA326MyBubble.utils.AppUtils.LIST_TYPE;
import static com.CA326MyBubble.utils.AppUtils.LIST_TYPE_LOCAL;
import static com.CA326MyBubble.utils.AppUtils.LIST_TYPE_SETUP;


public class ListActivity extends AppCompatActivity implements OnFragmentInteractionListener {


    String location;
    private void beginListTransaction(String data, String listType)
    {
        ListActivityFragment fragment = ListActivityFragment.newInstance(data, listType);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(fragment.getTag())
                .commit();
    }
    private void beginDetailsTransaction(CountryServer countryServer){
        ListDetailFragment fragment = ListDetailFragment.newInstance(countryServer);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(fragment.getTag()).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String data = getIntent().getStringExtra(LIST_REQUEST);
        String listType = getIntent().getStringExtra(LIST_TYPE);
        location = listType;
        beginListTransaction(data, listType);

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
    public void listItemClickServer(CountryServer countryServer) {
        beginDetailsTransaction(countryServer);

    }

    @Override
    public void listItemClickSetting(CountryLocal countryLocal, String location, String listType) {

        SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = getSharedPreferences.edit();

        String countryName;
        String continent;
        String code;

        countryName =  countryLocal.getName();
        continent = countryLocal.getContinent();
        code = countryLocal.getCode();

        e.putString("country", countryName);
        e.putString("continent",continent);
        e.putString("code",code);

        AppController.getInstance().setCountry(countryName);
        AppController.getInstance().setContinent(continent);
        AppController.getInstance().setCode(code);


        if(listType.equals(LIST_TYPE_SETUP))
        {
            e.putBoolean("firstStart",false);
            e.apply();
            startActivity(new Intent(getApplicationContext(), com.CA326MyBubble.activities.MainActivity.class));
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            finish();
        }
        else if(listType.equals(LIST_TYPE_LOCAL)) {
            e.apply();
            startActivity(new Intent(getApplicationContext(), com.CA326MyBubble.activities.SettingsActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
