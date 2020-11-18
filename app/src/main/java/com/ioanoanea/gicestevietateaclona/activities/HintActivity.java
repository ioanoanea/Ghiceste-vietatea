package com.ioanoanea.gicestevietateaclona.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.objects.InformationManager;

public class HintActivity extends AppCompatActivity {

    private TextView close;
    private ConstraintLayout exposeLetter;
    private ConstraintLayout removeLetters;
    private ConstraintLayout solveQuestion;
    private InformationManager informationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        informationManager = new InformationManager(this);

        setViews();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HintActivity.super.onBackPressed();
            }
        });

        exposeLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(informationManager.getCoins() >= 75){
                    ((MainActivity) MainActivity.activity).exposeLetter();
                    HintActivity.this.finish();
                    ((MainActivity) MainActivity.activity).removeCoins(75);
                } else Toast.makeText(HintActivity.this, "Not enough coins!", Toast.LENGTH_SHORT).show();
            }
        });

        removeLetters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(informationManager.getCoins() >= 120){
                    ((MainActivity) MainActivity.activity).removeLetters();
                    HintActivity.this.finish();
                    ((MainActivity) MainActivity.activity).removeCoins(120);
                } else Toast.makeText(HintActivity.this, "Not enough coins!", Toast.LENGTH_SHORT).show();
            }
        });

        solveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(informationManager.getCoins() >= 170){
                    ((MainActivity) MainActivity.activity).solveQuestion();
                    HintActivity.this.finish();
                    ((MainActivity) MainActivity.activity).removeCoins(170);
                } else Toast.makeText(HintActivity.this, "Not enough coins!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setViews(){
        close = findViewById(R.id.close);
        exposeLetter = findViewById(R.id.expose_letter);
        removeLetters = findViewById(R.id.remove_letters);
        solveQuestion = findViewById(R.id.solve_question);
    }
}