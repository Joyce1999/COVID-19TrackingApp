package com.example.ca326drawer;

public class CoronaItem {

    private String State;
    private String Deaths;
    private String Active;
    private String Recovered;
    private String Confirmed;
    private String LastUpdated;
    private String TodayDeaths;
    private String TodayRecovered;
    private String TodayActive;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getDeaths() {
        return Deaths;
    }

    public void setDeaths(String deaths) {
        Deaths = deaths;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getRecovered() {
        return Recovered;
    }

    public void setRecovered(String recovered) {
        Recovered = recovered;
    }

    public String getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(String confirmed) {
        Confirmed = confirmed;
    }

    public String getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        LastUpdated = lastUpdated;
    }

    public String getTodayDeaths() {
        return TodayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        TodayDeaths = todayDeaths;
    }

    public String getTodayRecovered() {
        return TodayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        TodayRecovered = todayRecovered;
    }

    public String getTodayActive() {
        return TodayActive;
    }

    public void setTodayActive(String todayActive) {
        TodayActive = todayActive;
    }

    public CoronaItem(String state, String deaths, String active, String recovered, String confirmed, String lastUpdated, String todayDeaths, String todayRecovered, String todayActive) {
        State = state;
        Deaths = deaths;
        Active = active;
        Recovered = recovered;
        Confirmed = confirmed;
        LastUpdated = lastUpdated;
        TodayDeaths = todayDeaths;
        TodayRecovered = todayRecovered;
        TodayActive = todayActive;
    }
}
