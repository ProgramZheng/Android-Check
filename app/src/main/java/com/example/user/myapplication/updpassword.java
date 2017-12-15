package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updpassword extends AppCompatActivity {
    String id;
    int intent_permissions;
    private EditText old_password,new_password,new_password_check;
    private Button submit_updpassword,clean_updpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updpassword);
        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        intent_permissions = intent.getIntExtra("permissions",0);
        //取得eID和password的實體
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        new_password_check = (EditText) findViewById(R.id.new_password_check);
        submit_updpassword = (Button) findViewById(R.id.submit_updpassword);
        clean_updpassword = (Button) findViewById(R.id.clean_updpassword);
        //清除EditText
        clean_updpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_password.setText("");
                new_password.setText("");
                new_password_check.setText("");
            }
        });
        //實做OnClickListener界面
        //修改密碼
        /*鍵盤送出鍵的事件*/
        new_password_check.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String get_old_password = old_password.getText().toString();
                    String get_new_password = new_password.getText().toString();
                    String get_new_password_check = new_password_check.getText().toString();
                    new send_updpassword(getApplicationContext()).execute(id,get_old_password,get_new_password,get_new_password_check);
                    old_password.setText("");
                    new_password.setText("");
                    new_password_check.setText("");
                    return false;
                }
                return false;
            }
        });

        submit_updpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_old_password = old_password.getText().toString();
                String get_new_password = new_password.getText().toString();
                String get_new_password_check = new_password_check.getText().toString();
                new send_updpassword(getApplicationContext()).execute(id,get_old_password,get_new_password,get_new_password_check);
                old_password.setText("");
                new_password.setText("");
                new_password_check.setText("");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*menu.add 參數1:群組id, 參數2:itemId, 參數3:item順序, 參數4:item名稱*/
        if(intent_permissions>0) {
            menu.add(0, 0, 0, "註冊員工");
        }
        menu.add(0,1,1,"修改密碼");
        //將menu添加到操作欄
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 處理點擊操作欄發生的事件
        int select_id = item.getItemId();
        if (select_id == 0) {
            //初始化Intent物件，並將主畫面變成register
            Intent intent = new Intent();
            intent.setClass(this, register.class);
            intent.putExtra("id",id);
            intent.putExtra("permissions",intent_permissions);
            //開啟Activity
            startActivity(intent);
        }
        if (select_id == 1) {
            //初始化Intent物件，並將主畫面變成register
            Intent intent = new Intent();
            intent.setClass(this, updpassword.class);
            intent.putExtra("id",id);
            intent.putExtra("permissions",intent_permissions);
            //開啟Activity
            startActivity(intent);
        }
        if (select_id == R.id.log_out){
            //初始化Intent物件
            Intent intent = new Intent();
            //從Main2Activity 到MainActivity
            intent.setClass(this,MainActivity.class);
            //開啟Activity
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}