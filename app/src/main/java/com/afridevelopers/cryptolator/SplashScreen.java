package com.afridevelopers.cryptolator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    private ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        splashImage = (ImageView)findViewById(R.id.splashImage);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        splashImage.startAnimation(animation);

        final Intent intent = new Intent(this, MainActivity.class);

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {

                    sleep(4000);

                }catch (Exception e){
                   e.printStackTrace();
                }finally {

                    startActivity(intent);
                    SplashScreen.this.finish();
                }

            }
        };
        timer.start();

    }
}
