package com.handy.androidclub.core;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.handy.androidclub.R;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app.
 */
public class App extends Application {

    private static GoogleAnalytics mAnalytics;
    private static Tracker mTracker;

    public static GoogleAnalytics analytics() {
        return mAnalytics;
    }

    public static Tracker tracker() {
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeAnalytics();
    }

    private void initializeAnalytics() {
        mAnalytics = GoogleAnalytics.getInstance(this);
        mTracker = mAnalytics.newTracker(R.xml.global_tracker);
        mTracker.enableExceptionReporting(true);
        mTracker.enableAdvertisingIdCollection(true);
        mTracker.enableAutoActivityTracking(true);
    }
}
