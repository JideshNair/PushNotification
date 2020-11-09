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
import com.clevertap.android.sdk.InAppNotificationButtonListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONObject;

import java.util.HashMap;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;


public class MainActivity extends AppCompatActivity implements InAppNotificationButtonListener {

	private static final String TAG = "MainActivity";
	private String token;
	CleverTapAPI clevertapDefaultInstance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		String id = intent.getStringExtra("extraKey1");
		String name = intent.getStringExtra("extraKey2");

		 clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(this);

		CleverTapAPI.getDefaultInstance(this).setInAppNotificationButtonListener(MainActivity.this);
		MiPushClient.registerPush(this,"", "");
		String xiaomiToken = MiPushClient.getRegId(this);
		if(clevertapDefaultInstance != null){
			clevertapDefaultInstance.pushXiaomiRegistrationId(xiaomiToken,true);
			Toast.makeText(MainActivity.this,xiaomiToken,Toast.LENGTH_LONG).show();
		}else{
			Log.e(TAG,"CleverTap is NULL");
		}


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
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		// if activity is in foreground (or in backstack but partially visible) launching the same
		// activity will skip onStart, handle this case with reInitSession
		Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit();
	}
	private Branch.BranchReferralInitListener branchReferralInitListener = new Branch.BranchReferralInitListener() {
		@Override
		public void onInitFinished(JSONObject linkProperties, BranchError error) {
			// do stuff with deep link data (nav to page, display content, etc)
		}
	};
public void  createEvent()
{
	 clevertapDefaultInstance.pushEvent("ButtonClick");
	Toast.makeText(MainActivity.this,"Event ButtonClick is triggered ",Toast.LENGTH_LONG).show();
}
	@Override
	public void onInAppButtonClick(HashMap<String, String> hashMap) {

		Toast.makeText(MainActivity.this,"Event ButtonClick is triggered ",Toast.LENGTH_LONG).show();
		if(hashMap != null){
			Log.d("hashmaptester",hashMap.toString());
		}
	}
	@Override
	protected void onStart() {
		super.onStart();
		Branch branch = Branch.getInstance();
		branch.setRequestMetadata("$clevertap_attribution_id",
				clevertapDefaultInstance.getCleverTapAttributionIdentifier());
		Log.d("ctid",clevertapDefaultInstance.getCleverTapAttributionIdentifier());
		//branch.initSession(branchReferralInitListener, MainActivity.this.getIntent().getData(), MainActivity.this);
		Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();
	}
}