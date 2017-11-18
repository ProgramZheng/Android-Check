package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class check_in extends AsyncTask<String, Void, String> {
    private Context context;
    String id,name;
    boolean status;

    //flag 0 means get and 1 means post.(By default it is get.)
    public check_in(Context context,int flag) {
        this.context = context;
    }
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];

                String link="http://10.0.2.2/check_in/login.php";
                String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
    }

    @Override
    protected void onPostExecute(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject member = jsonObject.getJSONObject("member");
            name = member.getString("name");
            status = jsonObject.getBoolean("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //status==true代表有此帳號密碼
        if(status) {
            //初始化Intent物件，並將主畫面變成choose
            Intent intent = new Intent(context, Main2Activity.class);
            intent.putExtra("name",name);
            //開啟Activity
            context.startActivity(intent);
        }
        else{
            Toast.makeText(context ,"登入失敗", Toast.LENGTH_SHORT).show();
        }
    }
}