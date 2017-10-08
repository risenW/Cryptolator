package com.afridevelopers.cryptolator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class LiveTrading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_trading);
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
