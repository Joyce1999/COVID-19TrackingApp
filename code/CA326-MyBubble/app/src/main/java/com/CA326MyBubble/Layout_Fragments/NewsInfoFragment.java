package com.CA326MyBubble.Layout_Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import com.CA326MyBubble.R;
import com.CA326MyBubble.App_Utilities.Utilities;
import com.CA326MyBubble.App_Interfaces.ListenerDetailsForNews;


public class NewsInfoFragment extends Fragment {

    private ListenerDetailsForNews mListener;

    private com.CA326MyBubble.Layout_Models.newsModel newsModel;
    private com.CA326MyBubble.Layout_Models.newsModel getNewsModel;

    public NewsInfoFragment() {
    }
    // Initialise the fragment
    public static NewsInfoFragment newInstance(com.CA326MyBubble.Layout_Models.newsModel newsModel) {
        NewsInfoFragment fragment = new NewsInfoFragment();
        fragment.setNewDetail(newsModel);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNewsModel = newsModel;
    }
    // Sets the design of the layout
    private void setNewDetail(com.CA326MyBubble.Layout_Models.newsModel newsModel) {
        this.newsModel = newsModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_detail, container, false);
        TextView newsTitle = root.findViewById(R.id.NewsTitle);
        TextView newsSource = root.findViewById(R.id.NewsSource);
        TextView newsAuthor = root.findViewById(R.id.NewsAuthor);
        TextView newsPublishDate = root.findViewById(R.id.NewsPublishDate);
        TextView newsDesc = root.findViewById(R.id.NewsDescription);
        TextView newsContent = root.findViewById(R.id.NewsContent);

        ImageView newsImage = root.findViewById(R.id.NewsImage);

        Button readMore = root.findViewById(R.id.ReadMore);


        // Sets the TextViews, Button and ImageView of the News info fragment
        newsTitle.setText(getNewsModel.getNewsTitle());
        newsSource.setText(String.format("%s %s", getResources().getString(R.string.sourceNews), getNewsModel.getNewsSource().getName()));
        if (getNewsModel.getNewsAuthor() == null) {
            newsAuthor.setText(R.string.editor);
        } else {
            newsAuthor.setText(String.format("%s %s", getResources().getString(R.string.authorNews), getNewsModel.getNewsAuthor()));
        }
        newsPublishDate.setText(String.format("%s %s", getResources().getString(R.string.dateNews),  Utilities.DateFormat(getNewsModel.getPublishDate())));
        newsDesc.setText(getNewsModel.getDesc());

        if (getNewsModel.getNewsContent() == null) {
            newsContent.setText(getResources().getString(R.string.description_unavailable));
        } else {
            newsContent.setText(getNewsModel.getNewsContent());
        }
        // Gets the image and loads it, no image will appear if an image isn't properly configured or doesn't exist
        Glide.with(this)
                .load(getNewsModel.getImageURL())
                .into(newsImage);
        // Opens up the web view of the article, which redirects the user to the source website where the article exists
        readMore.setOnClickListener(view -> {
            String URL = getNewsModel.getUrl();
            Uri urlUri = Uri.parse(URL);
            Intent toUrl = new Intent(Intent.ACTION_VIEW, urlUri);
            startActivity(toUrl);
            Toast.makeText(getContext(), getResources().getString(R.string.redirect), Toast.LENGTH_SHORT).show();
        });
        return root;
    }
    // Listener for pressing read more
    public void onMorePressed(String url) {
        if (mListener != null) {
            mListener.webIntent(url);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListenerDetailsForNews) {
            mListener = (ListenerDetailsForNews) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
