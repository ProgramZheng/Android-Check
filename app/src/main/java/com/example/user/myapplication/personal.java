package com.example.user.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class personal extends Fragment {
    private TextView department,name;
    private Button go_money;
    private TableLayout data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.personal, container, false);
        final String get_id = this.getArguments().getString("id");
        department = (TextView) rootView.findViewById(R.id.department);
        name = (TextView) rootView.findViewById(R.id.name);
        go_money = (Button) rootView.findViewById(R.id.go_money);
        go_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化Intent物件，並將主畫面變成register
                Intent intent = new Intent(getActivity(), money.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",get_id);
                //開啟Activity
                getActivity().startActivity(intent);
            }
        });
        data = (TableLayout) rootView.findViewById(R.id.data);
        new member(getActivity(),department,name,data).execute(get_id);
        return rootView;
    }
}

