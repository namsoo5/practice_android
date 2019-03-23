package com.example.ns.prac_recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.ViewHolder>{
    Context context;
    SingerItem item;
    ArrayList<SingerItem> items = new ArrayList<SingerItem>();  //데이터
    OnItemClickListener listener;

    public static interface OnItemClickListener{  //interface정의
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public SingerAdapter (Context context){
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.singer_item, viewGroup, false);
        //layout 디자인 적용
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        item = items.get(i); //position
        viewHolder.setItem(item);  //데이터 세팅

        viewHolder.setOnItemClickListener(listener);  //사용자정의 이벤트처리함수
    }

    public void addItem(SingerItem item){   //데이터추가
        items.add(item);
    }

    public void addItems(ArrayList<SingerItem> items){  //데이터추가
        this.items = items;
    }

    public SingerItem getItem(int position){   //해당아이템반환
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }




    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {  //디자인한 뷰가 들어옴
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {  //이벤트처리
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });

        }

        public void setItem(SingerItem item){
            textView.setText(item.getName());
            textView2.setText(item.getMobile());
        }

        public void setOnItemClickListener(OnItemClickListener listener){  //사용자정의 이벤트처리함수
            this.listener= listener;
        }


    }

}
