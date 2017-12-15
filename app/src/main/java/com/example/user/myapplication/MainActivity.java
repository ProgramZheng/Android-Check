package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText eID,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SigninActivity(this).execute();

        //取得eID和password的實體
        eID = (EditText) findViewById(R.id.eID);
        Password = (EditText) findViewById(R.id.Password);
        //取得clean的實體
        Button clean = (Button) findViewById(R.id.clean);
        //實做OnClean介面
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eID.setText("");
                Password.setText("");
            }
        });
        Button submit = (Button) findViewById(R.id.submit);

        /*鍵盤送出鍵的事件*/
        Password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String username = eID.getText().toString();
                    String password = Password.getText().toString();
                    new login(getApplicationContext(),eID,Password).execute(username,password);
                    return false;
                }
                return false;
            }
        });
            //實做OnClickListener界面
            //Login
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = eID.getText().toString();
                    String password = Password.getText().toString();
                    new login(getApplicationContext(),eID,Password).execute(username,password);
                }
            });
        }


    }
