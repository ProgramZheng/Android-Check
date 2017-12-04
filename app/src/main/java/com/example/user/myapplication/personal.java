package com.example.user.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

public class personal extends Fragment {
    private TextView department,name;
    private TableLayout data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.personal, container, false);
        String get_id = this.getArguments().getString("id");
        department = (TextView) rootView.findViewById(R.id.department);
        name = (TextView) rootView.findViewById(R.id.name);
        data = (TableLayout) rootView.findViewById(R.id.data);
        new member(getActivity(),department,name,data).execute(get_id);
        return rootView;
    }
}

