package com.example.fcm;

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

		 clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
		String fcmRegId = FirebaseInstanceId.getInstance().getToken();
		clevertapDefaultInstance.pushFcmRegistrationId(fcmRegId,true);
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
					Log.w("FCM TOKEN Failed", task.getException());
				} else {
					token = task.getResult().getToken();
					Log.i("FCM TOKEN", token);
				}
			}
		});
		Button bt=findViewById(R.id.event);
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