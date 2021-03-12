package com.CA326MyBubble.Layout_UI.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.CA326MyBubble.Layout_Models.Summary;
import com.CA326MyBubble.App_Services.json.ContinentDataRequest;
import com.CA326MyBubble.App_Services.json.CountryDataRequest;
import com.CA326MyBubble.App_Services.json.GlobeDataRequest;


public class DashboardViewModel extends ViewModel {

    private MutableLiveData<Summary> mutableLiveData;

    public void getDashboardViewModel(String request, Context context) {
        switch (request) {
            // Handles filtering in the radio group and gathers the data
            case "country":
                CountryDataRequest countryDataRequest = new CountryDataRequest();
                mutableLiveData = countryDataRequest.parseJSON();
                break;
            case "continent":
                ContinentDataRequest continentDataRequest = new ContinentDataRequest();
                mutableLiveData = continentDataRequest.parseJSON();
                break;
            case "globe":
                GlobeDataRequest globeDataRequest = new GlobeDataRequest();
                mutableLiveData = globeDataRequest.parseJSON();
                break;
        }
    }

    public LiveData<Summary> getStatistics() {

        return mutableLiveData;
    }

}


