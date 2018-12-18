package com.example.ns.final_prac4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> al;
    ListView lv;
    ArrayAdapter adapter;
    int listcount =0;
    final int REQ_CODE_NEW=1;
    int listpos;
    static int contextpos;
    final int FILESIZE=100;
    final int UPLOADSPEED=20;
    boolean exit = false; //강제종료변수
    int longclick;
    ActionMode mActionmode =null;
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        al = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listpos = position;
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("mainstring", al.get(position).toString());
                startActivityForResult(intent, REQ_CODE_NEW);
            }
        });

       // registerForContextMenu(lv);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longclick = position;

                if(mActionmode!=null){
                    return false;
                }
                mActionmode = MainActivity.this.startActionMode(Action);
                view.setSelected(true);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_new:
                al.add("new Memo"+listcount);
                adapter.notifyDataSetChanged();
                listcount++;
                break;
            default :
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_CODE_NEW:
                if(resultCode==RESULT_OK){
                    al.set(listpos, data.getStringExtra("substring"));
                    adapter.notifyDataSetChanged();
                }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
       switch (item.getItemId()){
           case R.id.menu_delete:
               al.remove(info.position);
               adapter.notifyDataSetChanged();
               break;
           case R.id.menu_upload:
               contextpos = info.position;
               new UploadTask(new DialogF(), FILESIZE).execute(UPLOADSPEED);
               break;
           default :
               return super.onContextItemSelected(item);
       }
        return true;
    }

    public static class DialogF extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            //return super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder adbuilder = new AlertDialog.Builder(getActivity());
            adbuilder.setView(R.layout.dialog)
                    .setTitle(al.get(contextpos)+" Uploading...");

            return adbuilder.create();
        }
    }

    private class UploadTask extends AsyncTask<Integer, Integer, Void>{

        DialogFragment mdialog;
        int mfilesize;
        ProgressBar mprogressbar;

        public UploadTask(DialogFragment dialog, int filesize){
            this.mdialog = dialog;
            this.mfilesize = filesize;
        }

        @Override
        protected void onPreExecute() {
           // super.onPreExecute();

            mdialog.setCancelable(false);
            mdialog.show(getSupportFragmentManager(), "dialog");
            getSupportFragmentManager().executePendingTransactions();

            mprogressbar = mdialog.getDialog().findViewById(R.id.progressBar);
            mprogressbar.setMax(mfilesize);
            mprogressbar.setProgress(0);

        }

        @Override
        protected Void doInBackground(Integer... params) {

            int uploadspeed = params[0];
            int remain = mfilesize;

            while(remain>0)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(uploadspeed);
                remain -= uploadspeed;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           // super.onProgressUpdate(values);
            mprogressbar.incrementProgressBy(values[0]);
            Log.i("testpro", "진행률: "+mprogressbar.getProgress());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           // super.onPostExecute(aVoid);
            mdialog.dismiss();
            Toast.makeText(getApplicationContext(), al.get(contextpos)+" Complete Uploading by "+mfilesize+"Kbyte",Toast.LENGTH_SHORT).show();
        }
    }

    ActionMode.Callback Action = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            mode.getMenuInflater().inflate(R.menu.contextmenu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()){
                case R.id.menu_delete:
                    al.remove(longclick);
                    adapter.notifyDataSetChanged();
                    mode.finish();
                    return true;

                case R.id.menu_upload:
                    contextpos =longclick;
                    new UploadTask(new DialogF(), FILESIZE).execute(UPLOADSPEED);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionmode = null;
        }
    };
}
