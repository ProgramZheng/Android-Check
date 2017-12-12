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
    private Button go_register;
    private Spinner personal_data,spinner_month;
    private TableLayout data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage, container, false);
        final String get_id = this.getArguments().getString("id");
        go_register =(Button) rootView.findViewById(R.id.go_register);
        personal_data = (Spinner) rootView.findViewById(R.id.personal_data);
        spinner_month = (Spinner) rootView.findViewById(R.id.spinner_month);
        data = (TableLayout) rootView.findViewById(R.id.data);
        new get_member(getActivity(),spinner_month,personal_data,data).execute(get_id);
        //實做OnClickListener界面
        //註冊員工
        go_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化Intent物件，並將主畫面變成register
                Intent intent = new Intent(getActivity(), register.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",get_id);
                //開啟Activity
                getActivity().startActivity(intent);
            }
        });
        return rootView;
    }
}
