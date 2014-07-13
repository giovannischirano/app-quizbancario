package com.giovanni.testapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class QuizStructure{

	public QuizStructure(Context context) {
	    this.context = context;
	}
	
	private static final int NUM_DOMANDE_RISPOSTE = 4;
	private Context context;
	private SQLiteDatabase database;
	private QuizDbHelper dbHelper;
	
	/* Elements of TABLE */
	public static final String TABLE_NAME_DOMANDE = "domandeTable";
	public static final String COLUMN_NAME_ENTRY_ID = "_id";
	public static final String COLUMN_NAME_DOMANDA = "domanda";
	public static final String COLUMN_NAME_RISPOSTA_1 = "risposta1";
	public static final String COLUMN_NAME_RISPOSTA_2 = "risposta2";
	public static final String COLUMN_NAME_RISPOSTA_3 = "risposta3";
	
	public static final String TABLE_NAME_STATISTICHE_UTENTE = "statisticheTable";
	public static final String COLUMN_NAME_STATISTICA_ID = "_id";
	public static final String COLUMN_NAME_PUNTEGGIO = "punteggio";
    
	public QuizStructure open() throws SQLException {
		dbHelper = new QuizDbHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	private ContentValues createContentValuesDomanda(String domanda, String risposta_1, String risposta_2, String risposta_3) {
	    ContentValues values = new ContentValues();
	    values.put(COLUMN_NAME_DOMANDA, domanda);
	    values.put(COLUMN_NAME_RISPOSTA_1, risposta_1);
	    values.put(COLUMN_NAME_RISPOSTA_2, risposta_2);
	    values.put(COLUMN_NAME_RISPOSTA_3, risposta_3);

		return values;
	}
	
	private ContentValues createContentValuesPunteggio(String punteggio) {
	    ContentValues values = new ContentValues();
	    values.put(COLUMN_NAME_PUNTEGGIO, punteggio);

		return values;
	}

	// crea una domanda con le 3 risposte possibili
	public long creaDomanda(String domanda, String risposta_1, String risposta_2, String risposta_3) {
		
		ContentValues initialValues = createContentValuesDomanda(domanda, risposta_1, risposta_2, risposta_3);
		return database.insertOrThrow(TABLE_NAME_DOMANDE, null, initialValues);
	}
	
	public long inserisciPunteggio(String punteggio){
		
		ContentValues initialValues = createContentValuesPunteggio(punteggio);
		return database.insertOrThrow(TABLE_NAME_STATISTICHE_UTENTE, null, initialValues);
	}
	
	//fetch di tutte le domande
	public Cursor fetchAllDomande() {
		return database.query(TABLE_NAME_DOMANDE, new String[] { COLUMN_NAME_ENTRY_ID, 
				COLUMN_NAME_DOMANDA, COLUMN_NAME_RISPOSTA_1, COLUMN_NAME_RISPOSTA_2, 
				COLUMN_NAME_RISPOSTA_3 }, null, null,
				null, null, null);
	}
	
	//fetch di tutte i punteggi conseguiti dall'utente
	public Cursor fetchAllPunteggi() {
		return database.query(TABLE_NAME_STATISTICHE_UTENTE, new String[] {
				COLUMN_NAME_STATISTICA_ID, COLUMN_NAME_PUNTEGGIO}, null, null, null, null, null);
	}
	
	public void clearDbDomande() {
		database.execSQL("delete from " + TABLE_NAME_DOMANDE);
	}
	
	public void clearDbPunteggio() {
		database.execSQL("delete from " + TABLE_NAME_STATISTICHE_UTENTE);
	}
	
	public void buildDb(InputStream is){
		String lineFile;
        try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			while((lineFile = bufferedReader.readLine()) != null){
				createDatabaseQuiz(lineFile);
			}
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createDatabaseQuiz(String string){
		
		String splitString[] = new String[NUM_DOMANDE_RISPOSTE];
		splitString = string.split("\\|");
		String domanda = splitString[0];
		String risposta_1 = splitString[1];
		String risposta_2 = splitString[2];
		String risposta_3 = splitString[3];
		
		creaDomanda(domanda, risposta_1, risposta_2, risposta_3);
	}
}
