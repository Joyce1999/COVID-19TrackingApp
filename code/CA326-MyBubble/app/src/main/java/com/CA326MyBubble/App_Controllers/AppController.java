package com.CA326MyBubble.App_Controllers;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.CA326MyBubble.App_Utilities.ThemeController;

// Essentially a helper class to assist with methods and movement throughout the app
public class AppController extends Application {

    private RequestQueue ReqQueue;
    private static AppController mInstance;
    public static final String TAG = AppController.class.getSimpleName();

    String code;
    String continent;
    String country;
    String appType;

    private boolean isFirstStart;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        SharedPreferences getSharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        // Set based on whether or not its the applications first start EG. App just been installed on a new device
        setFirstStart(getSharedPreferences.getBoolean("firstStart", true));
        // Controls what happens based on the appType
        setAppType(getSharedPreferences.getString("appType", "covidGlobal"));
        // Defaults to what I have set as the System display, can be changed to dark/light
        new ThemeController(getSharedPreferences.getString("theme", "FollowSystem"));
        // Setting continent on dashboard and also grabs continent based on country selection
        setContinent(getSharedPreferences.getString("continent", null));
        // Sets Country based on selection
        setCountry(getSharedPreferences.getString("country", null));
        // Grabs country code, EG. Ireland = ie, this allows the newapi to display country related news
        setCode(getSharedPreferences.getString("code", null));

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (ReqQueue == null) {
            ReqQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return ReqQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (ReqQueue != null) {
            ReqQueue.cancelAll(tag);
        }
    }

    public boolean isFirstStart() {
        return isFirstStart;
    }

    public void setFirstStart(boolean firstStart) {
        isFirstStart = firstStart;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAppType() {
        return appType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

}
