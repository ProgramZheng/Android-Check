package com.example.user.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.LinearLayout;
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

public class get_member_data extends AsyncTask<String, Void, String> {
    private Context context;
    LinearLayout member_data_layout;
    //select_month 0 means get and 1 means post.(By default it is get.)
    public get_member_data(Context context, LinearLayout member_data_layout) {
        this.context = context;
        this.member_data_layout=member_data_layout;
    }
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String select_member_id = (String)arg0[0];
            String select_month = (String)arg0[1];

            String link="https://esz759486.000webhostapp.com/manage_check_in.php";
            String data  = URLEncoder.encode("select_member_id", "UTF-8") + "=" +
                    URLEncoder.encode(select_member_id, "UTF-8");
            data += "&" + URLEncoder.encode("select_month", "UTF-8") + "=" +
                    URLEncoder.encode(select_month, "UTF-8");
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
            JSONArray now_month_data = new JSONArray(jsonObject.getString("now_month_data"));



//            Toast.makeText(context,jsonObject.getString("now_month_data").length(),Toast.LENGTH_SHORT).show();
            if(now_month_data.length()>0) {
                member_data_layout.removeAllViews();
                for (int i = 0; i < now_month_data.length(); i++) {
                    String status_on = now_month_data.getJSONObject(i).getString("status_on");
                    String status_off = now_month_data.getJSONObject(i).getString("status_off");
                    String work_on_time = now_month_data.getJSONObject(i).getString("work_on_time");
                    String work_off_time = now_month_data.getJSONObject(i).getString("work_off_time");
                    String work_time = now_month_data.getJSONObject(i).getString("work_time");
                    member_data_layout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1
                    ));
                    LinearLayout data_layout = new LinearLayout(context);
                    data_layout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1
                    ));
//                /*上班打卡狀態*/
//                TextView status_on_textview = new TextView(context);
//                status_on_textview.setText(status_on);
//                status_on_textview.setGravity(Gravity.CENTER);
//                status_on_textview.setLayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                ));
//                /*下班打卡狀態*/
//                TextView status_off_textview = new TextView(context);
//                status_off_textview.setText(status_off);
//                status_off_textview.setGravity(Gravity.CENTER);
//                status_off_textview.setLayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                ));
                /*上班打卡時間*/
                    TextView work_on_time_textview = new TextView(context);
                    work_on_time_textview.setText(work_on_time);
                    work_on_time_textview.setGravity(Gravity.CENTER);
                    work_on_time_textview.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1
                    ));
                /*下班打卡時間*/
                    TextView work_off_time_textview = new TextView(context);
                    work_off_time_textview.setText(work_off_time);
                    work_off_time_textview.setGravity(Gravity.CENTER);
                    work_off_time_textview.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1
                    ));
                /*當日工作時數*/
                    TextView work_time_textview = new TextView(context);
                    work_time_textview.setText(work_time);
                    work_time_textview.setGravity(Gravity.CENTER);
                    work_time_textview.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1
                    ));
                    data_layout.addView(work_on_time_textview);
                    data_layout.addView(work_off_time_textview);
                    data_layout.addView(work_time_textview);
                    member_data_layout.addView(data_layout);
                }
            }
            else{
                member_data_layout.removeAllViews();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}