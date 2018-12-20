package com.example.ns.pro_exchangerate;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Content extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<MainActivity.Info> alinfo;
   // DbOpenHelper_money db ;
    DbOpenHelper db1;

    public Content(ArrayList<MainActivity.Info> info, Activity activity) {   //생성자
        this.alinfo = info;
      //  db = new DbOpenHelper_money(activity);
        db1 = new DbOpenHelper(activity);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextVIew1;
        TextView mtextView2;
      //  TextView mTextVIew3;


        public MyViewHolder(View v){    //생성자
            super(v);
            mImageView = v.findViewById(R.id.iv);
            mTextVIew1 = v.findViewById(R.id.tvmemo);
            mtextView2 = v.findViewById(R.id.tvmoney);
          //  mTextVIew3 = v.findViewById(R.id.tvwon);
        }


    }

    @NonNull
    @Override  //행표시에사용되는 xml가져옴
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {

        View v = (View)LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerow, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v);
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(viewGroup.getContext(), "확인", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return vh;
    }

    @Override  //행에보여질 iv와tv설정
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.mImageView.setImageResource(alinfo.get(i).category);
        myViewHolder.mTextVIew1.setText(alinfo.get(i).memo);
        myViewHolder.mtextView2.setText(alinfo.get(i).money);

       // int num = Integer.parseInt(alinfo.get(i).money.toString());
       // myViewHolder.mTextVIew3.setText(db.getexchangmoney();
    }

    @Override  //recyclerview 행갯수리턴
    public int getItemCount() {
        return alinfo.size();
    }
}
