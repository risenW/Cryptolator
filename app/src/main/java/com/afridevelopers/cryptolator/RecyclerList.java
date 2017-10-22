package com.afridevelopers.cryptolator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerList extends AppCompatActivity implements MyItemClickListener,MyItemLongClickListener{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CoinAdapter coinAdapter;
    ArrayList<Coin> coinArrayList;
    //Used as Intent Keys
    public String coinType = "coinType";
    public String currencyType = "currType";
    public String inputValueHolder = "input_value";
    public String convertedValueHolder = "value";

    @Override
    public void onBackPressed() {
       AlertDialog.Builder builder = new AlertDialog.Builder(RecyclerList.this);
        builder.setTitle("Exit Cryptolator");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);
        coinArrayList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.coin_recycler_view);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        //Calls the method to Populates the coin Array list;
        fillAdapter();
        //Calls the Method to check if the array list is empty, signifying if it is a first time install.
        check_if_array_empty();

    }



//    Method to check if the array list is empty
    private void check_if_array_empty() {
        if (coinArrayList.size() == 0){

            AlertDialog.Builder builder = new AlertDialog.Builder(RecyclerList.this);
            builder.setTitle("No Item to display");
            builder.setMessage("Please Add a pair");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(RecyclerList.this,ConversionActivity.class);
                    intent.putExtra("new_user","yes");   //Notifies the Main activity that it is a first time install
                    intent.putExtra(coinType,0);         //Sets the views to default values
                    intent.putExtra(currencyType,0);
                    intent.putExtra(inputValueHolder,1);
                    intent.putExtra(convertedValueHolder,0.0);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

//    method to Populates the Array list ans set the Adapter;
    private void fillAdapter() {
        try {
            DbHelper dbHelper = new DbHelper(this);
            dbHelper.open();
            Cursor cursor = dbHelper.getCoinPair();
            if (cursor != null){
                do{
                    Coin coin = new Coin(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                    coinArrayList.add(coin);
                } while (cursor.moveToNext());
            }
            dbHelper.close();
            coinAdapter = new CoinAdapter(coinArrayList);
            recyclerView.setAdapter(coinAdapter);
            this.coinAdapter.setClickListener(this);
            this.coinAdapter.setLongClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //OnItemClick starts the Conversion Activity with the selected values.
    @Override
    public void onItemClick(View view, int position) {
        Coin coin = coinArrayList.get(position);
        if (coin != null){
            Intent intent = new Intent(RecyclerList.this,ConversionActivity.class);
            intent.putExtra("new_user","no");
            intent.putExtra(coinType,coin.getCoin_type());
            intent.putExtra(currencyType,coin.getCurrency());
            intent.putExtra(inputValueHolder,coin.getInput_value());
            intent.putExtra(convertedValueHolder,coin.getCurrency_value());
            startActivity(intent);

        }

    }

    //OnLongClick deletes an item from the recycler view and database
    @Override
    public void onItemLongClick(View view, final int position) {
       AlertDialog.Builder builder = new AlertDialog.Builder(RecyclerList.this);
        builder.setTitle("Delete this item");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbHelper dbHelper = new DbHelper(RecyclerList.this);
                Coin coin = coinArrayList.get(position);
                if (coin != null){
                    try {
                        dbHelper.open();
                        dbHelper.deletePairByID(coin.getIndex());  //Deletes coin object from the database with the help of the unique index value
                        coinArrayList.remove(position);             //Removes coin object from Recycler view as well
                        coinAdapter.notifyItemRemoved(position);
                        coinAdapter.notifyDataSetChanged();
                        Toast.makeText(RecyclerList.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        dbHelper.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
        //Checks if array is empty after every deletion;
        check_if_array_empty();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.about:
                Intent intent = new Intent(this,About.class);
                startActivity(intent);
                break;
            case R.id.rate:

                break;
            case R.id.update:

                break;

            default:
                return super.onOptionsItemSelected(item);


        }
        return true;
    }
}
