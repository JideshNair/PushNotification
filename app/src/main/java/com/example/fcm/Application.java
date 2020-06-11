package com.example.fcm;

import android.app.NotificationManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.xiaomi.mipush.sdk.MiPushClient;

public class Application extends android.app.Application {
    CleverTapAPI clevertapDefaultInstance;
    private String token;
    @Override
    public void onCreate() {

        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        FirebaseApp.initializeApp(this);
        MiPushClient.registerPush(Application.this, "", "");
        ActivityLifecycleCallback.register(this);
        CleverTapAPI.setDebugLevel(3);
        super.onCreate();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    token = task.getException().getMessage();
                    Log.w("FCM TOKEN Failed", task.getException());
                } else {
                    token = task.getResult().getToken();
                    Log.i("FCM TOKEN", token);

                    clevertapDefaultInstance.pushFcmRegistrationId(token,true);
                    clevertapDefaultInstance.createNotificationChannelGroup(getApplicationContext(),"123","TEST_group");
                    clevertapDefaultInstance.createNotificationChannel(getApplicationContext(),"1","TEST_CHANNEL","This is TEST", NotificationManager.IMPORTANCE_MAX,"123",true);//Set to false in production

                    String xiaomiToken = MiPushClient.getRegId(Application.this);
                    if(clevertapDefaultInstance  != null){
                        clevertapDefaultInstance .pushXiaomiRegistrationId(xiaomiToken,true);
                    }else{
                        Log.e("MainActivity","CleverTap is NULL");
                    }

                }
            }
        });
        //Your other Application code
    }

}