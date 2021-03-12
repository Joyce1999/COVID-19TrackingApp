package com.CA326MyBubble.App_Activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.CA326MyBubble.R;
import com.CA326MyBubble.Layout_Fragments.NewsInfoFragment;
import com.CA326MyBubble.App_Interfaces.ListenerDetailsForNews;
import com.CA326MyBubble.Layout_Models.newsModel;

public class NewsInfoActivity extends AppCompatActivity implements ListenerDetailsForNews {
    public static final String PARCELABLE_PARSING_DATA = "parcelable_parsing_data" ;

    private void beginSliderTransaction(newsModel newsModel)
    {
        // Starts the fragment, and allows it to display to user
        NewsInfoFragment frag = NewsInfoFragment.newInstance(newsModel);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        // Serializes the data from a diff page, much faster than reg java serialize
        newsModel model = getIntent().getParcelableExtra(PARCELABLE_PARSING_DATA);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        beginSliderTransaction(model);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    // Opens up the web view of a news article
    @Override
    public void webIntent(String url) {
    }
}
