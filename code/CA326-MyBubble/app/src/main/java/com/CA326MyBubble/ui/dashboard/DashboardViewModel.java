package com.CA326MyBubble.ui.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.CA326MyBubble.model.Summary;
import com.CA326MyBubble.service.json.ContinentDataRequest;
import com.CA326MyBubble.service.json.CountryDataRequest;
import com.CA326MyBubble.service.json.GlobeDataRequest;


public class DashboardViewModel extends ViewModel {

    private MutableLiveData<Summary> mutableLiveData;

    public void getDashboardViewModel(String request, Context context) {
        switch (request) {
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


