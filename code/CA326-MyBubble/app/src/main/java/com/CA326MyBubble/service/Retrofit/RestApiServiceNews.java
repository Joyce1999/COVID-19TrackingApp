package com.CA326MyBubble.service.Retrofit;

import com.CA326MyBubble.utils.Utilities;
import com.CA326MyBubble.model.NewsList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiServiceNews {

    @GET(Utilities.ENDPOINT_TOP_HEADLINE_NEWS)
    Call<NewsList> getNews(@Query("country") String country,
                           @Query("category") String category,
                           @Query("apiKey") String apiKey);

    @GET(Utilities.ENDPOINT_TOP_HEADLINE)
    Call<NewsList> getNewsAll(@Query("language") String language,
                              @Query("category") String category,
                              @Query("apiKey") String apiKey);
}
