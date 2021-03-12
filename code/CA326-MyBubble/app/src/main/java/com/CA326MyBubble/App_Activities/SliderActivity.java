package com.CA326MyBubble.App_Activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.CA326MyBubble.R;
import com.CA326MyBubble.Layout_Fragments.ShowInfoAsSliderFragment;
import com.CA326MyBubble.App_Interfaces.ListenerForInfoSlides;

public class SliderActivity extends AppCompatActivity implements ListenerForInfoSlides {
    private String sliderRequest;

    String tag;

    // Manages the changing of slides, and what will be displayed based on user presses
    private void beginSliderTransaction(String sliderRequest)
    {
        tag = "slider";
        ShowInfoAsSliderFragment fragment = ShowInfoAsSliderFragment.newInstance(sliderRequest);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment,"slide")
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        sliderRequest = getIntent().getStringExtra("sliderRequest");

        beginSliderTransaction(sliderRequest);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // actionBar.setTitle("");
            actionBar.hide();
        }
    }
    // Back button pressed return to menu
    @Override
    public void doOnBackPressed() {
        onBackPressed();
    }
    // Displays the Next and Previous buttons for each fragment to go between each page
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slider, menu);

        return true;
    }
    // When pressed return back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
