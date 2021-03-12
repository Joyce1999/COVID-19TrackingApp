package com.CA326MyBubble.Layout_UI.news;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.CA326MyBubble.App_Adapters.RVAdapterNews;


import com.CA326MyBubble.App_Interfaces.FragListener;
import com.CA326MyBubble.App_Interfaces.ClickListener;
import com.CA326MyBubble.App_Listeners.TouchListener;
import com.CA326MyBubble.Layout_Models.newsModel;
import com.CA326MyBubble.App_Services.Retrofit.RestApiResponse;

import com.CA326MyBubble.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragListener fragListener;
    private SwipeRefreshLayout swipeDown;
    private RVAdapterNews theAdapter;
    private com.CA326MyBubble.Layout_UI.news.NewsViewModel viewModel;


    private RecyclerView rv;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        swipeDown = root.findViewById(R.id.swipeRefreshNews);
        rv = root.findViewById(R.id.newsRecycler);

        theAdapter = new RVAdapterNews(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(theAdapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(com.CA326MyBubble.Layout_UI.news.NewsViewModel.class);
        // When the news button is pressed it loads of all the news, and gets the required content
        rv.addOnItemTouchListener(new TouchListener(getActivity(), rv, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final newsModel newsModel = theAdapter.getNews().get(position);
                startIntent(newsModel);
            }
            @Override
            public void onLongClick(View view, final int position) {
            }
        }));

        loadNewsData(viewModel.getNewsData());


    }
    // Method to make use of the REST API, to grab the news articles and its content/data
    private void loadNewsData(LiveData<RestApiResponse> liveData) {
        // Swipe down to reload the page
        refreshNews(true);
        liveData.observe(getViewLifecycleOwner(), new Observer<RestApiResponse>() {
            @Override
            public void onChanged(RestApiResponse apiResponse) {

                if (apiResponse == null) {
                    return;
                }
                if (apiResponse.getError() == null) {
                    // The call is successful
                    theAdapter.setNews(apiResponse.getPosts());
                    refreshNews(false);

                } else {
                    // Call has failed.
                   Throwable e = apiResponse.getError();
                   Toast.makeText(getActivity(), "Error is " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        swipeDown.setOnRefreshListener(this);
    }
    // Displays the refresh icon
    private void refreshNews(boolean isRefresh) {

        if (isRefresh) {
            swipeDown.setRefreshing(true);
        } else {
            swipeDown.setRefreshing(false);
        }
    }
    // Realods the news page
    @Override
    public void onRefresh() {
        loadNewsData(viewModel.getNewsData());
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragListener) {
            fragListener = (FragListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        fragListener = null;

    }

    // Now we can fire the event when the user selects something in the fragment
    private void startIntent(newsModel newsModel) {
        if (fragListener != null) {
            fragListener.getNewIntent(newsModel);
        }
    }

}