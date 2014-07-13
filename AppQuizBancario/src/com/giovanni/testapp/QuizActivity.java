package com.giovanni.testapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.testapp.R;
import com.giovanni.test.app.utils.QuizStructure;

public class QuizActivity extends Activity {

	private QuizStructure dbHelper; 
    private Cursor cursor;
    
    private TextView statoQuiz;
    private TextView domandaText;
    private RadioGroup radio_group;
    private RadioButton radio_question_1;
    private RadioButton radio_question_2;
    private RadioButton radio_question_3;
    
    private String domanda = "";
    private String risposta_1 = ""; 
    private String risposta_2 = ""; 
    private String risposta_3 = "";
    
    private int numeroDomanda = 1;
    private double risultato = 0;
    private int numDomande;
    private boolean risRispostaFull = false;
    private boolean risRispostaHalf = false;
    private boolean dialogAlreadyShowed = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		statoQuiz = (TextView) findViewById(R.id.stato_quiz);
		domandaText = (TextView) findViewById(R.id.domanda);
		radio_group = (RadioGroup) findViewById(R.id.radioGroup);
		radio_question_1 = (RadioButton) findViewById(R.id.radio_question_1);
		radio_question_2 = (RadioButton) findViewById(R.id.radio_question_2);
		radio_question_3 = (RadioButton) findViewById(R.id.radio_question_3);
		Button nextQuestion = (Button) findViewById(R.id.button_next_question);
		
		dbHelper = new QuizStructure(this);
        dbHelper.open();
        dbHelper.clearDb();
        
        AssetManager am = getApplicationContext().getAssets();
        InputStream is = null;
		try {
			is = am.open("domande.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        dbHelper.buildDb(is);
              
        cursor = dbHelper.fetchAllDomande();
        numDomande = cursor.getCount();
        cursor.moveToFirst();
        
        domanda = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_DOMANDA));
        risposta_1 = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_RISPOSTA_1));
        risposta_2 = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_RISPOSTA_2));
        risposta_3 = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_RISPOSTA_3));

        
        statoQuiz.setText("Domanda " + numeroDomanda);
        domandaText.setText(domanda);
        initializeRadioRisposte(risposta_1, risposta_2, risposta_3);
        
        nextQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!(radio_question_1.isChecked() || radio_question_2.isChecked() || radio_question_3.isChecked()) && !dialogAlreadyShowed)
				{
					new AlertDialog.Builder(QuizActivity.this)
				    .setTitle(getResources().getString(R.string.nessuna_risposta_title))
				    .setMessage(getResources().getString(R.string.nessuna_risposta_message))
				    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	dialogAlreadyShowed = true;
				        }
				     })
				    .show();
				}
				
				else{
					radio_group.clearCheck();
					
					if(risRispostaFull){
						risultato++;
					}
					else if(risRispostaHalf){
						risultato = risultato + 0.5;
					}
					else{
					}
					
					if(cursor.moveToNext())
					{
						numeroDomanda++;
						statoQuiz.setText("Domanda " + numeroDomanda);
						domanda = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_DOMANDA));
			        	risposta_1 = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_RISPOSTA_1));
			        	risposta_2 = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_RISPOSTA_2));
			        	risposta_3 = cursor.getString(cursor.getColumnIndex(QuizStructure.COLUMN_NAME_RISPOSTA_3));
			        	domandaText.setText(domanda);
			        	
			        	initializeRadioRisposte(risposta_1, risposta_2, risposta_3);
					}
					else{
						Intent risultatoIntent = new Intent(QuizActivity.this, RisultatoActivity.class);
						double risultatoFinaleTemp = (double) risultato/numDomande*100;
						String risultatoFinale = String.valueOf((int) risultatoFinaleTemp) + "%";
						dbHelper.inserisciPunteggio(risultatoFinale);
						
						risultatoIntent.putExtra("risultatoQuiz", risultatoFinale);
						startActivity(risultatoIntent);
						finish();
						Log.d("MIAPP", "domande finite");
						dbHelper.close();
						cursor.close();
					}
					
					dialogAlreadyShowed = false;
				}
			}
		});		
	}

	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();

	    // Check which radio button was clicked
	    switch(view.getId()) {
	        
	    	case R.id.radio_question_1:
	            if (checked)
	            {
	            	if(radio_question_1.getText().equals(risposta_1)){
	            		risRispostaFull = true;
	            		risRispostaHalf = false;
	                }
	            	else if(radio_question_1.getText().equals(risposta_2)){
	            		risRispostaFull = false;
	            		risRispostaHalf = true;
	                }
	                else{
	                	risRispostaFull = false;
	                	risRispostaHalf = false;
	                }
	    		}
	            break;
	        
	    	case R.id.radio_question_2:
	            if (checked)
	            {
	            	if(radio_question_2.getText().equals(risposta_1)){
	            		risRispostaFull = true;
	            		risRispostaHalf = false;
	                }
	            	else if(radio_question_2.getText().equals(risposta_2)){
	            		risRispostaFull = false;
	            		risRispostaHalf = true;
	                }
	            	else{
	            		risRispostaFull = false;
	            		risRispostaHalf = false;
	                }
	            }
	            break;
	        
	    	case R.id.radio_question_3:
	            if (checked)
	            {
	            	if(radio_question_3.getText().equals(risposta_1)){
	            		risRispostaFull = true;
	            		risRispostaHalf = false;
	                }
	            	else if(radio_question_3.getText().equals(risposta_2)){
	            		risRispostaFull = false;
	            		risRispostaHalf = true;
	                }
	            	else{
	            		risRispostaFull = false;
	            		risRispostaHalf = false;
	                }
	            }
	            break;
	    }
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
	
	public void initializeRadioRisposte(String stringA, String stringB, String stringC){
		
		ArrayList<String> randomString = new ArrayList<String>();
		
		randomString.add(stringA);
		randomString.add(stringB);
		randomString.add(stringC);
		
		Collections.shuffle(randomString);
		
		radio_question_1.setText(randomString.get(0));
        radio_question_2.setText(randomString.get(1));
        radio_question_3.setText(randomString.get(2));
	}
}
