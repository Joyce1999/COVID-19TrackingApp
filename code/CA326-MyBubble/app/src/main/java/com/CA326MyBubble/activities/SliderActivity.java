package com.CA326MyBubble.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.CA326MyBubble.R;
import com.CA326MyBubble.fragments.ShowInfoAsListFragment;
import com.CA326MyBubble.fragments.ShowInfoAsSliderFragment;
import com.CA326MyBubble.interfaces.OnFragmentListenerSlider;

public class SliderActivity extends AppCompatActivity implements OnFragmentListenerSlider {
    private String sliderRequest;

    String tag;
    FloatingActionButton floatingActionButton;

    private void beginSliderTransaction(String sliderRequest)
    {
        tag = "slider";
        ShowInfoAsSliderFragment fragment = ShowInfoAsSliderFragment.newInstance(sliderRequest);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .replace(R.id.fragment, fragment,"slide")
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();

        }

    }
    private void beginRecyclerTransaction(String sliderRequest)
    {tag = "recycler";
        ShowInfoAsListFragment fragment = ShowInfoAsListFragment.newInstance(sliderRequest);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .replace(R.id.fragment, fragment, "recycler")
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(sliderRequest);
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

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            ShowInfoAsSliderFragment fragment =(ShowInfoAsSliderFragment)getSupportFragmentManager().findFragmentByTag("slide");
            if(fragment!= null && fragment.isVisible())
            {
                beginRecyclerTransaction(sliderRequest);
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_view_carousel_white_24dp));
            }
            ShowInfoAsListFragment fragmentR =(ShowInfoAsListFragment)getSupportFragmentManager().findFragmentByTag("recycler");
            if(fragmentR!= null && fragmentR.isVisible()) {

                beginSliderTransaction(sliderRequest);
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_view_list_white_24dp));
            }

        });

    }



    @Override
    public void doOnBackPressed() {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slider, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
