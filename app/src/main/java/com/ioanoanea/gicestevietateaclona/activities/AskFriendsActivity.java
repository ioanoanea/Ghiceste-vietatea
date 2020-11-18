package com.ioanoanea.gicestevietateaclona.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.objects.InformationManager;

public class AskFriendsActivity extends AppCompatActivity {

    private TextView close;
    private ConstraintLayout whatsAppMessage;
    private ConstraintLayout messengerMessage;
    private InformationManager informationManager;
    private String url;
    int MESSENGER = 1;
    int WHATSAPP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_friends);

        informationManager = new InformationManager(this);

        setViews();

        if(informationManager.getLevel() == 1)
            url = getResources().getString(R.string.Level1);
        else if(informationManager.getLevel() == 2)
            url = getResources().getString(R.string.Level2);
        else url = getResources().getString(R.string.Level3);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AskFriendsActivity.super.onBackPressed();
            }
        });

        whatsAppMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("Hi, I am stuck on the level. Can you help me please? " + url, WHATSAPP);
            }
        });

        messengerMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("Hi, I am stuck on the level. Can you help me please? " + url, MESSENGER);
            }
        });
    }

    private void setViews(){
        close = findViewById(R.id.close);
        whatsAppMessage = findViewById(R.id.message_whatsapp);
        messengerMessage = findViewById(R.id.message_messenger);
    }

    private void sendMessage(String message, int app){
        // Creating new intent
        Intent intent
                = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        if(app == MESSENGER){
            intent.setPackage("com.facebook.orca");

            // Checking whether Messenger is installed or not
            if (intent.resolveActivity(
                    getPackageManager())
                    == null) {
                Toast.makeText(
                        this,
                        "Please install Messenger first.",
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        } else {
            intent.setPackage("com.whatsapp");

            // Checking whether WhatsApp is installed or not
            if (intent.resolveActivity(
                    getPackageManager())
                    == null) {
                Toast.makeText(
                        this,
                        "Please install whatsapp first.",
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        }

        // Give your message here
        intent.putExtra(
                Intent.EXTRA_TEXT,
                message);

        // Starting Whatsapp
        startActivity(intent);
    }

}