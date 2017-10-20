package com.afridevelopers.cryptolator;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Coin> coinArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);

        recyclerView = (RecyclerView)findViewById(R.id.coin_recycler_view);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            DbHelper dbHelper = new DbHelper(this);
            dbHelper.open();
            Cursor cursor = dbHelper.getCoinPair();

            do{
                Coin coin = new Coin(cursor.getString(0),cursor.getString(1),cursor.getDouble(2));
                coinArrayList.add(coin);
            } while (cursor.moveToNext());
            dbHelper.close();

            adapter = new CoinAdapter(coinArrayList);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
