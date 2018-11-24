package com.example.ns.report7_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
                                                                                                    //final static String[] s = {"월요일","화요일","수요일","목요일","금요일"};   기본리스트
    ArrayList<String> alist1 = new ArrayList<String>();
    ArrayList<String> alist2 = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    ActionMode mActionmode = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv = findViewById(R.id.listview);
                                                                                                                //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, s); 기본리스트
        final EditText et = findViewById(R.id.editText);
        final EditText et2 = findViewById(R.id.editText2);
        ImageButton ibt = findViewById(R.id.imageButton);
                                                                                                       //텍스트뷰1 adapter = new ArrayAdapter<String>(this, R.layout.clayout1, R.id.tv_list, alist);
        adapter = new clayoutAdapter(this, R.layout.clayout2, alist1, alist2);
        lv.setAdapter(adapter);

        ibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alist1.add(et.getText().toString());
                et.setText("");
                alist2.add(et2.getText().toString());
                et2.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "학번: "+alist1.get(position)+" 이름: "+alist2.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });

       // registerForContextMenu(lv);  //context floatmenu를 하기위한 코드

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                if(mActionmode != null) {
                    return false;
                };
                mActionmode =  MainActivity.this.startActionMode(mActionMenu);
                v.setSelected(true);
                return true;
            }
        });
        final ArrayList<Integer> check = new ArrayList<Integer>();
        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                Toast.makeText(getApplicationContext(), lv.getCheckedItemCount()+"selected",Toast.LENGTH_SHORT).show();


            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.actionmenu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()){
                    case R.id.act_delete :

                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        return true;
                    default :
                        return false;
                }
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionmode = null;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.floatmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //AdapterView의 어느부분을 선택한건지 알아야 그해당View를 지울수있음
        switch (item.getItemId()){
            case R.id.delete :
                alist1.remove(info.position);
                alist2.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            default :
                return super.onContextItemSelected(item);
        }
    }

    private ActionMode.Callback mActionMenu = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.actionmenu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionmode = null;

        }
    };
}
