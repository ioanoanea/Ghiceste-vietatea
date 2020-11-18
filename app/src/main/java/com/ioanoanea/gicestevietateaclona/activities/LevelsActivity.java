package com.ioanoanea.gicestevietateaclona.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.adapters.LevelAdapter;

public class LevelsActivity extends AppCompatActivity {

    private Button back;
    private RecyclerView levelsList;
    private LevelAdapter levelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        setViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LevelsActivity.super.onBackPressed();
            }
        });

        levelAdapter = new LevelAdapter(this, ((MainActivity) MainActivity.activity).levels);
        levelsList.setLayoutManager(new GridLayoutManager(LevelsActivity.this, 4));
        levelsList.setAdapter(levelAdapter);

    }

    private void setViews(){
        back = findViewById(R.id.back);
        levelsList = findViewById(R.id.levels_list);
    }
}