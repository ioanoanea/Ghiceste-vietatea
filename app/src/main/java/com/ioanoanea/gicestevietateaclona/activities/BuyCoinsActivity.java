package com.ioanoanea.gicestevietateaclona.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.objects.InformationManager;

import java.util.ArrayList;
import java.util.List;

import static com.ioanoanea.gicestevietateaclona.activities.MainActivity.activity;


public class BuyCoinsActivity extends AppCompatActivity {

    private Button back;
    private Button buy;
    private InformationManager informationManager;

    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                    && purchases != null) {
                for (Purchase purchase : purchases) {
                    handlePurchase(purchase);
                }
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                // Handle an error caused by a user cancelling the purchase flow.
            } else {
                // Handle any other error codes.
            }

        }
    };

    private BillingClient billingClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_coins);

        informationManager = new InformationManager(this);

        setViews();

        setupBillingClient();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuyCoinsActivity.super.onBackPressed();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(billingClient.isReady()){
                    List<String> skuList = new ArrayList<> ();
                    skuList.add("coins500");
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    billingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                            if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                                //Toast.makeText(BuyCoinsActivity.this, "Loading product...", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(BuyCoinsActivity.this, String.valueOf(skuDetailsList.get(0).getTitle() + " " + skuDetailsList.get(0).getPrice()), Toast.LENGTH_SHORT).show();
                                try{
                                    // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
                                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetailsList.get(0))
                                            .build();
                                    int responseCode = billingClient.launchBillingFlow(BuyCoinsActivity.this, billingFlowParams).getResponseCode();
                                } catch (Exception e) {
                                    Toast.makeText(BuyCoinsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }

                            } else Toast.makeText(BuyCoinsActivity.this, "" + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else Toast.makeText(BuyCoinsActivity.this, "Billing client is not ready!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setViews(){
        back = findViewById(R.id.back);
        buy = findViewById(R.id.buy);
    }

    private void setupBillingClient(){
        // set billing client
        billingClient = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        // Establish a connection to Google Play
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    //Toast.makeText(BuyCoinsActivity.this, "conection finished", Toast.LENGTH_SHORT).show();
                    if(billingClient.isReady()){
                        final List<String> skuList = new ArrayList<> ();
                        skuList.add("coins500");
                        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                        billingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
                            @Override
                            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> skuDetailsList) {
                                buy.setText(String.valueOf(skuDetailsList.get(0).getPrice()));
                            }
                        });
                    }
                } else Toast.makeText(BuyCoinsActivity.this, "" + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                billingClient.startConnection(this);
                //Toast.makeText(BuyCoinsActivity.this, "disconected", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void handlePurchase(Purchase purchase) {
        // Purchase retrieved from BillingClient#queryPurchases or your PurchasesUpdatedListener.
        //Purchase purchase = ...;

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                    informationManager.addCoins(500);
                    Toast.makeText(BuyCoinsActivity.this, "You've got 500 coins.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }


}