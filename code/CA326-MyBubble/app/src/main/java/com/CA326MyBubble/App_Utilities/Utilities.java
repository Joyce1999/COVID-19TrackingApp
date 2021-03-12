package com.CA326MyBubble.App_Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {
    // Provides the application with the necessary strings, urls, and methods providing ease of access and clean code
    public static final String LOCATION = "location";

    public static final String TYPE = "listType";
    public static final String COUNTRYSELECT = "country select";
    public static final String COUNTRIES = "countries";

    public static final String SETUP = "setup";

    public static final String COUNTRY = "country";

    public static final String NULL_INFO = "...";
    public static final Boolean INFO = true;

    public static final String INTENT = "list_intent";
    public static final String SLIDER_INTENT = "slider_intent";

    public static final String NEWS_URL = "https://newsapi.org/v2/";
    public static final String TOP_HEADLINES = "top-headlines";
    public static final String TOP_HEADLINES1 = "top-headlines";

    public static final String COUNTRY_URL = "https://corona.lmao.ninja/v2/countries/";
    public static final String GLOBE_URL = "https://corona.lmao.ninja/v2/all";
    public static final String CONTINENT_URL = "https://corona.lmao.ninja/v2/continents/";

    // Date format for the application
    public static String DateFormat(String oldStringDate){
        String setNewDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldStringDate);
            if (date != null) {
                setNewDate = dateFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            setNewDate = oldStringDate;
        }

        return setNewDate;
    }
    // Gets the country name
    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
    // Gets the language, for news
    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String country = locale.getLanguage();
        return country.toLowerCase();
    }
}
