package com.CA326MyBubble.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    public static final String LOCATION = "location";

    public static final String TYPE = "listType";
    public static final String SERVER = "server";
    public static final String LOCAL = "local";

    public static final String SETUP = "setup";

    public static final String COUNTRY = "country";

    public static final String NULL_INFO = "...";
    public static final Boolean INFO = true;

    public static final String INTENT = "list_intent";
    public static final String SLIDER_INTENT = "slider_intent";

    public static final String URL_NEWS = "https://newsapi.org/v2/";
    public static final String ENDPOINT_TOP_HEADLINE_NEWS = "top-headlines";
    public static final String ENDPOINT_TOP_HEADLINE = "top-headlines";

    public static final String COUNTRY_URL = "https://corona.lmao.ninja/v2/countries/";
    public static final String GLOBE_URL = "https://corona.lmao.ninja/v2/all";
    public static final String CONTINENT_URL = "https://corona.lmao.ninja/v2/continents/";

    public static String DateFormat(String oldStringDate){
        String newDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldStringDate);
            if (date != null) {
                newDate = dateFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldStringDate;
        }

        return newDate;
    }
    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String country = locale.getLanguage();
        return country.toLowerCase();
    }
}
