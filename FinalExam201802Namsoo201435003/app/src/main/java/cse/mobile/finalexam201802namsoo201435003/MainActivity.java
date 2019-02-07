package cse.mobile.finalexam201802namsoo201435003;

import android.app.Dialog;
import android.content.DialogInterface;
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
    static ArrayAdapter adapter;
    final int REQ_CODE_NEW=1;
    int listcount =0; //연락처번호증가 저장변수
    int listpos;  //클릭된리스트아이템을 수정하기위한 위치저장변수
    static int contextpos;  //클릭된 아이템을 삭제하기위한 위치저장변수
    final int CONTACTSIZE = 100;  //연락처사이즈
    final int SHARINGSPEED = 15;  //속도

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("arrayList",al);
        outState.putInt("listcount", listcount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        al = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(adapter);

        if(savedInstanceState != null){   //화면전환시 저장

            listcount = savedInstanceState.getInt("listcount");

            al = savedInstanceState.getStringArrayList("arrayList");
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
            lv.setAdapter(adapter);
        }


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listpos = position;
                Intent intent = new Intent(getApplicationContext(), ContactEditActivity.class);
                intent.putExtra("mainstring", al.get(position).toString());
                startActivityForResult(intent, REQ_CODE_NEW);
            }
        });

        registerForContextMenu(lv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.menu_new_Contact:
               al.add("Name"+listcount+": 010-000-0000");
               listcount++;
               adapter.notifyDataSetChanged();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.cmenu_main_lvcontacts, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.cmenu_delete:
                contextpos = info.position;
                DialogFragment dialog = new DeleteDialog();
                dialog.setCancelable(false);
                dialog.show(getSupportFragmentManager(), "deletedialog");
                return true;

            case R.id.cmenu_share:
                new ShareTask(new ShareDialog(), CONTACTSIZE).execute(SHARINGSPEED);
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    public static class DeleteDialog extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            //return super.onCreateDialog(savedInstanceState);
            final AlertDialog.Builder adbuilder = new AlertDialog.Builder(getActivity());
            adbuilder.setTitle("Delete Contact")
                    .setMessage("Are you sure to delete the contact ["+al.get(contextpos)+"]?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            al.remove(contextpos);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("NO", null);

            return adbuilder.create();
        }
    }


    public static class ShareDialog extends DialogFragment{

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            //return super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder adbuilder = new AlertDialog.Builder(getActivity());
            adbuilder.setTitle("Share Contact")
                    .setView(R.layout.dialog_sharecontactprogress);

            return adbuilder.create();
        }
    }

    private class ShareTask extends AsyncTask<Integer, Integer, Void>{

        DialogFragment mDialog;
        int mContactsize;
        ProgressBar mProgressbar;

        ShareTask(DialogFragment dialog, int contactsize){
            mDialog = dialog;
            mContactsize = contactsize;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mDialog.setCancelable(false);
            mDialog.show(getSupportFragmentManager(), "sharedialog");
            getSupportFragmentManager().executePendingTransactions();

            mProgressbar = mDialog.getDialog().findViewById(R.id.pbShareContact);
            mProgressbar.setMax(mContactsize);
            mProgressbar.setProgress(0);
        }

        @Override
        protected Void doInBackground(Integer... params) {
            int sharespeed = params[0];
            int remain = mContactsize;

            while (remain>0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                remain -= sharespeed;
                publishProgress(sharespeed);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           // super.onProgressUpdate(values);

            mProgressbar.incrementProgressBy(values[0]);
            Log.i("progress", "진행률 "+mProgressbar.getProgress());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            mDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Completed sharing contact", Toast.LENGTH_SHORT).show();
        }
    }















    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQ_CODE_NEW:
                if(resultCode==RESULT_OK){
                    al.set(listpos, data.getStringExtra("substring"));
                    adapter.notifyDataSetChanged();
                    break;
                }
            default :
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
