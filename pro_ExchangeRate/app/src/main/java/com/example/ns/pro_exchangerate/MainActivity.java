package com.example.ns.pro_exchangerate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter MyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



        ArrayList<Info> Infoarraylist = new ArrayList<>();
        Infoarraylist.add(new Info(1,"돈돈","6000"));
        Infoarraylist.add(new Info(2,"돈돈","15000"));
        MyAdapter = new Content(Infoarraylist);

        mRecyclerView.setAdapter(MyAdapter);
    }



    public class Info{
        public int category;
        public String memo;
        public String money;
        public Info(int category, String memo, String money){
            this.category = category;
            if(this.category==1){
                this.category=R.drawable.meal;
            }else if(this.category==2) {
                this.category=R.drawable.bear;
            }
            this.memo = memo;
            this.money = money;
        }
    }
}
