package com.afridevelopers.cryptolator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ConversionActivity extends AppCompatActivity {

    private static final String MY_PREF = "my_preference";
    private String TAG = ConversionActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private EditText input_value;
    private TextView output;
    private Spinner spinner_coin,spinner_currency;
    private Button convert,add_to_list,goto_list;
    private String selected_coin,selected_currency;
    private double value_to_convert;
    private static String url = "";
    public static int index;
    private CalculationHelper calculationHelper;
    private DbHelper dbHelper;
    private RecyclerList RecyclerlistObject;
    private String[] currency_symbols, coin_drawable_id;
    private final String INDEX_VALUE = "indexValue";   //Key for saving index in preference


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ConversionActivity.this,RecyclerList.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        //Initializes all views, database, and classes
        call_initializer();
        // get bundles from Recycler Activity and sets the views respectively
        get_extras_from_list();

        //Conversion button gets the user value and Starts an Async task to fetch the data from the API
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationHelper = new CalculationHelper();
                value_to_convert = Double.parseDouble(input_value.getText().toString());  // Gets the user's value
                selected_coin = calculationHelper.getCoinSelected(spinner_coin.getSelectedItemPosition());  // Gets the user's selected coin
                selected_currency = calculationHelper.getCurrencySelected(spinner_currency.getSelectedItemPosition()); // Gets the user's selected currency
                url = buildUrl(selected_coin,selected_currency);  //Calls a method to build the request Url

                //Calls the Async class
                new GetCurrentData().execute();
            }
        });

        //Saves the input and result to the database to be used in the Recycler view
        add_to_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    index = getSavedIndex();   //Index is used when deleting an item from the database and Recycler view.
                    index++;
                    dbHelper.open();
                    String tempCoin,tempCurrency,tempConvertedValue,tempInputValue,coin_Image_Id,currency_Image_Id;

                    coin_Image_Id = coin_drawable_id[spinner_coin.getSelectedItemPosition()];           //Gets the coin drawable id from the coin array
                    currency_Image_Id = currency_symbols[spinner_coin.getSelectedItemPosition()];                     //gets the currency symbol from the selected array
                    tempCurrency = calculationHelper.getCurrencySelected(spinner_currency.getSelectedItemPosition());
                    tempCoin = calculationHelper.getCoinSelected(spinner_coin.getSelectedItemPosition());
                    tempInputValue = input_value.getText().toString();
                    tempConvertedValue = output.getText().toString();

                    //Makes the insertion into Database
                    dbHelper.insertPair(index,coin_Image_Id,tempCoin,currency_Image_Id,tempCurrency,tempInputValue,tempConvertedValue);
                    dbHelper.close();
                    Log.d("Insertion","Values Inserted");
                    Toast.makeText(ConversionActivity.this, "The Pair has been added to your list", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    e.printStackTrace();
                }
                //Saves the current index
                saveIndexInPref();
            }
        });

        //Starts the Recycler List activity
        goto_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConversionActivity.this,RecyclerList.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void call_initializer() {

        input_value = (EditText)findViewById(R.id.amount);
        output = (TextView)findViewById(R.id.output);
        convert = (Button)findViewById(R.id.convert);
        add_to_list = (Button)findViewById(R.id.btn_add_to_list);
        goto_list = (Button)findViewById(R.id.btn_goto_list);
        spinner_coin = (Spinner)findViewById(R.id.spinner_coin);
        spinner_currency = (Spinner)findViewById(R.id.spinner_currency);
        dbHelper = new DbHelper(this);
        RecyclerlistObject = new RecyclerList();
        calculationHelper = new CalculationHelper();
        currency_symbols = getResources().getStringArray(R.array.currency_symbols);
        coin_drawable_id = getResources().getStringArray(R.array.coin_symbols);
    }

    private void get_extras_from_list() {

        Bundle extras = getIntent().getExtras();
        String first_time_checker = extras.getString("new_user");
        String tempCoinHolder = extras.getString(RecyclerlistObject.coinType);
        String tempCurrHolder = extras.getString(RecyclerlistObject.currencyType);
        String tempInputValue = extras.getString(RecyclerlistObject.inputValueHolder);
        String tempConvertedValue = extras.getString(RecyclerlistObject.convertedValueHolder);

        if (first_time_checker.equals("yes")){       //Default state for first time users
            spinner_coin.setSelection(0);
            spinner_currency.setSelection(0);
            input_value.setText("1");
            output.setText("0.0");

        } else {
            spinner_coin.setSelection(calculationHelper.getCoinSelectedFromText(tempCoinHolder));
            spinner_currency.setSelection(calculationHelper.getCurrencySelectedFromText(tempCurrHolder));
            input_value.setText(tempInputValue);
            output.setText(tempConvertedValue);
        }
    }

    private void saveIndexInPref() {
        SharedPreferences preferences = getSharedPreferences(MY_PREF, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(INDEX_VALUE,index);
        editor.apply();
        Log.d("Saved Index", "" + index);
    }

    public int getSavedIndex() {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF,0);
        int savedIndex = sharedPreferences.getInt(INDEX_VALUE,-1);
        Log.d("Returned Index:", "" + savedIndex);
        return savedIndex;
    }

     //Method builds the url request from the user entered values.
     private String buildUrl(String selected_coin,String selected_currency){
         String url = "https://min-api.cryptocompare.com/data/price?fsym=" + selected_coin + "&tsyms=" + selected_currency;
         Log.e(TAG, "Url is:  " + url);
        return url;
    }


    //Async task class to get json by making HTTP call
    private class GetCurrentData extends AsyncTask<Void, Void, Void> {
         String price;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ConversionActivity.this);
            pDialog.setMessage("Converting...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler serviceHandler = new HttpHandler();
            String jsonStr = serviceHandler.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    String returnedValue;
                        JSONObject myObject = new JSONObject(jsonStr);
                        returnedValue = myObject.getString(selected_currency);
                        price = calculationHelper.convert(value_to_convert,returnedValue);      //Converts the value.

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ConversionActivity.this);
                        builder.setIcon(getResources().getDrawable(R.drawable.icon));
                        builder.setTitle("Sorry");
                        builder.setMessage("There was an error converting to your currency." +
                                "Please check your internet Connection and try again");
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();

                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            //Sets Output TextView to converted value
            output.setText(price);
        }

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
                Intent intent2 = new Intent(this,About.class);
                startActivity(intent2);
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
