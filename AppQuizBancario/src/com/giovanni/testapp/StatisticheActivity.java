package com.giovanni.testapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.testapp.R;
import com.giovanni.testapp.utils.QuizStructure;
import com.giovanni.testapp.utils.StatisticItem;
import com.giovanni.testapp.utils.StatisticsViewAdapter;

public class StatisticheActivity extends Activity {

	private QuizStructure dbHelper; 
    private Cursor cursor;
	private Button pulisciStoria;
    private ListView listView;
    private StatisticsViewAdapter adapter;
    private List<StatisticItem> statisticItems = new ArrayList<StatisticItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistiche);
		
		listView = (ListView) findViewById(R.id.listView);
		pulisciStoria = (Button) findViewById(R.id.button_pulisci_statistiche);
		
		dbHelper = new QuizStructure(this);
        dbHelper.open();
        cursor = dbHelper.fetchAllPunteggi();
		
        if(cursor!=null && cursor.getCount()>0)
        {
        	while(cursor.moveToNext()){
        		String punteggio = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_PUNTEGGIO));
        		String dataPunteggio = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_PUNTEGGIO_DATA)).substring(0, 10);
        		
        		StatisticItem item = new StatisticItem(punteggio, dataPunteggio, "");
        		statisticItems.add(item);
	        }
        	Collections.reverse(statisticItems);
		}

        adapter = new StatisticsViewAdapter(getApplicationContext(), R.layout.list_statistiche_layout, statisticItems);
        listView.setAdapter(adapter);
        
        pulisciStoria.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbHelper.clearDbPunteggio();
				statisticItems = new ArrayList<StatisticItem>();
				listView.setAdapter(null);
			}
		});
        
        cursor.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistiche, menu);
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
