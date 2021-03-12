package com.CA326MyBubble.App_Services.json;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import com.CA326MyBubble.App_Controllers.AppUtilsController;
import com.CA326MyBubble.App_Controllers.AppController;
import com.CA326MyBubble.Layout_Models.Summary;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CountryDataRequest {

    private String url;

    public CountryDataRequest() {

        AppUtilsController appUtilsController = new AppUtilsController();
        url = appUtilsController.getCountryUrl();
    }
        public MutableLiveData<Summary> parseJSON() {

        final MutableLiveData<Summary> mutableLiveData = new MutableLiveData<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String cases = response.getString("cases");
                    String todayCases = response.getString("todayCases");
                    String deaths = response.getString("deaths");
                    String todayDeaths = response.getString("todayDeaths");
                    String recovered = response.getString("recovered");
                    String active = response.getString("active");
                    String critical = response.getString("critical");
                    String tested = response.getString("tests");
                    long updated = response.getLong("updated");
                    String country = response.getString("country");

                    final Summary summary = new Summary();

                    summary.setCases(cases);
                    summary.setTodayCases(todayCases);
                    summary.setTotalDeaths(deaths);
                    summary.setDeathsToday(todayDeaths);
                    summary.setTotalRecovered(recovered);
                    summary.setActive(active);
                    summary.setCritical(critical);
                    summary.setTested(tested);
                    summary.setUpdated(updated);
                    summary.setLocation(country);
                    summary.setCountry("country");
                    mutableLiveData.setValue(summary);

                } catch (JSONException e) {
                    e.printStackTrace();
                    VolleyLog.d(TAG, "Error: " + e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                    }
                });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

        return mutableLiveData;
    }
}
