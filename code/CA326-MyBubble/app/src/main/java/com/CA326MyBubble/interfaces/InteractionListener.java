package com.CA326MyBubble.interfaces;


import com.CA326MyBubble.model.CountrySelection;
import com.CA326MyBubble.model.GlobalCountries;

public interface InteractionListener {
    void listItemClickServer(GlobalCountries globalCountries);
    void listItemClickSetting(CountrySelection countrySelection, String location, String listType);
}
