package com.example.fcm;



import android.annotation.SuppressLint;
import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Rational;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SecondActivity extends AppCompatActivity {
	VideoDialogFragment fragment1;
	Bundle bundle;
	private SimpleExoPlayer player;
	PlayerView videoView;
	private boolean playWhenReady = true;
	private int currentWindow = 0;
	private long playbackPosition = 0;
//	private final PictureInPictureParams.Builder pictureInPictureParamsBuilder=new PictureInPictureParams.Builder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		TextView txt = findViewById(R.id.textView);

		 bundle = getIntent().getBundleExtra("bundle");
		if (bundle != null) {
			for (String key : bundle.keySet()) {
				Object value = bundle.get(key);
				txt.append(key + ": " + value + "\n\n");
			}
		}
		fragment1 = new VideoDialogFragment();
		Bundle bundle2 = new Bundle();
		bundle2.putString("videourl",bundle.getString("videourl"));
		fragment1.setArguments(bundle);
		fragment1.show(getSupportFragmentManager(), "");


		/*pictureInPictureMode();
		videoView = findViewById(R.id.videoView);
		videoView.showController();
		initializePlayer(bundle.getString("videourl"));
	//	this.enterPictureInPictureMode();

		 */
	}

	@Override
	public void onBackPressed() {

		if(fragment1.isVisible())
		{
			fragment1.dismiss();
		}
		else
		{
			super.onBackPressed();
		}
	}

}