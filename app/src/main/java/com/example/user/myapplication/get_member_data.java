package com.example.user.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
    //flag 0 means get and 1 means post.(By default it is get.)
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

            String link="https://esz759486.000webhostapp.com/manage_check_in.php";
            String data  = URLEncoder.encode("select_member_id", "UTF-8") + "=" +
                    URLEncoder.encode(select_member_id, "UTF-8");
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
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
    }
}