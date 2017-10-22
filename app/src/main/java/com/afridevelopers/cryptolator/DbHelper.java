package com.afridevelopers.cryptolator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Risen on 10/19/2017.
 */

public class DbHelper {

    private static final String DB_NAME = "CrytoDataBase";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "CrytoTable";
    private static final String INDEX = "id";
    private static final String COIN_TYPE = "coinType";
    private static final String CURRENCY_TYPE = "currencyType";
    private static final String INPUT_VALUE = "inputValue";
    private static final String CURRENCY_VALUE = "currencyValue";
    private SQLiteDatabase sqLiteDatabase;
    private CoinDbHelper coinDbHelper;
    private Context context;

    private static final String CREATE_QUERY = "create table " + TABLE_NAME + "( "
            + INDEX + " INTEGER, "
            + COIN_TYPE + " text,"
            + CURRENCY_TYPE + " text,"
            + INPUT_VALUE + " text,"
            + CURRENCY_VALUE + " text);";
    private static final String DROP_QUERY = "drop table if exist " + TABLE_NAME + ";";
    private static final String DEBUDTAG = "Database Debug";

    public DbHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        coinDbHelper = new CoinDbHelper(context);
        sqLiteDatabase = coinDbHelper.getReadableDatabase();


    }

    //close
    public void close() {
        if (coinDbHelper != null) {
            coinDbHelper.close();
        }
    }


    private static class CoinDbHelper extends SQLiteOpenHelper {

        CoinDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            Log.d(DEBUDTAG, "database created...");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(DEBUDTAG, CREATE_QUERY);
            sqLiteDatabase.execSQL(CREATE_QUERY);
            Log.d(DEBUDTAG, "Table created...");


        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_QUERY);
            Log.d(DEBUDTAG, "database dropped...");
            onCreate(sqLiteDatabase);

        }
    }

        public void insertPair(int index, String coinName, String currencyName,String inputValue, String currencyValue) {
            sqLiteDatabase = coinDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(INDEX,index);
            values.put(COIN_TYPE, coinName);
            values.put(CURRENCY_TYPE, currencyName);
            values.put(INPUT_VALUE, inputValue);
            values.put(CURRENCY_VALUE, currencyValue);
            sqLiteDatabase.insert(TABLE_NAME, null, values);
            Log.d(DEBUDTAG, "One row inserted...");

        }

        public Cursor getCoinPair() {
            sqLiteDatabase = coinDbHelper.getReadableDatabase();
            String[] columns = {INDEX,COIN_TYPE, CURRENCY_TYPE,INPUT_VALUE, CURRENCY_VALUE};
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
        }

    public void deletePairByID(int index){
        sqLiteDatabase = coinDbHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,INDEX + "=?",new String[]{String.valueOf(index)});
        Log.d("DBHELPER","Deletion successful");

    }


    }






