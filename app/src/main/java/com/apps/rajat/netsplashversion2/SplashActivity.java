package com.apps.rajat.netsplashversion2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    LinearLayout l1,l2, fullL;
    Animation uptodown,downtoup, myanim;

    TextView tvtitle;
    Typeface netSplashFont;
    ImageView ivglobe;
    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if(sharedPref.loadNightModeState()==true){
            setTheme(R.style.splashScreenNightMode);
        }else {
            setTheme(R.style.splashScreenNormalMode);
        }
        sharedPref = new SharedPref(this);

        if(sharedPref.loadNightModeState()==true){
            setTheme(R.style.splashScreenNightMode);
        }else {
            setTheme(R.style.splashScreenNormalMode);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = getSharedPreferences("prefs" , MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firststart", true);


        l1 = (LinearLayout) findViewById(R.id.logos);
        l2 = (LinearLayout) findViewById(R.id.texts);
        fullL = (LinearLayout) findViewById(R.id.fullFrame);
        ivglobe = (ImageView) findViewById(R.id.imageglobe);
        tvtitle = (TextView) findViewById(R.id.title1);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        netSplashFont = Typeface.createFromAsset(this.getAssets(), "fonts/NetSplashFont.ttf");
        tvtitle.setTypeface(netSplashFont);
        //Animaton of fullL
        if (Build.VERSION.SDK_INT>20) {
            fullL.postDelayed(new Runnable() {
                @Override
                public void run() {
                    revealeffectframe();
                }
            }, 100);
        }else{
            fullL.setVisibility(View.VISIBLE);
        }

        l2.setAnimation(downtoup);
        l1.setAnimation(uptodown);


        if (firstStart == true) {
            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
            showStartIntro();
        }else if (firstStart == false){
        final Intent main1 = new Intent(this, MainRoot.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(main1);
                    finish();

                }
            }
        };
        timer.start();}


    }

    private void showStartIntro(){

        final Intent main2 = new Intent(this, MainActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(main2);
                    finish();

                }
            }
        };
        timer.start();


        SharedPreferences preferences = getSharedPreferences( "prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firststart", false);
        editor.apply();
    }

    void revealeffectframe(){
        if (Build.VERSION.SDK_INT>20){
            int cx = fullL.getMeasuredWidth()/2;
            int cy = fullL.getMeasuredHeight()/2;
            int radius = Math.max(fullL.getWidth(),fullL.getHeight());
            android.animation.Animator a = ViewAnimationUtils.createCircularReveal(fullL,cx,cy,0,radius);
            a.setDuration(1000);
            fullL.setVisibility(View.VISIBLE);
            a.start();

        }
    }


}
