package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Button;
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
    int check_work;
    Button btn;

    //flag 0 means get and 1 means post.(By default it is get.)
    public check_in(Context context,Button btn) {
        this.context = context;
        this.btn = btn;
    }
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String member_id = (String)arg0[0];
            String flag = (String)arg0[1];

            String link="https://esz759486.000webhostapp.com/check_in.php";
            String data  = URLEncoder.encode("member_id", "UTF-8") + "=" +
                    URLEncoder.encode(member_id, "UTF-8");
            data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                    URLEncoder.encode(flag, "UTF-8");

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
            check_work = jsonObject.getInt("check_work");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(check_work==1) {
            btn.setEnabled(false);
            btn.setText("今日已打卡完畢");
        }
    }
}