package com.example.ns.pro_boost1;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListView_review extends ArrayAdapter {

    private  ArrayList al;
    private Context context;
    public ListView_review(Context context, int resource, ArrayList al){
        super(context, resource);
        this.al = al;
        this.context = context;
    }
    @Override
    public int getCount() {
        return al.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = View.inflate(context, R.layout.listview_review, null);

        TextView note = rowView.findViewById(R.id.tv_listreview_note);
        note.setText(al.get(position).toString());

        return rowView;
    }
}
