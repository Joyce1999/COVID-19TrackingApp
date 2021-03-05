package com.CA326MyBubble.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.CA326MyBubble.R;
import com.CA326MyBubble.fragments.NewsDetailFragment;
import com.CA326MyBubble.interfaces.OnFragmentListenerNewsDetail;
import com.CA326MyBubble.model.News;

public class NewsDetailsActivity extends AppCompatActivity implements OnFragmentListenerNewsDetail {
    public static final String PARCELABLE_PARSING_DATA = "parcelable_parsing_data" ;

    private void beginSliderTransaction(News news)
    {

        NewsDetailFragment fragment = NewsDetailFragment.newInstance(news);
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

        News model = getIntent().getParcelableExtra(PARCELABLE_PARSING_DATA);

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
