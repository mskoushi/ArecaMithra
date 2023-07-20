package com.example.arecamithra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class PestsAndManagement extends AppCompatActivity {
CardView anabe,die_back,leaf_spot,mite,bud_rot,red_palm,scale_insect,stem_bleeding;
ImageView back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pests_and_management);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.app_background, getTheme()));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        back_button=findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        anabe=findViewById(R.id.anabe_roga);
        die_back=findViewById(R.id.die_back);
        leaf_spot=findViewById(R.id.leaf_spot);
        mite=findViewById(R.id.mite);
        bud_rot=findViewById(R.id.bud_rot);
        red_palm=findViewById(R.id.red_palm_weevil);
        scale_insect=findViewById(R.id.scale_insect);
        stem_bleeding=findViewById(R.id.stem_bleeding);


        anabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(PestsAndManagement.this,AnabeRoga.class);
                startActivity(a);
            }
        });



        die_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d=new Intent(PestsAndManagement.this,DieBack.class);
                startActivity(d);
            }
        });

        leaf_spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l=new Intent(PestsAndManagement.this,LeafSpot.class);
                startActivity(l);
            }
        });

        mite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(PestsAndManagement.this,Mite.class);
                startActivity(m);
            }
        });

        bud_rot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b=new Intent(PestsAndManagement.this,MunduSiri.class);
                startActivity(b);
            }
        });

        red_palm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r=new Intent(PestsAndManagement.this,RedPalmWeevil.class);
                startActivity(r);
            }
        });

        scale_insect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s=new Intent(PestsAndManagement.this,ScaleInsect.class);
                startActivity(s);
            }
        });

        stem_bleeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s=new Intent(PestsAndManagement.this,Stem_Bleeding.class);
                startActivity(s);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}