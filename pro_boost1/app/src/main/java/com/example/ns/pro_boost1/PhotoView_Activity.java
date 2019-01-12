package com.example.ns.pro_boost1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PhotoView_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_);

        setTitle("사진보기");

        ImageView iv = findViewById(R.id.photoview_imageView);

        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("url"))
                .into(iv);
    }
}
