package com.example.ns.listviewupdatetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //동적인추가를위해 리스트사용
    ArrayList<String> malitems;
    ArrayAdapter<String> madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvItems = findViewById(R.id.lvItems);   //어뎁타뷰만듬
        malitems = new ArrayList<String>();//데이타만듬
        madapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, malitems);  //어뎁터만듬, 어뎁터와 데이터연결
        lvItems.setAdapter(madapter);  // 어뎁터랑 어뎁터뷰연결

        Button btadd = findViewById(R.id.button);
        final EditText etAdd = findViewById(R.id.editText);
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                malitems.add(etAdd.getText().toString());
                madapter.notifyDataSetChanged();  //데이터갱신
                etAdd.setText("");
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), alitems.get(position), Toast.LENGTH_SHORT ).show();
                Toast.makeText(getApplicationContext(), madapter.getItem(position), Toast.LENGTH_SHORT ).show();  //더일반적인방법
            }
        });

        registerForContextMenu(lvItems);  //리스트뷰에 컨텍스트메뉴 달기
    }

    //******컨텍스트메뉴기능추가*******
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cmenu_activity_main_lvitems, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();  //메뉴로부터 어느뷰가 눌린건지 위치정보를 찾을수있음
        switch (item.getItemId()){
            case R.id.cmenuDelete :
                malitems.remove(info.position);
                madapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

}
