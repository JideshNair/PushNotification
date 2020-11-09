package com.example.fcm;

import android.app.Application;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;

import io.branch.referral.Branch;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        CleverTapAPI.setDebugLevel(3);

        ActivityLifecycleCallback.register(this);
        super.onCreate();
        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);
    }
}
