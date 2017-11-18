package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

            //實做OnClickListener界面
            //Login
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = eID.getText().toString();
                    String password = Password.getText().toString();
                    new login(getApplicationContext()).execute(username,password);
//                    Toast toast=Toast.makeText(getApplicationContext(), "登入失敗，請檢查帳號密碼是否有誤", Toast.LENGTH_SHORT);
//                    for (int i = 0;i<ID.length; i++) {
//                        if (eID.getText().toString().equals(ID[i])&&Password.getText().toString().equals(Pass[i])) {
//                                toast.cancel();
//                                //初始化Intent物件，並將主畫面變成choose
//                                Intent intent = new Intent(MainActivity.this, choose.class);
//                                //開啟Activity
//                                startActivity(intent);
//                            } else {
//                                toast.show();
//                            }
//                        }
                }
            });
        }


    }
