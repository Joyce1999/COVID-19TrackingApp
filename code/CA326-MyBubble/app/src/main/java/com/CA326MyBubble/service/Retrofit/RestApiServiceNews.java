package com.CA326MyBubble.service.Retrofit;

import com.CA326MyBubble.utils.AppUtils;
import com.CA326MyBubble.model.NewsResponseWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiServiceNews {

    @GET(AppUtils.ENDPOINT_TOP_HEADLINE_NEWS)
    Call<NewsResponseWrapper> getNews(@Query("country") String country,
                                      @Query("category") String category,
                                      @Query("apiKey") String apiKey);

    @GET(AppUtils.ENDPOINT_TOP_HEADLINE)
    Call<NewsResponseWrapper> getNewsAll(@Query("language") String language,
                                         @Query("category") String category,
                                         @Query("apiKey") String apiKey);
}
