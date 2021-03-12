package com.CA326MyBubble.App_Services.Retrofit;

import com.CA326MyBubble.App_Utilities.Utilities;
import com.CA326MyBubble.Layout_Models.NewsList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RestApiServiceNews {
    // Gets the headlines based off Country
    @GET(Utilities.TOP_HEADLINES)
    Call<NewsList> getNews(@Query("country") String country,
                           @Query("category") String category,
                           @Query("apiKey") String apiKey);
    // Gets the headlines in the language
    @GET(Utilities.TOP_HEADLINES1)
    Call<NewsList> getNewsAll(@Query("language") String language,
                              @Query("category") String category,
                              @Query("apiKey") String apiKey);
}
