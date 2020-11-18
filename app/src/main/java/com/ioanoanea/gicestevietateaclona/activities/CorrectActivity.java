package com.ioanoanea.gicestevietateaclona.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.objects.InformationManager;

public class CorrectActivity extends AppCompatActivity {

    private Button buttonContinue;
    private ImageView image;
    private TextView solution;
    private Intent intent;
    private InterstitialAd interstitialAd;
    private InformationManager informationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);

        setViews();
        intent = getIntent();
        informationManager = new InformationManager(this);

        // set ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        // load ad
        interstitialAd.loadAd(new AdRequest.Builder().build());

        image.setImageResource(getImage(informationManager.getLevel() - 1));
        solution.setText(intent.getStringExtra("SOLUTION"));


        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(CorrectActivity.this, MainActivity.class));
                displayAdd();
                informationManager.addCoins(10);
            }
        });
    }

    private void setViews(){
        buttonContinue = findViewById(R.id.button_continue);
        image = findViewById(R.id.image);
        solution = findViewById(R.id.solution);
    }

    private void displayAdd(){
        final Intent intent = new Intent(CorrectActivity.this, MainActivity.class);
        // display ad
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {
                    CorrectActivity.this.finish();
                    startActivity(intent);
                }
            });
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            this.finish();
            startActivity(intent);
        }
    }

    private int getImage(int i){
        if(i == 1)
            return R.drawable.papagal;
        else if(i == 2)
            return R.drawable.vulpe;
        else return R.drawable.girafa;
    }

}