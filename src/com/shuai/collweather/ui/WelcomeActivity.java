package com.shuai.collweather.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.example.collweather.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WelcomeActivity extends Activity {

	
	private final int GONEXT = 250;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GONEXT:
				startActivity(new Intent(WelcomeActivity.this, ChooseAreaActicity.class));
				finish();
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		goNext();
		
	}
	private void goNext() {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = GONEXT;
				handler.sendMessage(msg);
			}
		};
		timer.schedule(timerTask, 2000);
	}
}
