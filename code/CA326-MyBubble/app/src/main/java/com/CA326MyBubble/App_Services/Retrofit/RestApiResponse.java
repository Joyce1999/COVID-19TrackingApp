package com.CA326MyBubble.App_Services.Retrofit;

import java.util.List;

import com.CA326MyBubble.Layout_Models.newsModel;
// Sets the list map of all the posts taken from the NewsAPI site
public class RestApiResponse {

    private List<newsModel> posts;
    private Throwable error;

    public RestApiResponse(List<newsModel> posts) {
        this.posts = posts;
        this.error = null;
    }

    public RestApiResponse(Throwable error) {
        this.error = error;
        this.posts = null;
    }

    public List<newsModel> getPosts() {
        return posts;
    }

    public void setPosts(List<newsModel> posts) {
        this.posts = posts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
