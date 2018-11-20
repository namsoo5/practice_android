package com.example.ns.pro_exchangerate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class moneyset extends AppCompatActivity{
    moenysetAdapter adapter;
    ArrayList<String> alitems2;
    ArrayList<String> alitems1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moneyform);

         alitems1 = new ArrayList<String>();
         alitems2 = new ArrayList<String>();
        ListView liview = findViewById(R.id.listview);
         adapter = new moenysetAdapter(this, R.layout.moneylistview,  alitems1, alitems2);

        liview.setAdapter(adapter);

        Button bt = findViewById(R.id.bt_form_add);
        final EditText et1 = findViewById(R.id.et_form_kindsmoney);
        final EditText et2 = findViewById(R.id.et_form_exchange);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alitems1.add(et1.getText().toString());
                alitems2.add(et2.getText().toString());

                adapter.notifyDataSetChanged();
                et1.setText("");
                et2.setText("");


            }
        });

    }
}
