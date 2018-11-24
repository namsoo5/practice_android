package com.example.ns.report7_listview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class clayoutAdapter extends ArrayAdapter {
    private final ArrayList al1;
    private final ArrayList al2;
    private final Activity context;
    public clayoutAdapter(Activity context,int resource, ArrayList al1, ArrayList al2){
        super(context, resource);
        this.al1 = al1;
        this.al2 = al2;
        this.context = context;
    }
    @Override
    public int getCount() {
        return al1.size();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

// LayoutInflater inflater = context.getLayoutInflater();
// View rowView = inflater.inflate(R.layout.clayout2, null, false);
        View rowView = View.inflate(context, R.layout.clayout2, null);

        TextView left = rowView.findViewById(R.id.tv_c2_1);
        TextView right = rowView.findViewById(R.id.tv_c2_2);
        left.setText(al1.get(position).toString());
        right.setText(al2.get(position).toString());
        return rowView;
    }
}
