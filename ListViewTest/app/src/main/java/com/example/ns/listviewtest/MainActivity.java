package com.example.ns.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String[] FRUITS = {"Apple", "Banana", "Cherry", "Durian"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvFruits = findViewById(R.id.lvfurits);
        //2번째인자로 R.id.___하면 내가만든리소스에넣는것
     //   final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FRUITS);
        //리소스로 받아오는경우 어뎁터만드는 방식이 달라짐
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fruits, android.R.layout.simple_list_item_1);

        lvFruits.setAdapter(adapter);  //adapter와 adapterview의 연결

        lvFruits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //parent는 listview자체, view는 눌려진뷰, position은 눌려진view의 위치  array받아서오므로 id와 position은같은의미
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplication(), ((TextView)view).getText() , Toast.LENGTH_SHORT).show();
                //view는 업케스팅된상태이므로 gettext가없음 다운캐스팅해서받아야함

                // Toast.makeText(getApplication(), FRUITS[position] , Toast.LENGTH_SHORT).show();
                //string이 안보이게 걸리면 찾기힘듬 일반적으로 아래방법을 많이씀
               // Toast.makeText(getApplication(), adapter.getItem(position) , Toast.LENGTH_SHORT).show();

                //리소스에있는것을 직접읽어오는경우
                String[] safruits = getResources().getStringArray(R.array.fruits);  //위의 string[] FRUITS랑 같아짐
                Toast.makeText(getApplication(), safruits[position], Toast.LENGTH_SHORT).show();


            }
        });//아이템누를때의 핸들러
    }
}
