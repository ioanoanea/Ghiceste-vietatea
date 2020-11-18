package com.ioanoanea.gicestevietateaclona.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ioanoanea.gicestevietateaclona.R;

public class CoinsActivity extends AppCompatActivity {

    private Button back;
    private Button buyCoins;
    private ConstraintLayout watchAd;
    private TextView textWatchAd;
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);

        setViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoinsActivity.super.onBackPressed();
            }
        });

        textWatchAd.setText(Html.fromHtml("<b>+150</b> Watch video Ad"));

        buyCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoinsActivity.this, BuyCoinsActivity.class));
            }
        });

        // create rewarded Ad
        rewardedAd = new RewardedAd(this, "ca-app-pub-3940256099942544/5224354917");
        // load rewarded Ad
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

        // show rewarded Ad
        watchAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rewardedAd.isLoaded()){
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            // Ad closed.
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            ((MainActivity) MainActivity.activity).addCoins(150);
                        }

                        @Override
                        public void onRewardedAdFailedToShow(AdError adError) {
                            Toast.makeText(CoinsActivity.this, "Ad failed to show!", Toast.LENGTH_SHORT).show();
                        }
                    };
                    rewardedAd.show(CoinsActivity.this,adCallback);
                }
            }
        });

    }

    private void setViews(){
        back = findViewById(R.id.back);
        buyCoins = findViewById(R.id.buy_coins);
        watchAd = findViewById(R.id.watch_ad);
        textWatchAd = findViewById(R.id.watch_ad_text);
    }
}