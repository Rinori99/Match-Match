package com.example.asus.testmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.asus.testmatch.R;

public class CategoriesActivity extends AppCompatActivity {

    private Button btnNature, btnDogs, btnCars;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        setupWidgets();

        btnNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, LevelActivity.class);
                intent.putExtra(getString(R.string.category), getString(R.string.category_nature));
                startActivity(intent);
                finish();
            }
        });

        btnDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, LevelActivity.class);
                intent.putExtra(getString(R.string.category), getString(R.string.category_dogs));
                startActivity(intent);
                finish();
            }
        });

        btnCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, LevelActivity.class);
                intent.putExtra(getString(R.string.category), getString(R.string.category_cars));
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupWidgets(){
        btnNature = (Button)findViewById(R.id.btnNature);
        btnDogs = (Button)findViewById(R.id.btnDogs);
        btnCars = (Button)findViewById(R.id.btnCars);
    }
}
