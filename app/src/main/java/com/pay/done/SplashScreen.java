package com.pay.done;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        VideoView videoView=findViewById(R.id.videoView);
        String vPath="android.resource://"+getPackageName()+"/raw/logo";
        Uri videoURI=Uri.parse(vPath);
        videoView.setVideoURI(videoURI);
        videoView.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                SharedPreferences pref=getSharedPreferences("login",MODE_PRIVATE);
//                Boolean check=pref.getBoolean("flag", false);
                Intent iNext;
//                if (check){
                    iNext=new Intent(SplashScreen.this, MainActivity.class);
//                }
//                else {
//                    iNext=new Intent(  SplashScreen.this, LoginPage.class) ;
//                }
                startActivity(iNext);
                finish();

            }
        }, 4000);

    }
}