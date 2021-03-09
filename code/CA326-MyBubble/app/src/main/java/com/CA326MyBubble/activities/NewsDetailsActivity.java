package com.CA326MyBubble.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.CA326MyBubble.R;
import com.CA326MyBubble.fragments.NewsDetailFragment;
import com.CA326MyBubble.interfaces.ListenerDetailsForNews;
import com.CA326MyBubble.model.newsModel;

public class NewsDetailsActivity extends AppCompatActivity implements ListenerDetailsForNews {
    public static final String PARCELABLE_PARSING_DATA = "parcelable_parsing_data" ;

    private void beginSliderTransaction(newsModel newsModel)
    {

        NewsDetailFragment fragment = NewsDetailFragment.newInstance(newsModel);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment)
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
    public void webIntent(String url) {

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
