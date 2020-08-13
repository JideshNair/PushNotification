package com.example.fcm;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.SyncListener;
import com.segment.analytics.Analytics;
import com.segment.analytics.android.integrations.clevertap.CleverTapIntegration;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class MyApplication extends Application implements SyncListener {
    private static final String CLEVERTAP_KEY = "CleverTap";
    public static boolean sCleverTapSegmentEnabled = false;
    private static Handler handler = null;
    private CleverTapAPI clevertap;
    private static final String WRITE_KEY = "FQAPL2ATNAFlS3dQAZrl4nAsLoTqHJSU";
    @Override
    public void onCreate() {
        CleverTapAPI.setDebugLevel(3);

        ActivityLifecycleCallback.register(this);
        Analytics analytics = new Analytics.Builder(getApplicationContext(), WRITE_KEY)
                .logLevel(Analytics.LogLevel.VERBOSE)
                .use(CleverTapIntegration.FACTORY)
                .build();
        analytics.onIntegrationReady(CLEVERTAP_KEY, new Analytics.Callback<CleverTapAPI>() {
            @Override
            public void onReady(CleverTapAPI instance) {
                Log.i(TAG, "analytics.onIntegrationReady() called");
                CleverTapIntegrationReady(instance);
            }
        });

        Analytics.setSingletonInstance(analytics);
    }
    private void CleverTapIntegrationReady(CleverTapAPI instance) {
        instance.enablePersonalization();
        sCleverTapSegmentEnabled = true;
        clevertap = instance;
        clevertap.setSyncListener(this);
        getCleverTapAttributionIdentifier();
    }

    private void getCleverTapAttributionIdentifier() {
        //on initial app install, a call to getCleverTapAttributionIdentifier will return NULL until the sdk is fully initialized
        String cleverTapID = clevertap.getCleverTapAttributionIdentifier();
        if (cleverTapID == null) {
            if (handler == null) {
                handler = new Handler();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getCleverTapAttributionIdentifier();
                }
            }, 500);
        } else {
            Log.d("CT_ATTRIBUTION_ID", cleverTapID);
        }
    }

    // SyncListener
    public void profileDidInitialize(String CleverTapID){
        Log.d("CT_PROFILE_INITIALIZED", CleverTapID);
    }
    public void profileDataUpdated(JSONObject updates) {
        Log.d("CT_PROFILE_UPDATES", updates.toString());
    }
}
