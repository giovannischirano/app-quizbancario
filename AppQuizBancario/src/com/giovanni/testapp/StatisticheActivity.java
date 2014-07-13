package com.giovanni.testapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.testapp.R;
import com.giovanni.testapp.utils.QuizStructure;

public class StatisticheActivity extends Activity {

	private QuizStructure dbHelper; 
    private Cursor cursor;
    private ArrayList<String> listPunteggi = new ArrayList<String>();
	private Button pulisciStoria;
    private ListView listView;
	
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
	        	listPunteggi.add(cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_PUNTEGGIO)));
	        }
		}

        ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_adapter_list, listPunteggi);
        listView.setAdapter(adapter);
        
        pulisciStoria.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbHelper.clearDbPunteggio();
				listPunteggi = new ArrayList<String>();
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
