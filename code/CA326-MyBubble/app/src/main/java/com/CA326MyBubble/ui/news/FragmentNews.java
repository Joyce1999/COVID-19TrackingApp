package com.CA326MyBubble.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

import com.CA326MyBubble.adapters.RVAdapterNews;


import com.CA326MyBubble.interfaces.FragListener;
import com.CA326MyBubble.interfaces.ClickListener;
import com.CA326MyBubble.listeners.TouchListener;
import com.CA326MyBubble.model.newsModel;
import com.CA326MyBubble.service.Retrofit.RestApiResponse;

import com.CA326MyBubble.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragListener mListener;
    private SwipeRefreshLayout swipe;
    private RVAdapterNews adapter;
    private TextView tvEmpty;
    private List<newsModel> articles = new ArrayList<>();
    private com.CA326MyBubble.ui.news.NewsViewModel viewModel;


    private RecyclerView rv;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        swipe = root.findViewById(R.id.swipeRefreshNews);
        rv = root.findViewById(R.id.newsRecycler);

        adapter = new RVAdapterNews(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(com.CA326MyBubble.ui.news.NewsViewModel.class);

        rv.addOnItemTouchListener(new TouchListener(getActivity(), rv, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final newsModel newsModel = adapter.getNews().get(position);
                startIntent(newsModel);
            }
            @Override
            public void onLongClick(View view, final int position) {

            }
        }));

        loadNewsData(viewModel.getNewsData());


    }

    private void loadNewsData(LiveData<RestApiResponse> liveData) {

        refreshNews(true);

        liveData.observe(getViewLifecycleOwner(), new Observer<RestApiResponse>() {
            @Override
            public void onChanged(RestApiResponse apiResponse) {

                if (apiResponse == null) {
                    return;
                }
                if (apiResponse.getError() == null) {
                    // call is successful
                    Log.i(TAG, "Data response is " + apiResponse.getPosts());
                    adapter.setNews(apiResponse.getPosts());
                    refreshNews(false);

                } else {
                    // call failed.
                   Throwable e = apiResponse.getError();
                   Toast.makeText(getActivity(), "Error is " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   Log.e(TAG, "Error is " + e.getLocalizedMessage());



                }

            }


        });
        swipe.setOnRefreshListener(this);
    }


    private void refreshNews(boolean isRefresh) {

        if (isRefresh) {
            swipe.setRefreshing(true);
        } else {
            swipe.setRefreshing(false);
        }
    }




    @Override
    public void onRefresh() {

        loadNewsData(viewModel.getNewsData());
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragListener) {
            mListener = (FragListener) context;
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

    // Now we can fire the event when the user selects something in the fragment
    private void startIntent(newsModel newsModel) {
        if (mListener != null) {
            mListener.getNewIntent(newsModel);
        }
    }

}