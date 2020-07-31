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


public class MainActivity extends AppCompatActivity {

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
	 clevertapDefaultInstance.pushEvent("ButtonClick");
	Toast.makeText(MainActivity.this,"Event ButtonClick is triggered ",Toast.LENGTH_LONG).show();
}

}