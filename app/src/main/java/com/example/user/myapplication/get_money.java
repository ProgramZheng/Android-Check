package com.example.user.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class get_money extends AsyncTask<String, Void, String> {
    private Context context;
    private TextView month_time,time_money,labor_money,health_money,tax,total;

    //flag 0 means get and 1 means post.(By default it is get.)
    public get_money(Context context,TextView month_time,TextView time_money,TextView labor_money, TextView health_money, TextView tax,TextView total) {
        this.context = context;
        this.month_time = month_time;
        this.time_money = time_money;
        this.labor_money = labor_money;
        this.health_money = health_money;
        this.tax = tax;
        this.total = total;
    }
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
            try{
                String id = (String)arg0[0];

                String link="https://esz759486.000webhostapp.com/money.php";
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
            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
            month_time.setText(jsonObject.getString("month_time"));
            time_money.setText(jsonObject.getString("time_money"));
            labor_money.setText(jsonObject.getString("labor_money"));
            health_money.setText(jsonObject.getString("health_money"));
            tax.setText(jsonObject.getString("tax"));
            total.setText(jsonObject.getString("total"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}