package com.afridevelopers.cryptolator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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
    ArrayList<CoinModel> coinModelArrayList;
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
        coinModelArrayList = new ArrayList<>();
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

    private void check_if_array_empty() {
        if (coinModelArrayList.size() == 0){

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

    private void fillAdapter() {
        try {
            DbHelper dbHelper = new DbHelper(this);
            dbHelper.open();
            Cursor cursor = dbHelper.getCoinPair();

            if (cursor != null){
                do{
                    CoinModel coinModel = new CoinModel(cursor.getInt(0),   //index
                                                        cursor.getString(1), //coin_icon
                                                        cursor.getString(2),  //coin_type
                                                        cursor.getString(3),  //currency_icon
                                                        cursor.getString(4),   //currency_type
                                                        cursor.getString(5),   //input value
                                                        cursor.getString(6));  //output value
                    coinModelArrayList.add(coinModel);
                } while (cursor.moveToNext());
            }

            dbHelper.close();
            coinAdapter = new CoinAdapter(coinModelArrayList);
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
        CoinModel coinModel = coinModelArrayList.get(position);
        if (coinModel != null){
            Intent intent = new Intent(RecyclerList.this,ConversionActivity.class);
            intent.putExtra("new_user","no");
            intent.putExtra(coinType, coinModel.getCoin_type());
            intent.putExtra(currencyType, coinModel.getCurrency());
            intent.putExtra(inputValueHolder, coinModel.getInput_value());
            intent.putExtra(convertedValueHolder, coinModel.getOutput_value());

            //Creates a pair used in transition between coinModel and currency value
            Pair<View, String> pair1 = Pair.create(findViewById(R.id.input_value),"coin_trans_name");
            Pair<View, String> pair2 = Pair.create(findViewById(R.id.output_value),"currency_trans_name");

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(RecyclerList.this,pair1,pair2);
            startActivity(intent,optionsCompat.toBundle());

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
                CoinModel coinModel = coinModelArrayList.get(position);
                if (coinModel != null){
                    try {
                        dbHelper.open();
                        dbHelper.deletePairByID(coinModel.getIndex());  //Deletes coinModel object from the database with the help of the unique index value
                        coinModelArrayList.remove(position);             //Removes coinModel object from Recycler view as well
                        coinAdapter.notifyItemRemoved(position);
                        coinAdapter.notifyDataSetChanged();
                        Toast.makeText(RecyclerList.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        dbHelper.close();

                        //Checks if array is empty after every deletion;
                        check_if_array_empty();
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
            case R.id.menu_list:
                Intent intent1 = new Intent(this,RecyclerList.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.about:
                Intent intent = new Intent(this,About.class);
                startActivity(intent);
                break;
            case R.id.rate:
                //Google play id goes here
                break;
            case R.id.update:
                //Google play id goes here
                break;

            default:
                return super.onOptionsItemSelected(item);


        }
        return true;
    }
}
