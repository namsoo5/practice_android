package com.example.ns.pro_exchangerate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter MyAdapter;
    static ArrayList<Info> Infoarraylist ; //카드뷰형식 리스트저장
    DbOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent gintent = getIntent();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        int check = gintent.getIntExtra("check",0);


        ////////db에서 데이터불러와서 카드뷰데이터넣어주기//////
        //db생성
        db = new DbOpenHelper(this);
        db.open();
        db.create();

////////카드뷰에 데이터//////////
        Infoarraylist = db.getArrayList();
        MyAdapter = new Content(Infoarraylist);
        mRecyclerView.setAdapter(MyAdapter);

        if(check==1) {
           /* getcate = gintent.getIntExtra("categoryid", -1);
            getmemo = gintent.getStringExtra("memo");
            getmoney = gintent.getStringExtra("money");
            Infoarraylist.add(new Info(getcate, getmemo, getmoney));*/
            MyAdapter.notifyDataSetChanged();
            check = 0;
        }


        /////////////////////////////카드뷰 생성//////////////////

        Button btadd = findViewById(R.id.btadd);    //내역추가버튼
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InputForm.class );
                startActivity(intent);
            }
        });

        /////////////////////////////

        Button btset = findViewById(R.id.btset);  //화폐설정
        btset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), moneyset.class );
                startActivity(intent);
            }
        });


    }

    public static class Info{
        public int category;
        public String memo;
        public String money;
        public String changmoney;
        public Info(int category){
            this.category = category;
            if(this.category == 1){
                this.category=R.drawable.meal;
            }else if(this.category==2) {
                this.category=R.drawable.bear;
            }else
                this.category=R.drawable.x;
        }
        /*
        public Info(int category, String memo, String money){
            this.category = category;
            if(this.category == 1){
                this.category=R.drawable.meal;
            }else if(this.category==2) {
                this.category=R.drawable.bear;
            }else
                this.category=R.drawable.x;
            this.memo = memo;
            this.money = money;
        }*/
    }
}
