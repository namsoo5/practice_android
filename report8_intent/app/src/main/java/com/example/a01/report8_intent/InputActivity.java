package com.example.a01.report8_intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        final EditText et = findViewById(R.id.et_input);
        Button bt = findViewById(R.id.bt_input_add);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", et.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
