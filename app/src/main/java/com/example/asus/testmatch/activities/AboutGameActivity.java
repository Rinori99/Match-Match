package com.example.asus.testmatch.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.asus.testmatch.R;

public class AboutGameActivity extends AppCompatActivity {

    private RelativeLayout relHomeAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_game);

        relHomeAbout = (RelativeLayout)findViewById(R.id.btnHomeAbout);

        relHomeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
