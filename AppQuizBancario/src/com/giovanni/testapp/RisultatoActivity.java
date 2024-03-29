package com.giovanni.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.testapp.R;

public class RisultatoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_risultato);
		
		Intent intent = getIntent();
		String risultato = intent.getStringExtra("risultatoQuiz");

		TextView risultatoText = (TextView) findViewById(R.id.risultato_quiz);
		risultatoText.setText(getResources().getString(R.string.risultatoMessage) + "\n" + risultato + "%");
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.risultato, menu);
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


}
