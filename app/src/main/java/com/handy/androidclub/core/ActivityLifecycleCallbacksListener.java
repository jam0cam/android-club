package com.handy.androidclub.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;

public class ActivityLifecycleCallbacksListener implements Application.ActivityLifecycleCallbacks {
    private static ActivityLifecycleCallbacksListener mActivityLifecycleCallbacksListener;

    private ActivityLifecycleCallbacksListener() {
    }

    public static synchronized ActivityLifecycleCallbacksListener get() {
        if (mActivityLifecycleCallbacksListener == null) {
            mActivityLifecycleCallbacksListener = new ActivityLifecycleCallbacksListener();
        }
        return mActivityLifecycleCallbacksListener;
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
        track(activity,"created");
    }

    @Override
    public void onActivityStarted(final Activity activity) {
        track(activity, "started");
    }

    @Override
    public void onActivityResumed(final Activity activity) {
        track(activity, "resumed");
        AppEventsLogger.activateApp(activity);
    }

    @Override
    public void onActivityPaused(final Activity activity) {
        track(activity, "paused");
        AppEventsLogger.deactivateApp(activity);
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        track(activity, "stopped");

    }

    @Override
    public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {
        track(activity, "saveInstanceState");

    }

    @Override
    public void onActivityDestroyed(final Activity activity) {
        track(activity, "destroyed");
    }

    private void track(final Activity activity, final String action) {
        App.tracker().send(
                new HitBuilders.EventBuilder("activity_lifecycle", action)
                        .setLabel(activity.getLocalClassName())
                        .build()
        );
    }

}
