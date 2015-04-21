package com.giovanni.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.example.testapp.R;

public class MainActivity extends Activity {

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button startQuiz = (Button) findViewById(R.id.button_start_quiz);
		Button statisticheQuiz = (Button) findViewById(R.id.button_start_statistiche);
		
		startQuiz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentStartQuiz = new Intent(MainActivity.this, QuizActivity.class);
				startActivity(intentStartQuiz);
			}
		});
		
		statisticheQuiz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentStartQuiz = new Intent(MainActivity.this, StatisticheActivity.class);
				startActivity(intentStartQuiz);				
			}
		});
		
		AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		this.finish();
	}
	
}
