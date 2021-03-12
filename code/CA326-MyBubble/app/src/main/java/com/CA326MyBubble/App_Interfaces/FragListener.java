package com.CA326MyBubble.App_Interfaces;


import com.CA326MyBubble.Layout_Models.newsModel;

public interface FragListener {
    void getListIntent(String intent, String argument);
    void getNewIntent(newsModel newsModel);
}
