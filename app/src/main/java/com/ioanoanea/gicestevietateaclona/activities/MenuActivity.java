package com.ioanoanea.gicestevietateaclona.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.objects.InformationManager;

public class MenuActivity extends AppCompatActivity {

    private TextView closeMenu;
    private TextView getCoins;
    private TextView sound;
    private TextView share;
    private InformationManager informationManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setViews();

        informationManager = new InformationManager(this);

        if(informationManager.isSoundOn()){
            sound.setText(Html.fromHtml("Sounds <b><u>On</u><b>"));
        } else {
            sound.setText(Html.fromHtml("Sounds <b><u>Off</u><b>"));
        }

        closeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity.super.onBackPressed();
            }
        });

        getCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, CoinsActivity.class));
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(informationManager.isSoundOn()) {
                    informationManager.setSound(informationManager.OFF);
                    sound.setText(Html.fromHtml("Sounds <b><u>Off</u><b>"));
                } else {
                    informationManager.setSound(informationManager.ON);
                    sound.setText(Html.fromHtml("Sounds <b><u>On</u><b>"));
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();
            }
        });
    }

    /** Initializing all views */
    private void setViews(){
        closeMenu = findViewById(R.id.close_menu);
        getCoins = findViewById(R.id.get_coins);
        sound = findViewById(R.id.sound);
        share = findViewById(R.id.share);
    }

    private void shareApp(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = getResources().getString(R.string.AppLink);
        String shareSub = "Your subject here";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}