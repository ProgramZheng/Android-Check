package com.example.user.myapplication;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class member extends AsyncTask<String, Void, String> {
    private Context context;
    private TextView department,name;
    private TableLayout data;

    //flag 0 means get and 1 means post.(By default it is get.)
    public member(Context context,TextView department,TextView name,TableLayout data) {
        this.context = context;
        this.department = department;
        this.name = name;
        this.data = data;
    }
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String id = (String)arg0[0];

            String link="https://esz759486.000webhostapp.com/member.php";
            String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                    URLEncoder.encode(id, "UTF-8");

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
            JSONObject member = new JSONObject(jsonObject.getString("member"));
            JSONObject department = new JSONObject(jsonObject.getString("department"));
            String department_name = department.getString("name");
            String name = member.getString("name");
            JSONArray now_month_data = new JSONArray(jsonObject.getString("now_month_data"));

            for (int i=0;i<now_month_data.length();i++) {
                String status_on = now_month_data.getJSONObject(i).getString("status_on");
                String status_off = now_month_data.getJSONObject(i).getString("status_off");
                String work_on_time = now_month_data.getJSONObject(i).getString("work_on_time");
                String work_off_time = now_month_data.getJSONObject(i).getString("work_off_time");
                String work_time = now_month_data.getJSONObject(i).getString("work_time");
                TableRow tr_head = new TableRow(context);
                tr_head.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1
                ));
//                /*上班打卡狀態*/
//                TextView status_on_textview = new TextView(context);
//                status_on_textview.setText(status_on);
//                status_on_textview.setGravity(Gravity.CENTER);
//                status_on_textview.setLayoutParams(new TableRow.LayoutParams(
//                        TableRow.LayoutParams.WRAP_CONTENT,
//                        TableRow.LayoutParams.WRAP_CONTENT
//                ));
//                /*下班打卡狀態*/
//                TextView status_off_textview = new TextView(context);
//                status_off_textview.setText(status_off);
//                status_off_textview.setGravity(Gravity.CENTER);
//                status_off_textview.setLayoutParams(new TableRow.LayoutParams(
//                        TableRow.LayoutParams.WRAP_CONTENT,
//                        TableRow.LayoutParams.WRAP_CONTENT
//                ));
                /*上班打卡時間*/
                TextView work_on_time_textview = new TextView(context);
                work_on_time_textview.setText(work_on_time);
                work_on_time_textview.setGravity(Gravity.CENTER);
                work_on_time_textview.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1
                ));
                /*下班打卡時間*/
                TextView work_off_time_textview = new TextView(context);
                work_off_time_textview.setText(work_off_time);
                work_off_time_textview.setGravity(Gravity.CENTER);
                work_off_time_textview.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1
                ));
                /*當日工作時數*/
                TextView work_time_textview = new TextView(context);
                work_time_textview.setText(work_time);
                work_time_textview.setGravity(Gravity.CENTER);
                work_time_textview.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1
                ));
//                tr_head.addView(status_on_textview);
//                tr_head.addView(status_off_textview);
                tr_head.addView(work_on_time_textview);
                tr_head.addView(work_off_time_textview);
                tr_head.addView(work_time_textview);
                data.addView(tr_head);
            }
            this.department.setText(department_name);
            this.name.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}