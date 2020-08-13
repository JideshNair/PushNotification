package com.example.fcm;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
	Random mRandom = new Random();
	private String token;
	CleverTapAPI clevertapDefaultInstance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		String id = intent.getStringExtra("extraKey1");
		String name = intent.getStringExtra("extraKey2");

		 clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

		CleverTapAPI.createNotificationChannel(this,"Test","Push Template App Channel","Channel for Push Template App", NotificationManager.IMPORTANCE_HIGH,true);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String tmp = "";
			for (String key : bundle.keySet()) {
				Object value = bundle.get(key);
				tmp += key + ": " + value + "\n\n";
			}

		}

		FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
			@Override
			public void onComplete(@NonNull Task<InstanceIdResult> task) {
				if (!task.isSuccessful()) {
					token = task.getException().getMessage();
					clevertapDefaultInstance.pushFcmRegistrationId(token,true);
					Log.w("FCM TOKEN Failed", task.getException());
				} else {
					token = task.getResult().getToken();
					Log.i("FCM TOKEN", token);
				}
			}
		});
		Button bt=findViewById(R.id.event);
		bt.setText(id);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				createEvent();
			}
		});
	}
public void  createEvent()
{
	String newUser = Integer.toString(Math.abs(mRandom.nextInt()));
	ArrayList<String> testArr = new ArrayList<>();
	testArr.add("one");
	testArr.add("two");
	testArr.add("three");
	Traits traits = new Traits();
	traits.putEmail("foo@foo.com");
	traits.putName("FooName");
	traits.putGender("male");
	traits.putPhone("+14155551234");
	traits.put("boolean", true);
	traits.put("integer", 50);
	traits.put("float", 1.5);
	traits.put("long", 12345L);
	traits.put("string", "hello");
	traits.put("stringInt", "1");
	traits.put("testStringArr", testArr);
	Analytics.with(getApplicationContext()).identify(newUser, traits, null);
	Analytics.with(getApplicationContext()).screen("Home Screen");
	// clevertapDefaultInstance.pushEvent("ButtonClick");
	Analytics.with(getApplicationContext()).track("testEvent From Segment",
			new Properties().putValue("value", "testValue")
					.putValue("testDate", new Date(System.currentTimeMillis()))
	);
	Toast.makeText(MainActivity.this,"Event ButtonClick is triggered ",Toast.LENGTH_LONG).show();
}

}