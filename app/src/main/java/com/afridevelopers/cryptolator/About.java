package com.afridevelopers.cryptolator;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class About extends AppCompatActivity {
    ImageButton fb,ig,twitter,youtube,website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        fb = (ImageButton)findViewById(R.id.facebook);
        ig = (ImageButton)findViewById(R.id.instagram);
        twitter = (ImageButton)findViewById(R.id.twitter);
        youtube = (ImageButton)findViewById(R.id.youtube);
        website = (ImageButton)findViewById(R.id.website);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/risingodegua"));
                startActivity(intent);
            }
        });
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/rising_developer"));
                startActivity(intent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/risenwealthy"));
                startActivity(intent);
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/rising_developer"));
                startActivity(intent);
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.afridevelopers.com"));
                startActivity(intent);
            }
        });
    }
}
