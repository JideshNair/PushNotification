package com.example.fcm;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CTExperimentsListener;
import com.clevertap.android.sdk.CleverTapAPI;
import com.example.fcm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.xiaomi.mipush.sdk.MiPushClient;


public class MainActivity extends AppCompatActivity implements CTExperimentsListener {
	 String bgColour="bull";

	RelativeLayout rl;
	CleverTapAPI clevertapDefaultInstance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		rl=findViewById(R.id.main);


		clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());


		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String tmp = "";
			for (String key : bundle.keySet()) {
				Object value = bundle.get(key);
				tmp += key + ": " + value + "\n\n";
			}

		}


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

	@Override
	public void CTExperimentsUpdated() {

	}
}