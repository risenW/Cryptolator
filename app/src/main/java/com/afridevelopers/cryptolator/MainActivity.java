package com.afridevelopers.cryptolator;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView convertedValue;
    private EditText inputValue;
    private Spinner coinSelected, currencySelected;
    private ImageButton launchCurrentData,swap,reload,share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convertedValue = (TextView)findViewById(R.id.convertedValue);
        inputValue = (EditText)findViewById(R.id.inputValue);
        coinSelected = (Spinner)findViewById(R.id.coinSpinner);
        currencySelected = (Spinner)findViewById(R.id.currencySpinner);
        launchCurrentData = (ImageButton)findViewById(R.id.launchCurrentData);
        swap = (ImageButton)findViewById(R.id.swapBtn);
        reload = (ImageButton)findViewById(R.id.reloadBtn);
        share = (ImageButton)findViewById(R.id.shareBtn);
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
