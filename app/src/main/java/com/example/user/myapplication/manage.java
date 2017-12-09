package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class manage extends Fragment {
    private Spinner personal_data,spinner_month;
    private LinearLayout head_layout,member_data_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage, container, false);
        String get_id = this.getArguments().getString("id");
        personal_data = (Spinner) rootView.findViewById(R.id.personal_data);
        spinner_month = (Spinner) rootView.findViewById(R.id.spinner_month);
        head_layout = (LinearLayout) rootView.findViewById(R.id.head_layout);
        member_data_layout = (LinearLayout) rootView.findViewById(R.id.member_data_layout);
        new get_member(getActivity(),spinner_month,personal_data,head_layout,member_data_layout).execute(get_id);
        return rootView;
    }
}
