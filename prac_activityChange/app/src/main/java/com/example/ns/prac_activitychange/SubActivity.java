package com.example.ns.prac_activitychange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        TextView nText = (TextView) findViewById(R.id.nameText);
        Intent intent = getIntent();
        nText.setText(intent.getStringExtra("nText").toString());
    }
}
