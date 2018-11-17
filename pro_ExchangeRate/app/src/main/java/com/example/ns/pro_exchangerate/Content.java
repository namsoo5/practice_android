package com.example.ns.pro_exchangerate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Content extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<MainActivity.Info> alinfo;

    public Content(ArrayList<MainActivity.Info> info) {   //생성자
        this.alinfo = info;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextVIew1;
        TextView mtextView2;
       // public TextView mTextVIew3;

        public MyViewHolder(View v){    //생성자
            super(v);
            mImageView = v.findViewById(R.id.iv);
            mTextVIew1 = v.findViewById(R.id.tvmemo);
            mtextView2 = v.findViewById(R.id.tvmoney);
           // mTextVIew3 = v.findViewById(R.id.tvwon);
        }
    }

    @NonNull
    @Override  //행표시에사용되는 xml가져옴
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = (View)LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerow, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override  //행에보여질 iv와tv설정
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.mImageView.setImageResource(alinfo.get(i).category);
        myViewHolder.mTextVIew1.setText(alinfo.get(i).memo);
        myViewHolder.mtextView2.setText(alinfo.get(i).money);
    }

    @Override  //recyclerview 행갯수리턴
    public int getItemCount() {
        return alinfo.size();
    }
}
