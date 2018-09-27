package com.example.asus.testmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.asus.testmatch.R;

public class LevelActivity extends AppCompatActivity {

    private Button btnEasy, btnMedium, btnHard;
    private String extraFromCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        setupWidgets();

        setExtraFromCategory();

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, GameActivity.class);
                intent.putExtra(getString(R.string.level), extraFromCategory+":"+getString(R.string.level_easy));
                startActivity(intent);
                finish();
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, GameActivity.class);
                intent.putExtra(getString(R.string.level), extraFromCategory+":"+getString(R.string.level_medium));
                startActivity(intent);
                finish();
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, GameActivity.class);
                intent.putExtra(getString(R.string.level), extraFromCategory+":"+getString(R.string.level_hard));
                startActivity(intent);
                finish();
            }
        });
    }

    private void setExtraFromCategory(){
        Bundle bundle = getIntent().getExtras();
        String selectedCategory = bundle.getString(getString(R.string.category));
        switch (selectedCategory){
            case "nature": extraFromCategory = getString(R.string.category_nature); break;
            case "dogs": extraFromCategory = getString(R.string.category_dogs); break;
            case "cars": extraFromCategory = getString(R.string.category_cars); break;
            default: extraFromCategory = "colors";
        }
    }

    private void setupWidgets(){
        btnEasy = (Button)findViewById(R.id.btnEasy);
        btnMedium = (Button)findViewById(R.id.btnMedium);
        btnHard = (Button)findViewById(R.id.btnHard);
    }
}
