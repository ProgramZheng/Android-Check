package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class manage extends Fragment {
    private Spinner personal_data,spinner_month;
    private TableLayout data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage, container, false);
        final String get_id = this.getArguments().getString("id");
        personal_data = (Spinner) rootView.findViewById(R.id.personal_data);
        spinner_month = (Spinner) rootView.findViewById(R.id.spinner_month);
        data = (TableLayout) rootView.findViewById(R.id.data);
        new get_member(getActivity(),spinner_month,personal_data,data).execute(get_id);
        return rootView;
    }
}
