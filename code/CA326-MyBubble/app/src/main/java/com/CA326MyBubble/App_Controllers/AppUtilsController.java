package com.CA326MyBubble.App_Controllers;

import java.util.Calendar;

import static com.CA326MyBubble.App_Utilities.Utilities.CONTINENT_URL;
import static com.CA326MyBubble.App_Utilities.Utilities.COUNTRY_URL;
import static com.CA326MyBubble.App_Utilities.Utilities.GLOBE_URL;

public class AppUtilsController {

    private String continentUrl;
    private String countryUrl;
    private String globalUrl;

// Controls getting time, setting up the URL's for going to the API's
    public AppUtilsController() {

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        boolean getYesterday = hour >= 6;

        String yesterday = "true";
        if(!getYesterday)
        {
            yesterday = "false";
        }


        countryUrl = COUNTRY_URL + AppController.getInstance().getCountry()+ "?yesterday="+ yesterday;
        continentUrl = CONTINENT_URL + AppController.getInstance().getContinent()+ "?yesterday="+ yesterday;
        globalUrl = GLOBE_URL + "?yesterday="+ yesterday;
    }

    public String getContinentUrl() {
        return continentUrl;
    }

    public void setContinentUrl(String continentUrl) {
        this.continentUrl = continentUrl;
    }

    public String getCountryUrl() {
        return countryUrl;
    }

    public void setCountryUrl(String countryUrl) {
        this.countryUrl = countryUrl;
    }

    public String getGlobalUrl() {
        return globalUrl;
    }

    public void setGlobalUrl(String globalUrl) {
        this.globalUrl = globalUrl;
    }
}
