package com.afridevelopers.cryptolator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Risen on 10/19/2017.
 */

public class coinDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CrytoDataBase";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "CrytoTable";
    private static final String COIN_TYPE = "coinType";
    private static final String CURRENCY_TYPE = "currencyType";
    private static final String CURRENCY_VALUE = "currencyValue";
    private static final String CREATE_QUERY = "create table "+ TABLE_NAME +
                                                    "( " + COIN_TYPE + " text,"
                                                         + CURRENCY_TYPE + " text,"
                                                         + CURRENCY_VALUE + " double);";
    private static final String DROP_QUERY = "drop table if exist " + TABLE_NAME + ";";
    private static final String DEBUDTAG = "Database Debug";


    public coinDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(DEBUDTAG,"database created...");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
        Log.d(DEBUDTAG,"Table created...");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(DEBUDTAG,CREATE_QUERY);
        sqLiteDatabase.execSQL(DROP_QUERY);
        Log.d(DEBUDTAG,"database dropped...");

    }

    public void insertPair(String coinName, String currencyName, double currencyValue, SQLiteDatabase sq){
        ContentValues values = new ContentValues();
        values.put(COIN_TYPE,coinName);
        values.put(CURRENCY_TYPE,currencyName);
        values.put(CURRENCY_VALUE,currencyValue);
        Long aLong = sq.insert(TABLE_NAME,null,values);
        Log.d(DEBUDTAG,"One row inserted...");

    }

    public Cursor getCoinPair(SQLiteDatabase sq){
        String[] columns = {COIN_TYPE,CURRENCY_TYPE,CURRENCY_VALUE};
        Cursor cursor = sq.query(TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }


}
