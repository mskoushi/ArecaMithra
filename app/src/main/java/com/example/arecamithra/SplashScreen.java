package com.example.arecamithra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN=5000;
Animation topAnim,bottomAnim;
ImageView logo_image;
TextView slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.app_background, getTheme()));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        logo_image = findViewById(R.id.logo_image);

        slogan = findViewById(R.id.slogan);

        String text = "ArecaMithra";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.app_green, getTheme()));
        spannableString.setSpan(colorSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        slogan.setText(spannableString);


        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anomation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//Set animation to elements
        logo_image.setAnimation(topAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManagerUser sessionManagerUser=new SessionManagerUser(SplashScreen.this);

                HashMap<String,String> userDetails=sessionManagerUser.getUserDetailFromSession();
                String fullName=userDetails.get(SessionManagerUser.KEY_FULLNAME);
                Intent s;
                if(sessionManagerUser.checkLogin())
                 s=new Intent(SplashScreen.this,MainActivity.class);
                else {
                    s=new Intent(SplashScreen.this,Login.class);
                }
                startActivity(s);
                finish();
            }
        },SPLASH_SCREEN);

    }
}