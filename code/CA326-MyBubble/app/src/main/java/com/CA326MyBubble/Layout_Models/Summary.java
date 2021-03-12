package com.CA326MyBubble.Layout_Models;

public class Summary {
    // Model for the Dashboard
    private String totalCases, casesToday, totalRecovered, totalDeaths, deathsToday, critical, active, tested, location, country;
    private long updated;

    public String getTotalCases() {

        return totalCases;
    }

    public void setCases(String confirmed) {

        this.totalCases = confirmed;
    }

    public long getUpdated() {

        return updated;
    }

    public void setUpdated(long updated) {

        this.updated = updated;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public String getTested() {

        return tested;
    }

    public void setTested(String tested) {

        this.tested = tested;
    }

    public String getCasesToday() {

        return casesToday;
    }

    public void setTodayCases(String todayConfirmed) {
        this.casesToday = todayConfirmed;
    }

    public String getTotalRecovered() {

        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {

        this.totalRecovered = totalRecovered;
    }

    public String getTotalDeaths() {

        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {

        this.totalDeaths = totalDeaths;
    }

    public String getDeathsToday() {

        return deathsToday;
    }

    public void setDeathsToday(String deathsToday) {

        this.deathsToday = deathsToday;
    }

    public String getCritical() {

        return critical;
    }

    public void setCritical(String critical) {

        this.critical = critical;
    }

    public String getActive() {

        return active;
    }

    public void setActive(String active) {

        this.active = active;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }
}
