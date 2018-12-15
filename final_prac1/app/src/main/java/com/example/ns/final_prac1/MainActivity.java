package com.example.ns.final_prac1;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static int REQ_MODIFY_MEMO_CODE = 1;
    ListView lv;
    ArrayList<String> al;
    ArrayAdapter<String> adapter;
    int memoCount=0;
    int listpos=-1;
    static int contextpos=-1;

    final int FILESIZE = 100;
    final int DOWNSPEED = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv = findViewById(R.id.lv);
        al = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewMemoActivity.class);
                intent.putExtra("mainstring", adapter.getItem(position).toString());
                listpos=position;
                startActivityForResult(intent, REQ_MODIFY_MEMO_CODE );
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       // super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.delete_memo:
                al.remove(info.position);
                adapter.notifyDataSetChanged();
                break;
            case R.id.upload_memo:
                contextpos = info.position;
                new downloadTast(new dialogf(), FILESIZE).execute(DOWNSPEED);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public static class dialogf extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder adbuilder = new AlertDialog.Builder(getActivity());
            adbuilder.setView(R.layout.dialog)
                    .setTitle("Uploading the memo\"New Memo"+contextpos+"\"...");
            return adbuilder.create();
        }
    }

    private class downloadTast extends AsyncTask<Integer, Integer, Void>{
        DialogFragment mdialog;
        int mfilesize;
        ProgressBar mprogressbar;
        public downloadTast(DialogFragment mdialog, int mfilesize){
            this.mdialog = mdialog;
            this.mfilesize = mfilesize;
        }
        @Override
        protected Void doInBackground(Integer... params) { //실행
            int downloadspeed = params[0];
            int ramain = mfilesize;

            while(ramain>0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(downloadspeed);
                ramain -= downloadspeed;
            }




            return null;
        }

        @Override
        protected void onPreExecute() {   //초기화
            //super.onPreExecute();
            mdialog.setCancelable(false);
            mdialog.show(getSupportFragmentManager(), "dialog");
            getSupportFragmentManager().executePendingTransactions();

            mprogressbar = mdialog.getDialog().findViewById(R.id.progressBar);
            mprogressbar.setMax(mfilesize);
            mprogressbar.setProgress(0);
        }

        @Override
        protected void onPostExecute(Void aVoid) {   //return
           // super.onPostExecute(aVoid);
            mdialog.dismiss();
            Toast.makeText(getApplicationContext(), "Upload Complete"+mfilesize+"kbyte", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {   //ing
           // super.onProgressUpdate(values);
            mprogressbar.incrementProgressBy(values[0]);
            Log.i("pro", "현상태:"+mprogressbar.getProgress());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.newMemo:
               al.add("New Memo"+memoCount);
               adapter.notifyDataSetChanged();
               memoCount++;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_MODIFY_MEMO_CODE:
                if (resultCode == RESULT_OK) {
                    al.set(listpos, data.getStringExtra("string"));
                    adapter.notifyDataSetChanged();
                }
        }
    }
}
