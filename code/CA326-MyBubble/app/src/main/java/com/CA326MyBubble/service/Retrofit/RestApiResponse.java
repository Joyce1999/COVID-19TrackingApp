package com.CA326MyBubble.service.Retrofit;

import java.util.List;

import com.CA326MyBubble.model.newsModel;

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
