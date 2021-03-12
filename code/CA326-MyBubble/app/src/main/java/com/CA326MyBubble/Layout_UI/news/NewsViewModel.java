package com.CA326MyBubble.Layout_UI.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.CA326MyBubble.API_Repositories.NewsRepository;
import com.CA326MyBubble.App_Services.Retrofit.RestApiResponse;

public class NewsViewModel extends AndroidViewModel {

    private final MediatorLiveData<RestApiResponse> mObservableNews;

    private boolean isGetAll;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        // Initialises the repo for all the articles retrieved
        NewsRepository mRepository = new NewsRepository(application);
        mObservableNews = new MediatorLiveData<>();
        mObservableNews.addSource(mRepository.getMutableLiveData(isGetAll()), new Observer<RestApiResponse>() {
            @Override
            public void onChanged(RestApiResponse restApiResponse) {

                mObservableNews.setValue(restApiResponse);

            }
        });
        mRepository.getMutableLiveData(isGetAll());
    }
    // Shows the news
    public LiveData<RestApiResponse> getNewsData() {
     return mObservableNews;
    }

    public boolean isGetAll() {
        return isGetAll;
    }

    public void setGetAll(boolean getAll) {
        isGetAll = getAll;
    }
}