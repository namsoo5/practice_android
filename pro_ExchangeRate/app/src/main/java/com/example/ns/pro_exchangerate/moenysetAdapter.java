package com.example.ns.pro_exchangerate;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class moenysetAdapter extends ArrayAdapter {
    private  final ArrayList al1;
    private  final ArrayList al2;
    private  final Activity context;
    public moenysetAdapter(@NonNull Activity context, int resource, ArrayList al1, ArrayList al2) {
        super(context, resource);
        this.al1 = al1;
        this.al2 = al2;
        this.context = context;

    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return al1.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.moneylistview, null, false);
        TextView left = (TextView) rowView.findViewById(R.id.textViewleft);
        TextView right = (TextView) rowView.findViewById(R.id.textViewright);
        left.setText(al1.get(position).toString());
        right.setText(al2.get(position).toString());
        return rowView;
    }
}
