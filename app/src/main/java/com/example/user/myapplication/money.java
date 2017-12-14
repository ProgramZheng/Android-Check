package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class money extends AppCompatActivity {
    private TextView month_time,time_money,labor_money,health_money,tax,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money);
        Intent intent = this.getIntent();
        String get_id = intent.getStringExtra("id");
        month_time = (TextView) findViewById(R.id.month_time);
        time_money = (TextView) findViewById(R.id.time_money);
        labor_money = (TextView) findViewById(R.id.labor_money);
        health_money = (TextView) findViewById(R.id.health_money);
        tax = (TextView) findViewById(R.id.tax);
        total = (TextView) findViewById(R.id.total);
        new get_money(getApplicationContext(),month_time,time_money,labor_money,health_money,tax,total).execute(get_id);

    }
}
