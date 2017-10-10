package com.afridevelopers.cryptolator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private EditText input_value;
    private TextView output;
    private Spinner spinner_coin,spinner_currency;
    private Button calculate;
    private String selected_coin,selected_currency,converted_value;
    private double value_to_convert;
    private static String url = "";
    private CalculationHelper calculationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_value = (EditText)findViewById(R.id.amount);
        output = (TextView)findViewById(R.id.output);
        calculate = (Button)findViewById(R.id.btn_calculate);
        spinner_coin = (Spinner)findViewById(R.id.spinner_coin);
        spinner_currency = (Spinner)findViewById(R.id.spinner_currency);



        //Calculates button gets the user value and Starts the Async class to fetch the data from the API
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculationHelper = new CalculationHelper();
//
                  value_to_convert = Double.parseDouble(input_value.getText().toString());  // Gets the entered string and cast it to a double type
//                selected_coin = calculationHelper.getCoinSelected(spinner_coin.getSelectedItemPosition());
//                selected_currency = calculationHelper.getCurrencySelected(spinner_currency.getSelectedItemPosition());
//                url = buildUrl(selected_coin,selected_currency);  //Calls the method to build the request Url

                new GetContacts().execute();
            }
        });
    }



    /**
     * Method to build the url request from the user entered value
     */
    private String buildUrl(String selected_coin,String selected_currency){

        String url = "https//min-api.cryptocompare.com/data/price?fsym=" + selected_coin + "&tsyms=" + selected_currency;
        Log.e(TAG, "Url is:  " + url);
        return url;
    }



    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

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
//            HttpHandler serviceHandler = new HttpHandler();

            // Making a request to url and getting response
//            String jsonStr = serviceHandler.makeServiceCall(url);
             JSONArray myArray;
             String jsonStr = "[{\"NGN\":1686045.39}]";
            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    String currency = "";
                    myArray = new JSONArray(jsonStr);
                    for (int i = 0; i < myArray.length(); i++){

                        JSONObject myObject = myArray.getJSONObject(i);
                        currency = myObject.getString("NGN");
                        Log.e(TAG, "Returned price is: NGN " + currency);
                    }

                    converted_value = calculationHelper.convert(value_to_convert,currency); //Converts the value


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
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

            output.setText(converted_value);

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
