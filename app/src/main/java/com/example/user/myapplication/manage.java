package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class manage extends Fragment {
    private Spinner personal_data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage, container, false);
        String get_id = this.getArguments().getString("id");
        personal_data = (Spinner) rootView.findViewById(R.id.personal_data);
        new get_member(getActivity(),personal_data).execute(get_id);
        return rootView;
    }
}
