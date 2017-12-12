package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class register extends AppCompatActivity {
    String id;
    private EditText register_email,register_name;
    private Button submit_register,clean_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        //取得eID和password的實體
        register_email = (EditText) findViewById(R.id.register_email);
        register_name = (EditText) findViewById(R.id.register_name);
        submit_register = (Button) findViewById(R.id.submit_register);
        clean_register = (Button) findViewById(R.id.clean_register);
        //清除EditText
        clean_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_email.setText("");
                register_name.setText("");
            }
        });
        //實做OnClickListener界面
        //註冊會員
        submit_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = register_email.getText().toString();
                String name = register_name.getText().toString();
                new register_member(getApplicationContext()).execute(id,email,name);
                register_email.setText("");
                register_name.setText("");
            }
        });
    }
}