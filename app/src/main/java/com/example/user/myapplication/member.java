package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private TextView name;
    private TableLayout data;

    //flag 0 means get and 1 means post.(By default it is get.)
    public member(Context context,TextView name,TableLayout data ) {
        this.context = context;
        this.name = name;
        this.data = data;
    }
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
            try{
                String id = (String)arg0[0];

                String link="http://10.0.2.2/check_in/member.php";
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
            String name = member.getString("name");
            JSONArray now_month_data = new JSONArray(jsonObject.getString("now_month_data"));
            TableRow tr_head[] = new TableRow[now_month_data.length()];
            TextView[] textArray = new TextView[now_month_data.length()];
            TextView[] work_on_time_array = new TextView[now_month_data.length()];
            HashMap<String, String> map = new HashMap<String,String>();
            for (int i=0;i<now_month_data.length();i++) {
                String status_on = now_month_data.getJSONObject(i).getString("status_on");
                String status_off = now_month_data.getJSONObject(i).getString("status_off");
                String work_on_time = now_month_data.getJSONObject(i).getString("work_on_time");
                String work_off_time = now_month_data.getJSONObject(i).getString("work_off_time");
                String work_time = now_month_data.getJSONObject(i).getString("work_time");
                tr_head[i] = new TableRow(context);
                tr_head[i].setBackgroundColor(Color.GRAY);        // part1
                tr_head[i].setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                textArray[i] = new TextView(context);
                textArray[i].setText(status_on);
                textArray[i].setTextColor(Color.WHITE);

                work_on_time_array[i] = new TextView(context);
                work_on_time_array[i].getAutoSizeTextType();
                work_on_time_array[i].setText(work_on_time);
                work_on_time_array[i].setTextColor(Color.WHITE);
                tr_head[i].addView(textArray[i]);
                tr_head[i].addView(work_on_time_array[i]);
                data.addView(tr_head[i], new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                Toast.makeText(context,now_month_data.getJSONObject(i).getString("work_time"),Toast.LENGTH_SHORT).show();
            }


            Toast.makeText(context,name,Toast.LENGTH_SHORT).show();
            this.name.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}