package com.giovanni.testapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Quiz.db";
	
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    
    private static final String SQL_CREATE_TABLE_DOMANDE =
        "CREATE TABLE " + QuizStructure.TABLE_NAME_DOMANDE + " (" +
        QuizStructure.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        QuizStructure.COLUMN_NAME_DOMANDA + TEXT_TYPE + COMMA_SEP +
        QuizStructure.COLUMN_NAME_RISPOSTA_1 + TEXT_TYPE + COMMA_SEP +
        QuizStructure.COLUMN_NAME_RISPOSTA_2 + TEXT_TYPE + COMMA_SEP +
        QuizStructure.COLUMN_NAME_RISPOSTA_3 + TEXT_TYPE +
        " )";
    
    private static final String SQL_CREATE_STATISTICHE_UTENTE =
    	"CREATE TABLE " + QuizStructure.TABLE_NAME_STATISTICHE_UTENTE + " (" +
        QuizStructure.COLUMN_NAME_STATISTICA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        QuizStructure.COLUMN_NAME_PUNTEGGIO + TEXT_TYPE +
        " )";

    private static final String SQL_DELETE_TABLE_DOMANDE =
        "DROP TABLE IF EXISTS " + QuizStructure.TABLE_NAME_DOMANDE;
    
    private static final String SQL_DELETE_TABLE_STATISTICHE =
        "DROP TABLE IF EXISTS " + QuizStructure.TABLE_NAME_STATISTICHE_UTENTE;
    
	public QuizDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_DOMANDE);
		db.execSQL(SQL_CREATE_STATISTICHE_UTENTE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_TABLE_DOMANDE);
		db.execSQL(SQL_DELETE_TABLE_STATISTICHE);
        onCreate(db);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
