package com.CA326MyBubble.App_Services.Retrofit;

import java.util.concurrent.TimeUnit;

import com.CA326MyBubble.App_Utilities.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceNews {

    private static final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

    private static Retrofit retrofit = null;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();


    public static RestApiServiceNews getRetrofitServiceNews(){
        // Pulls the page data into text data for the app
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utilities.NEWS_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .client(okHttpClient)
                    .build();
        }

        return retrofit.create(RestApiServiceNews.class);
    }

}
