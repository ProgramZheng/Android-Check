package com.example.user.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class personal extends Fragment {
    private TextView name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.personal, container, false);
        String get_name = this.getArguments().getString("name");
        name = (TextView) rootView.findViewById(R.id.name);
        name.setText(get_name);
        return rootView;
    }
}

