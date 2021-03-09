package com.CA326MyBubble.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GlobalCountries implements Parcelable {
    private String CountryWithCovid, CasesToday, TotalDeaths, DeathsToday, AmountRecovered, ActiveCases, CriticalCases, CountryFlags;
    private int TotalCases;

    public GlobalCountries(String CountryWithCovid, int TotalCases, String CasesToday, String TotalDeaths, String DeathsToday, String AmountRecovered, String ActiveCases, String CriticalCases, String CountryFlags) {
        this.CountryWithCovid = CountryWithCovid;
        this.TotalCases = TotalCases;
        this.CasesToday = CasesToday;
        this.TotalDeaths = TotalDeaths;
        this.DeathsToday = DeathsToday;
        this.AmountRecovered = AmountRecovered;
        this.ActiveCases = ActiveCases;
        this.CriticalCases = CriticalCases;
        this.CountryFlags = CountryFlags;
    }

    public String getCountryWithCovid() {
        return CountryWithCovid;
    }

    public int getTotalCases() {
        return TotalCases;
    }

    public String getCasesToday() {
        return CasesToday;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public String getDeathsToday() {
        return DeathsToday;
    }

    public String getTotalRecovered() {
        return AmountRecovered;
    }

    public String getActiveCases() {
        return ActiveCases;
    }

    public String getCriticalCases() {
        return CriticalCases;
    }

    public String getCountryFlags() {
        return CountryFlags;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel text, int flags) {
        text.writeString(this.CountryWithCovid);
        text.writeInt(this.TotalCases);
        text.writeString(this.CasesToday);
        text.writeString(this.TotalDeaths);
        text.writeString(this.DeathsToday);
        text.writeString(this.AmountRecovered);
        text.writeString(this.ActiveCases);
        text.writeString(this.CriticalCases);
        text.writeString(this.CountryFlags);
    }

    protected GlobalCountries(Parcel send) {
        this.CountryWithCovid = send.readString();
        this.TotalCases = send.readInt();
        this.CasesToday = send.readString();
        this.TotalDeaths = send.readString();
        this.DeathsToday = send.readString();
        this.AmountRecovered = send.readString();
        this.ActiveCases = send.readString();
        this.CriticalCases = send.readString();
        this.CountryFlags = send.readString();
    }

    public static final Creator<GlobalCountries> CREATOR = new Creator<GlobalCountries>() {
        @Override
        public GlobalCountries createFromParcel(Parcel startPoint) {
            return new GlobalCountries(startPoint);
        }

        @Override
        public GlobalCountries[] newArray(int size) {

            return new GlobalCountries[size];
        }
    };
}

