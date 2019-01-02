package com.example.ns.pro_boost1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment6 extends Fragment {
    DrawerActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (DrawerActivity)context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment6, container, false);

        Button bt = rootView.findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.ExecuteMain(-1);
            }
        });

        return rootView;
    }
}
