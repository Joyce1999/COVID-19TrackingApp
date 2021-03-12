package com.CA326MyBubble.API_Repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.CA326MyBubble.BuildConfig;
import com.CA326MyBubble.App_Controllers.AppController;
import com.CA326MyBubble.App_Utilities.Utilities;
import com.CA326MyBubble.Layout_Models.NewsList;
import com.CA326MyBubble.App_Services.Retrofit.RestApiResponse;
import com.CA326MyBubble.App_Services.Retrofit.RestApiServiceNews;
import com.CA326MyBubble.App_Services.Retrofit.RetrofitInstanceNews;
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
        Call<NewsList> call;
        if (isGetAll) {
            call = endpoint.getNews(code, "health", BuildConfig.API_NEWS);
        } else {
            if (code.equals("")) {
                call = endpoint.getNewsAll(Utilities.getLanguage(), "health", BuildConfig.API_NEWS);
            } else {
                call = endpoint.getNews(code, "health", BuildConfig.API_NEWS);
            }
        }
        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(@NonNull Call<NewsList> call, @NonNull Response<NewsList> response) {

                if(response.isSuccessful())
                {
                    NewsList newsResponse =  response.body();
                    if (newsResponse != null  && newsResponse.getArticles() != null) {
                        liveData.postValue(new RestApiResponse(newsResponse.getArticles()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<NewsList> call, Throwable t) {

                liveData.postValue(new RestApiResponse(t));

            }
        });

        return liveData;
    }
}
