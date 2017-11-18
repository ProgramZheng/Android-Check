package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Button system1 = (Button)findViewById(R.id.system1);

        system1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化Intent物件，並將主畫面變成Main2Activity
                Intent intent = new Intent(choose.this, Main2Activity.class);
                //開啟Activity
                startActivity(intent);
            }
        });
    }
}
