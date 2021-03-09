package com.CA326MyBubble.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.CA326MyBubble.model.newsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.CA326MyBubble.R;
import com.CA326MyBubble.interfaces.FragListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.CA326MyBubble.utils.Utilities.INTENT;
import static com.CA326MyBubble.utils.Utilities.LOCATION;
import static com.CA326MyBubble.utils.Utilities.TYPE;
import static com.CA326MyBubble.utils.Utilities.SERVER;
import static com.CA326MyBubble.utils.Utilities.SLIDER_INTENT;

public class MainActivity extends AppCompatActivity implements FragListener {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration;

            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_dashboard, R.id.navigation_info, R.id.navigation_news)
                    .build();




        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {


        });
    }




    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Intent it = new Intent(MainActivity.this, com.CA326MyBubble.activities.SettingsActivity.class);
            startActivity(it);
            overridePendingTransition(0,0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        }
    }

    private void sendToLogin() {
        Intent loginintent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginintent);
        finish();
    }

    @Override
    public void getListIntent(String intent, String arguement) {
        if (intent.equals(INTENT))
        {
            Intent it = new Intent(MainActivity.this, CountriesActivity.class);
            it.putExtra(LOCATION, arguement);
            it.putExtra(TYPE, SERVER);
            startActivity(it);
            overridePendingTransition(0,0);
        }
        if (intent.equals(SLIDER_INTENT))
        {
            Intent it = new Intent(MainActivity.this, com.CA326MyBubble.activities.SliderActivity.class);
            it.putExtra("sliderRequest", arguement);
            startActivity(it);
            overridePendingTransition(0,0);
        }


    }

    @Override
    public void getNewIntent(newsModel newsModel) {
        Intent it = new Intent(MainActivity.this, com.CA326MyBubble.activities.NewsDetailsActivity.class);
        it.putExtra(com.CA326MyBubble.activities.NewsDetailsActivity.PARCELABLE_PARSING_DATA, newsModel);
        startActivity(it);
        overridePendingTransition(0,0);
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

}
