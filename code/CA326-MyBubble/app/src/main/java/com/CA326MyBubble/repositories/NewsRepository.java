package com.CA326MyBubble.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.CA326MyBubble.BuildConfig;
import com.CA326MyBubble.controllers.AppController;
import com.CA326MyBubble.utils.AppUtils;
import com.CA326MyBubble.model.NewsResponseWrapper;
import com.CA326MyBubble.service.Retrofit.RestApiResponse;
import com.CA326MyBubble.service.Retrofit.RestApiServiceNews;
import com.CA326MyBubble.service.Retrofit.RetrofitInstanceNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final MutableLiveData<RestApiResponse> liveData = new MutableLiveData<>();

    private Application application;


    public NewsRepository(Application application) {
        this.application = application;
    }


    public MutableLiveData<RestApiResponse>  getMutableLiveData(boolean isGetAll) {

        RestApiServiceNews endpoint = RetrofitInstanceNews.getRetrofitServiceNews();

        String code = AppController.getInstance().getCode();
        Call<NewsResponseWrapper> call;
        if (isGetAll) {
            call = endpoint.getNews(code, "health", BuildConfig.API_NEWS);
        } else {
            if (code.equals("")) {
                call = endpoint.getNewsAll(AppUtils.getLanguage(), "health", BuildConfig.API_NEWS);
            } else {
                call = endpoint.getNews(code, "health", BuildConfig.API_NEWS);
            }
        }
        call.enqueue(new Callback<NewsResponseWrapper>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponseWrapper> call, @NonNull Response<NewsResponseWrapper> response) {

                if(response.isSuccessful())
                {
                    NewsResponseWrapper newsResponse =  response.body();
                    if (newsResponse != null  && newsResponse.getArticles() != null) {
                        liveData.postValue(new RestApiResponse(newsResponse.getArticles()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<NewsResponseWrapper> call, Throwable t) {

                liveData.postValue(new RestApiResponse(t));

            }
        });

        return liveData;
    }
}
