package com.CA326MyBubble.interfaces;


import com.CA326MyBubble.model.newsModel;

public interface FragListener {
    void getListIntent(String intent, String argument);
    void getNewIntent(newsModel newsModel);
}
