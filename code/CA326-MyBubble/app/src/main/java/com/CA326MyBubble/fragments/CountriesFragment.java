package com.CA326MyBubble.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CA326MyBubble.interfaces.InteractionListener;
import com.CA326MyBubble.model.CountrySelection;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.CA326MyBubble.adapters.RVAdapter;
import com.CA326MyBubble.adapters.RVAdapterSelectCountry;
import com.CA326MyBubble.controllers.AppController;
import com.CA326MyBubble.R;
import com.CA326MyBubble.repositories.CountriesData;
import com.CA326MyBubble.interfaces.ClickListener;
import com.CA326MyBubble.listeners.TouchListener;
import com.CA326MyBubble.model.GlobalCountries;

import static com.CA326MyBubble.ut.Utilities.LOCAL;
import static com.CA326MyBubble.ut.Utilities.SERVER;
import static com.CA326MyBubble.ut.Utilities.SETUP;
import static com.CA326MyBubble.ut.Utilities.NULL_INFO;

/**
 * A placeholder fragment containing a simple view.
 */
public class CountriesFragment extends Fragment {
    private static final String getLocation = "location";
    private static final String getList = "listType";

    private RecyclerView rV;
    private ProgressBar progressBar;



    private static final String identifier = CountriesFragment.class.getSimpleName();

    private String location;
    private String currentState;
    private List<GlobalCountries> globalCountriesList;
    private List<CountrySelection> selectACountryList;
    private String url;
    private InteractionListener interactionListener;
    private RVAdapter rvAdapter;
    private RVAdapterSelectCountry rvAdapterSelectCountry;

