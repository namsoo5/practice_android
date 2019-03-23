package com.example.ns.prac_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = findViewById(R.id.button);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //컨텍스트 , 방향, 아이템보이는방향(아래서위로 올라오는지 위에서아래로올라오는지...)
        recyclerView.setLayoutManager(layoutManager);

        final SingerAdapter adapter = new SingerAdapter(getApplicationContext());

        adapter.addItem(new SingerItem("레드벨벳", "010-1234-4567"));
        adapter.addItem(new SingerItem("트와이스", "010-4567-4567"));
        adapter.addItem(new SingerItem("아이유", "010-7896-4567"));
        recyclerView.setAdapter(adapter);

        //사용자가만든 이벤트처리함수
        adapter.setOnItemClickListener(new SingerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SingerAdapter.ViewHolder holder, View view, int position) {
                SingerItem item = adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "아이템 선택됨: "+item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
