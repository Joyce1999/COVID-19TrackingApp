package com.CA326MyBubble.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import com.CA326MyBubble.adapters.RecyclerViewAdapterNews;


import com.CA326MyBubble.interfaces.OnFragmentListener;
import com.CA326MyBubble.interfaces.RecyclerViewClickListener;
import com.CA326MyBubble.listeners.RecyclerViewTouchListener;
import com.CA326MyBubble.model.News;
import com.CA326MyBubble.service.Retrofit.RestApiResponse;

import com.CA326MyBubble.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private OnFragmentListener mListener;
    private SwipeRefreshLayout swipe;
    private RecyclerViewAdapterNews adapter;
    private TextView tvEmpty;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    private List<News> articles = new ArrayList<>();
    private com.CA326MyBubble.ui.news.NewsViewModel viewModel;


    private RecyclerView rv;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // newsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        swipe = root.findViewById(R.id.swipeRefreshNews);
        rv = root.findViewById(R.id.newsRecycler);

        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage =root. findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);

        adapter = new RecyclerViewAdapterNews(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(com.CA326MyBubble.ui.news.NewsViewModel.class);

        rv.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), rv, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                final News news = adapter.getNews().get(position);
                startIntent(news);
            }
            @Override
            public void onLongClick(View view, final int position) {

            }
        }));

        loadNewsData(viewModel.getNewsData());


    }

    private void loadNewsData(LiveData<RestApiResponse> liveData) {

        errorLayout.setVisibility(View.GONE);

        refreshNews(true);

        liveData.observe(getViewLifecycleOwner(), new Observer<RestApiResponse>() {
            @Override
            public void onChanged(RestApiResponse apiResponse) {

                if (apiResponse == null) {
                    showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Retry\n");
                    return;
                }
                if (apiResponse.getError() == null) {
                    // call is successful
                    Log.i(TAG, "Data response is " + apiResponse.getPosts());
                    adapter.setNews(apiResponse.getPosts());
                    refreshNews(false);

                } else {
                    // call failed.
                   // Throwable e = apiResponse.getError();
                   // Toast.makeText(getActivity(), "Error is " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   // Log.e(TAG, "Error is " + e.getLocalizedMessage());

                    showErrorMessage(
                            R.drawable.no_result,
                            "Network Error",
                            "Check Network\n");



                }

            }


        });
        swipe.setOnRefreshListener(this);
    }

/*
    private void loadNewsData() {
        errorLayout.setVisibility(View.GONE);
        refreshNews(true);
        Retrofit retrofit = RetrofitInstanceNews.getRetrofitServiceNews();
        RestApiServiceNews endpoint = retrofit.create(RestApiServiceNews.class);
        String code = AppController.getInstance().getCode();
        Call<NewsResponseWrapper> call;

            if(code.equals(""))
            {
                call = endpoint.getNewsAll(AppUtils.getLanguage(),"health", BuildConfig.API_NEWS);
            }
            else {
                call = endpoint.getNews(code,"health", BuildConfig.API_NEWS);
            }

        call.enqueue(new Callback<NewsResponseWrapper>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponseWrapper> call, @NonNull Response<NewsResponseWrapper> response) {
                if (response.body() != null && response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    adapter = new RecyclerViewAdapterNews(getActivity());
                    rv.setAdapter(adapter);
                    adapter.setNews(articles);
                    refreshNews(false);
                }
                else {
                    refreshNews(false);
                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                    showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Please Try Again!\n"+
                                    errorCode);
                }

            }

            @Override
            public void onFailure(@NonNull Call<NewsResponseWrapper> call, Throwable t) {
                refreshNews(false);
                showErrorMessage(
                        R.drawable.oops,
                        "Oops..",
                        "Network failure, Please Try Again");

            }
        });
        swipe.setOnRefreshListener(this);

    }
*/


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

    private void showErrorMessage(int imageView, String title, String message){

        refreshNews(false);

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListener) {
            mListener = (OnFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    // Now we can fire the event when the user selects something in the fragment
    private void startIntent(News news) {
        if (mListener != null) {
            mListener.getNewIntent(news);
        }
    }

}