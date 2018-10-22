package com.example.ns.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class imageAdapter extends ArrayAdapter<String> {
    imageAdapter(Context context, String[] items){
        super(context, R.layout.image_layout, items);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater imagesinflater = LayoutInflater.from(getContext());
        View view = imagesinflater.inflate(R.layout.image_layout, parent, false);
        String item = getItem(position);
        TextView textView = (TextView)view.findViewById(R.id.textView);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        textView.setText(item);
        imageView.setImageResource(R.mipmap.ryon);
        return view;
    }
}
