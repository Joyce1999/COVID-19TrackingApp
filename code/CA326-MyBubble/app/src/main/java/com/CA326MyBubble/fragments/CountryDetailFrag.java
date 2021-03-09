package com.CA326MyBubble.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.CA326MyBubble.R;
import com.CA326MyBubble.model.GlobalCountries;

public class CountryDetailFrag extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private GlobalCountries globalCountries;

    private GlobalCountries stats;

    private TextView TotalCases;
    private TextView CasesToday;
    private TextView AmountRecovered;
    private TextView TotalDeaths;
    private TextView DeathsToday;
    private TextView AmountCritical;
    private TextView TotalActive;
    private TextView tvUpdated;
    private TextView AmountTested;
    private TextView Title;




    public CountryDetailFrag() {
        // Required empty public constructor
    }

    public static CountryDetailFrag newInstance(GlobalCountries globalCountries) {
        CountryDetailFrag instance = new CountryDetailFrag();
        instance.setGlobalCountries(globalCountries);
        return instance;
    }

    private void setGlobalCountries(GlobalCountries globalCountries) {
        this.globalCountries = globalCountries;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stats = globalCountries;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_list_detail, container, false);

        TotalCases = root.findViewById(R.id.TotalCases);
        CasesToday = root.findViewById(R.id.casesToday);
        AmountRecovered = root.findViewById(R.id.totalRecovered);
        TotalDeaths = root.findViewById(R.id.totalDeaths);
        DeathsToday = root.findViewById(R.id.deathsToday);
        AmountCritical = root.findViewById(R.id.totalCritical);
        TotalActive = root.findViewById(R.id.totalActiveCases);
        AmountTested = root.findViewById(R.id.totalTested);
        Title = root.findViewById(R.id.layoutTitle);

        TotalCases.setText(String.valueOf(stats.getTotalCases()));
        CasesToday.setText(stats.getCasesToday());
        AmountRecovered.setText(stats.getTotalRecovered());
        TotalDeaths.setText(stats.getTotalDeaths());
        DeathsToday.setText(stats.getDeathsToday());
        AmountCritical.setText(stats.getCriticalCases());
        TotalActive.setText(stats.getActiveCases());
        AmountTested.setText(stats.getCountryFlags());

        String heading = "COVID-19 Statistics for " + stats.getCountryWithCovid();
        Title.setText(heading);

        return root;
    }

    private String setDate(long milliSecond){
        // The day, the date, the month, the year and the time.
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar updatedTime = Calendar.getInstance();
        updatedTime.setTimeInMillis(milliSecond);
        return formatter.format(updatedTime.getTime());
    }
}