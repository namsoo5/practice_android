package com.example.ns.pro_boost1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ViewHolder> {

    Context context;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<Boolean> photo = new ArrayList<Boolean>();

    public ThumbnailAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recyclerview_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setContext(context);
        viewHolder.setAdapter(items, photo);
        if(photo.get(i)) {
            viewHolder.setItem(items.get(i));  // 레이아웃에 데이터 적용
        }else
            viewHolder.setMovieItem(); //영화인경우 썸네일이미지
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addPhotoItem(String item){
        items.add(item);
        photo.add(true);
    }

    public void addMovieItem(String item){
        items.add(item);
        photo.add(false);
    }


    public String getItem(int position){
        return items.get(position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        Context context;
        ArrayList<Boolean> photo = new ArrayList<Boolean>();
        ArrayList<String> items = new ArrayList<String>();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recyclerView_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(photo.get(position)){  //사진보기
                        Intent intent = new Intent(context, PhotoView_Activity.class);
                        intent.putExtra("url", items.get(position));
                        context.startActivity(intent);
                    }else{//영상재생
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(items.get(position)));
                        //해당 주소로 이동
                        context.startActivity(intent);

                    }
                }
            });
        }

        public void setItem(String item){

            Glide.with(context)
                    .load(item)
                    .into(imageView);
        }

        public void setMovieItem(){

            Glide.with(context)
                    .load(R.drawable.ic_play_32)
                    .into(imageView);
        }

        public void setContext(Context context){
            this.context=context;
        }

        public void setAdapter(ArrayList<String> items, ArrayList<Boolean> photo){
            this.photo = photo;
            this.items = items;
        }
    }
}
