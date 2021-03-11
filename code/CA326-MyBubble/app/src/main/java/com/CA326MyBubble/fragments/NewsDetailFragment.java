package com.CA326MyBubble.fragments;

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
import com.CA326MyBubble.ut.Utilities;
import com.CA326MyBubble.interfaces.ListenerDetailsForNews;


public class NewsDetailFragment extends Fragment {

    private ListenerDetailsForNews mListener;

    private com.CA326MyBubble.model.newsModel newsModel;
    private com.CA326MyBubble.model.newsModel getNewsModel;

    public NewsDetailFragment() {
    }

    public static NewsDetailFragment newInstance(com.CA326MyBubble.model.newsModel newsModel) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setNewDetail(newsModel);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNewsModel = newsModel;
    }

    private void setNewDetail(com.CA326MyBubble.model.newsModel newsModel) {
        this.newsModel = newsModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_detail, container, false);
        TextView tvTitle = root.findViewById(R.id.tvNewsTitleDetail);
        TextView tvSource = root.findViewById(R.id.tvNewsSourceDetail);
        TextView tvAuthor = root.findViewById(R.id.tvNewsAuthorDetail);
        TextView tvPublished = root.findViewById(R.id.tvNewsPublishedDetail);
        TextView tvDesc = root.findViewById(R.id.tvNewsDescDetail);
        TextView tvContent = root.findViewById(R.id.tvNewsContentDetail);

        ImageView imageView = root.findViewById(R.id.imageDetailNews);

        Button btnMore = root.findViewById(R.id.btnMore);



        tvTitle.setText(getNewsModel.getNewsTitle());
        tvSource.setText(String.format("%s %s", getResources().getString(R.string.sourceNews), getNewsModel.getNewsSource().getName()));
        if (getNewsModel.getNewsAuthor() == null) {
            tvAuthor.setText(R.string.editor);
        } else {
            tvAuthor.setText(String.format("%s %s", getResources().getString(R.string.authorNews), getNewsModel.getNewsAuthor()));
        }
        tvPublished.setText(String.format("%s %s", getResources().getString(R.string.dateNews),  Utilities.DateFormat(getNewsModel.getPublishDate())));
        tvDesc.setText(getNewsModel.getDesc());

        if (getNewsModel.getNewsContent() == null) {
            tvContent.setText(getResources().getString(R.string.description_unavailable));
        } else {
            tvContent.setText(getNewsModel.getNewsContent());
        }
        Glide.with(this)
                .load(getNewsModel.getImageURL())
                .into(imageView);

        btnMore.setOnClickListener(view -> {
            String URL = getNewsModel.getUrl();
            Uri urlUri = Uri.parse(URL);
            Intent toUrl = new Intent(Intent.ACTION_VIEW, urlUri);
            startActivity(toUrl);
            Toast.makeText(getContext(), getResources().getString(R.string.redirect), Toast.LENGTH_SHORT).show();
        });

        return root;
    }

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
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
