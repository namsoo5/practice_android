package com.example.ns.pro_exchangerate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class InputForm extends AppCompatActivity {
    String exchange;
    DbOpenHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);

        final EditText etmemo = findViewById(R.id.et_input_memo);
        final EditText etmoney = findViewById(R.id.et_input_money);

        final Spinner spinner = findViewById(R.id.spinner_input_exchange);

        //db생성
        /*db = new DbOpenHelper();
        db.open();*/


        Button btinputadd = findViewById(R.id.bt_input_add);
        btinputadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                EditText etcategory = findViewById(R.id.et_input_category);
                final int catenum = new category_ex(etcategory.getText().toString()).getCategoryId();
                //카테고리 입력값에따른 id 전환

                //intent.putExtra("exchange", exchange);
                intent.putExtra("check", 1);
              //  db.insertColumn(catenum, etmemo.getText().toString(), etmoney.getText().toString(), spinner.getSelectedItem().toString(), );  환전값미지정
                MainActivity.Infoarraylist.add(new MainActivity.Info(catenum,etmemo.getText().toString(),etmoney.getText().toString()));
                startActivity(intent);
                finish();
            }
        });




    }
}
