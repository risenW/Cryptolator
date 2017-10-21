package com.afridevelopers.cryptolator;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerList extends AppCompatActivity implements MyItemClickListener,MyItemLongClickListener{

    RecyclerView recyclerView;
    //RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    CoinAdapter coinAdapter;
    ArrayList<Coin> coinArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);

        recyclerView = (RecyclerView)findViewById(R.id.coin_recycler_view);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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

            coinAdapter = new CoinAdapter(coinArrayList);
            recyclerView.setAdapter(coinAdapter);
            this.coinAdapter.setClickListener(this);
            this.coinAdapter.setLongClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        Coin coin = coinArrayList.get(position);
        if (coin != null){
            Toast.makeText(RecyclerList.this, "Item Click:" + coin.getCoin_type() +", "+ coin.getCurrency() +", "+ coin.getCurrency_value(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemLongClick(View view, int position) {
        Coin coin = coinArrayList.get(position);
        if (coin != null){
            Toast.makeText(RecyclerList.this, "Longclick: "  + coin.getCoin_type() +", "+ coin.getCurrency() +", "+ coin.getCurrency_value(), Toast.LENGTH_SHORT).show();
        }

    }
}
