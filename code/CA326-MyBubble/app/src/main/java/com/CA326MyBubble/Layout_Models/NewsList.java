package com.CA326MyBubble.Layout_Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsList {

    @SerializedName("status")
    private String status;

    @SerializedName("totalResult")
    private int totalResult;


    @SerializedName("articles")
    private List<newsModel> articles;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public NewsList(List<newsModel> articles) {
        this.articles = articles;
    }

    public List<newsModel> getArticles() {
        return articles;
    }

}