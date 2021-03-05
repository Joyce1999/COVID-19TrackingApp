package com.CA326MyBubble.interfaces;


import com.CA326MyBubble.model.CountryLocal;
import com.CA326MyBubble.model.CountryServer;

public interface OnFragmentInteractionListener {
    void listItemClickServer(CountryServer countryServer);
    void listItemClickSetting(CountryLocal countryLocal, String location, String listType);
}
