package com.CA326MyBubble.App_Interfaces;


import com.CA326MyBubble.Layout_Models.CountrySelection;
import com.CA326MyBubble.Layout_Models.GlobalCountries;

public interface InteractionListener {
    void listItemClickCountry(GlobalCountries globalCountries);
    void listItemClickSetting(CountrySelection countrySelection, String location, String listType);
}
