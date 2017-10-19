package com.afridevelopers.cryptolator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.Preference;
import android.support.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String MY_PREF = "my_preference";
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private EditText input_value;
    private TextView output;
    private Spinner spinner_coin,spinner_currency;
    private Button calculate;
    private String selected_coin,selected_currency;
    private double value_to_convert;
    private static String url = "";
    private CalculationHelper calculationHelper;
    private final String INPUT = "input", OUTPUT = "output", COIN = "coin", CURRENCY = "currency";   //Keys for saving in preference

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Saves the last entered value and result.
        SharedPreferences preferences = getSharedPreferences(MY_PREF, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(INPUT,input_value.getText().toString());
        editor.putString(OUTPUT,output.getText().toString());
        editor.putInt(COIN,spinner_coin.getSelectedItemPosition());
        editor.putInt(CURRENCY,spinner_currency.getSelectedItemPosition());
        editor.apply();

        Log.e(TAG, "Saved in Preference input: " + input_value.getText().toString() +
                " output: " + output.getText().toString() + " coin: " +
                calculationHelper.getCoinSelected(spinner_coin.getSelectedItemPosition()) +
                " currency: " + calculationHelper.getCurrencySelected(spinner_currency.getSelectedItemPosition()) );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        input_value = (EditText)findViewById(R.id.amount);
        output = (TextView)findViewById(R.id.output);
        calculate = (Button)findViewById(R.id.btn_calculate);
        spinner_coin = (Spinner)findViewById(R.id.spinner_coin);
        spinner_currency = (Spinner)findViewById(R.id.spinner_currency);

        //Gets the saved preference to enable consistency
        SharedPreferences preferences = getSharedPreferences(MY_PREF,0);
        input_value.setText(preferences.getString(INPUT,"0.0"));
        output.setText(preferences.getString(OUTPUT,"0.0"));
        spinner_coin.setSelection(preferences.getInt(COIN,0));
        spinner_currency.setSelection(preferences.getInt(CURRENCY,0));

        //Conversion button gets the user value and Starts the Async class to fetch the data from the API
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationHelper = new CalculationHelper();

                value_to_convert = Double.parseDouble(input_value.getText().toString());  // Gets the entered string and cast it to a double type
                selected_coin = calculationHelper.getCoinSelected(spinner_coin.getSelectedItemPosition());
                selected_currency = calculationHelper.getCurrencySelected(spinner_currency.getSelectedItemPosition());
                url = buildUrl(selected_coin,selected_currency);  //Calls the method to build the request Url

                new GetContacts().execute();
            }
        });
    }



    /**
     * Method to build the url request from the user entered value
     */
    private String buildUrl(String selected_coin,String selected_currency){

        String url = "https://min-api.cryptocompare.com/data/price?fsym=" + selected_coin + "&tsyms=" + selected_currency;
        Log.e(TAG, "Url is:  " + url);
        return url;
    }



    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
         String price;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Converting...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler serviceHandler = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = serviceHandler.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    String currency;
                        JSONObject myObject = new JSONObject(jsonStr);
                        currency = myObject.getString(selected_currency);
                        Log.e(TAG, "Returned value is " + selected_currency + ": " + currency);


                   price = calculationHelper.convert(value_to_convert,currency); //Converts the value


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setIcon(getResources().getDrawable(R.drawable.facebook));
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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            // Updating converted value to the output textview
            output.setText(price);
            Log.e(TAG, "Returned Converted value: " + price);

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
