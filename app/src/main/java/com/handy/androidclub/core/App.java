package com.handy.androidclub.core;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.handy.androidclub.R;
import com.handy.androidclub.places.PlacesService;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app.
 */
public class App extends Application {

    private static GoogleAnalytics mAnalytics;
    private static Tracker mTracker;

    private PlacesService mPlacesService;

    public static GoogleAnalytics analytics() {
        return mAnalytics;
    }

    public static Tracker tracker() {
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeFabric();
        initializeFacebookSdk();
        initializeAnalytics();
        initializePlacesService();
    }

    private void initializePlacesService() {

        final String apiKey = getString(R.string.google_browser_api_key);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request();
                                HttpUrl url = request.url().newBuilder().addQueryParameter("key", apiKey).build();
                                request = request.newBuilder().url(url).build();
                                return chain.proceed(request);
                            }
                        })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build();

        mPlacesService = retrofit.create(PlacesService.class);
    }

    private void initializeFacebookSdk() {
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    private Fabric initializeFabric() {
        return Fabric.with(this, new Crashlytics());
    }

    private void initializeAnalytics() {
        mAnalytics = GoogleAnalytics.getInstance(this);
        mTracker = mAnalytics.newTracker(R.xml.global_tracker);
        mTracker.enableExceptionReporting(true);
        mTracker.enableAdvertisingIdCollection(true);
        mTracker.enableAutoActivityTracking(true);
    }

    public PlacesService getPlacesService() {
        return mPlacesService;
    }
}
