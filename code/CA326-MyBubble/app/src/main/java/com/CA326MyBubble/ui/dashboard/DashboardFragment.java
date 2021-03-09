package com.CA326MyBubble.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.CA326MyBubble.controllers.AppController;
import com.CA326MyBubble.R;
import com.CA326MyBubble.interfaces.FragListener;

import static com.CA326MyBubble.utils.Utilities.INTENT;

public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private com.CA326MyBubble.ui.dashboard.DashboardViewModel dashboardViewModel;

    private ProgressBar progressBar;
    private FragListener mListener;

    private TextView tvCases;
    private TextView tvCasesToday;
    private TextView tvRecovered;
    private TextView tvDeaths;
    private TextView tvDeathsToday;
    private TextView tvCritical;
    private TextView tvActive;
    private TextView tvUpdated;
    private TextView tvTested;
    private TextView tvHeading;
    private TextView tvNetwork;
    private String locationDataRequest;
    private FloatingActionButton fab;
    private boolean isNetworkOk;
    private SwipeRefreshLayout swipe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {

        dashboardViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(com.CA326MyBubble.ui.dashboard.DashboardViewModel.class);


        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //String appType = AppController.getInstance().getAppType();

        progressBar = root.findViewById(R.id.progress_circular_home);


        final RadioGroup radioFilter = root.findViewById(R.id.radioFilter);


        swipe = root.findViewById(R.id.swipeView);
        tvCases = root.findViewById(R.id.TotalCases);
        tvCasesToday = root.findViewById(R.id.casesToday);
        tvRecovered = root.findViewById(R.id.totalRecovered);
        tvDeaths = root.findViewById(R.id.totalDeaths);
        tvDeathsToday = root.findViewById(R.id.deathsToday);
        tvCritical = root.findViewById(R.id.totalCritical);
        tvActive = root.findViewById(R.id.totalActiveCases);
        tvUpdated = root.findViewById(R.id.tvLastUpdated);
        tvTested = root.findViewById(R.id.totalTested);
        tvHeading = root.findViewById(R.id.layoutTitle);
        tvNetwork = root.findViewById(R.id.tvNetwork);

        fab = root.findViewById(R.id.floatingActionButton);

            radioFilter.check(R.id.radioCountry);
            locationDataRequest = "country";




        radioFilter.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = root.findViewById(checkedId);

            locationDataRequest = radioButton.getText().toString();
            loadSummaryData(locationDataRequest);
            // Toast.makeText(getContext(), ""+radioButton.getText(), Toast.LENGTH_SHORT).show();
            if ("globe".equals(locationDataRequest)) {
                fab.setEnabled(false);
                fab.setVisibility(View.INVISIBLE);
            } else {
                fab.setEnabled(true);
                fab.setVisibility(View.VISIBLE);

            }
        });


        fab.setOnClickListener(v -> startIntent(locationDataRequest));

        return root;
    }


    private void loadSummaryData(String dataRequest) {

        removeText();
        dashboardViewModel.getDashboardViewModel(dataRequest, getActivity());
        dashboardViewModel.getStatistics().observe(getViewLifecycleOwner(), stats -> {
            if (stats != null){
                if(locationDataRequest.equals(stats.getCountry()))
                {
                    tvNetwork.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    refreshStats(false);
                    tvCases.setText(stats.getTotalCases());
                    tvCasesToday.setText(stats.getCasesToday());
                    tvRecovered.setText(stats.getTotalRecovered());
                    tvDeaths.setText(stats.getTotalDeaths());
                    tvDeathsToday.setText(stats.getDeathsToday());
                    tvCritical.setText(stats.getCritical());
                    tvActive.setText(stats.getActive());
                    tvTested.setText(stats.getTested());


                    String date = "Last Updated"+"\n"+getDate(stats.getUpdated());
                    tvUpdated.setText(date);
                    String heading = stats.getLocation()+" Cases";
                    tvHeading.setText(heading);
                }
            }
            else {
                tvNetwork.setVisibility(View.VISIBLE);
            }

        });

        swipe.setOnRefreshListener(this);
    }

    private void removeText() {
        setText();
        progressBar.setVisibility(View.VISIBLE);

        String def = "--";
        tvCases.setText(def);
        tvCasesToday.setText(def);
        tvRecovered.setText(def);
        tvDeaths.setText(def);
        tvDeathsToday.setText(def);
        tvCritical.setText(def);
        tvActive.setText(def);
        tvTested.setText(def);

        String date = "No Offline Data";

        tvUpdated.setText(date);

    }

    private void setText() {
        if ("globe".equals(locationDataRequest)) {
            String heading = "Global Cases";
            tvHeading.setText(heading);
        } else if ("country".equals(locationDataRequest)) {
            String heading = AppController.getInstance().getCountry() + " Cases";
            tvHeading.setText(heading);

        }
         else if ("continent".equals(locationDataRequest)) {
            String heading = AppController.getInstance().getContinent() + " Cases";
            tvHeading.setText(heading);
        }
    }

    private void refreshStats(boolean isRefresh) {
        if (isRefresh) {
            swipe.setRefreshing(true);

        } else {
            swipe.setRefreshing(false);
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
            mListener = (FragListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private String getDate(long milliSecond) {
        // Mon, 23 Mar 2020 02:01:04 PM
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }

    // Now we can fire the event when the user selects something in the fragment
    private void startIntent(String arguement) {
        if (mListener != null) {

                mListener.getListIntent(INTENT, arguement);

        }
    }


    @Override
    public void onResume() {
        super.onResume();

        loadSummaryData(locationDataRequest);
    }
}


