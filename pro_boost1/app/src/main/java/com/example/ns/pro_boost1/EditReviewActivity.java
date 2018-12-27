package com.example.ns.pro_boost1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class EditReviewActivity extends AppCompatActivity {
    ImageView im_editreview_age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);

        TextView tv_editreview_title = findViewById(R.id.tv_editreview_title);
        im_editreview_age = findViewById(R.id.im_editreview_age);

        tv_editreview_title.setText(getIntent().getStringExtra("title"));
        selectImage(getIntent().getIntExtra("age", 0));

        Button bt_save = findViewById(R.id.bt_editreview_save);
        Button bt_cancel = findViewById(R.id.bt_editreview_cancel);
        final EditText et = findViewById(R.id.et_editreview);
        final RatingBar ratingBar = findViewById(R.id.ratingbar_editreview);


        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("review", et.getText().toString());
                intent.putExtra("rating", ratingBar.getRating());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    void selectImage(int age){
        switch (age){
            case 0:
                im_editreview_age.setImageResource(R.drawable.ic_all);
                break;
            case 12:
                im_editreview_age.setImageResource(R.drawable.ic_12);
                break;
            case 15:
                im_editreview_age.setImageResource(R.drawable.ic_15);
                break;
            case 19:
                im_editreview_age.setImageResource(R.drawable.ic_19);
                break;
            default :
                im_editreview_age.setImageResource(R.drawable.ic_all);
                break;
        }
    }

}