    public CountriesFragment() {
    }
    public static CountriesFragment newInstance(String inst, String inst2) {
        CountriesFragment setUpFrag = new CountriesFragment();

        Bundle args = new Bundle();
        args.putString(getLocation, inst);
        args.putString(getList, inst2);
        setUpFrag.setArguments(args);
        return setUpFrag;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            location = getArguments().getString(getLocation);
            currentState = getArguments().getString(getList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        // set has option menu as true because we have menu
        setHasOptionsMenu(true);
        setMenuVisibility(false);

        // call view
        rV = root.findViewById(R.id.CountryList);
        progressBar = root.findViewById(R.id.progressBarTwo);
        rV.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rV.getContext(), DividerItemDecoration.VERTICAL);
        rV.addItemDecoration(dividerItemDecoration);

        // call Volley method
        //call list

        if(currentState.equals(SERVER))
        {
            switch (location) {
                case "country":
                    url = "https://corona.lmao.ninja/v2/countries";
                    getStatsForTotalCases();
                    break;
                case "continent":
                    url = "https://corona.lmao.ninja/v2/continents";
                    getStatsForTotalCases();
                    break;
                default:
                    Toast.makeText(getActivity(), "Location not found!", Toast.LENGTH_LONG).show();
                    break;
            }
            if (location.equals("country"))
            {
                getActivity().setTitle(" countries available");
            }
            else {
                getActivity().setTitle(location+"s");
            }
        }
        else if(currentState.equals(LOCAL) || currentState.equals(SETUP))
        {
            getStatsFromCountrySelected();
        }
        return root;
    }
    // If a user makes an interaction, it attaches
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            interactionListener = (InteractionListener) context;
        }
    }
    // Once the user stops interacting, it will detach
    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }


    private void showRV() {
        rvAdapter = new RVAdapter(getActivity(), globalCountriesList);
        setMenuVisibility(true);
        rV.setAdapter(rvAdapter);
        rV.addOnItemTouchListener(new TouchListener(getActivity(), rV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                final GlobalCountries globalCountriesItem = rvAdapter.getFilteredList().get(position);
                onMenuItemClickServer(globalCountriesItem);
            }
            @Override
            public void onLongClick(View view, final int position) {
            }
        }));
    }
    private void showSelectCountryRV() {
        setMenuVisibility(true);
        rvAdapterSelectCountry = new RVAdapterSelectCountry(getActivity(), selectACountryList);
        rV.setAdapter(rvAdapterSelectCountry);
        progressBar.setVisibility(View.GONE);
        rV.addOnItemTouchListener(new TouchListener(getActivity(), rV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final CountrySelection countrySelection = rvAdapterSelectCountry.getmValuesFilteredList().get(position);
                onMenuItemClickSetting(countrySelection,location, currentState);
            }
            @Override
            public void onLongClick(View view, final int position) {
            }
        }));
    }
    private void getStatsForTotalCases() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(identifier, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        globalCountriesList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject stats = jsonArray.getJSONObject(i);

                            // Extract JSONObject inside JSONObject
                            switch (location) {
                                case "country":
                                    JSONObject countryInfo = stats.getJSONObject("countryInfo");
                                    globalCountriesList.add(new GlobalCountries(
                                            stats.getString("country"),
                                            stats.getInt("cases"),
                                            stats.getString("todayCases"),
                                            stats.getString("deaths"),
                                            stats.getString("todayDeaths"),
                                            stats.getString("recovered"),
                                            stats.getString("active"),
                                            stats.getString("critical"),
                                            stats.getString("tests")
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " countries available");
                                    break;
                                case "continent":

                                    globalCountriesList.add(new GlobalCountries(
                                            stats.getString("continent"),
                                            stats.getInt("cases"),
                                            stats.getString("todayCases"),
                                            stats.getString("deaths"),
                                            stats.getString("todayDeaths"),
                                            stats.getString("recovered"),
                                            stats.getString("active"),
                                            stats.getString("critical"),
                                            NULL_INFO // There is no data on how many tests theres been per continent
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " continents available");
                                    break;
                            }
                        }

                        // sort descending
                        Collections.sort(globalCountriesList, new Comparator<GlobalCountries>() {
                            @Override
                            public int compare(GlobalCountries x, GlobalCountries y) {
                                if (x.getTotalCases() > y.getTotalCases()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        });

                        showRV();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(identifier, "onResponse: " + error);
                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void getStatsAlphabetically() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(identifier, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        globalCountriesList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            // Extract JSONObject inside JSONObject
                            switch (location) {
                                case "country":
                                    JSONObject countryInfo = data.getJSONObject("countryInfo");
                                    globalCountriesList.add(new GlobalCountries(
                                            data.getString("country"),
                                            data.getInt("cases"),
                                            data.getString("todayCases"),
                                            data.getString("deaths"),
                                            data.getString("todayDeaths"),
                                            data.getString("recovered"),
                                            data.getString("active"),
                                            data.getString("critical"),
                                            data.getString("tests")
                                    ));
                                    getActivity().setTitle(jsonArray.length() + " countries available");
                                    break;
                                case "continent":

                                    globalCountriesList.add(new GlobalCountries(
                                            data.getString("continent"),
                                            data.getInt("cases"),
                                            data.getString("todayCases"),
                                            data.getString("deaths"),
                                            data.getString("todayDeaths"),
                                            data.getString("recovered"),
                                            data.getString("active"),
                                            data.getString("critical"),
                                            NULL_INFO // No Test stats for continents
                                    ));
                                    getActivity().setTitle(+ jsonArray.length() + " continents available");
                                    break;
                            }
                        }
                        showRV();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(identifier, "onResponse: " + error);
                    }
                });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void getStatsFromCountrySelected()
    {
        selectACountryList = new ArrayList<>();

        CountriesData countriesData = new CountriesData();

        selectACountryList = countriesData.getCountrySelectionList();

        getActivity().setTitle("select "+location);

        showSelectCountryRV();

    }
    // Side menu drop down, allows User to filter between Alphabetically / Numerical and also to search for the country they would like
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        // Grabs the menu items
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem itemCases = menu.findItem(R.id.action_sort_cases);
        MenuItem itemAlpaha = menu.findItem(R.id.action_sort_alpha);
        // Looks for the countries with the highest case rate
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            // If query is changed in search bar, change display
            @Override
            public boolean onQueryTextChange(String newText) {
                if (rvAdapter != null) {
                    rvAdapter.getFilter().filter(newText);
                }
                else if (rvAdapterSelectCountry != null)
                {
                    rvAdapterSelectCountry.getFilter().filter(newText);
                }
                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }
    // Manages what happens when a user clicks either Alphabetical sort or Sort by cases
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort_alpha:
                if(currentState.equals(SERVER))
                {
                    Toast.makeText(getContext(), "Sorting Alphabetically", Toast.LENGTH_SHORT).show();
                    globalCountriesList.clear();
                    progressBar.setVisibility(View.VISIBLE);
                    getStatsAlphabetically();
                }
                return true;

            case R.id.action_sort_cases:
                if(currentState.equals(SERVER))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    globalCountriesList.clear();
                    Toast.makeText(getContext(), "Sorting by Total Cases", Toast.LENGTH_SHORT).show();
                    getStatsForTotalCases();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onMenuItemClickServer(GlobalCountries globalCountries) {
        if (interactionListener != null) {
            getActivity().setTitle(globalCountries.getCountryWithCovid());
            interactionListener.listItemClickServer(globalCountries);
        }
    }

    public void onMenuItemClickSetting(CountrySelection CountrySelection, String location, String listType) {
        if (interactionListener != null) {
            interactionListener.listItemClickSetting(CountrySelection, location, listType);
        }
    }
}
