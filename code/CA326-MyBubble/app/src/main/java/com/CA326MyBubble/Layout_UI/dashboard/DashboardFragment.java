package com.CA326MyBubble.Layout_UI.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.CA326MyBubble.App_Services.ScannerService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.CA326MyBubble.App_Controllers.AppController;
import com.CA326MyBubble.R;
import com.CA326MyBubble.App_Interfaces.FragListener;

import static com.CA326MyBubble.App_Utilities.Utilities.INTENT;

public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private com.CA326MyBubble.Layout_UI.dashboard.DashboardViewModel dashboardViewModel;

    private ProgressBar progressBar;
    private FragListener Listener;

    private TextView TotalCases;
    private TextView CasesToday;
    private TextView TotalRecovered;
    private TextView TotalDeaths;
    private TextView DeathsToday;
    private TextView TotalCritical;
    private TextView TotalActive;
    private TextView TimeUpdated;
    private TextView TotalTested;
    private TextView CountryName;
    private String locationDataRequest;
    private FloatingActionButton ActionButton;
    private boolean isNetworkOk;
    private SwipeRefreshLayout SwipeDown;

    private Button startScanButton;
    private Button stopScanButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {

        dashboardViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(com.CA326MyBubble.Layout_UI.dashboard.DashboardViewModel.class);


        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        progressBar = root.findViewById(R.id.Progress_Bar_Home);

        final RadioGroup filterView = root.findViewById(R.id.FilterView);

        SwipeDown = root.findViewById(R.id.swipeView);
        TotalCases = root.findViewById(R.id.TotalCases);
        CasesToday = root.findViewById(R.id.casesToday);
        TotalRecovered = root.findViewById(R.id.totalRecovered);
        TotalDeaths = root.findViewById(R.id.totalDeaths);
        DeathsToday = root.findViewById(R.id.deathsToday);
        TotalCritical = root.findViewById(R.id.totalCritical);
        TotalActive = root.findViewById(R.id.totalActiveCases);
        TimeUpdated = root.findViewById(R.id.lastUpdated);
        TotalTested = root.findViewById(R.id.totalTested);
        CountryName = root.findViewById(R.id.layoutTitle);
        Intent intent = new Intent(getActivity(),ScannerService.class);

        startScanButton = (Button) root.findViewById(R.id.StartScanButton);
        startScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanButton.setVisibility(View.INVISIBLE);
                stopScanButton.setVisibility(View.VISIBLE);
                getActivity().startService(intent);
            }
        });

        stopScanButton = (Button) root.findViewById(R.id.StopScanButton);
        stopScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopScanButton.setVisibility(View.INVISIBLE);
                startScanButton.setVisibility(View.VISIBLE);
                getActivity().stopService(intent);

            }
        });
        stopScanButton.setVisibility(View.INVISIBLE);


        ActionButton = root.findViewById(R.id.floatingActionButton);
            // Handles the switching of the filters in the radio group, Country, Continent, if on globe floating action button disappears
            filterView.check(R.id.radioCountry);
            locationDataRequest = "country";
        filterView.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = root.findViewById(checkedId);

            locationDataRequest = radioButton.getText().toString();
            loadSummaryData(locationDataRequest);
            if ("globe".equals(locationDataRequest)) {
                ActionButton.setEnabled(false);
                ActionButton.setVisibility(View.INVISIBLE);
            } else {
                ActionButton.setEnabled(true);
                ActionButton.setVisibility(View.VISIBLE);

            }
        });
        ActionButton.setOnClickListener(v -> startIntent(locationDataRequest));
        return root;
    }

    // Gets the statistics of the country selected and sets all into their correct text view
    private void loadSummaryData(String dataRequest) {
        dashboardViewModel.getDashboardViewModel(dataRequest, getActivity());
        dashboardViewModel.getStatistics().observe(getViewLifecycleOwner(), stats -> {
            if (stats != null){
                if(locationDataRequest.equals(stats.getCountry()))
                {
                    progressBar.setVisibility(View.GONE);
                    refreshStats(false);
                    TotalCases.setText(stats.getTotalCases());
                    CasesToday.setText(stats.getCasesToday());
                    TotalRecovered.setText(stats.getTotalRecovered());
                    TotalDeaths.setText(stats.getTotalDeaths());
                    DeathsToday.setText(stats.getDeathsToday());
                    TotalCritical.setText(stats.getCritical());
                    TotalActive.setText(stats.getActive());
                    TotalTested.setText(stats.getTested());

                    String date = "Last Updated"+"\n"+getDate(stats.getUpdated());
                    TimeUpdated.setText(date);
                    String heading = "COVID-19 Statistics for "+ stats.getLocation();
                    CountryName.setText(heading);
                }
            }
        });
        SwipeDown.setOnRefreshListener(this);
    }
    // Settings heading titles, Eg. Country Name, Continent name
    private void setText() {
        if ("globe".equals(locationDataRequest)) {
            String heading = "COVID-19 Global Stats";
            CountryName.setText(heading);
        } else if ("country".equals(locationDataRequest)) {
            String heading = "COVID-19 Statistics for " + AppController.getInstance().getCountry();
            CountryName.setText(heading);

        }
         else if ("continent".equals(locationDataRequest)) {
            String heading = "COVID-19 Statistics for " + AppController.getInstance().getContinent();
            CountryName.setText(heading);
        }
    }
    // Refresh stats if users swipes down, stats will refresh if they have been updated
    private void refreshStats(boolean isRefresh) {
        if (isRefresh) {
            SwipeDown.setRefreshing(true);

        } else {
            SwipeDown.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
            loadSummaryData(locationDataRequest);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragListener) {
            Listener = (FragListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }
    private String getDate(long milliSecond) {
        // Day, date, month, year, hour, minute, second am/pm
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar settingDate = Calendar.getInstance();
        settingDate.setTimeInMillis(milliSecond);
        return dateFormat.format(settingDate.getTime());
    }

    // Now we can fire the event when the user selects something in the fragment
    private void startIntent(String arguement) {
        if (Listener != null) {
                Listener.getListIntent(INTENT, arguement);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadSummaryData(locationDataRequest);
    }
}


