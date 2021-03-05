package com.CA326MyBubble.interfaces;


import com.CA326MyBubble.model.News;

public interface OnFragmentListener {
    void getListIntent(String intent, String argument);
    void getNewIntent(News news);
}
